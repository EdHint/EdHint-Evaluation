# EdSynth-evaluation
This repository provides experimental code and results of EdSynth, an execution driven synthesis tool that handles  large,  complex  APIs  in  the  presence  of conditional  statements,  loops,  and  more  advanced  constructs  of commonly  used  languages.

We provide two sets of benchmarks that handle non-straight-line code fragments and straight-line code fragments respectively.

## Straight-line benchmarks
Each benchmark contains a Main.java file that includes both test cases and a main method that runs execution-driven sketching engine.
To run a benchmark with specific time limit, go to a benchmark directory and run:
```
ant compile && ant run -Dargs=[your time limit]
```
We also include our experimental logs for reference.

## Non-straight-line benchmarks
Our non-straight-line benchamrks use real-life projects that are too big to fit into this repository. To illustrate, we only put files that contain our target code fragment and test files that reach these target code. We also include a file named Synthesis.java that runs our execution-driven sketching engine. Our experimental logs are also included for reference. 
