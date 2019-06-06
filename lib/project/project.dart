

import 'package:flutter/material.dart';


import 'package:flutter_refresh/flutter_refresh.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter_spinkit/flutter_spinkit.dart';
import 'dart:async';
import 'package:http/http.dart' as http;
import 'dart:convert';


import 'ProjectBean.dart';
import 'ProjectTab.dart';
import 'package:wanandroid/home/HomeWebview.dart';


/***
 * https://github.com/shijiacheng/wanandroid_flutter/blob/master/lib/ui/ProjectTreePageUI.dart
 *
 * https://blog.csdn.net/weixin_36250061/article/details/87975021
 */
class ProjectMain extends StatefulWidget{

  @override
  State<StatefulWidget> createState() {
    // TODO: implement createState
    return new ProjectPage();
  }

}

class ProjectPage extends State<ProjectMain> with TickerProviderStateMixin{
  List<DataTab> listTab;
  TabController _tabController;
  // 接口数据处理
  getTab() async {
    await http
        .get('https://www.wanandroid.com/project/tree/json')
        .then((response) {
      if (response.statusCode == 200) {
        var jsonRes = json.decode(response.body);
         print(jsonRes);
        ProjectTab listdata = ProjectTab.fromJson(jsonRes);
        setState(() {
          listTab = listdata.data;
        });
      }
    });
  }

  @override
  void initState() {
    // TODO: implement initState
    super.initState();
    getTab();
  }

  @override
  void dispose() {
    _tabController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    // TODO: implement build
    _tabController = new TabController(vsync: this, length: listTab.length);
    return new Scaffold(
      appBar: new AppBar(
        elevation: 0.4,
        automaticallyImplyLeading: false, //设置没有返回按钮
        title: new TabBar(
          controller: _tabController,
          tabs: listTab.map((DataTab item) {
            return Tab(
              text: item.name,
            );
          }).toList(),
          isScrollable:
          true, //水平滚动的开关，开启后Tab标签可自适应宽度并可横向拉动，关闭后每个Tab自动压缩为总长符合屏幕宽度的等宽，默认关闭
        ),
      ),
      body: new TabBarView(
        controller: _tabController,
        children: listTab.map((item) {
           return NewListView(
            id: item.id,
          );
         // return A(item.name);
        }).toList(),
      ),
    );
  }
}



class NewListView extends StatefulWidget{

  var id;
  @override
  NewListView({Key key, this.id}) : super(key: key);

  @override
  State<StatefulWidget> createState() {
    // TODO: implement createState
    return new ListViewPage(id);
  }

}



class ListViewPage extends State<NewListView>{


  ScrollController _scrollController = ScrollController(); //listview的控制器
  var rowNumber = 1;
  List<Datas> dataItems = new List();

  var id;
  ListViewPage(this.id);

  @override
  void initState() {
    // TODO: implement initState
    getNewsData(rowNumber);
    _scrollController.addListener(() {
      if (_scrollController.position.pixels ==
          _scrollController.position.maxScrollExtent) {
        getNewsData(rowNumber);
      }
    });
  }

  // 顶部刷新
  Future<Null> onHeaderRefresh() {
    return new Future.delayed(new Duration(seconds: 2), () {
      setState(() {
        rowNumber = 0;
        getNewsData(rowNumber);
      });
    });
  }

// 底部刷新
  Future<Null> onFooterRefresh() async {
    return new Future.delayed(new Duration(seconds: 2), () {
      setState(() {
        rowNumber++;
        getNewsData(rowNumber);
      });
    });
  }

// 接口数据处理
  getNewsData(var page) async {
    await http
        .get('https://www.wanandroid.com/project/list/${page}/json?cid=${id}')
        .then((response) {
      if (response.statusCode == 200) {
        var jsonRes = json.decode(response.body);
        print(jsonRes);
        ProjectBean listdata = ProjectBean.fromJson(jsonRes);
        if (page == 0) {
          dataItems.clear();
        }
        setState(() {
          dataItems.addAll(listdata.data.datas);
        });
      }
    });
  }

 /* @override
  Widget build(BuildContext context) {
    // TODO: implement build
    return new Scaffold(
      body: new A(dataItems[0].title),
    );
  }*/


  @override
  Widget build(BuildContext context) {
    return new Scaffold(
      body: RefreshIndicator(
        onRefresh: onHeaderRefresh,
         child: ListView.builder(
          padding: const EdgeInsets.all(16.0),
          itemBuilder: _renderRow,
          itemCount: dataItems.length + 1,
          controller: _scrollController,
          //separatorBuilder: _separatorView,  //有直接的分隔符设置方式
        ),
      ),
    );
  }

