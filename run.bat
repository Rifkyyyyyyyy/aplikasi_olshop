:: jika mengunakan os windows jalankan ini

@echo off

set SRC_DIR=.\src
set BIN_DIR=.\bin
set LIB_DIR=.\lib
set ASSETS_DIR=.\assets


set MAIN_CLASS=App

set CLASSPATH=%LIB_DIR%\*;%BIN_DIR%

if not exist %BIN_DIR% mkdir %BIN_DIR%


echo Mengompilasi kode Java...
javac -d %BIN_DIR% -cp %CLASSPATH% %SRC_DIR%\*.java


if %ERRORLEVEL% equ 0 (
    echo Kompilasi berhasil!

    echo Menjalankan aplikasi...
    java -cp %CLASSPATH%;%BIN_DIR% %MAIN_CLASS%
) else (
    echo Kompilasi gagal!
    exit /b 1
)
