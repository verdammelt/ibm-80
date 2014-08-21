(ns ibm-80.parser.record-test
  #+cljs
  (:require-macros [cemerick.cljs.test :refer [deftest testing is are]])
  (:require 
   #+clj [clojure.test :refer [deftest testing is are]]
   #+cljs [cemerick.cljs.test :as t]
   [ibm-80.parser.record :refer [guess-delimeter parse-string]]))

(deftest guessing-delimieter
  (are [expected actual] (= expected (guess-delimeter actual))
       \, "foo,bar"
       \space "foo bar"
       \| "foo|bar"
       nil "foobar"))

(deftest parsing-comma-delimited-string
  (let [test-string "Abercrombie, Neil, Male, Tan, 2/13/1943"
        record (parse-string test-string)]
    (are [expected key] (= expected (get record key))
         "Abercrombie" :last-name
         "Neil" :first-name
         "Male" :gender
         "Tan" :favorite-color
         "2/13/1943" :date-of-birth)))
