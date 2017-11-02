(ns flink-example.socket-stream-word-count
  (:import (org.apache.flink.streaming.api.environment StreamExecutionEnvironment)
           (org.apache.flink.api.common.functions FlatMapFunction)
           (org.apache.flink.util Collector)
           (org.apache.flink.api.java.typeutils TupleTypeInfo)
           (org.apache.flink.api.java.tuple Tuple2))
  (:gen-class))

(def line-splitter-proxy
  (proxy [FlatMapFunction] []
    (flatMap [^String x ^Collector acc]
      (let [tokens (.split (.toLowerCase x) "\\W+")]
        (doseq [t tokens]
          (when (> (count t) 0)
            (.collect acc (Tuple2. t (int 1)))))))))

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
                     (.returns (TupleTypeInfo/getBasicTupleTypeInfo (into-array [String Integer])))
                     (.keyBy (int-array [0]))
                     (.sum (int 1)))]
      (.print counts)
      (.execute env "Clojure WordCount from SocketTextStream example"))))