(ns film-ratings.views.film
  (:require [film-ratings.views.template :refer [page labeled-radio]]
            [hiccup.form :refer [form-to label text-field text-area submit-button]]
            [ring.util.anti-forgery :refer [anti-forgery-field]]))

(defn create-film-view
  []
  (page
   [:div.ui.form
    [:h2.ui.dividing.header "Add a film"]
    [:div
     (form-to [:post "/add-film"]
              (anti-forgery-field)
              [:div.field
               (label :name "Name:")
               (text-field {:class "field" :placeholder
                            "Enter film name"} :name)]
              [:div.field
               (label :description "Description:")
               (text-area {:class "field" :placeholder
                           "Enter film description"} :description)]
              [:div.field
               (label :ratings "Rating (1-5):")]

              [:div.inline.fields
               (map (labeled-radio "rating") (repeat 5 false) (range
                                                               1 6))]
              (submit-button {:class "ui button" :tabindex 0} "Add")
              )]]))

(defn- film-attributes-view
  [name description rating]
  [:div.ui.list
   [:div.ui.item
    [:div.header "Name:"]
    name]
   (when description
     [:div.ui.item
      [:div.header "Description:"]
      description])
   (when rating
     [:div.ui.item
      [:div.header "Rating:"]
      rating])])

(defn film-view
  [{:keys [name description rating]} {:keys [errors messages]}]
  (page
   [:div.ui.segment
    [:h2 "Film"]
    (film-attributes-view name description rating)
    (when errors
      [:div.ui.error.message
       [:i.close.icon]
       (for [error (doall errors)]
         [:ul.list error])])
    (when messages
      [:div.ui.success.message
       [:i.close.icon]
       (for [message (doall messages)]
         [:ul.list message])])]))

(defn list-films-view
  [films {:keys [messages]}]
  (page
   [:div.ui.segment
    [:h2.header "Films"]
    (for [{:keys [name description rating]} (doall films)]
      [:div
       (film-attributes-view name description rating)
       [:hr]])
    (when messages
      (for [message (doall messages)]
        [:div.row.alert.alert-success
         [:div.col message]]))]))
