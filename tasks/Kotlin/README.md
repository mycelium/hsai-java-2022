Usage: grek [-hinV] [-A=<afterNum>] [-B=<beforeNum>] [-r[--exclude=<exclusivePathsAll>]] <regex> <path>
Use the regular expression to get satisfied strings from the files.
-      <regex>         Regular expression for the satisfied string.
-      <path>          Path of the file or the folder of the files to be scanned.
-      -A, --after-context=<afterNum>
                      Print afterNum lines of trailing context after matching
                        lines.
-      -B, --before-context=<beforeNum>
                      Print beforeNum lines of leading context before matching
                        lines.
-      --exclude=<exclusivePathsAll>
                      The exclusive paths not to scan.
                      (In format like "a\b.txt a\a.txt")
-      -h, --help          Show this help message and exit.
-      -i, --ignore-case   Ignore case shifting.
-      -n, --line-number   Show line and file name of satisfied strings.
-      -r, --recursive     Recursively scan all the files in the folder.
-      -V, --version       Print version information and exit.
Exaples:
- ```.\grek -inr --exclude "Samples\a\a\sampleaa1.txt" -A 3 -B 2 "([\w]+\.?)+@([\w]+\.)[\w]+" "Samples" ```
- ```.\grek -nr "asd" "Samples" ```
