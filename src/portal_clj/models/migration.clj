(ns portal-clj.models.migration
  (:require [clojure.java.jdbc :as sql]
            [portal-clj.db :as db]))

(defn post-migration []
  (sql/execute! db/spec
                ["CREATE TABLE IF NOT EXISTS posts (
                   id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
                   title VARCHAR(255) NOT NULL,
                   body TEXT,
                   created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
                 )"]))


(defn page-migration []
  (sql/execute! db/spec
                ["CREATE TABLE IF NOT EXISTS pages (
                   id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
                   title VARCHAR(255) NOT NULL,
                   body TEXT,
                   created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
                 )"]))

(defn do-migrate []
  (do
    (println "Creating database scaffold... ")
    (post-migration)
    (page-migration)
    (println "Successfully created DB entries.")))

(defn migrated? [table-name]
  (pos? (count (sql/query db/spec
                          [(str "select * from information_schema.tables "
                                "where table_name='" table-name "'")]))))

(defn all-migrated? []
  (and
   (migrated? "posts")
   (migrated? "pages")))

(defn migrate []
  (if (all-migrated?)
    (println "Migrations already up to date.")
    (do-migrate)))
