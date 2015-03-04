(ns portal-clj.models.user
  (:require [clojure.java.jdbc :as sql]
            [portal-clj.db :as db]))

(defn all []
  (into [] (sql/query db/spec ["select * from users order by id username"])))

(defn create [user]
  (sql/insert! db/spec :users user))

(defn get [id]
  (sql/query db/spec ["select * from users where id = ?" id]))

(defn update! [id updated]
  (sql/update! db/spec :users updated ["id = ?" id]))

(defn delete! [id]
  (sql/delete! db/spec :pages ["id = ?" id]))
