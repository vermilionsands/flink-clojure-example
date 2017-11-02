# flink-clojure-example

Flink SocketTextStreamWordCount example implemented in Clojure. 

## Usage

* Compile with```lein uberjar```. It would generate ```flink-clojure-example-standalone.jar```. 

* See ([Flink Quickstart](https://ci.apache.org/projects/flink/flink-docs-release-1.3/quickstart/setup_quickstart.html#run-the-example)) for setting up Flink. 

* Start **netcat**:

    ```nc -l 9000```

* Submit the Flink program:

    ```<flink-dir>/bin/flink run -c flink_example.socket_stream_word_count <uberjar-path>/flink-clojure-example-standalone.jar localhost 9000```

* Type some text to **netcat**

* Check Flink's *.out* logs for output produced by the application.

## License

Copyright © 2017 vermilionsands

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
