import 'package:flutter/material.dart';

/***
 *
 * 个人中心
 *
 */

class PersonalCenterMain extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    // TODO: implement createState
    return new PersonalCenterPage();
  }
}

class PersonalCenterPage extends State<PersonalCenterMain> {
  @override
  Widget build(BuildContext context) {
    // TODO: implement build
    return new Scaffold(
      appBar: new AppBar(
        title: new Text("个人中心"),
      ),
      body: new Column(
        children: <Widget>[new Text("个人中心")],
      ),
    );
  }
}
