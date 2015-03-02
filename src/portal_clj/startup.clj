(ns portal-clj.startup
  (:require [portal-clj.models.migration :as migration]))

(defn -main [] (migration/migrate))
