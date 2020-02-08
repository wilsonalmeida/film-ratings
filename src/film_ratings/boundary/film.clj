(ns film-ratings.boundary.film
  (:require [clojure.java.jdbc :as jdbc]
            [clojure.pprint :refer [pprint]]
            duct.database.sql
            )
  (:import java.sql.SQLException))

(defprotocol FilmDatabase
  (list-films [db])
  (create-film [db film]))

(extend-protocol FilmDatabase
  duct.database.sql.Boundary
  (list-films [{db :spec}]
    (jdbc/query db ["SELECT * FROM film"]))
  (create-film [{db :spec} film]
    (pprint "In FilmDatabase - create-film")
    (try
      (let [result (jdbc/insert! db :film film)]
        (if-let [id (val (ffirst result))]
          {:id id}
          {:errors ["Failed to add film."]}))
      (catch SQLException ex
        {:errors [(format "Film not added due to %s" (.getMessage ex))]}))))
