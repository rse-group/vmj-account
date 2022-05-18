#!/bin/bash
set -e

err(){
    echo "Error: $*" >>/dev/stderr
}

if [ -d "$1" ]; then rm -r $1; fi
if [ -d "classes" ]; then rm -r classes; fi
mkdir $1
cp winvmj-libraries/*.jar $1
javac -d classes --module-path $1 $(find src/$1 -name "*.java") src/$1/module-info.java 
jar --create --file $1/$1.jar -C classes .
rm -r classes
echo "  $1.jar is created"