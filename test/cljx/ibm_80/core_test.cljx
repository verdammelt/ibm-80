(ns ibm-80.core-test
  #+cljs
  (:require-macros [cemerick.cljs.test :refer [deftest testing is are]])
  (:require 
   #+clj [clojure.test :refer [deftest testing is are]]
   #+cljs [cemerick.cljs.test :as t]
   [ibm-80.core :refer []]))

(deftest a-test
  (testing "FIXME, I fail."
    (is (= 0 1))))
