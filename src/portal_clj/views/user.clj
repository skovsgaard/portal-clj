(ns portal-clj.views.user
  (:use [hiccup.core])
  (:require [portal-clj.views.layout :as layout]
            [hiccup.form :refer [form-to]]
            [clojure.java.io :as io]))

(def img-options
  (map (fn [item] [:option {:value item} item])
       (drop 1 (map (fn [f] (.getName f))
                    (-> (io/file "resources/public/img/")
                        file-seq)))))

(def user-upload
  [:form {:action "/user/upload"  :method "post" :enctype "multipart/form-data"}
   [:input {:type "file" :name "upload" :enctype "multipart"}] [:br]
   [:input {:type "submit" :name "submit-upload" :value "Upload file"}]])

(def post-author
  (form-to [:post "/user/post"]
           [:input {:type :text :name :title :placeholder "Enter your title here."}] [:br]
           [:textarea {:width 700 :name "post-content" :placeholder "Enter your post here."}] [:br]
           [:label {:for "file-attach"} "Optional image-attachment: "]
           [:select {:name "file-attach"} (into [:option {:selected "selected" :value ""} "None"] img-options)] [:br]
           [:input {:type "checkbox" :name "restricted"} "Internal post only."] [:br]
           [:input {:type :submit :name :submit-post}]))

(defn index [session]
  [:section.wrap
   (layout/cond-header session)
   [:section.dash-main
    [:h1 "Welcome to your dashboard!"]
    [:h2 "Create new post."]
    post-author
    [:h2 "Upload a file to the server."]
    user-upload]
   layout/footer])
