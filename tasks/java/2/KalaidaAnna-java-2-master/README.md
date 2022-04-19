#### Java - 2

## Production
### Build
```bash
javac -d target --module-source-path modules $(find modules -name "*.java" | grep -v "Test.java") --module-path lib
```

### Run
```bash
java --module-path target:lib -m app/main.Main -d uniform --origin 10 --bound 20 -n 10 -f csv -t result-dir
```
