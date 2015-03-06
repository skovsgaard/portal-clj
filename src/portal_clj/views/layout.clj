(ns portal-clj.views.layout
  (:require [hiccup.page :refer [html5 include-css]]))

(defn common [& body]
  (html5
   [:head
    [:title "Welcome to portal-clj"]
    (include-css "/css/screen.css")
    [:script {:src "/js/cljs.js"}]]
    [:body body]))

(def footer
  [:footer
   [:span "&copy;SBC 2015"]])

(defn menu [links]
  [:nav.main-nav
   [:ul (for [x links] [:li [:a {:href (first x)} (second x)]])]])

(defn sidebar [items]
  [:aside.sidebar
   [:ul (for [x items] [:li x])]])
