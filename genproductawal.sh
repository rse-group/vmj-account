#!/bin/bash
set -e

err(){
    echo "Error: $*" >>/dev/stderr
}

function copy_vmj_libraries_and_their_dependencies_to_module_path() {
    cp winvmj-libraries/*.jar $product
}

function copy_databaseproperties() {
    cp auth.properties $product/
    cp database.properties $product/
    cp hibernate.properties $product/
}

function external_module()
{
    echo " add external module"
}

# function check_module()
# {
#     if [ $1 == "aisco.donation.pgateway" ]; then
#     cp external/payment.page.core.jar $product/
#     echo "  add external module"
#     elif [ $1 == "aisco.financialreport.incomewithfrequency" ]; then
#     sh ./genmodule.sh $1 $product
#     echo "  generate module $1"
#     elif [ $1 == "aisco.financialreport.expensewithfrequency" ]; then
#     sh ./genmodule.sh $1 $product
#     echo "  generate module $1"
#     fi
# }

function validate_product()
{
 if [ $1 == "$plname.account.core" ];then
  account=true
 fi
}

function build_module()
{
    echo "build module $1"
    javac -d classes  --module-path lib $(find src/$1 -name "*.java") src/$1/module-info.java
    jar --create --file lib/$1.jar -C classes .
    rm -r classes
    echo "Module $1 is ready"
}

function build_product(){
  echo -e "building the product: $mainclass"
  javac -d classes  --module-path $product $(find src/$product -name "*.java") src/$product/module-info.java 
  jar --create --file $product/$mainclass.jar --main-class $product.$mainclass -C classes .
  rm -r classes
  echo "Product $mainclass is ready"
}

function build_product_requirement(){
  echo " -- checking requirement -- "
  product=$1
  targetpath="src/$product/module-info.java"
  req=$(cat $targetpath | grep "requires \( transitive | static \)\?"| awk '{print $2}' | cut -d';' -f 1 )
  for requ in $req; do
  validate_product $requ
  done 
  if [ "$account" == true ]; then
    for reqprod in $req; do
    if [[ $reqprod =~ "$plname" ]]; then
    echo -e "building requirement for $mainclass: $reqprod"
    # check_module $reqprod
    javac -d classes --module-path $product $(find src/$reqprod -name "*.java") src/$reqprod/module-info.java 
    jar --create --file $product/$reqprod.jar -C classes .
    rm -r classes
    else
    echo "check requirement from another product line"
    external_module $reqprod
    fi
    done
    echo "requirement building done"
    build_product
  else
  echo "product is not valid"
  echo "build failed"
  fi
}

product=$1
mainclass=$2
plname="accountpl"
if [ -d "$1" ]; then rm -r $1; fi
if [ -d "classes" ]; then rm -r classes; fi 
if [ ! -d "lib" ]; then mkdir -p lib; fi 
if [ -z "$mainclass" ]; then
    if [[ $1 =~ "$plname.product" ]];
    then err "Please specify the main class in the product" && exit;
    elif [ ! -d "src/$1" ]; then err "module does not exist" && exit;
    else build_module $product
    fi
else
    if [ ! -d "src/$1" ]; then  err "product does not exist" && exit;
    else mkdir $product; build_product_requirement $product
    fi
fi