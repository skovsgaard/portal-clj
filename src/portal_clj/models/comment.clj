(ns portal-clj.models.comment
  (:require [clojure.java.jdbc :as sql]
            [portal-clj.db :as db]))

(defn all []
  (into [] (sql/query db/spec ["select * from comments order by id"])))

(defn create! [comment-entry]
  (sql/insert! db/spec :comments comment-entry))

(defn retrieve [id]
  (sql/query db/spec ["select * from users where id = ?" id]))

(defn update! [id updated]
  (sql/update! db/spec :comments updated ["id = ?" id]))

(defn delete! [id]
  (sql/delete! db/spec :comments ["id = ?" id]))
