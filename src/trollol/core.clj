(ns trollol.core
  (:require [clojure.data.json :as json]
            [compojure.core :refer :all]
            [org.httpkit.server :refer [run-server]]
            [trollol.mangler :as mangler]))

(defn read-secrets
  "Read API secrets from environment variables"
  []
  {:key   (System/getenv "KEY")
   :token (System/getenv "TOKEN")
   :board (System/getenv "BOARD")})

(defn print-usage "" []
  (println "Please pass environment variables KEY, TOKEN, BOARD."))

(defn cards-as-json []
  (json/write-str (mangler/fetch-cards (read-secrets))))

(defn get-server-port []
  (let [port (System/getenv "PORT")]
    (if (nil? port)
      5000
      (Integer/parseInt port))))

(defroutes trollol
  (GET "/api/funnel" req (cards-as-json)))
      
(defn -main
  "Read cards and output a JSON summary of their lists"
  [& args]
  (let [secrets (read-secrets)]
      (if (or (nil? (:key secrets)) (nil? (:token secrets)))
        (print-usage)
        ;(json/pprint (mangler/fetch-cards secrets))
        (run-server trollol {:port (get-server-port)})
        )))
