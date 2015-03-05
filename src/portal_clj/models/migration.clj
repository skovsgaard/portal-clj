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
                   password VARBINARY(256),
                   admin BOOLEAN NOT NULL DEFAULT 0,
                   comments BOOLEAN NOT NULL,
                   signup_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                   active BOOLEAN NOT NULL DEFAULT 1
                 )"]))

(defn page-migration []
  (sql/execute! db/spec
                ["CREATE TABLE IF NOT EXISTS pages (
                   id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
                   title VARCHAR(256) NOT NULL,
                   body TEXT,
                   user_id INT(11) NOT NULL,
                   created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                   slug VARCHAR(256) NOT NULL,
                   active BOOLEAN NOT NULL DEFAULT 1,
                   FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
                 )"]))

(defn post-migration []
  (sql/execute! db/spec
                ["CREATE TABLE IF NOT EXISTS posts (
                   id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
                   title VARCHAR(256) NOT NULL,
                   body TEXT,
                   user_id INT(11) NOT NULL,
                   commentable BOOLEAN NOT NULL DEFAULT 0,
                   created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                   slug VARCHAR(256) NOT NULL,
                   active BOOLEAN NOT NULL DEFAULT 1,
                   FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
                 )"]))

(defn author-migration []
  (sql/execute! db/spec
                ["CREATE TABLE IF NOT EXISTS comment_authors (
                   id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
                   name VARCHAR(256) NOT NULL,
                   email VARCHAR(512),
                   website VARCHAR(512),
                   user_id INT(11) NOT NULL,
                   FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
                 )"]))

(defn comment-migration []
  (sql/execute! db/spec
                ["CREATE TABLE IF NOT EXISTS comments (
                   id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
                   title VARCHAR(128),
                   body TEXT,
                   author_id INT(11) NOT NULL,
                   post_id INT(11) NOT NULL,
                   created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
                 )"]))

(defn tag-migration []
  (sql/execute! db/spec
                ["CREATE TABLE IF NOT EXISTS tags (
                   id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
                   name VARCHAR(256)
                 )"]))

(defn post-tag-migration []
  (sql/execute! db/spec
                ["CREATE TABLE IF NOT EXISTS post_tags (
                   id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
                   post_id INT(11) NOT NULL,
                   tag_id INT(11) NOT NULL,
                   FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE CASCADE,
                   FOREIGN KEY (tag_id) REFERENCES tags(id) ON DELETE CASCADE
                 )"]))

(defn do-migrate []
  (do
    (println "Creating database scaffold... ")
    (user-migration)
    (page-migration)
    (post-migration)
    (author-migration)
    (comment-migration)
    (tag-migration)
    (post-tag-migration)
    (println "Successfully created DB entries.")))

(defn down [table-name]
  (sql/execute! db/spec
                [(str "DROP TABLE IF EXISTS " table-name)]))

(defn nuke-all []
  (do
    (down "post_tags")
    (down "tags")
    (down "comments")
    (down "comment_authors")
    (down "pages")
    (down "posts")
    (down "users")
    (println "Destroyed all tables.")))

(defn migrated? [table-name]
  (pos? (count (sql/query db/spec
                          [(str "select * from information_schema.tables "
                                "where table_name='" table-name "'")]))))

(defn -main [& args]
  (if (= (first args) "up")
    (do-migrate))
  (if (= (first args) "down")
    (nuke-all)))
