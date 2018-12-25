
# 项目地址：https://github.com/wangjiandett/RxDemo

# 项目功能和结构介绍：

base目录：
db：        为使用room数据库的demo

dispatcher：为负责事件分发：如在post到单独线程中，post到主线程中

net：       为网络请求功能，负责执行网络请求和网络异常等相关操作，使用retrofit+okhttp+rxandroid的方式实现

ui：        为ui层，负责封装上层与ui相关的activity和fragment，adapter等公共实现

mvp         为使用mvp模式实现的一个使用本项目的demo

mvvm        为使用mvvm模式实现的一个使用本项目的demo（注：mvvm中的model暂未实现，请参看net中的BaseModel的实现）

utils       为相关utils封装类，减少重复冗余的代码

view        为自定义的view
