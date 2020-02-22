(ns prod
  (:refer-clojure :exclude [test])
  (:require [duct.core :as duct]
            [integrant.core :as ig]
            )
  )

(duct/load-hierarchy)

(defn go []
  (let [keys  [:duct/daemon]
        profiles [:duct.profile/prod]]
    (-> (duct/resource "film_ratings/config.edn")
        (duct/read-config)
        (duct/exec-config profiles keys))))
