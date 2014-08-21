(ns ibm-80.processing
  (:require-macros [enfocus.macros :as em])
  (:require [enfocus.core :as ef]
            [enfocus.events :as ev]
            [ibm-80.parser.record :refer [guess-delimeter]]))

(defn process [evt]
  (let [testString (ef/from "#testString" (ef/read-form-input))
        delim (guess-delimeter testString)
        message (str "I think the delimeter is: '" delim "'")]
    (ef/at "#messageArea" (ef/content message))
    (.preventDefault evt)))

(defn ^:export init []
  (ef/at "#submit" (ev/listen :click process)))
