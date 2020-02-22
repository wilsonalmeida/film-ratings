(ns film-ratings.handler.film
  (:require [ataraxy.core :as ataraxy]
            [ataraxy.response :as response]
            [film-ratings.boundary.film :as boundary.film]
            [film-ratings.views.film :as views.film]
            [integrant.core :as ig]
            [ring.util.codec :refer [percent-decode]]))

(defmethod ig/init-key :film-ratings.handler.film/show-create [_ _]
  (fn [_]
    (println "In show-create handler")
    [::response/ok (views.film/create-film-view)]))

(defn- film-form->film
  [film-form]
  (as-> film-form film
    (dissoc film "__anti-forgery-token")
    (reduce-kv (fn [m k v] (assoc m (keyword k) v))
               {}
               film)
    (update film :rating #(Integer/parseInt %))))

(defmethod ig/init-key :film-ratings.handler.film/create [_ {:keys
                                                             [db]}]
  (fn [{[_ film-form] :ataraxy/result :as request}]
    (println "In create handler")
    (let [film (film-form->film film-form)
          result (boundary.film/create-film db film)
          alerts (if (:id result)
                   {:messages ["Film added"]}
                   result)]
      (println film)
      [::response/ok (views.film/film-view film alerts)])))

(defmethod ig/init-key :film-ratings.handler.film/list [_ {:keys [db]}]
  (fn [_]
    (println "In list films")
    (let [films-list (boundary.film/list-films db)]
      (if (seq films-list)
        [::response/ok (views.film/list-films-view films-list {})]
        [::response/ok (views.film/list-films-view [] {:messages ["No films found."]})]))))


(defmethod ig/init-key :film-ratings.handler.film/destroy [_ {:keys [db]}]
  (fn [{[_ name] :ataraxy/result :as request}]
    (println (format "In destroy handler, name is %s" name))
    (let [result (boundary.film/destroy-film db (percent-decode name))]
      (println (format "Result is %s" result))
      [::response/ok (views.film/default-view result)]
      ))
  )
