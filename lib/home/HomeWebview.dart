import 'package:flutter/material.dart';
import 'package:flutter_webview_plugin/flutter_webview_plugin.dart';

class HomeWebView extends StatelessWidget {
  String title;
  String url;

  HomeWebView(this.title, this.url);

  @override
  Widget build(BuildContext context) {
    List<Widget> titleContent = [];
    //容器Container 用于文本过长（ overflow: TextOverflow.ellipsis,） 属性设置
    titleContent.add(new Container(
        width: 250.0,
        child: new Text(
          title,
          maxLines: 1,
          overflow: TextOverflow.ellipsis,
          style: new TextStyle(color: Colors.white, fontSize: 14.0),
        )));
    // WebviewScaffold是插件提供的组件，用于在页面上显示一个WebView并加载URL
    return new WebviewScaffold(
      url: url,
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
