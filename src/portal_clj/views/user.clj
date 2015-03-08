(ns portal-clj.views.user
  (:use [hiccup.core])
  (:require [portal-clj.views.layout :as layout]
            [hiccup.form :refer [form-to]]))

(def post-author
  (form-to [:post "/admin/post"]
           [:input {:type :text :name :title :placeholder "Enter your title here."}] [:br]
           [:textarea {:width 700 :name "post-content" :placeholder "Enter your post here."}] [:br]
           [:input {:type :submit :name :submit-post}]))

(defn index []
  [:section.wrap
   layout/header
   [:section.dash-main
    [:h1 "Welcome to your dashboard!"]
    [:h2 "Create new post."]
    post-author]
   layout/footer])
