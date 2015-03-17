(ns portal-clj.tester
  (:import [price_updates Scraper]))

(defn -main [& args]
  (println (Scraper/get "http://fupifarvandet.dk" "a")))
