(ns clj-trollol.core
  (:require [clojure.data.json :as json])
  (:gen-class))

(defn read-json-file [name]
    (json/read-str (slurp name) :key-fn keyword))

(defn get-list-ids
  "Get number of candidates indexes by list id"
  [cards]
  (map #(assoc {} (key %) (count (second %)))
      (group-by :idList cards)))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (json/pprint (->
      (read-json-file "cards.debug.json")
      (get-list-ids)
      (json/write-str)
      )))
