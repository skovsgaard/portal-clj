(ns portal-clj.routes.user
  (:require [compojure.core :refer :all]
            [portal-clj.views.layout :as layout]
            [portal-clj.views.user :as user]
            [ring.middleware [multipart-params :as multipart]]
            [clojure.java.io :as io]))

(defn with-session [template session]
  {:body template
   :session session})

(defn home [session]
  (-> (layout/common (user/index session))
      (with-session session)))

(defn do-post [req]
  (-> (layout/common (user/index (get req :session)))
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
  (io/copy (take-multipart req "upload")
           (io/file (str "resources/public/img/" (take-multipart-name req "upload"))))
  (home (get req :session)))

(defroutes user-routes
  (GET "/user/home" {session :session} (home session))
  (POST "/user/post" req (do-post req))
  (multipart/wrap-multipart-params
   (POST "/user/upload" req (do-upload req))))
