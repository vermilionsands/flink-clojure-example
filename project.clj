(defproject flink-clojure-example "0.1.0-SNAPSHOT"
  :description "Sample Flink application in Clojure"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.apache.flink/flink-streaming-java_2.10 "1.3.2"]
                 [org.apache.flink/flink-java "1.3.2"]
                 [org.apache.flink/flink-clients_2.10 "1.3.2"]]
  :jar-name "flink-clojure-example.jar"
  :uberjar-name "flink-clojure-example-standalone.jar"
  :aot :all)
