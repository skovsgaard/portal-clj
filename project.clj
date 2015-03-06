(defproject portal-clj "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/java.jdbc "0.3.6"]
                 [org.clojure/clojurescript "0.0-2913"]
                 [mysql/mysql-connector-java "5.1.6"]
                 [compojure "1.1.6"]
                 [hiccup "1.0.5"]
                 [ring-server "0.3.1"]
                 [com.cemerick/friend "0.2.1"]]
  :plugins [[lein-ring "0.8.12"]
            [lein-cljsbuild "1.0.5"]]
  :cljsbuild {:builds [{:source-paths ["src/cljs"]
                        :compiler {:output-to "resources/public/js/cljs.js"
                                   :optimizations :advanced}}]}
  :ring {:handler portal-clj.handler/app
         :init portal-clj.handler/init
         :destroy portal-clj.handler/destroy}
  :profiles
  {:uberjar {:aot :all}
   :production
   {:ring
    {:open-browser? false, :stacktraces? false, :auto-reload? false}}
   :dev
   {:dependencies [[ring-mock "0.1.5"] [ring/ring-devel "1.3.1"]]}})
