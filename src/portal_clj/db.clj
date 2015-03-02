(ns portal-clj.db
  (:require [clojure.java.jdbc :as sql]))

(def spec (or (System/getenv "DATABASE_URL")
              "mysql://compojure:clojure@localhost:3306/portal_clj"))
