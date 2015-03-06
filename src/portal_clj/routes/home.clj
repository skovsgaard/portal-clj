(ns portal-clj.routes.home
  (:require [compojure.core :refer :all]
            [portal-clj.views.layout :as layout]
            [portal-clj.views.base :as base]))

(defn home []
  (layout/common base/index))

(defroutes home-routes
  (GET "/" [] (home)))
