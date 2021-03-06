(defproject trollol "0.1.0"
  :description "printing out some trello summaries"
  :url "https://github.com/mieky/clj-trollol"
  :license {:name "MIT"
            :url "https://github.com/mieky/clj-trollol/blob/master/LICENSE"}
  :dependencies [
    [org.clojure/clojure "1.6.0"]
    [org.clojure/data.json "0.2.6"]
    [clj-http "2.0.0"]
    [compojure "1.1.8"]
    [http-kit "2.1.15"]]
  :main ^:skip-aot trollol.core
  :uberjar-name "trollol-standalone.jar"
  :min-lein-version "2.5.1"
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
