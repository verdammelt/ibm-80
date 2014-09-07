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

(deftest parsing-unknown-delimeter-string
  (is (thrown-with-msg? 
       #+clj Exception #+cljs js/Error
       #"unknown data format"
       (parse-string "Abercrombie:Neil:Male:Tan:2/13/1943"))))

(deftest parsing-comma-delimited-string
  (let [test-string "Abercrombie, Neil, Male, Tan, 2/13/1943"
        record (parse-string test-string)]
    (are [key expected] (= expected (get record key))
         :last-name "Abercrombie"
         :first-name "Neil"
         :gender "Male"
         :favorite-color "Tan"
         :date-of-birth "2/13/1943")))

(deftest parsing-space-delimited-string
  (let [test-string "Kournikova Anna F F 6-3-1975 Red"
        record (parse-string test-string)]
    (are [key expected] (= expected (get record key))
         :last-name "Kournikova"
         :first-name "Anna"
         :middle-initial "F"
         :gender "F"
         :favorite-color "Red"
         :date-of-birth "6-3-1975")))

(deftest parsing-pipe-delimited-string
  (let [test-string "Smith | Steve | D | M | Red | 3-3-1985"
        record (parse-string test-string)]
    (are [key expected] (= expected (get record key))
         :last-name "Smith"
         :first-name "Steve"
         :middle-initial "D"
         :gender "M"
         :favorite-color "Red"
         :date-of-birth "3-3-1985")))
