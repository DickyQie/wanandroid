import 'package:flutter/material.dart';

import 'package:flutter_refresh/flutter_refresh.dart';
import 'dart:async';
import 'package:http/http.dart' as http;
import 'dart:convert';

import 'NavigationBean.dart';
import 'package:wanandroid/home/HomeWebview.dart';


/***
 * https://www.jianshu.com/p/fb3bf633ee12
 */
class NavigationMain extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    // TODO: implement createState
    return new NavigationPage();
  }
}

class NavigationPage extends State<NavigationMain> {
  List<Data> listNavigation;
  var rowNumber = 0;

  @override
  void initState() {
    getNewsData();
  }

  // 接口数据处理
  getNewsData() async {
    await http.get('https://www.wanandroid.com/navi/json').then((response) {
      if (response.statusCode == 200) {
        var jsonRes = json.decode(response.body);
        print(jsonRes);
        NavigationBean listdata = NavigationBean.fromJson(jsonRes);
        setState(() {
          listNavigation = listdata.data;
          print(listNavigation[0].name);
        });
      }
    });
  }

  // 顶部刷新
  Future<Null> onHeaderRefresh() {
    return new Future.delayed(new Duration(seconds: 2), () {
      setState(() {
        rowNumber = 0;
        getNewsData();
      });
    });
  }

  @override
  Widget build(BuildContext context) {
    // TODO: implement build
    return new Scaffold(
      body: childFreshWidget(),
    );
  }

  Widget childFreshWidget() {
    Widget childFreWi;
    childFreWi = new Padding(
      padding: EdgeInsets.only(bottom: 1.0),
      child: new Refresh(
        onHeaderRefresh: onHeaderRefresh,
        childBuilder: (BuildContext context,
            {ScrollController controller, ScrollPhysics physics}) {
          return new Container(
              child: new ListView.builder(
            physics: physics,
            controller: controller,
            itemCount: listNavigation.length,
            itemBuilder: (context, item) {
              return _itemView(context, item, listNavigation);
            },
          ));
        },
      ),
    );

    return childFreWi;
  }

  /***
   * item
   */
  Widget _itemView(BuildContext context, int item, List<Data> data) {
    return new Container(
        child: new Column(
      children: <Widget>[
        //new Text(data[item].name),
        new Container(
          margin: EdgeInsets.only(left: 15.0, top: 20.0, bottom: 20.0),
          alignment: Alignment.topLeft,
          child: new Text(
            data[item].name,
            overflow: TextOverflow.ellipsis,
          ),
        ),
        builGrid(data[item].articles)
        // getGridView(),
      ],
    ));
  }

  Widget builGrid(List<Articles> articles) {
    return new GridView.count(
        padding: EdgeInsets.all(2.0),
        mainAxisSpacing: 2.0,
        ////竖向间距
        crossAxisSpacing: 2.0,
        ////横向间距
        //一行的Widget数量
        physics: new NeverScrollableScrollPhysics(),
        //增加解决不滚动问题
        shrinkWrap: true,
        //添加shrinkWrap:true即可解决报错问题；
        crossAxisCount: 3,
        childAspectRatio: 2.5,
        children: buildGridTileList(articles));
  }

  List<Widget> buildGridTileList(List<Articles> articles) {
    List<Widget> widgetList = new List();
    for (int i = 0; i < articles.length; i++) {
      widgetList.add(getItemWidget(articles[i].title,articles[i].link));
    }
    return widgetList;
  }

  Widget getItemWidget(String content,String link) {
    return new GestureDetector(
        onTap: () {
          Navigator.push(
              context, new MaterialPageRoute(builder: (context) => HomeWebView(content,link)));
        },
        child: new Container(
          height: 20.0,
          alignment: Alignment.center,
          margin: EdgeInsets.only(left: 5.0, top: 3.0, right: 5.0, bottom: 3.0),
          decoration: new BoxDecoration(
              border: new Border.all(width: 1.0, color: Colors.blue),
              borderRadius: new BorderRadius.all(new Radius.circular(16.0))),
          child: new Text(
            content,
            textAlign: TextAlign.center,
            maxLines: 1,
            overflow: TextOverflow.ellipsis,
            style: new TextStyle(fontSize: 12.5),
          ),
        ));
  }

  Widget getItemContainer(String item) {
    return Container(
      alignment: Alignment.center,
      child: Text(
        item,
        style: TextStyle(color: Colors.white, fontSize: 20.0),
      ),
      color: Colors.blue,
    );
  }

  List<String> getDataList() {
    List<String> list = [];
    for (int i = 0; i < 10; i++) {
      list.add(i.toString());
    }
    return list;
  }

  //https://www.jianshu.com/p/fb3bf633ee12
  Widget getGridView() {
    List<String> datas = getDataList();
    return GridView.builder(
        itemCount: datas.length,
        physics: new NeverScrollableScrollPhysics(),
        //增加
        shrinkWrap: true,
        //增加
        //SliverGridDelegateWithFixedCrossAxisCount 构建一个横轴固定数量Widget
        gridDelegate: SliverGridDelegateWithFixedCrossAxisCount(
            //横轴元素个数
            crossAxisCount: 3,
            //纵轴间距
            mainAxisSpacing: 20.0,
            //横轴间距
            crossAxisSpacing: 10.0,
            //子组件宽高长度比例
            childAspectRatio: 3.0),
        itemBuilder: (BuildContext context, int index) {
          //Widget Function(BuildContext context, int index)
          return getItemContainer(datas[index]);
        });
  }
}
