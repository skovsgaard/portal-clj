(ns portal-clj.views.admin
  (:use [hiccup.core])
  (:require [portal-clj.views.layout :as layout]
            [hiccup.form :refer [form-to]]
            [clojure.java.io :as io]))

(def img-options
  (map (fn [item] [:option {:value item} item])
       (drop 1 (map (fn [f] (.getName f))
                    (-> (io/file "resources/public/img/")
                        file-seq)))))

(def admin-upload
  [:form {:action "/admin/upload"  :method "post" :enctype "multipart/form-data"}
   [:input {:type "file" :name "upload" :enctype "multipart"}] [:br]
   [:input {:type "submit" :name "submit-upload" :value "Upload file"}]])

(def admin-author
  (form-to [:post "/admin/post"]
           [:input {:type :text :name :title :placeholder "Enter your title here."}] [:br]
           [:textarea {:width 700 :name "post-content" :placeholder "Enter your post here."}] [:br]
           [:label {:for "file-attach"} "Optional image-attachment: "]
           [:select {:name "file-attach"} (into [:option {:selected "selected" :value ""} "None"] img-options)] [:br]
           [:input {:type "checkbox" :name "restricted"} "Internal post only."] [:br]
           [:input {:type :submit :name :submit-post :value "Create post."}]))

(def admin-page
  (form-to [:post "/admin/page"]
           [:input {:type :text :name :title :placeholder "Enter your title here."}] [:br]
           [:textarea {:width 700 :name "post-content" :placeholder "Enter your page content here."}] [:br]
           [:label {:for "file-attach"} "Optional image-attachment: "]
           [:select {:name "file-attach"} (into [:option {:selected "selected" :value ""} "None"] img-options)] [:br]
           [:input {:type "checkbox" :name "restricted"} "Internal page only."] [:br]
           [:input {:type :submit :name :submit-post :value "Create page."}]))

(defn post-post [session]
  [:section.wrap
   (layout/cond-header session)
   [:section.dash-main
    [:article.post-post
     [:h1 "Post successful!"]]
    admin-author]
   layout/footer])

(defn user-listing [user]
  [:li.user-item
   [:a {:href (str "/user/list/" (get user :username))}
    [:span.username (get user :username)]]
   [:span.activity (str " - Status: " (if (get user :active) "Active" "Deactivated") " - ")
    (if (get user :active)
      [:form.deactivate {:method :post :action "/admin/deactivate"}
       [:input {:type "hidden" :name "uid" :value (get user :id)}]
       [:input {:type "submit" :value "Deactivate"}]]
      [:form.activate {:method :post :action "/admin/activate"}
       [:input {:type "hidden" :name "uid" :value (get user :id)}]
       [:input {:type "submit" :value "Activate"}]])]])

(defn index [session users]
  [:section.wrap
   (layout/cond-header session)
   [:section.dash-main
    [:h1 "Welcome, admin!"]
    [:h2 "Create new post."]
    admin-author
    [:h2 "Upload a file to the server."]
    admin-upload
    [:h2 "Create a new static page."]
     admin-page]
   [:section.user-list
    [:h2 "Full list of registered users."]
    [:ul.user-list
     (for [x users] (user-listing x))]]
   layout/footer])
