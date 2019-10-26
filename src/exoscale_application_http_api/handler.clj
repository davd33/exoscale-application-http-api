(ns exoscale-application-http-api.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.adapter.jetty :refer [run-jetty]]
            [ring.middleware.defaults :refer [wrap-defaults api-defaults]]
            [exoscale-application-http-api.storage.in-memory :as st]
            [exoscale-application-http-api.storage.handlers :as h]
            [exoscale-application-http-api.middleware :as m]
            [ring.middleware.json :refer [wrap-json-response]]
            [ring.middleware.json :as json]))

(defn app-routes
  [stg next-id]
  (routes
   (GET "/" [] "It Works! Try a GET /jobs :)")
   (GET "/jobs" [] (fn [_] (h/list-jobs stg)))
   (POST "/jobs" [] (fn [r] (h/create-job stg (next-id) r)))
   (DELETE "/jobs/:id" [id] (fn [_] (h/delete-job stg (Integer. id))))
   (route/not-found "Not Found")))

(def app
  (let [stg (st/in-memory-storage)
        id-counter (atom 0)]
    (-> (app-routes stg #(swap! id-counter inc))
        (wrap-routes (comp wrap-json-response
                           json/wrap-json-body))
        (wrap-defaults api-defaults))))

(defn main [& args]
  (run-jetty app {:port 8080}))
