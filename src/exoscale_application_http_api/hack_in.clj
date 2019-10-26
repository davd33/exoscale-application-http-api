(ns exoscale-application-http-api.hack-in
  (:require [cheshire.core :as json]
            [clj-http.client :as http]))

;; If not -> running with jetty (lein run)
(def running-ring false)

(def envs
  {:dev
   {:host "localhost"
    :ring? running-ring
    :port (if running-ring 3000 8080)}})

(def ^:dynamic *selected-env* :dev)

(defn env [get-in-l]
  (get-in envs (cons *selected-env* get-in-l)))

(defn env-port []
  (env [:port]))

(defn url [path]
  (let [host (env [:host])
        port (env-port)]
    (str "http://" host ":" port path)))

;; FOLLOWING SOME HTTP CALLS TO THE API

(defn create-request
  [callback]
  (fn make-request
    ([] (make-request false))
    ([debug]
     (binding [*selected-env* :dev]
       (when debug (println (str "calling: " (url ""))))
       (callback)))))

(def jobs-url (url "/jobs"))

(def list-jobs
  (create-request
   #(http/get jobs-url)))

(def create-job
  (create-request
   #(http/post
     jobs-url
     {:body (json/encode {:title "Cook"
                          :company "Chez Paul"
                          :description "make croissants"})
      :content-type :json
      :accept :json})))

;; CALL API

(list-jobs)
(create-job)
