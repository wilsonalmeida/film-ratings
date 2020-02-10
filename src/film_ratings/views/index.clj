(ns film-ratings.views.index
  (:require [film-ratings.views.template :refer [page]]))

(defn list-options []
  (page
   [:div.container
    [:p
     [:a.fluid.ui.button {:href "/add-film"} "Add a Film"]]
    [:p
     [:a.fluid.ui.button {:href "/list-films"} "List Films"]]]))
