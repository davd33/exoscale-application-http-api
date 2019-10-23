(ns exoscale-application-http-api.storage)

(defprotocol Storage
  (create-job [this id job]
    "Store the job under the ID.
     Returns if:
       - success: ID of the created job
       - ID already in use: nil")
  (get-job [this id]
    "Returns a job for the associated ID.
     Returns nil if there's no job")
  (delete-job [this id]
    "Removes a job with the given ID.
     Returns if:
       - success: ID of the deleted job
       - ID doesn't exist: nil")
  (list-jobs [this]
    "Returns a map of all known IDs to jobs."))
