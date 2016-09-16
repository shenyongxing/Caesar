#! /bin/bash
# 命令行打包实践
# author shenxing
# date 2016/09/16

function alert() {
	if [ $2 -eq 0 ]; then
        	echo "task: "$1" execute successfully"
        	echo "=========================="
	else
       		echo "task: "$1" failed"
        	echo "======================="
	fi
}

function makeDir() {
	if [ ! -e $1 ];then
		mkdir $1
		echo "create directory "$1
	fi	
}

function checkDex() {
	if [ -e $1 ];then
		rm -r $1
		echo "delete file "$1
	fi
}

frameworkJar="/Users/shenxing/Library/Android/sdk/platforms/android-21/android.jar"
targetJar="gradlebuilddemo.jar"
toolsJar="/Users/shenxing/Library/Android/sdk/tools/lib/sdklib.jar"
buildToolName="com.android.sdklib.build.ApkBuilderMain"
targetApk="GradleBuildDemo.apk"
targetZipalignApk="GradleBuildDemo_zipalign.apk"


checkDex "bin/classes.dex"
makeDir "gen"
makeDir "bin"
makeDir "apk"

echo "start to generate R.java file"
aapt p -f -m -J gen -S app/src/main/res -M app/src/main/AndroidManifest.xml -I $frameworkJar
alert "aapt" $?

echo "compile java files"
javac -version
javac -bootclasspath $frameworkJar -d bin app/src/main/java/com/gradle/shenxing/gradlebuilddemo/*.java gen/com/gradle/shenxing/gradlebuilddemo/R.java 
alert "compile java" $?

echo "generate jar file"
#此处利用cd切换目录的目的是为了防止将bin路径打入了jar，导致dx解析class时报错
# class name does not match path
cd bin
jar cvf $targetJar * 
cd ..
alert "create jar file" $?

#jdk必须为jdk8 dx才可以正常运行
#可以直接运行dx命令查看是否正常
echo "create dex file"
javac -version
dx --dex --output=bin/classes.dex bin/$targetJar
alert "create dex file" $?

echo "package resource"
aapt p -f -M app/src/main/AndroidManifest.xml -S app/src/main/res -I $frameworkJar -F bin/resource.ap_
alert "package resource" $?

echo "build apk"
java -cp $toolsJar $buildToolName apk/$targetApk -v -u -z bin/resource.ap_ -f bin/classes.dex -rf app/src/main/java
alert "build apk" $?

echo "sign the apk"
# 生成shenxing.keystore的密码库短语是：shenxing
jarsigner -verbose -sigalg SHA1withRSA -digestalg SHA1 -keystore shenxing.keystore apk/$targetApk shen
alert "sign apk" $?

echo "zipalign"
zipalign -v 4 apk/$targetApk apk/$targetZipalignApk
alert "zipalign" $? 
