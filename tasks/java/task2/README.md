Usage: double [-hV] -n=\<number> -o=\<outputType> -op=\<outputPath>
((--normal-dist --mean=\<mean> --variance=\<variance>) |
(--uniform-dist --min=\<min> --max=\<max>) | (--poisson-dist
--lambda=\<lambda>))

Generate double distribution

- -h, --help              Show this help message and exit.
- --lambda=\<lambda>   lambda parameter for poisson
- --max=\<max>         max value of the uniform distribution
- --mean=\<mean>       Mean of the normal distribution
- --min=\<min>         min value of the uniform distribution
- -n, --number=\<number>   Number of values to generate, minimum 10000
- --normal-dist       Normal distribution
- -o, --output=\<outputType> Output type
- -op, --output_path=\<outputPath> Output file path
- --poisson-dist      Poisson distribution
- --uniform-dist      Uniform distribution
- -V, --version           Print version information and exit.
- --variance=\<variance> Variance of the normal distribution

Usage example: 
- ```java.exe -jar ./out/artifacts/task2_jar/task2.jar -n 100000 -o SQLite -op C:\tst --poisson-dist --lambda 100000```
- ```java.exe -jar ./out/artifacts/task2_jar/task2.jar -n 100000 -o SQLite -op C:\tst --normal-dist --variance 100 --mean 10```
- ```java.exe -jar ./out/artifacts/task2_jar/task2.jar -n 100000 -o SQLite -op C:\tst  --uniform-dist --min 100 --max 1000```
