# BaseApp
安卓基础框架

lib-src: 资源，如文本，颜色，图片等
lib-common: 基础框架，如网络框架、自定义view、基类Activity、第三方框架引入等
lib-app: 演示
继承关系lib-src > lib-common > lib-app
新项目新建module引入lib-common即可

## 路由框架：ARoute
关于ARoute说明：
每个module都必须在gradle引入 annotationProcessor 'com.alibaba:arouter-compiler:1.5.2'
且一级路径不能一样，否则无法跳转

## 网络框架：RxHttp
Toast框架：ToastUtils.showToast("提示");

## 图片框架：Glide

## 权限框架：XXPermissions
基于该框架封装了com.huodada.lib_common.utils.PermissionUtil工具类，所有权限请求用此工具类

## 事件通信框架：Eventbus

## 列表框架：SmartRefreshLayout + BaseRecyclerViewAdapterHelper
将这两个框架相结合封装了 com.lib_common.view.layout.BaseSmartRefreshLayout 列表布局，实现了自动显示空布局和分页判断，减少模板代码

## JSON解析框架：Fastjson

## 浏览器：AgentWeb
使用 AgentWeb 框架封装了com.huodada.lib_common.base.WebActivity页面，传递url参数，标题自动加载

## PopupWindow框架：BasePopup
文档地址：[https://www.yuque.com/razerdp/basepopup](https://www.yuque.com/razerdp/basepopup)

## 标签布局：Taglayout

## MVVM架构
lib-common module base包目录下：
BaseMvvmActivity MVVM基类Activity
BaseMvvmFragment MVVM基类Fragment
BaseViewModel 基类ViewModel

MVVM由 ViewDataBinding + LiveData + LifeCycle + ViewModel 组成

1、ViewDataBinding
（1）开启ViewBinding 和 DataBinding

gradle中添加
buildFeatures {
viewBinding true
dataBinding true
}

引入：implementation 'androidx.databinding:databinding-runtime:7.3.1'

（2）单向绑定+双向绑定
DataBindingUtil创建ViewBinding对象，不同方式，如Activity, Fragment, Dialog等
xml布局转换为DataBinding布局
variable 和 import 
xml布局中，支持的表达式，如基础数据类型，自定义对象类型，三目运算符，合并运算符，资源，集合泛型等
单击事件、BindingAdapter自定义绑定属性

（3）RecyclerView封装可以使用DataBinding的adapter   BaseDataBingingAdapter

2、LiveData MutableLiveData
通知观察者数据改变
postValue 和 setValue 的区别

3、LifeCycle 生命周期组件

4、ViewModel MVVM中的VM处理业务逻辑，作为Model和View的桥梁
