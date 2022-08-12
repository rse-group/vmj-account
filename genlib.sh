javac -d cclasses --module-path $1 $(find src/$1 -name "*.java") src/$1/module-info.java
if [ ! -d "liba" ]; then mkdir -p liba; fi
jar --create --file liba/$1.jar -C cclasses .
rm -r cclasses
echo "  $1.jar is created"