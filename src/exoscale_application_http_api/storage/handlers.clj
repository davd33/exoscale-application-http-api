(ns exoscale-application-http-api.storage.handlers
  (:require [ring.util.request :as req]
            [ring.util.response :as res]
            [exoscale-application-http-api.storage :as st]
            [cheshire.core :as json]))

(defn create-job
  [stg id {job :body}]
  (if (st/create-job stg id job)
    (res/response {id job})
    (-> (format "The id %s is already in use." id)
        res/response
        (res/status 422))))

(defn delete-job
  [stg id]
  (res/response (if-let [res (st/delete-job stg id)]
                  res
                  {:error (str "ID " id " doesn't exist.")}))
  (-> (res/response "")
      (res/status 204)))

(defn list-jobs
  [stg]
  (res/response (st/list-jobs stg)))
