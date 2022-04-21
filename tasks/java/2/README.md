## Build
`javac -d target --module-source-path modules $(find modules -name "*.java" | grep -v "Test.java") --module-path lib`
## Run
`java --module-path target:lib -m app/app.Main type normal output sqlite file ./output.db mean 20 stddev 1 count 10000`
