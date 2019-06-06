
import 'package:flutter/material.dart';
import 'package:flutter_webview_plugin/flutter_webview_plugin.dart';


class WebMain extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    List<Widget> titleContent = [];
    titleContent.add(new Text(
      "关于我们",
      style: new TextStyle(color: Colors.white),
    ));
    titleContent.add(new Container(width: 50.0));
    // WebviewScaffold是插件提供的组件，用于在页面上显示一个WebView并加载URL
    return new WebviewScaffold(
      url:"http://www.wanandroid.com/about",
      // 登录的URL
      appBar: new AppBar(
        title: new Row(
          mainAxisAlignment: MainAxisAlignment.center,
          children: titleContent,
        ),
        iconTheme: new IconThemeData(color: Colors.white),
      ),
      withZoom: true,
      // 允许网页缩放
      withLocalStorage: true,
      // 允许LocalStorage
      withJavascript: true, // 允许执行js代码
    );
  }
}