  Widget _renderRow(BuildContext context, int index) {
    if (index < dataItems.length) {
      return _itemView(context, index);
    }
    return _getMoreWidget();
  }

  Widget _itemView(BuildContext context, int index) {
    return InkWell(
      child: _newsRow(dataItems[index]),
      onTap: () {
        _onItemClick(dataItems[index]);
      },
    );
  }


  void _onItemClick(Datas itemData) async {
    await Navigator.of(context).push(new MaterialPageRoute(builder: (context) {
      return new HomeWebView(
        itemData.title,
        itemData.link,
      );
    }));
  }


  Widget _separatorView(BuildContext context, int index) {
    return Container(
      height: 0.5,
      color: Colors.black26,
    );
  }

  Widget _newsRow(Datas item) {
    return new Row(
      children: <Widget>[
        Container(
            padding: EdgeInsets.fromLTRB(8.0, 16.0, 8.0, 8.0),
            child: Image.network(
              item.envelopePic,
              width: 80.0,
              height: 120.0,
              fit: BoxFit.fill,
            )),
        Expanded(
          child: new Column(
            children: <Widget>[
              Container(
                  padding: EdgeInsets.fromLTRB(8.0, 8.0, 8.0, 8.0),
                  child: Row(
                    children: <Widget>[
                      Expanded(
                          child: Text(
                            item.title,
                            style: TextStyle(
                                fontSize: 16.0, fontWeight: FontWeight.bold),
                            textAlign: TextAlign.left,
                          ))
                    ],
                  )),
              Container(
                  padding: EdgeInsets.fromLTRB(8.0, 0.0, 8.0, 8.0),
                  child: Row(
                    children: <Widget>[
                      Expanded(
                          child: Text(
                            item.desc,
                            style: TextStyle(fontSize: 12.0, color: Colors.grey),
                            textAlign: TextAlign.left,
                            maxLines: 3,
                          ))
                    ],
                  )),
              new Container(
                  padding: EdgeInsets.fromLTRB(8.0, 0.0, 8.0, 8.0),
                  child: new Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: <Widget>[
                      Text(
                        item.author,
                        style: TextStyle(fontSize: 12.0, color: Colors.grey),
                      ),
                      new Expanded(
                        child: new Text(
                          item.author,
                          // TimelineUtil.format(item.publishTime),
                          style: TextStyle(fontSize: 12.0, color: Colors.grey),
                          textAlign: TextAlign.right,
                        ),
                      ),
                    ],
                  )),
            ],
          ),
        ),
      ],
    );
  }

  Widget _getMoreWidget() {
    return Container(
      padding: EdgeInsets.all(16.0),
      alignment: Alignment.center,
      child: SizedBox(
        width: 24.0,
        height: 24.0,
        child: CircularProgressIndicator(
          strokeWidth: 2.0,
        ),
      ),
    );
  }


  @override
  void dispose() {
    super.dispose();
    _scrollController.dispose();
  }



}



class A extends StatelessWidget{

  String name;
  A(this.name);

  @override
  Widget build(BuildContext context) {
    // TODO: implement build
    return new Scaffold(
      body: new Container(
        child: new Text(name),
      ),
    );
  }

}





/*
class ProjectTreePageUI extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return _MyTabbedPageState();
  }
}

class _MyTabbedPageState extends State<ProjectTreePageUI>
    with TickerProviderStateMixin {

  List<Datas> _datas = new List();
  TabController _tabController;

  Future<Null> _getData() async {

  }

  @override
  void initState() {
    super.initState();
    _getData();
  }

  @override
  void dispose() {
    _tabController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    _tabController = new TabController(vsync: this, length: _datas.length);
    return new Scaffold(
      appBar: new AppBar(
        elevation: 0.4,
        title: new TabBar(
          controller: _tabController,
          tabs: _datas.map((Datas item) {
            return Tab(
              text: item.title,
            );
          }).toList(),
          isScrollable:
          true, //水平滚动的开关，开启后Tab标签可自适应宽度并可横向拉动，关闭后每个Tab自动压缩为总长符合屏幕宽度的等宽，默认关闭
        ),
      ),
      body: new TabBarView(
        controller: _tabController,
        children: _datas.map((item) {
         /* return NewsList(
            id: item.id,
          );*/
         A();
        }).toList(),
      ),
    );
  }
}




class NewsList extends StatefulWidget {
  final int id;
  @override
  NewsList({Key key, this.id}) : super(key: key);

  _NewsListState createState() => new _NewsListState();
}

class _NewsListState extends State<NewsList> {
  List<Datas> _datas = new List();
  ScrollController _scrollController = ScrollController(); //listview的控制器
  int rowNumber = 0; //加载的页数

