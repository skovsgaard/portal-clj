(ns portal-clj.routes.admin
  (:require [compojure.core :refer :all]
            [portal-clj.views.layout :as layout]
            [portal-clj.views.admin :as admin]
            [portal-clj.models.user :as user]))

(defn home []
  (layout/common (admin/index (user/all))))

(defn do-post []
  (layout/common (admin/post-post)))

(defroutes admin-routes
  (GET "/admin/home" [] (home))
  (POST "/admin/post" [] (do-post)))
