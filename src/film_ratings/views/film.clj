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
  [name description rating listview]
  (if listview
    [:div.five.column.row
     [:div.column name]
     (when description
       [:div.column description])
     (if rating
       [:div.column  rating]
       [:div.column ""])
     [:div.ui.button "Edit"]
     [:div.ui.button "Delete"]]
    [:div.container
     [:div.item "Name: " name]
     [:div.item "Description: " description ]
     [:div.item "Rating: " rating ]
     ])
  )

(defn film-view
  [{:keys [name description rating]} {:keys [errors messages]}]
  (page
   [:div.ui.segment
    [:h2 "Film"]
    (film-attributes-view name description rating false)
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
   [:div
    [:h2.header "Films"]
    [:div.ui.segment
     [:div.ui.relaxed.grid
      [:div.five.column.row
       [:div.column [:h4.header "Name:"] [:hr]]
       [:div.column [:h4.header "Description:"] [:hr]]
       [:div.column [:h4.header "Rating:"] [:hr]]
       [:div.column ""]
       [:div.column ""]]]
     (for [{:keys [name description rating]} (doall films)]
       [:div.ui.relaxed.grid
        (film-attributes-view name description rating true)])
     (when messages
       (for [message (doall messages)]
         [:div.ui.error.message
          [:ul.list message]]))]]))
