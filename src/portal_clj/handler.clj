(ns portal-clj.handler
  (:require [compojure.core :refer [defroutes routes]]
            [ring.middleware.session :as session]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.file-info :refer [wrap-file-info]]
            [hiccup.middleware :refer [wrap-base-url]]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [portal-clj.routes.home :refer [home-routes]]
            [portal-clj.routes.admin :refer [admin-routes]]))

(defn init []
  (println "portal-clj is starting"))

(defn destroy []
  (println "portal-clj is shutting down"))

(defroutes app-routes
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (-> (routes home-routes admin-routes app-routes)
      (handler/site)
      (wrap-base-url)
      (session/wrap-session)))
