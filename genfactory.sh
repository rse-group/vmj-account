package=$1
moo=$2
DIR="src/${package}.core/$(echo $package | tr "." "/")"
FILE="${moo}Factory.java"
sed -e "s/\[packagename]/$1/" -e "s/\[interfacename]/$2/g" templateFact.java > $DIR/$FILE
echo "Factory class $FILE is created"