# BaseApp
安卓基础框架

lib-src: 资源，如文本，颜色，图片等
lib-common: 基础框架，如网络框架、自定义view、基类Activity、第三方框架引入等
lib-app: 演示
继承关系lib-src > lib-common > lib-app
新项目新建module引入lib-common即可

# 路由框架：ARoute
关于ARoute说明：
每个module都必须在gradle引入 annotationProcessor 'com.alibaba:arouter-compiler:1.5.2'
且一级路径不能一样，否则无法跳转

# 网络框架：RxHttp
Toast框架：ToastUtils.showToast("提示");

# 图片框架：Glide

# 权限框架：XXPermissions
基于该框架封装了com.huodada.lib_common.utils.PermissionUtil工具类，所有权限请求用此工具类

# 事件通信框架：Eventbus

# 列表框架：SmartRefreshLayout + BaseRecyclerViewAdapterHelper
将这两个框架相结合封装了 com.huodada.lib_common.view.layout.BaseSmartRefreshLayout 列表布局，实现了自动显示空布局和分页判断，减少模板代码

# JSON解析框架：Fastjson

# 浏览器：AgentWeb
使用 AgentWeb 框架封装了com.huodada.lib_common.base.WebActivity页面，传递url参数，标题自动加载

# PopupWindow框架：BasePopup
文档地址：https://www.yuque.com/razerdp/basepopup

# 标签布局：Taglayout

#其它说明
新建页面推荐继承BaseDataBindingActivity类，可支持DataBinding，方便控件赋值，减少模板代码
设置标题：setTitle("")

