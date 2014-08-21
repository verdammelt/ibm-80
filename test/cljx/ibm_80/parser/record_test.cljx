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
       \| "foo | bar"
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

(deftest parsing-unknown-delimeter-string
  (is (thrown-with-msg? 
       #+clj Exception #+cljs js/Error
       #"unknown data format"
       (parse-string "Abercrombie:Neil:Male:Tan:2/13/1943"))))

(deftest parsing-space-delimited-string
  (let [test-string "Kournikova Anna F F 6-3-1975 Red"
        record (parse-string test-string)]
    (are [expected key] (= expected (get record key))
         "Kournikova" :last-name
         "Anna" :first-name
         "F" :middle-initial
         "F" :gender
         "Red" :favorite-color
         "6-3-1975" :date-of-birth)))

(deftest parsing-pipe-delimited-string
  (let [test-string "Smith | Steve | D | M | Red | 3-3-1985"
        record (parse-string test-string)]
    (are [expected key] (= expected (get record key))
         "Smith" :last-name
         "Steve" :first-name
         "D" :middle-initial
         "M" :gender
         "Red" :favorite-color
         "3-3-1985" :date-of-birth)))
