(ns film-ratings.handler.middleware
  (:require [ring.middleware.anti-forgery :refer [*anti-forgery-token*]]
            [integrant.core :as ig]
            [clojure.pprint :refer [pprint]]
            [ring.util.response :as resp]))

(defn- wrap-anti-forgery [handler]
  (pprint (format "Handler is %s" handler))
  (fn [request]
    (pprint (format "Request is %s" request))
    (let [response  (handler request)]
      (print (format "Response from handler: %s" response))
      (->  response
           (resp/header "X-CSRF-Token"  (force *anti-forgery-token*))))))

(defmethod ig/init-key  ::anti-forgery-field [this config]
  (print (format "key is %s: " this))
  #(wrap-anti-forgery %))
