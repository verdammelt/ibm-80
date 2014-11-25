(ns ibm-80.parser.record-test
  #+cljs
  (:require-macros [cemerick.cljs.test :refer [deftest testing is are assert-expr]])
  (:require
   #+clj [clojure.test :refer [deftest testing is are assert-expr]]
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
       #+clj Exception
       #+cljs js/Error
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
         :date-of-birth [1943 2 13])))

(deftest parsing-space-delimited-string
  (let [test-string "Kournikova Anna F F 6-3-1975 Red"
        record (parse-string test-string)]
    (are [key expected] (= expected (get record key))
         :last-name "Kournikova"
         :first-name "Anna"
         :middle-initial "F"
         :gender "Female"
         :favorite-color "Red"
         :date-of-birth [1975 6 3])))

(deftest parsing-pipe-delimited-string
  (let [test-string "Smith | Steve | D | M | Red | 3-3-1985"
        record (parse-string test-string)]
    (are [key expected] (= expected (get record key))
         :last-name "Smith"
         :first-name "Steve"
         :middle-initial "D"
         :gender "Male"
         :favorite-color "Red"
         :date-of-birth [1985 3 3])))

(defmethod assert-expr 'invalid-number-of-fields? [msg form]
  (assert-expr msg (conj (rest form)
                         #"incorrect number of fields"
                         #+clj Exception
                         #+cljs js/Error
                         'thrown-with-msg?)))

(defmethod assert-expr 'invalid-gender? [msg form]
  (assert-expr msg (conj (rest form)
                         #"incorrect gender"
                         #+clj Exception
                         #+cljs js/Error
                         'thrown-with-msg?)))

(deftest validates-that-string-has-correct-number-of-fields
  (are [s] (invalid-number-of-fields? (parse-string s))
       "Abercrombie,Neil,Male"
       "Abercrombie, Neil, Male, Tan, 2/13/1943,other"
       "Kournikova Anna F F 6-3-1975"
       "Kournikova Anna F F 6-3-1975 Red other"
       "Smith | Steve | D | M | Red"
       "Smith | Steve | D | M | Red | 3-3-1985 | other"
       ))

(deftest validates-gender
  (is (invalid-gender?
       (parse-string "Smith | Steve | D | W | Red | 3-3-1985"))))
