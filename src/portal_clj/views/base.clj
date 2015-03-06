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
   layout/header
   [:section.blog
    [:h1 "Latest news!"]
    (for [x posts] (post-item x))]
   layout/footer])

(defn about []
  [:section.wrap
   layout/header
   [:section.page-body
    [:h1 "About SBC"]
    [:p "SBC is a company specializing in retail sales of single board computers
     and related components. This includes the latest in flagship series such as
     the Raspberry Pi and the Parallela board."]
    [:img {:src "/img/raspberry.jpg" :title "The all new Raspberry Pi 2"}]]
   layout/footer])
