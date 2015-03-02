(ns portal-clj.models.migration
  (:require [clojure.java.jdbc :as sql]
            [portal-clj.db :as db]
            [portal-clj.models.post :as post]))

(defn do-migrate []
  (print "Creating database scaffold... ") (flush)
  (sql/db-do-commands db/spec
                      (sql/create-table-ddl
                       :posts
                       [:id "int(11)" "NOT NULL AUTO_INCREMENT PRIMARY KEY"]
                       [:title "varchar(255)" "NOT NULL"]
                       [:body :text]
                       [:created_at :timestamp
                        "NOT NULL DEFAULT CURRENT_TIMESTAMP"]))
  (println "Successfully created DB entries."))

(defn migrated? []
  (pos? (count (sql/query db/spec
                          [(str "select * from information_schema.tables "
                                "where table_name='posts'")]))))

(defn migrate []
  (if (not (migrated?))
    (do-migrate)
    (println "Migrations already up to date.")))
