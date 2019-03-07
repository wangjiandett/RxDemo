#gitbash 执行adb命令是路径报错参考地址：https://blog.csdn.net/tp7309/article/details/82934196
#原因是git bash把 / 给自动转换了，自动附加了git的安装路径C:/Program Files/Git
#按官方给定的转换规则看，得改成这样才行：//sdcard/plugindemo.apk

#编译插件apk
../gradlew clean assemblePlugin
#push 到 sdcard 里面
#adb push build/outputs/apk/release/plugindemo-release.apk //sdcard/plugindemo.apk
#copy 到 assets 里面
cp -R build/outputs/apk/release/plugindemo-release.apk ../app/src/main/assets/plugindemo.apk
#sleep 12 睡眠函数
adb shell am force-stop com.moa.rxdemo
adb shell am start -n com.moa.rxdemo/com.moa.rxdemo.mvp.view.SplashActivity
