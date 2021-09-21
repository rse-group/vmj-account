javac -d classes --module-path $1 $(find src/$1 -name "*.java") src/$1/module-info.java 
mkdir $1
jar --create --file $1/$1.jar -C classes .
rm -r classes
echo "  $1.jar is created"