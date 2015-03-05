(ns portal-clj.models.comment-author
  (:require [clojure.java.jdbc :as sql]
            [portal-clj.db :as db]))

(defn all []
  (into [] (sql/query db/spec ["select * from post_authors order by id username"])))

(defn create! [user]
  (sql/insert! db/spec :post_authors user))

(defn get [id]
  (sql/query db/spec ["select * from post_authors where id = ?" id]))

(defn update! [id updated]
  (sql/update! db/spec :post_authors updated ["id = ?" id]))

(defn delete! [id]
  (sql/delete! db/spec :post_authors ["id = ?" id]))
