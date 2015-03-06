(ns portal-clj.views.base
  (:use [hiccup.core])
  (:require [portal-clj.views.layout :as layout]))

(def index
  [:section.wrap
   [:h1 "Helloooooo, Wooooorld!"]
   layout/footer])
