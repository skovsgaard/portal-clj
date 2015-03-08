(ns portal-clj.routes.user
  (:require [compojure.core :refer :all]
            [portal-clj.views.layout :as layout]
            [portal-clj.views.user :as user]))

(defn home []
  (layout/common (user/index)))

(defroutes user-routes
  (GET "/user/home" [] (home)))
