
# 项目地址：https://github.com/wangjiandett/RxDemo

# 项目功能和结构介绍：

1.base目录下为dispatcher，负责事件分发：如在post到单独线程中，post到主线程中

2.net目录为网络请求功能，负责执行网络请求和网络异常等相关操作
使用retrofit+okhttp+rxandroid的方式实现

3.ui目录为ui层，负责封装上层与ui相关的activity和fragment，adapter等公共实现

4.mvp目录为使用mvp模式实现的一个使用本项目的demo

5.mvvm目录为使用mvvm模式实现的一个使用本项目的demo
使用mvvm模式实现加载数据和界面刷新：
mvvm和上面的mvp模式部分代码公用了所以这边view和model包中为空
在实际使用时可任选其一

6.utils目录为相关utils封装类，减少重复冗余的代码
