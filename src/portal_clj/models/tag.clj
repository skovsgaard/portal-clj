(ns portal-clj.models.tag
  (:require [clojure.java.jdbc :as sql]
            [portal-clj.db :as db]))

(defn all []
  (into [] (sql/query db/spec ["select * from tags order by id"])))

(defn create! [user]
  (sql/insert! db/spec :tags user))

(defn retrieve [id]
  (sql/query db/spec ["select * from tags where id = ?" id]))

(defn update! [id updated]
  (sql/update! db/spec :tags updated ["id = ?" id]))

(defn delete! [id]
  (sql/delete! db/spec :tags ["id = ?" id]))
