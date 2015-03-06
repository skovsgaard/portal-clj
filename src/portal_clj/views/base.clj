(ns portal-clj.views.base
  (:use [hiccup.core])
  (:require [portal-clj.views.layout :as layout]))

(def index
  [:section.wrap
   [:header.main-header
     [:span.title "SBC - Single Board Computers"]
     (layout/menu [["/" "Something"]
                 ["//google.com" "something"]
                 ["#" "something else"]])]
    layout/footer])
