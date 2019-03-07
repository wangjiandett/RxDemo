
# 项目地址：https://github.com/wangjiandett/RxDemo

android app快速开发，只需在此基础上做相应功能修改即可快速完成app功能开发：

# 项目功能和结构介绍：

项目主要通过组件化和插件化实现项目的解耦和独立开发，同时使用MVP和MVVM模式对项目整体结构进行规划，从而实现低耦合拓展性强的app框架


### 一.项目插件化开发

其中插件化使用滴滴开源的VirtualAp官网地址：https://github.com/didi/VirtualAPK

这里大概介绍一下本项目中的使用：

1. module plugindemo 就是滴滴插件的一个简单的demo
2. plugin_messenger是插件demo与宿主app进行交互使用的中间库
3. 在plugindemo中完成相应功能的开发后执行plugindemo目录中的make.sh脚本，实现打包插件apk或者通过
   android studio右上角的gradle->RxDemo2->plugindemo->Tasks->build->assemblePlugin实现打包插件apk
4. 将打包后的插件apk放入sdcard或宿主app的assets文件夹中加载调用，参考宿主app的HomeFragment的使用实例

**注意：** 打包出的插件apk是release版本，如果使用混淆需要多测试

更详细的开发流程和注意事项请参看官方文档

### 二.项目组件化开发

组件化开发demo有module_a,module_b,module_c3个。

通过项目根目录下的gradle.properties中配置isLibrary=true/false来控制宿主项目与module的引用关系

当isLibrary=true：module作为library被app引用
当isLibrary=false：module作为单独的应用可独立运行


## 组件化需要解决的4类问题

1. 组件之间的的交互问题
**解决：**
这里使用的是ARouter实现

2. 组件中要引用在application中初始化

**解决：**
这里使用在manifest中注册ComponentApplication的子类，之后在app中对manifest中的ComponentApplication子类进行过滤，统一进行回调对应的函数 

3. 组件之间的的资源引用冲突

**解决：**
在组件的build.gradle中添加resourcePrefix前缀，进行强制检测每个资源的加前缀特殊命名，防止资源冲突

4. 重复引用库

**解决：**
如果module和app引用相同的库，在module中使用compileOnly或provided在app中使用implementation

### 三.baselib作为插件开发和组件开发的公共引用库，里面封装了常用的开发工具和功能
大致功能介绍：
1. 事件分发器：单独线程，主线程，线程池
2. 网络请求封装（rxjava+okhttp+retrofit2+Gson）
3. BaseActivity,BaseFragment,BaseAdapter，异常处理等通用类的封装
4. 插件生命周期管理
5. 常用工具类，图片库，扫一扫
6. 常用组件，滚动广告，recyclerview，上拉下拉刷新组件
......

### 四.version.gradle作为宿主App，插件项目，module的引用库的版本控制