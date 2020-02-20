(ns film-ratings.handler.middleware
  (:require [ring.middleware.anti-forgery :refer [*anti-forgery-token*
                                                  wrap-anti-forgery]]
            [integrant.core :as ig]
            [clojure.pprint :refer [pprint]]
            [ring.util.response :as resp]))

(defn- wrap-anti-forgery-field [handler]
  ;; (pprint (format "Handler is %s\n" handler))
  (fn [request]
    ;; (pprint (format "Request is %s\n" request))
    (let [response  (handler request)]
      ;; (print (format "Response from handler: %s\n" response))
      (->  response
           (resp/set-cookie "x-csrf-token"  (force *anti-forgery-token*)
                            {:same-site :strict :path "/" :max-age 3600})))))

(defmethod ig/init-key  ::anti-forgery-field [this config]
  ;; (print (format "In anti-forgery-field: Key is: %s\n" this))
  #(wrap-anti-forgery-field %))

(defn- my-custom-token [request]
  ;; (pprint (format "In my-custom-token: Request is %s\n" request))
  (let [token (get-in request [:cookies "x-csrf-token" :value])]
    ;; (pprint (format "Token is %s\n" token))
    token))

(defmethod ig/init-key ::my-wrap-anti-forgery [this config]
  ;; (print (format "In my-wrap-anti-forgery: Key is %s\n" this))
  #(wrap-anti-forgery % {:read-token my-custom-token}))
