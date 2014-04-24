(defproject maths "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/math.numeric-tower "0.0.4"]
                 [local/gluegen "1.0.0"]
                 [local/jogl "1.0.0"]]
  :repositories {"local" ~(str (.toURI (java.io.File. "mvn_jars")))}
  :main ^:skip-aot maths.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
