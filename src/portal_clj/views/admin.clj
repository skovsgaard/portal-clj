(ns portal-clj.views.admin
  (:use [hiccup.core])
  (:require [portal-clj.views.layout :as layout]
            [hiccup.form :refer [form-to]]))

(def admin-author
  (form-to [:post "/admin/post"]
           [:input {:type :text :name :title :placeholder "Enter your title here."}] [:br]
           [:textarea {:width 700 :name "post-content" :placeholder "Enter your post here."}] [:br]
           [:input {:type :submit :name :submit-post}]))

(defn post-post []
  [:section.wrap
   layout/header
   [:section.dash-main
    [:article.post-post
     [:h1 "Post successful!"]]
    admin-author]
   layout/footer])

(defn user-listing [user]
  [:li.user-item
   [:a {:href (str "/user/list/" (get user :username))}
        [:span.username (get user :username)]]])

(defn index [session users]
  [:section.wrap
   (if (get session :id)
     layout/header
     layout/header-authed)
   [:section.dash-main
    [:h1 "Welcome, admin!"]
    [:h2 "Create new post."]
    admin-author]
   [:section.user-list
    [:h2 "Full list of registered users."]
    [:ul.user-list
     (for [x users] (user-listing x))]]
   layout/footer])
