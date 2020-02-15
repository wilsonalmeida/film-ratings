(ns film-ratings.handler.film
  (:require [ataraxy.core :as ataraxy]
            [ataraxy.response :as response]
            [film-ratings.boundary.film :as boundary.film]
            [film-ratings.views.film :as views.film]
            [integrant.core :as ig]
            [ring.util.codec :refer [percent-decode]]
            [clojure.pprint :refer [pprint]]))

(defmethod ig/init-key :film-ratings.handler.film/show-create [_ _]
  (fn [_]
    (pprint "In show-create handler")
    [::response/ok (views.film/create-film-view)]))

(defmethod ig/init-key :film-ratings.handler.film/create [_ {:keys
                                                             [db]}]
  (fn [{[_ film-form] :ataraxy/result :as request}]
    (pprint "In create handler")
    (let [film (reduce-kv (fn [m k v] (assoc m (keyword k) v)) {}
                          (dissoc film-form "__anti-forgery-token"))
          result (boundary.film/create-film db film)
          alerts (if (:id result)
                   {:messages ["Film added"]}
                   result)]
      (print film)
      [::response/ok (views.film/film-view film alerts)])))

(defmethod ig/init-key :film-ratings.handler.film/list [_ {:keys [db]}]
  (fn [_]
    (pprint "In list films")
    (let [films-list (boundary.film/list-films db)]
      (if (seq films-list)
        [::response/ok (views.film/list-films-view films-list {})]
        [::response/ok (views.film/list-films-view [] {:messages ["No films found."]})]))))


(defmethod ig/init-key :film-ratings.handler.film/destroy [_ {:keys [db]}]
  (fn [{[_ name] :ataraxy/result :as request}]
    (pprint (format "In destroy handler, name is %s" name))
    (let [result (boundary.film/destroy-film db (percent-decode name))]
      (pprint (format "Result is %s" result))
      [::response/ok (views.film/default-view result)]
      ))
  )
