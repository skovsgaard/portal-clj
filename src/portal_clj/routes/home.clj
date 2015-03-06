(ns portal-clj.routes.home
  (:require [compojure.core :refer :all]
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

(defroutes home-routes
  (GET "/" [] (home))
  (GET "/page/about" [] (about))
  (GET "/page/tags" [] (tags))
  (GET "/page/login" [] (login)))
