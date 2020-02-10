(ns film-ratings.views.template
  (:require [hiccup.page :refer [html5 include-css include-js]]
            [hiccup.element :refer [link-to]]
            [hiccup.form :as form]))

(defn page
  [content]
  (html5
   [:head
    [:meta {:name "viewport" :content "width=device-width, initial-scale=1, shrink-to-fit=no"}]
    [:title "Film Ratings"]
    (include-css "/semantic-ui/semantic.css")
    (include-js "https://code.jquery.com/jquery-3.3.1.slim.min.js"
                "/semantic-ui/semantic.js"
                )
    [:body
     [:div.ui.inverted.segment
      [:h1.ui.white.inverted.header "Film Ratings"]
      [:div.ui.inverted.white.attached.stackable.menu
       [:div.ui.inverted.black.right.menu
        [:a {:class "active item" :href "/"} [:i.home.icon] "Home"]]]]
     [:div.ui.segment
      content]]]))

(defn labeled-radio [group]
  (fn [checked? label]
    [:div.field
     (form/radio-button {:class "ui radio checkbox"} group checked? label)
     (form/label (str "label-" label) (str label))]))
