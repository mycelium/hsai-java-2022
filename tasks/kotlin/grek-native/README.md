Grek on Kotlin/Native

Different repo because of native differences.

Usage: Grek.exe [-n] [-r] [-i] [-A INT] [-B INT] [--exclude TEXT] REGEX PATH

Options:
- -n              enable line numbers
- -r              recursive folder mode
- -A INT          print NUM lines of trailing context after matching lines
- -B INT          print NUM lines of leading context before matching lines
- -i              case-insensitive mode
- --exclude TEXT  exclude files
- -h, --help      Show help message and exit

Arguments:
REGEX  regexp to match files
PATH   path to search in

/target directory has build mingw64 (Windows x64) binary.

Example (from target folder): Grek.exe -A 5 -B 5 -n -r --exclude 2.json uwu ../src/nativeTest/resources
