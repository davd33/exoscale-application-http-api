(defproject exoscale-application-http-api "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [compojure "1.6.1"]
                 [ring/ring-defaults "0.3.2"]
                 ;; JSON
                 [ring/ring-json "0.4.0"]
                 [cheshire "5.5.0"]
                 ;; jetty adapter
                 [ring/ring-core "1.6.3"]
                 [ring/ring-jetty-adapter "1.6.3"]
                 [ring/ring-devel "1.6.3"]]
  :plugins [[lein-ring "0.12.5"]]
  :ring {:handler exoscale-application-http-api.handler/app}
  :main exoscale-application-http-api.handler/main
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.3.2"]
                        ;; http requests
                        [clj-http "3.10.0"]]}})
