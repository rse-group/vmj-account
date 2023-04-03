#!/bin/bash
set -e

err(){
    echo "Error: $*" >>/dev/stderr
}


function copy_vmj_libraries_and_their_dependencies_to_module_path() {
    cp winvmj-libraries/*.jar $product
    echo "copy libraries"
}

product=$1
feature=$2
if [ -d "$1" ]; then rm -r $1; fi
mkdir $1
copy_vmj_libraries_and_their_dependencies_to_module_path

echo -e "building the module: $feature"
javac -d cclasses --module-path $product $(find src/$feature -name "*.java") src/$feature/module-info.java
jar --create --file $product/$feature.jar -C cclasses .
rm -r cclasses