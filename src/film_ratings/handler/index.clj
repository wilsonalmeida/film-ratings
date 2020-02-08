(ns film-ratings.handler.index
  (:require [ataraxy.core :as ataraxy]
            [ataraxy.response :as response]
            [film-ratings.views.index :as views.index]
            [integrant.core :as ig]
            [clojure.pprint :refer [pprint]]))


(defmethod ig/init-key :film-ratings.handler.index/index [_ options]
  (fn [{[_] :ataraxy.core/result}]
    [::response/ok (views.index/list-options)]))
