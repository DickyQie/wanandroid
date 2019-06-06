import 'package:flutter/material.dart';

import 'appmain.dart';


void main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  //直接打开第二个界面
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      //debugShowCheckedModeBanner: false, //去掉右上角debug标签
      theme: ThemeData(primaryColor: Colors.blue),
      home: Main(),
      routes: <String, WidgetBuilder>{'main': (_) => Main()},
    );
  }
}

