(ns flink-example.socket-stream-word-count
  (:require [flink-example.util.genclass :as g])
  (:import (org.apache.flink.streaming.api.environment StreamExecutionEnvironment)
           (org.apache.flink.api.common.functions FlatMapFunction)
           (org.apache.flink.util Collector)
           (org.apache.flink.api.common.typeinfo TypeHint))
  (:gen-class))

(g/gen-class
  :name flink_example.ResultTuple
  :extends ^{:types [String Integer]} org.apache.flink.api.java.tuple.Tuple2)

(g/gen-class
  :name flink_example.ResultHint
  :extends ^{:types [flink_example.ResultTuple]} org.apache.flink.api.common.typeinfo.TypeHint)

(import '[flink_example ResultTuple ResultHint])

(def line-splitter-proxy
  (proxy [FlatMapFunction] []
    (flatMap [^String x ^Collector acc]
      (let [tokens (.split (.toLowerCase x) "\\W+")]
        (doseq [t tokens]
          (when (> (count t) 0)
            ;; could be just Tuple2
            (.collect acc (ResultTuple. t (int 1)))))))))

(defn -main [& args]
  (if (< (count args) 2)
    (binding [*out* *err*]
      (println "USAGE:\n <hostname> <port>"))
    (let [[host port] args
          port (Integer/parseInt port)
          env (StreamExecutionEnvironment/getExecutionEnvironment)
          text-stream (.socketTextStream env host port)
          counts (-> text-stream
                     (.flatMap line-splitter-proxy)
                     (.returns ^TypeHint (ResultHint.))
                     (.keyBy (int-array [0]))
                     (.sum (int 1)))]
      (.print counts)
      (.execute env "Clojure WordCount from SocketTextStream example"))))