(ns portal-clj.routes.admin
  (:require [compojure.core :refer :all]
            [portal-clj.views.layout :as layout]
            [portal-clj.views.admin :as admin]
            [portal-clj.models.user :as user]))

(defn with-session [template session]
  {:body template
   :session session})

(defn home [session]
  (-> (layout/common (admin/index (user/all)))
      (with-session session)))

(defn do-post [req]
  (-> (layout/common (admin/post-post))
      (with-session session)))

(defroutes admin-routes
  (GET "/admin/home" {session :session} (home session))
  (POST "/admin/post" req (do-post req)))
