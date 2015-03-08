(ns portal-clj.views.layout
  (:require [hiccup.page :refer [html5 include-css]]))

(defn common [& body]
  (html5
   [:head
    [:title "SBC - Single Board Computers"]
    (include-css "/css/screen.css")
    [:script {:src "/js/cljs.js"}]]
    [:body body]))

(defn menu [links]
  [:nav.main-nav
   [:ul (for [x links] [:li [:a {:href (first x)} (second x)]])]])

(def header
  [:header.main-header
   [:span.title [:a {:href "/"} "SBC - Single Board Computers"]]
   (menu [["/page/about" "About us"]
          ["/page/tags" "News by tag"]
          ["/page/login" "Login"]])])

(def header-authed
  [:header.main-header
   [:span.title [:a {:href "/"} "SBC - Single Board Computers"]]
   (menu [["/page/about" "About us"]
          ["/page/tags" "News by tag"]
          ["/page/logout" "Logout"]])])

(defn cond-header [session]
  (if (get session :user)
    header-authed
    header))

(def footer
  [:footer
   [:span "&copy;SBC 2015"]])

(defn sidebar [items]
  [:aside.sidebar
   [:ul (for [x items] [:li x])]])
