(ns portal-clj.models.user
  (:require [clojure.java.jdbc :as sql]
            [portal-clj.db :as db]))

(defn all []
  (into [] (sql/query db/spec ["select * from users order by username desc"])))

(defn create! [user]
  (sql/insert! db/spec :users user))

(defn retrieve [id]
  (sql/query db/spec ["select * from users where id = ?" id]))

(defn get-by-email [email]
  (sql/query db/spec ["select * from users where email = ?" email]))

(defn update! [id updated]
  (sql/update! db/spec :users updated ["id = ?" id]))

(defn delete! [id]
  (sql/delete! db/spec :users ["id = ?" id]))

(defn is-admin? [id]
  (-> (retrieve id)
      first
      (get :admin)
      (= true)))
