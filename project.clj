(defproject ibm-80 "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]]
  :plugins [[com.keminglabs/cljx "0.4.0"]]

  :source-paths ["target/src/clj"]
  :test-paths ["target/test/clj"]

  :hooks [cljx.hooks]

  :cljx {:builds [{:source-paths ["src/cljx"]
                   :output-path "target/src/clj"
                   :rules :clj}
                  {:source-paths ["test/cljx"]
                   :output-path "target/test/clj"
                   :rules :clj}]})

