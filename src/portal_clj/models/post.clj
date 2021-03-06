(ns portal-clj.models.post
  (:require [clojure.java.jdbc :as sql]
            [portal-clj.db :as db]
            [portal-clj.models.tag :as tag]))

(defn all []
  (into [] (sql/query db/spec ["select * from posts order by created_at desc"])))

(defn create! [post]
  (sql/insert! db/spec :posts post))

(defn retrieve [id]
  (sql/query db/spec ["select * from posts where id = ?" id]))

(defn update! [id updated]
  (sql/update! db/spec :posts updated ["id = ?" id]))

(defn delete [id]
  (sql/delete! db/spec :posts ["id = ?" id]))

(defn all-unrestricted []
  (into [] (sql/query db/spec ["select * from posts
                               where restricted = 0
                               order by created_at desc"])))
