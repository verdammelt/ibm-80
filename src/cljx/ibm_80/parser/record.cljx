(ns ibm-80.parser.record
  (:require [clojure.string :as string]))

(defn guess-delimeter [str]
  (or (some #{\, \|} str)
     (some #{\space} str)))

(defn- split-and-trim [str delimiter]
  (map string/trim (string/split str delimiter)))

(defmulti parse-string guess-delimeter)
(defmethod parse-string :default [s]
  (throw (#+clj Exception. #+cljs js/Error. "unknown data format")))
(defmethod parse-string \, [s]
  (let [split-string (split-and-trim s #",")]
    {:last-name (nth split-string 0)
     :first-name (nth split-string 1)
     :gender (nth split-string 2)
     :favorite-color (nth split-string 3)
     :date-of-birth (nth split-string 4)}))
(defmethod parse-string \space [s]
  (let [split-string (split-and-trim s #" ")]
    {:last-name (nth split-string 0)
     :first-name (nth split-string 1)
     :middle-initial (nth split-string 2)
     :gender (nth split-string 3)
     :date-of-birth (nth split-string 4)
     :favorite-color (nth split-string 5)}))
(defmethod parse-string \| [s]
    (let [split-string (split-and-trim s #"\|")]
      {:last-name (nth split-string 0)
       :first-name (nth split-string 1)
       :middle-initial (nth split-string 2)
       :gender (nth split-string 3)
       :favorite-color (nth split-string 4)
       :date-of-birth (nth split-string 5)}))
