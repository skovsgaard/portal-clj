(ns portal-clj.models.migration
  (:require [clojure.java.jdbc :as sql]
            [portal-clj.db :as db]))

(defn user-migration []
  (sql/execute! db/spec
                ["CREATE TABLE IF NOT EXISTS users (
                   id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
                   firstname VARCHAR(128) NOT NULL,
                   lastname VARCHAR(128),
                   username VARCHAR(64) NOT NULL,
                   email VARCHAR(512) NOT NULL,
                   password VARBINARY(255),
                   admin BOOLEAN NOT NULL DEFAULT 0,
                   comments BOOLEAN NOT NULL,
                   signup_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                   active BOOLEAN NOT NULL DEFAULT 1
                 )"]))

(defn page-migration []
  (sql/execute! db/spec
                ["CREATE TABLE IF NOT EXISTS pages (
                   id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
                   title VARCHAR(255) NOT NULL,
                   body TEXT,
                   user_id INT(11) NOT NULL,
                   created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                   slug VARCHAR(255) NOT NULL,
                   active BOOLEAN NOT NULL DEFAULT 1,
                   FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
                 )"]))

(defn post-migration []
  (sql/execute! db/spec
                ["CREATE TABLE IF NOT EXISTS posts (
                   id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
                   title VARCHAR(255) NOT NULL,
                   body TEXT,
                   user_id INT(11) NOT NULL,
                   commentable BOOLEAN NOT NULL DEFAULT 0,
                   created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                   slug VARCHAR(255) NOT NULL,
                   active BOOLEAN NOT NULL DEFAULT 1,
                   FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
                 )"]))

(defn do-migrate []
  (do
    (println "Creating database scaffold... ")
    (user-migration)
    (page-migration)
    (post-migration)
    (println "Successfully created DB entries.")))

(defn migrated? [table-name]
  (pos? (count (sql/query db/spec
                          [(str "select * from information_schema.tables "
                                "where table_name='" table-name "'")]))))

(defn all-migrated? []
  (and
   (migrated? "users")
   (migrated? "posts")
   (migrated? "pages")))

(defn -main []
  ;(if (all-migrated?)
  ;  (println "Migrations already up to date.")
  (do-migrate))
