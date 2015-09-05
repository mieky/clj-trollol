(ns clj-trollol.core
  (:require [clojure.data.json :as json])
  (:require [clojure.set :as set])
  (:require [clj-http.client :as client])
  (:gen-class))

(defn read-secrets
  "Read API secrets from environment variables"
  []
  {:key   (System/getenv "KEY")
   :token (System/getenv "TOKEN")
   :board (System/getenv "BOARD")})

(defn work-locally? []
  (some? (System/getenv "LOCAL")))

(defn parse-json [input]
  (json/read-str input :key-fn keyword))

(defn read-json-file [name]
  (parse-json (slurp name)))

(defn read-list-names []
  (json/read-str (slurp "config.json")))

(defn convert-ids-to-names
  [keys-with-ids]
  (set/rename-keys keys-with-ids (read-list-names)))

(defn get-frequencies [cards]
  (frequencies (map #(:idList %) cards)))

(defn read-from-trello
  "Go fetch the cards from Trello API"
  [secrets]
  (let [url (str "https://api.trello.com/1/boards/" (:board secrets)
                 "/cards?key=" (:key secrets)
                 "&token=" (:token secrets))]
    (parse-json (:body (client/get url {:accept :json})))))

(defn print-usage "" []
  (println "Please pass environment variables KEY, TOKEN, BOARD."))

(defn fetch-cards
  "Fetch cards"
  [secrets]
  (json/pprint (-> (if (work-locally?)
                     (parse-json (slurp "cards.debug.json"))
                     (read-from-trello secrets))
                   (get-frequencies)
                   (convert-ids-to-names)
                   (json/write-str))))
      
(defn -main
  "Read cards and output a JSON summary of their lists"
  [& args]
  (let [secrets (read-secrets)]
      (if (or (nil? (:key secrets)) (nil? (:token secrets)))
        (print-usage)
        (fetch-cards secrets))))

