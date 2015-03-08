(ns portal-clj.routes.home
  (:require [compojure.core :refer :all]
            [ring.middleware.session :as session]
            [portal-clj.routes.admin :as admin]
            [portal-clj.routes.user :as user]
            [portal-clj.views.layout :as layout]
            [portal-clj.views.base :as base]
            [portal-clj.models.post :as post]
            [portal-clj.models.tag :as tag]))

(defn with-session [template session]
  {:body template
   :session session})

(defn home [session]
  (-> (layout/common (base/index (post/all)))
      (with-session session)))

(defn about [session]
  (-> (layout/common (base/about))
      (with-session session)))

(defn tags [session]
  (-> (layout/common (base/tags (tag/all)))
      (with-session session)))

(defn login [session]
  (-> (layout/common (base/login))
      (with-session session)))

(defn do-login [req]
  (let [params (get req :params)
        session (get req :session)]
    (if (= (get params :email) "idi@admin.com")
      (-> (layout/common (admin/home))
          (with-session session))
      (layout/common (user/home)))))

(defroutes home-routes
  (GET "/" {session :session} (home session))
  (GET "/page/about" {session :session} (about session))
  (GET "/page/tags" {session :session} (tags session))
  (GET "/page/login" {session :session} (login session))
  (POST "/page/login" req (do-login req)))
