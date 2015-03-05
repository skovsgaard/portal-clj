(ns portal-clj.models.page
  (:require [clojure.java.jdbc :as sql]
            [portal-clj.db :as db]))

(defn all []
  (into [] (sql/query db/spec ["select * from pages order by id title"])))

(defn create! [page]
  (sql/insert! db/spec :pages page))

(defn get [id]
  (sql/query db/spec ["select * from :pages where id = ?" id]))

(defn update! [id updated]
  (sql/update! db/spec :pages updated ["id = ?" id]))

(defn delete! [id]
  (sql/delete! db/spec :pages ["id = ?" id]))
