(ns ibm-80.parser.record
  (:require [clojure.string :as string]))

(defn guess-delimeter [str]
  (some #{\space \, \|} str))

(defn parse-string [data-line]
  (let [delimiter (guess-delimeter data-line)
        split-string (string/split data-line (re-pattern (str delimiter)))]
    {:last-name (string/trim (get split-string 0))
     :first-name (string/trim (get split-string 1))
     :gender (string/trim (get split-string 2))
     :favorite-color (string/trim (get split-string 3))
     :date-of-birth (string/trim (get split-string 4))}))
