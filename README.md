![Logo](https://raw.githubusercontent.com/foobnix/LirbiReader/master/logo.jpg)


# Librera Reader

Librera Reader is an e-book reader for Android devices; 
it supports the following formats: PDF, EPUB, EPUB3, MOBI, DjVu, FB2, TXT, RTF, AZW, AZW3, HTML, CBZ, CBR, DOC, DOCX, and OPDS Catalogs

Web: [http://librera.mobi/](http://librera.mobi/)

FAQ: [Read](http://librera.mobi/wiki/faq/)

Android Play Market Apps:

[Librera](https://play.google.com/store/apps/details?id=com.foobnix.pdf.reader)

[Librera PRO](https://play.google.com/store/apps/details?id=com.foobnix.pro.pdf.reader)

Application Fonts (**fonts.zip** download to internal sd card, to [Downloads] folder)
[link](https://github.com/foobnix/LirbiReader/tree/master/Builder/fonts) 

[Telegram](https://t.me/LibreraReader)

[Telegram Download Beta apk](https://t.me/LibreraBeta)

[Beta .apk (latest build)](http://beta.librera.mobi)

## Required build libs

~~~~
mesa-common-dev libxcursor-dev libxrandr-dev libxinerama-dev libglu1-mesa-dev libxi-dev pkg-config
~~~~

You also need the Android NDK in version 20.0.5594570.
Please ensure to download it using android studio and add the NDK to your PATH.

## Create a keystore

Even if you do not plan to upload a version yourself you need a keystore with a certificate to build.
The keystore needs to be in PKCS12 format.
You can create a keystore in your actual directory using the following call
(replace ALIAS by your alias, it is just a name):

~~~~
keytool -genkey -v -storetype PKCS12 -keystore keystore.pkcs12 -alias ALIAS -keyalg RSA -keysize 2048 -validity 10000
~~~~

Now edit or create the file ~/.gradle/gradle.properties and set following values
(replacing PASSWD by the password you typed while creating the keystore, ALIAS as before and using the path to your keystore):

~~~~
RELEASE_STORE_FILE=/PATH/TO/YOUR/keystore.pkcs12
RELEASE_STORE_PASSWORD=PASSWD
RELEASE_KEY_PASSWORD=PASSWD
RELEASE_KEY_ALIAS=ALIAS
~~~~

## Librera Build on MuPdf 1.11 (Default)

~~~~
cd Builder
./link_to_mupdf_1.11.sh (Change the paths to mupdf and jniLibs folders)
./gradlew assembleLibrera
~~~~

## Librera Build on MuPdf 1.16.1 (Optional, alpha)

~~~~
cd Builder
./link_to_mupdf_1.16.1.sh
./gradlew assembleAlpha
~~~~

## Building for Fdroid

If you wish to build for Fdroid (e.g. not using google services) you can run the build with

~~~~
cd Builder
./link_to_mupdf_1.11.sh (Change the paths to mupdf and jniLibs folders)
./gradlew assembleFdroid
~~~~

This build does also not need a google-services.json.
You can also use this build with the MuPdf 1.16.1 as described before.

## Librera depends on:

MuPDF - (AGPL License) https://mupdf.com/downloads/archive/

* EbookDroid
* djvulibre
* hpx
* junrar
* Universal Image Loader
* libmobi
* commons-compress
* eventbus
* greendao
* jsoup
* juniversalchardet
* commons-compress
* okhttp3
* okhttp-digest
* okio
* rtfparserkit
* java-mammoth

Librera is distributed under the GPL

## License

See the [LICENSE](LICENSE.txt) file for license rights and limitations (GPL v.3).
