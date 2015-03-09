(ns portal-clj.routes.admin
  (:require [compojure.core :refer :all]
            [clojure.string :as string]
            [portal-clj.views.layout :as layout]
            [portal-clj.views.admin :as admin]
            [portal-clj.models.user :as user]
            [portal-clj.models.post :as post]))

(defn sluggify [title]
  (-> (string/lower-case title)
      (string/replace #" " "-")))

(defn with-session [template session]
  {:body template
   :session session})

(defn home [session]
    (-> (layout/common (admin/index session (user/all)))
        (with-session session)))

(defn do-post [req]
  (let [post (get req :params)]
    (post/create! {:title (get post :title)
                   :body (get post :post-content)
                   :user_id (-> (get req :session) (get :user))
                   :commentable 0
                   :slug (sluggify (get post :title))
                   :active 1
                   :restricted 0}))
  (-> (layout/common (admin/post-post (get req :session)))
      (with-session (get req :session))))

(defroutes admin-routes
  (GET "/admin/home" {session :session} (home session))
  (POST "/admin/post" req (do-post req)))
