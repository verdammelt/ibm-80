(ns ibm-80.processing
  (:require-macros [enfocus.macros :as em])
  (:require [enfocus.core :as ef]
            [enfocus.events :as ev]
            [ajax.core :refer [POST GET]]
            [goog.string :as gstring]
            [goog.string.format :as gformat]
            [ibm-80.parser.record :refer [guess-delimeter]]))

(defn- display-message [msg]
  (ef/at "#messageArea" (ef/content msg)))

(def ^:private message-format "The %s thinks the delimiter is: '%s'")

(defn- process-on-client [data]
  (display-message 
   (gstring/format message-format 
                   (:processingLocation data) 
                   (guess-delimeter (:testString data))) ))

(defn- process-on-server [data]
  (POST "/sort" 
        {:format :json
         :params data
         :error-handler #(display-message (str "An error occurred: " %))
         :handler #(display-message 
                    (gstring/format message-format 
                                    "server"
                                    (get % "delimiter")))}))

(defn process [evt]
  (.preventDefault evt)
  (let [form-data (ef/from "#sortingForm" (ef/read-form))
        processing-fn (case (:processingLocation form-data)
                        "server" process-on-server
                        "client" process-on-client)]
    (processing-fn form-data)))

(defn ^:export init []
  (ef/at "#submit" (ev/listen :click process)))
