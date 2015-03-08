(ns portal-clj.views.base
  (:use [hiccup.core])
  (:require [portal-clj.views.layout :as layout]
            [hiccup.form :refer [form-to]]))

(defn post-item [post]
  [:article.blog-post
   [:h2 (get post :title)]
   [:p (get post :body)]
   [:p.timestamp (get post :created_at)]])

(defn tag-item [tag]
  [:li.tag-entry
   [:a {:href (str "/tag/" (get tag :name))} (get tag :name)]])

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

(defn login [msg]
  [:section.wrap
   layout/header
   [:section.login-container
    [:h1 "Please log in below."]
    (if (> (count msg) 0) [:p.message msg])
    (form-to [:post "/page/login"]
             [:input {:type "email" :name "email"}] [:br]
             [:input {:type "password" :name "password"}] [:br]
             [:input {:type "submit"}])]
   layout/footer])

(defn tags [tag-list]
  [:section.wrap
   layout/header
   [:section.tag-list
    [:h1 "Tagged categories."]
    [:ul
     (for [x tag-list] (tag-item x))]]])
