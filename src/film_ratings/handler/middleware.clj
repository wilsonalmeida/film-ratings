(ns film-ratings.handler.middleware
  (:require [ring.middleware.anti-forgery :refer [*anti-forgery-token*]]
            [integrant.core :as ig]
            [ring.util.response :as resp]))

(defn- wrap-anti-forgery [handler]
  ;;(println (format "Handler is %s" handler))
  (fn [request]
    ;;(println (format "Request is %s" request))
    (let [response  (handler request)]
      ;;(println (format "Response from handler: %s" response))
      (->  response
           (resp/header "anti-forgery-token"  (force *anti-forgery-token*))))))

(defmethod ig/init-key  ::anti-forgery-field [this config]
  ;;(println (format "key is %s: " this))
  #(wrap-anti-forgery %))
