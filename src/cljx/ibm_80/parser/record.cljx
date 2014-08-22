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
  (apply hash-map
         (interleave 
          [:last-name :first-name :gender :favorite-color :date-of-birth]
          (split-and-trim s #","))))
(defmethod parse-string \space [s]
  (apply hash-map
         (interleave
          [:last-name :first-name :middle-initial :gender :date-of-birth :favorite-color]
          (split-and-trim s #" "))))
(defmethod parse-string \| [s]
  (apply hash-map
         (interleave
          [:last-name :first-name :middle-initial :gender :favorite-color :date-of-birth]
          (split-and-trim s #"\|"))))
