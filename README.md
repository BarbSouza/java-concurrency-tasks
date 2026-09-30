# 🧵 Java Concurrency Tasks

A Java application that applies multithreading to three common tasks (standard deviation, matrix multiplication and merge sort) on a 200-value CSV dataset, comparing the trade-offs of concurrency against a single-threaded approach. Built individually for the **Concurrent Systems** module (Year 3, CCT College Dublin).

## What it does

| Task | Concurrency approach |
|---|---|
| **Standard deviation** | Two rounds of `Runnable` worker threads: `SumThread`s compute partial sums over data chunks to get the mean, then `VarianceThread`s compute partial sums of squared differences. Each thread writes to its own field, so no locking is needed; results are combined after `join()`. |
| **Matrix multiplication** | The 200 values are split into two 10×10 matrices. `RowTask` threads each compute a block of rows of the result, since every row can be calculated independently. |
| **Merge sort** (largest to smallest) | `ForkJoinPool` with a `RecursiveAction`: sub-arrays larger than a threshold of 25 are split, with the left half forked in parallel; smaller sub-arrays are sorted sequentially to avoid overhead. |

Each thread logs what it's working on, so the console output shows the work being divided up.

## Tech

Java · `Thread` / `Runnable` · `ForkJoinPool` / `RecursiveAction` · `AtomicInteger` · Maven · IntelliJ IDEA

```
ConcurrentSystems/src/main/java/org/example/
├── Main.java                  Runs the three tasks in order and prints the results
├── CSVReader.java             Reads data.csv into a double[]
├── StandardDeviation.java     SumThread + VarianceThread
├── MatrixMultiplication.java  RowTask threads
└── MergeSort.java             Fork/join merge sort
```

## How to run

**You need:** a JDK. The Maven project (`ConcurrentSystems/pom.xml`) targets Java 24, but the code itself uses nothing newer than Java 8.

From the repository's root folder (where `data.csv` is):

```bash
mkdir out
javac -d out ConcurrentSystems/src/main/java/org/example/*.java
java -cp out org.example.Main
```

Or open the `ConcurrentSystems` folder in IntelliJ IDEA as a Maven project and run `Main`, with the working directory set to the repository root.

**Sample output:**

```
Total values: 200
Total sum: 2344.0, Mean: 11.72
Standard Deviation: 7.5678002087793 calculated using 5 threads.
Total parallel tasks forked: 7
```

## Notes and known limitations

- With only 200 values, creating and joining threads costs more than it saves, so a single-threaded version would be faster here. The design is meant to scale to larger datasets and more cores.
- The final merge at each level of the merge sort runs sequentially, which limits the overall speed-up (Amdahl's Law).
- CSV reading stays single-threaded, since each line depends on the file pointer advancing from the previous one.
- The matrix split assumes exactly 200 values (two 10×10 matrices).

## Credits

- `data.csv` was provided as part of the assignment brief.
- The thread-per-chunk and fork/join patterns follow code examples from the module's lectures.
