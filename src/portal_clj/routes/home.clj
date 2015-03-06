(ns portal-clj.routes.home
  (:require [compojure.core :refer :all]
            [portal-clj.views.layout :as layout]
            [portal-clj.views.base :as base]
            [portal-clj.models.post :as post]))

(defn home []
  (layout/common (base/index (post/all))))

(defroutes home-routes
  (GET "/" [] (home)))