  @override
  void initState() {
    super.initState();
    _scrollController.addListener(() {
      if (_scrollController.position.pixels ==
          _scrollController.position.maxScrollExtent) {
        getNewsData(rowNumber);
      }
    });
  }

  // 顶部刷新
  Future<Null> onHeaderRefresh() {
    return new Future.delayed(new Duration(seconds: 2), () {
      setState(() {
        rowNumber = 0;
        getNewsData(rowNumber);
      });
    });
  }

// 底部刷新
  Future<Null> onFooterRefresh() async {
    return new Future.delayed(new Duration(seconds: 2), () {
      setState(() {
        rowNumber++;
        getNewsData(rowNumber);
      });
    });
  }

// 接口数据处理
  getNewsData(var page) async {
    await http
        .get('https://www.wanandroid.com/article/list/${page}/json')
        .then((response) {
      if (response.statusCode == 200) {
        var jsonRes = json.decode(response.body);
        //print(jsonRes);
       /* Autogenerated listdata = Autogenerated.fromJson(jsonRes);
        if (page == 0) {
          dataItems.clear();
        }
        setState(() {
          dataItems.addAll(listdata.data.datas);
        });*/
      }
    });
  }

  @override
  Widget build(BuildContext context) {
    return new Scaffold(
      body: RefreshIndicator(
        onRefresh: onHeaderRefresh,
       /* child: ListView.separated(
          padding: const EdgeInsets.all(16.0),
          itemBuilder: _renderRow,
          itemCount: _datas.length + 1,
          controller: _scrollController,
          separatorBuilder: _separatorView,
        ),*/
      ),
    );
  }

  Widget _renderRow(BuildContext context, int index) {
    if (index < _datas.length) {
      return _itemView(context, index);
    }
    return _getMoreWidget();
  }

  Widget _itemView(BuildContext context, int index) {
    return InkWell(
      child: _newsRow(_datas[index]),
      onTap: () {
        _onItemClick(_datas[index]);
      },
    );
  }


  void _onItemClick(Datas itemData) async {
    await Navigator.of(context).push(new MaterialPageRoute(builder: (context) {
      return new HomeWebView(
        itemData.title,
        itemData.link,
      );
    }));
  }


  Widget _separatorView(BuildContext context, int index) {
    return Container(
      height: 0.5,
      color: Colors.black26,
    );
  }

  Widget _newsRow(Datas item) {
    return new Row(
      children: <Widget>[
        Container(
            padding: EdgeInsets.fromLTRB(8.0, 16.0, 8.0, 8.0),
            child: Image.network(
              item.envelopePic,
              width: 80.0,
              height: 120.0,
              fit: BoxFit.fill,
            )),
        Expanded(
          child: new Column(
            children: <Widget>[
              Container(
                  padding: EdgeInsets.fromLTRB(8.0, 8.0, 8.0, 8.0),
                  child: Row(
                    children: <Widget>[
                      Expanded(
                          child: Text(
                            item.title,
                            style: TextStyle(
                                fontSize: 16.0, fontWeight: FontWeight.bold),
                            textAlign: TextAlign.left,
                          ))
                    ],
                  )),
              Container(
                  padding: EdgeInsets.fromLTRB(8.0, 0.0, 8.0, 8.0),
                  child: Row(
                    children: <Widget>[
                      Expanded(
                          child: Text(
                            item.desc,
                            style: TextStyle(fontSize: 12.0, color: Colors.grey),
                            textAlign: TextAlign.left,
                            maxLines: 3,
                          ))
                    ],
                  )),
              new Container(
                  padding: EdgeInsets.fromLTRB(8.0, 0.0, 8.0, 8.0),
                  child: new Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: <Widget>[
                      Text(
                        item.author,
                        style: TextStyle(fontSize: 12.0, color: Colors.grey),
                      ),
                      new Expanded(
                        child: new Text(
                          item.author,
                         // TimelineUtil.format(item.publishTime),
                          style: TextStyle(fontSize: 12.0, color: Colors.grey),
                          textAlign: TextAlign.right,
                        ),
                      ),
                    ],
                  )),
            ],
          ),
        ),
      ],
    );
  }

  Widget _getMoreWidget() {
    return Container(
      padding: EdgeInsets.all(16.0),
      alignment: Alignment.center,
      child: SizedBox(
        width: 24.0,
        height: 24.0,
        child: CircularProgressIndicator(
          strokeWidth: 2.0,
        ),
      ),
    );
  }

  @override
  void dispose() {
    super.dispose();
    _scrollController.dispose();
  }
}*/