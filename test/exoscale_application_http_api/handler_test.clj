(ns exoscale-application-http-api.handler-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [cheshire.core :as json]
            [exoscale-application-http-api.storage.handlers :as h]
            [exoscale-application-http-api.storage.in-memory :refer [in-memory-storage]]
            [exoscale-application-http-api.handler :refer :all]
            [exoscale-application-http-api.storage :as st]))

(deftest test-app
  (testing "main route"
    (let [response (app (mock/request :get "/"))]
      (is (= (:status response) 200))
      (is (= (:body response) "Hello World"))))

  (testing "not-found route"
    (let [response (app (mock/request :get "/invalid"))]
      (is (= (:status response) 404)))))

(defn create-request [method path body]
  (-> (mock/request method path (json/encode body))
      (update :body slurp)
      (update :body json/decode)))

(def job-mock {"title" "Chef"
               "company" "Vapiano"
               "description" "Manage Cooks"})

(defn list-n-jobs-mock
  "Returns a map of id -> jobs"
  [n]
  (zipmap (map str (range 0 n)) (take n (repeat job-mock))))

;; TEST STORAGE HANDLERS

(deftest list-jobs-test
  (let [stg (in-memory-storage)
        id-jobs (list-n-jobs-mock 3)]
    (doseq [[id job] id-jobs]
      (st/create-job stg id job))

    (let [handler (h/list-jobs-handler stg)
          response (handler (mock/request :get "/jobs"))
          parsed-jobs (json/decode (:body response))]
      (testing "the response is a 200"
        (is (= 200 (:status response))))

      (testing "with a body that decodes to the original map"
        (is (= id-jobs parsed-jobs))))))

(deftest delete-job-test
  (let [stg (in-memory-storage)
        id "job-id"
        job job-mock]

    (testing "when the job exists"
      (st/create-job stg id job)
      (let [response (h/delete-job stg id)]
        (testing "the response is a 204"
          (is (= 204 (:status response)))

          (testing "the job is deleted"
            (is (nil? (st/get-job stg id)))))))

    (testing "when the job does not exist"
      (let [response (h/delete-job stg "bogus")]
        (testing "the response is still 204"
          (is (= 204 (:status response))))))))

(deftest create-job-test
  (let [stg (in-memory-storage)
        job job-mock
        request (create-request :post "/jobs" job)]
    (testing "when the ID does not exist"
      (let [response (h/create-job stg "job-id" request)]
        (testing "the result is a 200"
          (is (= 200 (:status response)))

          (testing "with the expected body"
            (is (= job ((:body response) "job-id"))))

          (testing "and the job is actually created"
            (is (= job (st/get-job stg "job-id")))))))

    (testing "when the ID does exist"
      (let [response (h/create-job stg "job-id" request)]
        (testing "the result is a 422"
          (is (= 422 (:status response))))))))
