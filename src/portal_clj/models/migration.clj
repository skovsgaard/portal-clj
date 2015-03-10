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
                   image VARCHAR(128),
                   active BOOLEAN NOT NULL DEFAULT 1,
                   restricted BOOLEAN NOT NULL DEFAULT 0,
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
                   image VARCHAR(128),
                   active BOOLEAN NOT NULL DEFAULT 1,
                   restricted BOOLEAN NOT NULL DEFAULT 0,
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
                   name VARCHAR(256),
                   restricted BOOLEAN NOT NULL DEFAULT 0
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

(defn do-dummy []
  (do
    (println "Running dummy inserts.")
    (sql/insert! db/spec :users {:firstname "Some"
                                 :lastname "Admin"
                                 :username "dummy_admin"
                                 :email "some@admin.com"
                                 :admin 1
                                 :comments 1
                                 :active 1})
    (sql/insert! db/spec :users {:firstname "Some"
                                 :lastname "Guy"
                                 :username "dummy_user"
                                 :email "some@guy.com"
                                 :admin 0
                                 :comments 1
                                 :active 1})
    (sql/insert! db/spec :pages {:title "Dummy Page"
                                 :body "This here is some dummy text."
                                 :user_id 1
                                 :slug "dummy-page"
                                 :active 1
                                 :restricted 0})
    (sql/insert! db/spec :posts {:title "Dummy Post"
                                 :body "I'm a dummy update for testing purposes."
                                 :user_id 2
                                 :commentable 1
                                 :slug "dummy-post"
                                 :active 1
                                 :restricted 0})
    (sql/insert! db/spec :posts {:title "Also Dummy Post"
                                 :body "I too am a dummy update for testing purposes."
                                 :user_id 1
                                 :commentable 1
                                 :slug "also-dummy-post"
                                 :active 1
                                 :restricted 0})
    (sql/insert! db/spec :comment_authors {:name "Some Admin" :user_id 1})
    (sql/insert! db/spec :comments {:title "Heyoo"
                                    :body "I'm a dummy comment!"
                                    :author_id 1
                                    :post_id 1})
    (sql/insert! db/spec :tags {:name "dummy" :restricted 0})
    (sql/insert! db/spec :post_tags {:post_id 1 :tag_id 1})
    (println "Successfully added dummy content.")))


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
  (when (= (first args) "up")
    (do-migrate))
  (when (= (first args) "down")
    (nuke-all))
  (when (= (first args) "dummy")
    (do-dummy)))
