(ns portal-clj.routes.admin
  (:require [compojure.core :refer :all]
            [clojure.string :as string]
            [portal-clj.views.layout :as layout]
            [portal-clj.views.admin :as admin]
            [portal-clj.models.user :as user]
            [portal-clj.models.post :as post]
            [ring.middleware [multipart-params :as multipart]]
            [clojure.java.io :as io]))

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

(defn take-multipart [req upload-name]
  (-> (get req :multipart-params)
      (get upload-name)
      (get :tempfile)))

(defn take-multipart-name [req upload-name]
  (-> (get req :multipart-params)
      (get upload-name)
      (get :filename)))

(defn do-upload [req]
  (io/copy (take-multipart req "testupload")
           (io/file (str "resources/public/img/" (take-multipart-name req "testupload")))))

(defn activate-user [req]
  (-> (get req :params)
      (get :uid)
      (user/update! {:active 1}))
  (home (get req :session)))

(defn deactivate-user [req]
  (-> (get req :params)
      (get :uid)
      (user/update! {:active 0}))
  (home (get req :session)))

(defroutes admin-routes
  (GET "/admin/home" {session :session} (home session))
  (POST "/admin/post" req (do-post req))
  (POST "/admin/activate" req (activate-user req))
  (POST "/admin/deactivate" req (deactivate-user req))
  (multipart/wrap-multipart-params
   (POST "/admin/upload" req (do-upload req))))
