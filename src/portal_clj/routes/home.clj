(ns portal-clj.routes.home
  (:require [compojure.core :refer :all]
            [portal-clj.routes.admin :as admin]
            [portal-clj.views.layout :as layout]
            [portal-clj.views.base :as base]
            [portal-clj.models.post :as post]
            [portal-clj.models.tag :as tag]))

(defn home []
  (layout/common (base/index (post/all))))

(defn about []
  (layout/common (base/about)))

(defn tags []
  (layout/common (base/tags (tag/all))))

(defn login []
  (layout/common (base/login)))

(defn do-login [user-credentials]
  (let [params (get user-credentials :params)]
    (if (= (get params :email) "idi@admin.com")
      (layout/common (admin/home))
      (layout/common (base/index (post/all))))))

(defroutes home-routes
  (GET "/" [] (home))
  (GET "/page/about" [] (about))
  (GET "/page/tags" [] (tags))
  (GET "/page/login" [] (login))
  (POST "/page/login" user-cred (do-login user-cred)))
