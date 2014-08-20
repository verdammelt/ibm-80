(defproject ibm-80 "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :source-paths ["target/src/clj"]
  :test-paths ["target/test/clj"]

  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/clojurescript "0.0-2311"]]
  :plugins [[com.keminglabs/cljx "0.4.0" :exclusions [org.clojure/clojure]]
            [lein-cljsbuild "1.0.3"]
            [com.cemerick/clojurescript.test "0.3.1"]
            [com.cemerick/austin "0.1.4"]]

  :hooks [cljx.hooks leiningen.cljsbuild]

  :cljx 
  {:builds [{:source-paths ["src/cljx"]
             :output-path "target/src/clj"
             :rules :clj}
            {:source-paths ["src/cljx"]
             :output-path "target/src/cljs"
             :rules :cljs}
            {:source-paths ["test/cljx"]
             :output-path "target/test/clj"
             :rules :clj}
            {:source-paths ["test/cljx"]
             :output-path "target/test/cljs"
             :rules :cljs}]}

  :cljsbuild
  {:builds {:dev
            {:source-paths ["target/src/cljs"]
             :compiler {:output-to "resources/public/js/ibm80.js"
                        :optimizations :whitespace
                        :pretty-print true}}
            :dev-test
            {:source-paths ["target/test/cljs"]
             :compiler {:output-to "target/test/js/ibm80-test.js"
                        :optimizations :whitespace
                        :pretty-print true}}}
   :test-commands {"dev"
                   ["phantomjs" :runner "target/test/js/ibm80-test.js"]}}
)
