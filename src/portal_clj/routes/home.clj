(ns portal-clj.routes.home
  (:require [compojure.core :refer :all]
            [ring.middleware.session :as session]
            [portal-clj.routes.admin :as admin]
            [portal-clj.routes.user :as user]
            [portal-clj.views.layout :as layout]
            [portal-clj.views.base :as base]
            [portal-clj.models.post :as post]
            [portal-clj.models.post-tag :as post-tag]
            [portal-clj.models.tag :as tag]
            [portal-clj.models.user :as db-user]))

(defn with-session [template session]
  {:body template
   :session session})

(defn home [session]
  (if (get session :user)
    (-> (layout/common (base/index session (post/all)))
        (with-session session))
    (-> (layout/common (base/index session (post/all-unrestricted)))
        (with-session session))))

(defn about [session]
  (-> (layout/common (base/about session))
      (with-session session)))

(defn tags [session]
  (-> (layout/common (base/tags session (tag/all)))
      (with-session session)))

(defn login [session msg]
  (-> (layout/common (base/login session msg))
      (with-session session)))

(defn logout [session]
  (try
    (-> (layout/common (base/index {} (post/all)))
        (with-session (assoc session :user nil)))
    (catch Exception ex
      (println ex))))

(defn do-login [req]
  (let [params (get req :params)
        session (get req :session)]
    (let [user (-> (get params :email) db-user/get-by-email first)]
      (if (= (get user :active) true)
        (if-not (= user nil)
          (if (get user :admin)
            (-> (assoc session :user (user :id))
                (admin/home))
            (-> (assoc session :user (user :id))
                (user/home)))
          (login {} "You either mistyped something or are not in our system."))
        (login {} "Your account is disabled. Please contact an administrator.")))))

(defn search [req]
  (layout/common (base/search (get req :session) [])))

(defroutes home-routes
  (GET "/" {session :session} (home session))
  (GET "/page/about" {session :session} (about session))
  (GET "/page/tags" {session :session} (tags session))
  (GET "/page/login" {session :session} (login session ""))
  (GET "/page/logout" {session :session} (logout session))
  (POST "/page/search" req (search req))
  (POST "/page/login" req (do-login req)))
