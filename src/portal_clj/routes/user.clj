(ns portal-clj.routes.user
  (:require [compojure.core :refer :all]
            [portal-clj.views.layout :as layout]
            [portal-clj.views.user :as user]))

(defn with-session [template session]
  {:body template
   :session session})

(defn home [session]
  (println session)
  (-> (layout/common (user/index))
      (with-session session)))

(defroutes user-routes
  (GET "/user/home" [] (home)))
