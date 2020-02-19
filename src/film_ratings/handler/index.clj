(ns film-ratings.handler.index
  (:require [ataraxy.core :as ataraxy]
            [ataraxy.response :as response]
            [film-ratings.views.index :as views.index]
            [integrant.core :as ig]
            ;;[ataraxy.handler :as handler]
            ))

;; (defmethod handler/sync-default ::ok [{[_ body] :ataraxy/result}]
;;   (let [result (response/->response body)]
;;     (pprint (format "Result : %s" result))
;;     (-> result
;;         (resp/header "anti-forgery-token" (force *anti-forgery-token*))
;;         (resp/status 200)))
;;   )

(defmethod ig/init-key :film-ratings.handler.index/index [_ options]
  (fn [_]
    ;;fn [{[_] :ataraxy/result}]
    [::response/ok (views.index/list-options)]))
