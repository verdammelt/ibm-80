(defproject ibm-80 "0.1.0-SNAPSHOT"
  :description "A sorting machine like the IBM-80 series."
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :source-paths ["src/clj" "target/src/clj"]
  :test-paths ["target/test/clj"]

  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/clojurescript "0.0-2311"]
                 [enfocus "2.1.0"]]
  :plugins [[com.keminglabs/cljx "0.4.0" :exclusions [org.clojure/clojure]]
            [lein-cljsbuild "1.0.3"]
            [lein-ring "0.8.11"]]

  :hooks [cljx.hooks leiningen.cljsbuild]

  :ring {:handler ibm-80.app/app}

  :cljx 
  {:builds [{:source-paths ["src/cljx"]
             :output-path "target/src/clj"
             :rules :clj}
            {:source-paths ["src/cljx"]
             :output-path "target/src/cljs"
             :rules :cljs}]}

  :cljsbuild
  {:builds {:dev
            {:source-paths ["src/cljs" "target/src/cljs"]
             :compiler {:output-to "resources/public/js/ibm_80.js"
                        :optimizations :whitespace
                        :pretty-print true}}
            }})
