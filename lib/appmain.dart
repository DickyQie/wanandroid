import 'package:flutter/material.dart';

import 'home/home.dart';
import 'knowledge/knowledge.dart';
import 'project/project.dart';
import 'navigation/navigation.dart';
import 'personal_center.dart';

import 'webview/webview.dart';

import 'package:fluttertoast/fluttertoast.dart';

class Main extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    // TODO: implement createState
    return new MainPage();
  }
}

class MainPage extends State<Main> {
  var _index = 0;
  List<Widget> _listWidget = [
    HomeMain(),
    KnowledgeMain(),
    NavigationMain(),
    ProjectMain()
  ];
  List<BottomNavigationBarItem> _listItem = <BottomNavigationBarItem>[
    BottomNavigationBarItem(icon: Icon(Icons.home), title: Text('首页')),
    BottomNavigationBarItem(icon: Icon(Icons.apps), title: Text('知识体系')),
    BottomNavigationBarItem(icon: Icon(Icons.navigation), title: Text('导航')),
    BottomNavigationBarItem(
        icon: Icon(Icons.branding_watermark), title: Text('项目')),
  ];


  @override
  Widget build(BuildContext context) {
    // TODO: implement build
    return Scaffold(
      drawer: new Drawer(
        child: new ListView(
          children: <Widget>[
            new UserAccountsDrawerHeader(
              accountName: Text("切切心语"),
              accountEmail: Text("zhangqiem@126.com"),
              currentAccountPicture: new GestureDetector(
                child: new CircleAvatar(
                  backgroundImage: new ExactAssetImage("images/pic2.png"),
                ),
              ),
              decoration: new BoxDecoration(
                image: new DecorationImage(
                  fit: BoxFit.fill,
                  image: new ExactAssetImage("images/lake.jpg"),
                ),
              ),
            ),
            new ListTile(
              leading: new Icon(Icons.account_balance),
              title: new Text("个人中心"),
              onTap: (){
                Navigator.push(context, new MaterialPageRoute(builder: (context) => PersonalCenterMain()));
                Navigator.of(context).pop();
              },
            ),
            new Divider(),
            new ListTile(
              title: new Text("我的收藏"),
              leading: new Icon(Icons.import_contacts),
              onTap: () {
                Fluttertoast.showToast(
                    msg: " 开发中...... ",
                    toastLength: Toast.LENGTH_SHORT,
                    gravity: ToastGravity.BOTTOM,
                    timeInSecForIos: 1,
                  backgroundColor: Colors.blueAccent,
                  textColor: Colors.black,
                );
              },
            ),
            new Divider(),
            new ListTile(
              title: new Text("关于我们"),
              leading: new Icon(Icons.insert_emoticon),
              onTap: () {
                Navigator.push(context, new MaterialPageRoute(builder: (context) => WebMain()));
               // Navigator.of(context).pop();
              },
            ),
            new Divider(),
          ],
        ),
      ),
      bottomNavigationBar: BottomNavigationBar(
        items: _listItem,
        type: BottomNavigationBarType.fixed,
        currentIndex: _index,
        onTap: onItemCheck,
      ),
      body: _listWidget[_index],
    );
  }

  void onItemCheck(var index) {
    setState(() {
      _index = index;
    });
  }
}
