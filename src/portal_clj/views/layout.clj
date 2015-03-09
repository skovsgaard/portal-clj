(ns portal-clj.views.layout
  (:require [hiccup.page :refer [html5 include-css]]
            [hiccup.form :refer [form-to]]))

(defn common [& body]
  (html5
   [:head
    [:title "SBC - Single Board Computers"]
    (include-css "/css/screen.css")
    [:script {:src "/js/cljs.js"}]]
    [:body body]))

(defn search-bar []
  [:article#search
   (form-to [:post "/page/search"]
            [:input {:type :text :placeholder "Search archive" :name "search"}]
            [:input {:type :submit :value "Search"}])])

(defn menu [links]
  [:nav.main-nav
   [:ul (for [x links] [:li [:a {:href (first x)} (second x)]])]])

(def header
  [:header.main-header
   [:span.title [:a {:href "/"} "SBC - Single Board Computers"]]
   (menu [["/page/about" "About us"]
          ["/page/tags" "News by tag"]
          ["/page/login" "Login"]])
   (search-bar)])

(def header-authed
  [:header.main-header
   [:span.title [:a {:href "/"} "SBC - Single Board Computers"]]
   (menu [["/page/about" "About us"]
          ["/page/tags" "News by tag"]
          ["/page/logout" "Logout"]])
   (search-bar)])

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
