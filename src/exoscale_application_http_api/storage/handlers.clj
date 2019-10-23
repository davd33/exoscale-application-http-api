(ns exoscale-application-http-api.storage.handlers
  (:require [ring.util.request :as req]
            [ring.util.response :as res]
            [exoscale-application-http-api.storage :as st]))

(defn get-job
  [stg id]
  (if-let [job (st/get-job stg id)]
    (res/response job)
    (res/not-found "Sorry, that job is not referenced.")))
