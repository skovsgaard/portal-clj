(ns portal-clj.views.base
  (:use [hiccup.core])
  (:require [portal-clj.views.layout :as layout]
            [hiccup.form :refer [form-to]]
            [clojure.string :as string])
  (:import [price_updates Scraper]))

(defn htmlify [text]
  (string/replace text #"\n" "<br/>"))

(defn post-item [post]
  [:article.blog-post
   [:h2 (get post :title)]
   [:p (htmlify (get post :body))]
   (when (and (get post :image) (> (count (get post :image)) 1))
     [:a {:href (str "/img/" (get post :image))}
      [:img {:src (str "/img/" (get post :image))
             :title (get post :image)
             :width 300}]])
   [:p.timestamp (get post :created_at)]])

(defn tag-item [tag]
  [:li.tag-entry
   [:a {:href (str "/tag/" (get tag :name))} (get tag :name)]])

(defn index [session posts]
  [:section.wrap
   (layout/cond-header session)
   [:section.blog
    [:h1 "Latest news!"]
    (for [x posts] (post-item x))]
   layout/footer])

(defn about [session]
  [:section.wrap
   (layout/cond-header session)
   [:section.page-body
    [:h1 "About SBC"]
    [:p "SBC is a company specializing in retail sales of single board computers
     and related components. This includes the latest in flagship series such as
     the Raspberry Pi and the Parallela board."]
    [:img {:src "/img/raspberry.jpg" :title "The all new Raspberry Pi 2"}]
    [:p "Below you will find live price updates on some of our biggest products."]
    [:div#raspberry
     [:p (str "The latest price of the Raspberry Pi 2 is "
              (Scraper/getRaspberryPi 1)
              " USD")]]
    [:div#parallella
     [:p (str "The latest price of the Parallella board is "
              (Scraper/getParallella 1)
              " USD")]]
    [:div#beagle
     [:p (str "The latest price of the BeagleBone Black is "
              (Scraper/getBeagleBone 1)
              " USD")]]]
   layout/footer])

(defn login [session msg]
  [:section.wrap
   (layout/cond-header session)
   [:section.login-container
    [:h1 "Please log in below."]
    (if (> (count msg) 0) [:p.message msg])
    (form-to [:post "/page/login"]
             [:input {:type "email" :name "email"}] [:br]
             [:input {:type "password" :name "password"}] [:br]
             [:input {:type "submit"}])]
   layout/footer])

(defn tags [session tag-list]
  [:section.wrap
   (layout/cond-header session)
   [:section.tag-list
    [:h1 "Tagged categories."]
    [:ul
     (for [x tag-list] (tag-item x))]]])

(defn logout []
  [:section.wrap
   layout/header
   [:section.page-body
    [:h1 "You have successfully been logged out."]]
   layout/footer])

(defn search [session result]
  [:section.wrap
   (layout/cond-header session)
   [:h1 "Search results to come."]
   layout/footer])
