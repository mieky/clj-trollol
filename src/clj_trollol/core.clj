(ns clj-trollol.core
  (:require [clojure.data.json :as json])
  (:gen-class))

(defn read-json-file [name]
    (json/read-str (slurp name)))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (json/pprint (read-json-file "lists.debug.json")))
