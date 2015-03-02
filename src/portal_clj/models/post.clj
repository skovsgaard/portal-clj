(ns portal-clj.models.post
  (:require [clojure.java.jdbc :as sql]
            [portal-clj.db :as db]))

(defn all []
  (into [] (sql/query db/spec ["select * from posts order by id title"])))

(defn create [post]
  (sql/insert! db/spec :posts post))

