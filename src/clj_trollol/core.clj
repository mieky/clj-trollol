(ns clj-trollol.core
  (:require [clojure.data.json :as json])
  (:gen-class))

(defn read-json-file [name]
    (json/read-str (slurp name) :key-fn keyword))

(defn get-list-name
  [boardId]
  )

(defn filter-props
  "Get only those properties we need"
  [cards]
  (map
    #(assoc {} :idList (:idList %) :idLabels (:idLabels %))
    cards))

(defn get-list-ids
  "Get number of candidates indexes by list id"
  [cards]
  (map
    #(assoc {} (key %) (count (second %)))
    (group-by :idList cards)))

(defn -main
  "Read cards and output a JSON summary of their lists"
  [& args]
  (json/pprint (->
      (read-json-file "cards.debug.json")
      (filter-props)
      (get-list-ids)
      (json/write-str)
      )))
