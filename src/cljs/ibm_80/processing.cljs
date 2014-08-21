(ns ibm-80.processing
  (:require-macros [enfocus.macros :as em])
  (:require [enfocus.core :as ef]
            [enfocus.events :as ev]
            [ibm-80.parser.record :refer [guess-delimeter]]))

(defn- process-on-client [data]
  (str "The " (:processingLocation data) 
       " thinks the delimiter is: '" 
       (guess-delimeter (:testString data)) 
       "'"))

(defn- process-on-server [data]
  "Cannot process on server yet (sorry).")


(defn process [evt]
  (let [form-data (ef/from "#sortingForm" (ef/read-form))
        processing-fn (case (:processingLocation form-data)
                        "server" process-on-server
                        "client" process-on-client)]
    (ef/at "#messageArea" (ef/content (processing-fn form-data)))
    (.preventDefault evt)))

(defn ^:export init []
  (ef/at "#submit" (ev/listen :click process)))
