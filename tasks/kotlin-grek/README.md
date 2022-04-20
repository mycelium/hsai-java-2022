    Usage: app [OPTIONS] REGEX PATH
    
    Options:
    -n, --line-number         Each output line is preceded by its relative line
    number in the file, starting at line 1. The line
    number counter is reset for each file processed.
    -R, -r, --recursive       Recursively search subdirectories listed.
    -A, --after-context INT   Print num lines of trailing context after each
    match.
    -B, --before-context INT  Print num lines of leading context before each
    match.
    -i, --ignore-case         Perform case insensitive matching. By default,
    grek is case sensitive.
    --exclude TEXT            If specified, it excludes files matching the given
    filename.
    -h, --help                Show this message and exit
    
    Arguments:
    REGEX  Regexp to match files.
    PATH   Path to search in.
