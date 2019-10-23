(ns exoscale-application-http-api.storage-test
  (:require [exoscale-application-http-api.storage :refer :all]
            [exoscale-application-http-api.storage.in-memory :as in-m]
            [clojure.test :refer :all]))

(defn is-valid-storage
  "Assert that storage protocol implementation fulfills the contract."
  [stg]
  (let [content {:company "Sun"
                 :title "Cook"
                 :description "Cook Italian food!"}
        id "italian-job"]
    (testing "can store and retrieve a job"
      (testing "create-job returns the ID"
        (is (= id (create-job stg id content)))

        (testing "and doesn't overwrite an existing ID"
          (is (nil? (create-job stg id "bug")))
          (is (= content (get-job stg id))))))

    (testing "can delete a job"
      (delete-job stg id)
      (is (nil? (get-job stg id))))

    (testing "can list all jobs"
      (let [three-contents (take 3 (repeat content))
            three-ids (map str (range 0 3))
            id-contents (zipmap three-ids three-contents)
            ids (doseq [[id content] id-contents]
                  (create-job stg id content))
            contents (list-jobs stg)]

        (testing "in a map"
          (is (map? contents))

          (testing "equal to the contents we created"
            (is (= id-contents contents))))))))

(deftest in-memory-storage-test
  (let [stg (in-m/in-memory-storage)]
    (is-valid-storage stg)))
