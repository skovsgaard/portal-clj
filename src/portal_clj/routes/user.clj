(ns portal-clj.routes.user
  (:require [compojure.core :refer :all]
            [portal-clj.views.layout :as layout]
            [portal-clj.views.user :as user]))

(defn with-session [template session]
  {:body template
   :session session})

(defn home [session]
  (-> (layout/common (user/index session))
      (with-session session)))

(defn do-post [req]
  (-> (layout/common (user/index (get req :session)))
      (with-session (get req :session))))

(defroutes user-routes
  (GET "/user/home" {session :session} (home session))
  (POST "/user/post" req (do-post req)))
