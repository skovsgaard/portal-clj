(ns portal-clj.views.base
  (:use [hiccup.core])
  (:require [portal-clj.views.layout :as layout]))

(defn post-item [post]
  [:article.blog-post
   [:h2 (get post :title)]
   [:p (get post :body)]
   [:p.timestamp (get post :created_at)]])

(defn index [posts]
  [:section.wrap
   [:header.main-header
     [:span.title "SBC - Single Board Computers"]
     (layout/menu [["/" "Something"]
                   ["//google.com" "something"]
                   ["#" "something else"]])]
   [:section.blog
    [:h1 "Latest news!"]
    (for [x posts] (post-item x))]
    layout/footer])
