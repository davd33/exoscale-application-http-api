(ns exoscale-application-http-api.storage.in-memory
  (:require [exoscale-application-http-api.storage :refer :all]))

(defn create-job*
  [!stg id content]
  (when-not (contains? @!stg id)
    (swap! !stg assoc id content)
    id))

(defn get-job*
  [!stg id]
  (when (contains? @!stg id)
    (get @!stg id)))

(defn delete-job*
  [!stg id]
  (if (contains? @!stg id)
    (swap! !stg dissoc id)
    nil))

(def list-jobs* deref)

(defn in-memory-storage
  []
  (let [!stg (atom {})]
    (reify Storage
      (create-job [_ id content]
        (create-job* !stg id content))
      (get-job [_ id]
        (get-job* !stg id))
      (delete-job [_ id]
        (delete-job* !stg id))
      (list-jobs [_]
        (list-jobs* !stg)))))
