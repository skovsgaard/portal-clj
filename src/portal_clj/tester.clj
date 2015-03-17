(ns portal-clj.tester
  (:import [price_updates Scraper]))

(defn -main [& args]
  (Scraper/testSelf))
