(ns ibm-80.parser.record
  (:require [clojure.string :as string]))

(defn guess-delimeter [str]
  (or (some #{\, \|} str)
     (some #{\space} str)))

(defn- split-and-trim [str delimiter]
  (map string/trim (string/split str delimiter)))

(defn- validate-length [n record]
  (if (not (= n (count record)))
    (throw (#+clj Exception. #+cljs js/Error.
                  "incorrect number of fields"))
    record))

(defn- string->record [s delimiter fields]
  (apply hash-map
         (interleave fields
                     (validate-length (count fields)
                                      (split-and-trim s delimiter)))))

(defmulti parse-string guess-delimeter)
(defmethod parse-string :default [s]
  (throw (#+clj Exception. #+cljs js/Error. "unknown data format")))
(defmethod parse-string \, [s]
  (string->record s #","
                  [:last-name :first-name
                   :gender :favorite-color :date-of-birth]))
(defmethod parse-string \space [s]
  (string->record s #" "
                  [:last-name :first-name :middle-initial
                   :gender :date-of-birth :favorite-color]))
(defmethod parse-string \| [s]
  (string->record s #"\|"
                  [:last-name :first-name :middle-initial
                   :gender
                   :favorite-color :date-of-birth]))
