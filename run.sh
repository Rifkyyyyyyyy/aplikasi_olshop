# jika mengunakan linux jalankan ini

#!/bin/bash


SRC_DIR="./src"
BIN_DIR="./bin"
LIB_DIR="./lib"
ASSETS_DIR="./assets"


MAIN_CLASS="App"

CLASSPATH="$LIB_DIR/*:$BIN_DIR"


mkdir -p $BIN_DIR

echo "Mengompilasi kode Java..."
javac -d $BIN_DIR -cp $CLASSPATH $SRC_DIR/*.java


if [ $? -eq 0 ]; then
  echo "Kompilasi berhasil!"

  echo "Menjalankan aplikasi..."
  java -cp $CLASSPATH:$BIN_DIR $MAIN_CLASS
else
  echo "Kompilasi gagal!"
  exit 1
fi
