(ns portal-clj.models.migration
  (:require [clojure.java.jdbc :as sql]
            [portal-clj.db :as db]
            [portal-clj.models.post :as post]))

(defn migrated? []
  (-> (sql/query db/spec
                 [(str "select count(*) from information_schema.table "
                       "where table_name = 'posts'")])
      first :count pos?))

(defn migrate []
  (when (not (migrated?))
    (print "Creating database scaffold...") (flush)
    (sql/db-do-commands db/spec
                        (sql/create-table-ddl
                         :posts
                         [:id :serial "primary key"]
                         [:title :varchar "not null"]
                         [:body :varchar]
                         [:created_at :timestamp
                          "not null" "default current_timestamp"]))
    (println "Successfully created DB entries.")))
