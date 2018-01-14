# spring security 安全框架 #

[![vue](https://img.shields.io/badge/vue-2.4.2-brightgreen.svg)](https://github.com/vuejs/vue)
[![element-ui](https://img.shields.io/badge/element--ui-1.4.1-brightgreen.svg)](https://github.com/ElemeFE/element)
[![license](https://img.shields.io/github/license/mashape/apistatus.svg)](https://github.com/PanJiaChen/vue-element-admin/blob/master/LICENSE)
[![GitHub release](https://img.shields.io/github/release/PanJiaChen/vue-element-admin.svg)]()


[线上地址](http://panjiachen.github.io/vue-element-admin)

[English Document](https://github.com/PanJiaChen/vue-element-admin/blob/master/README-en.md)

[wiki](https://github.com/PanJiaChen/vue-element-admin/wiki)

**本项目的定位是后台集成方案，不适合当基础模板来开发，模板建议使用 [vueAdmin-template](https://github.com/PanJiaChen/vueAdmin-template) ， 桌面端 [electron-vue-admin](https://github.com/PanJiaChen/electron-vue-admin)**



**注意：该项目目前使用element-ui@1.4.1版本，所以最低兼容 Vue 2.3.0**

## 前言

 **一.开发基于表单的认证**
 - 图形验证码基本参数可配置
 
        1.security-core中默认有图形验证码的参数
        2.demo中可配置图形验证码的参数覆盖1中的参数
        3.url中可传图形验证码的参数覆盖2中的参数
 
 - 图形验证码拦截的接口可配置
 
        1.一定会拦截登录的接口
        2.demo中可配置多个需要拦截的uri
    
 - 验证码的生成逻辑可配置
        1.security-core中默认有图形验证码的生成器
        2.demo中可实现ValidateCodeGenerator接口，重写图形验证码生成
        
 - 短信验证码的长度可配置
        1.security-core中默认有短信验证码的长度
        2.demo中可配置短信验证码的长度参数覆盖1中的参数
        
 - 短信验证码的发送逻辑可配置
        1.security-core中默认有短信验证发送逻辑DefaultSmsCodeSender
        2.demo中可实现SmsCodeSender接口，重写发送短信的逻辑
        
        
        ValidateCodeProcessor 处理整个验证码生成流程：生成，储存，发送
-- 子类实例化后的名称要以ValidateCodeProcessor结尾
        
ServletWebRequest: spring的工具类，封装请求和相应。包含了ServletHttpRequest,ServletHttpResponse

AuthenticationManager : 选择provider来处理传进来的token
AuthenticationProvider : 接口 supports方法 判断支Token的类型

UsernamePasswordAuthenticationToken 中
    private final Object principal; // 用户名
    private Object credentials;// 密码

## 功能
- 登录/注销
- 权限验证
- 侧边栏
- 面包屑
- 富文本编辑器
- Markdown编辑器
- JSON编辑器
- 列表拖拽
- plitPane
- Dropzone
- Sticky
- CountTo
- echarts图表
- 401，404错误页面
- 错误日志
- 导出excel
- table example
- 动态table example
- 拖拽table example
- 内联编辑table example
- form example
- 多环境发布
- dashboard
- 二次登录
- 动态侧边栏（支持多级路由）
- mock数据
- cache tabs example
- screenfull
- markdown2html
- views-tab


## 开发
```bash
    # 克隆项目
    git clone https://github.com/PanJiaChen/vue-element-admin.git

    # 安装依赖
    npm install
    //or # 建议不要用cnpm  安装有各种诡异的bug 可以通过如下操作解决npm速度慢的问题
    npm install --registry=https://registry.npm.taobao.org

    # 本地开发 开启服务
    npm run dev
```
浏览器访问 http://localhost:9527

## 发布
```bash
    # 发布测试环境 带webpack ananalyzer
    npm run build:sit-preview

    # 构建生成环境
    npm run build:prod
```

## 目录结构
```shell
├── build                      // 构建相关  
├── config                     // 配置相关
├── src                        // 源代码
│   ├── api                    // 所有请求
│   ├── assets                 // 主题 字体等静态资源
│   ├── components             // 全局公用组件
│   ├── directive              // 全局指令
│   ├── filtres                // 全局filter
│   ├── mock                   // mock数据
│   ├── router                 // 路由
│   ├── store                  // 全局store管理
│   ├── styles                 // 全局样式
│   ├── utils                  // 全局公用方法
│   ├── view                   // view
│   ├── App.vue                // 入口页面
│   └── main.js                // 入口 加载组件 初始化等
├── static                     // 第三方不打包资源
│   └── Tinymce                // 富文本
├── .babelrc                   // babel-loader 配置
├── eslintrc.js                // eslint 配置项
├── .gitignore                 // git 忽略项
├── favicon.ico                // favicon图标
├── index.html                 // html模板
└── package.json               // package.json

```

## Changelog
Detailed changes for each release are documented in the [release notes](https://github.com/PanJiaChen/vue-element-admin/releases).

## 状态管理
后台只有user和app配置相关状态使用vuex存在全局，其它数据都由每个业务页面自己管理。


## 效果图

#### 两步验证登录 支持微信和qq

![两步验证 here](https://github.com/PanJiaChen/vue-element-admin/blob/master/gifs/2login.gif)

#### 真正的动态换肤

![真正的动态换肤](https://github.com/PanJiaChen/vue-element-admin/blob/master/gifs/theme.gif)<br />

#### tabs

![tabs](https://github.com/PanJiaChen/vue-element-admin/blob/master/gifs/tabs.gif)<br />



#### 可收起侧边栏

![enter image description here](https://github.com/PanJiaChen/vue-element-admin/blob/master/gifs/leftmenu.gif)

#### table拖拽排序

![enter image description here](https://github.com/PanJiaChen/vue-element-admin/blob/master/gifs/order.gif)


#### 动态table

![enter image description here](https://github.com/PanJiaChen/vue-element-admin/blob/master/gifs/dynamictable.gif)


#### 上传裁剪头像

![enter image description here](https://github.com/PanJiaChen/vue-element-admin/blob/master/gifs/uploadAvatar.gif)


#### 错误统计

![enter image description here](https://github.com/PanJiaChen/vue-element-admin/blob/master/gifs/errorlog.gif)


#### 富文本(整合七牛 打水印等个性化功能)

![enter image description here](https://github.com/PanJiaChen/vue-element-admin/blob/master/gifs/editor.gif)

#### 封装table组件

![enter image description here](https://github.com/PanJiaChen/vue-element-admin/blob/master/gifs/table.gif)

#### 图表

![enter image description here](https://github.com/PanJiaChen/vue-element-admin/blob/master/gifs/echarts.gif)


#### 导出excel

![enter image description here](https://github.com/PanJiaChen/vue-element-admin/blob/master/gifs/excel.png)


## [查看更多demo](http://panjiachen.github.io/vue-element-admin)

## License

MIT
