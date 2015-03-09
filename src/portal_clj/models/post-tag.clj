(ns portal-clj.models.post-tag
  (:require [clojure.java.jdbc :as sql]
            [portal-clj.db :as db]))

(defn all []
  (into [] (sql/query db/spec ["select * from post_tags order by id username"])))

(defn create! [user]
  (sql/insert! db/spec :post_tags user))

(defn retrieve [id]
  (sql/query db/spec ["select * from post_tags where id = ?" id]))

(defn update! [id updated]
  (sql/update! db/spec :post_tags updated ["id = ?" id]))

(defn delete! [id]
  (sql/delete! db/spec :post_tags ["id = ?" id]))
