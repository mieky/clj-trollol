(ns trollol.mangler
  (:require [clojure.data.json :as json]
            [clojure.set :as set]
            [clj-http.client :as client]))

(defn work-locally? []
  (some? (System/getenv "LOCAL")))

(defn read-list-names []
  (let [config-file "config.json"]
    (json/read-str
     (if (.exists (clojure.java.io/as-file config-file))
       (slurp config-file)
       (System/getenv "CONFIG")))))

(defn convert-ids-to-names
  [keys-with-ids]
  (set/rename-keys keys-with-ids (read-list-names)))

(defn get-frequencies [cards]
  (frequencies (map #(:idList %) cards)))

(defn parse-json [input]
  (json/read-str input :key-fn keyword))

(defn read-json-file [name]
  (parse-json (slurp name)))

(defn read-from-trello
  "Go fetch the cards from Trello API"
  [secrets]
  (let [url (str "https://api.trello.com/1/boards/" (:board secrets)
                 "/cards?key=" (:key secrets)
                 "&token=" (:token secrets))]
    (parse-json (:body (client/get url {:accept :json})))))

(defn fetch-cards
  "Fetch cards"
  [secrets]
  (-> (if (work-locally?)
        (parse-json (slurp "cards.debug.json"))
        (read-from-trello secrets))
      (get-frequencies)
      (convert-ids-to-names)))

