{:dev
 {:plugins [[com.cemerick/clojurescript.test "0.3.1"]
            [com.cemerick/austin "0.1.4"]]
  
  :cljx
  {:builds [{:source-paths ["test/cljx"]
             :output-path "target/test/clj"
             :rules :clj}
            {:source-paths ["test/cljx"]
             :output-path "target/test/cljs"
             :rules :cljs}]}

  :cljsbuild 
  {:builds {:dev-test
            {:source-paths ["target/test/cljs"]
             :compiler {:output-to "target/test/js/ibm80-test.js"
                        :optimizations :whitespace
                        :pretty-print true}}}
   :test-commands {"dev"
                   ["phantomjs" 
                    :runner "target/test/js/ibm80-test.js"]}}}}
