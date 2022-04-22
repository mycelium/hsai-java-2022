Usage: RandomGenerator [-hV] [-n=<num>] [-p=<path>] ([-dist-g
                       [-sd-g=<standardDeviation>] [-e-g=<expect>]] | [-dist-p
                       [-e-p=<expect>]] | [-dist-u [-l-u=<lowerBound>]
                       [-u-u=<upperBound>]]) ([-csv] | [-sql])
Get random numbers of different distributions.
-      -csv                Store the results in the CSV format file.
-      -dist-g             Distribution of Gaussian
-      -dist-p             Distribution of Poisson
-      -dist-u             Uniform distribution
-      -e-g=<expect>       Expection value of distribution of Gaussian
-      -e-p=<expect>       Expection value of distribution of Poisson
-      -h, --help              Show this help message and exit.
-      -l-u=<lowerBound>   Lowerbound
-      -n, --number=<num>      Number of random numbers to gernerate.
-      -p, --path=<path>       Path to store the results.
-      -sd-g=<standardDeviation>   Standard deviation
-      -sql                Store the results in the Sqlite database.
-      -u-u=<upperBound>   Upperbound
-      -V, --version           Print version information and exit.

Example: 
- ```java -jar random.jar -sql -dist-p -e-p 50 -p test.db -n 1500```
- ```java -jar random.jar -csv -dist-g -sd-g 10 -e-g 50 -p test.csv -n 1029```
- ```java -jar random.jar -csv -dist-u -l-u 10 -u-u 15 -p random.csv -n 2000```
