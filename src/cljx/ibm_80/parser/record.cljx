(ns ibm-80.parser.record)

(defn guess-delimeter [str]
  (some #{\space \, \|} str))
