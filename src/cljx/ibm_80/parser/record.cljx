(ns ibm-80.parser.record
  (:require [clojure.string :as string]))

(defn guess-delimeter [str]
  (some #{\space \, \|} str))

(defmulti parse-string guess-delimeter)
(defmethod parse-string \, [s]
  (let [split-string (string/split s #",")]
    {:last-name (string/trim (get split-string 0))
     :first-name (string/trim (get split-string 1))
     :gender (string/trim (get split-string 2))
     :favorite-color (string/trim (get split-string 3))
     :date-of-birth (string/trim (get split-string 4))}))
