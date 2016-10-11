#!/bin/sh
cat firstname.dat | cut -f 2 >> firsNames.txt
cat firstname.dat | cut -f 4 >> firsNames.txt
sed -e ':a' -e 'N' -e 'ba' -e 's/\n/,/g' firstNames.txt
echo "Firstnames:"
echo "===================================="
cat firstNames.txt
sed -e ':a' -e 'N' -e 'ba' -e 's/\n/,/g' firstNames.txt
cp last.dat lastNames.txt
sed -e ':a' -e 'N' -e 'ba' -e 's/\n/,/g' lastNames.txt



