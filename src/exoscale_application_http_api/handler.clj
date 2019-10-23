(ns exoscale-application-http-api.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.adapter.jetty :refer [run-jetty]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(defroutes app-routes
  (GET "/" [] "Hello World")
  (GET "/jobs" [] "Hello World")
  (POST "/jobs" [] "Hello World")
  (DELETE "/jobs/:id" [] "Hello World")
  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))

(defn main [& args]
  (run-jetty app {:port 8080}))
