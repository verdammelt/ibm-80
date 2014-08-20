(ns ibm-80.parser.record-test
  #+cljs
  (:require-macros [cemerick.cljs.test :refer [deftest testing is are]])
  (:require 
   #+clj [clojure.test :refer [deftest testing is are]]
   #+cljs [cemerick.cljs.test :as t]
   [ibm-80.parser.record :refer [guess-delimeter]]))

(deftest guessing-delimieter
  (are [expected actual] (= expected (guess-delimeter actual))
       \, "foo,bar"
       \space "foo bar"
       \| "foo|bar"
       nil "foobar"))
