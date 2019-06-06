import 'package:flutter/material.dart';

import 'package:flutter_spinkit/flutter_spinkit.dart';
import 'dart:async';
import 'package:http/http.dart' as http;
import 'dart:convert';

import 'package:wanandroid/knowledge/KnowledgeBean.dart';

class KnowledgeMain extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    // TODO: implement createState
    return new KnowledgePage();
  }
}

class KnowledgePage extends State<KnowledgeMain> {

  List<Data> data = [];

  @override
  void initState() {
    // TODO: implement initState
    getNewsData();
  }

  // 接口数据处理
  getNewsData() async {
    await http.get('https://www.wanandroid.com/tree/json').then((response) {
      if (response.statusCode == 200) {
        var jsonRes = json.decode(response.body);
        KnowledgeBean listdata = KnowledgeBean.fromJson(jsonRes);
        print(listdata.data[0].name);
        print(listdata.errorMsg);
        setState(() {
          //rowNumber = dataItems.length;
          data = listdata.data;
        });
      }
    });
  }

  @override
  Widget build(BuildContext context) {
    // TODO: implement build
    return new Scaffold(
     /*   appBar: new AppBar(
          title: new Text("Knowledge"),
        ),*/
        body: new ListView.builder(itemCount: data.length, itemBuilder: _itemView));
  }

  Widget _itemView(BuildContext context, int index) {
    return new Container(
      child: Card(
        margin: EdgeInsets.all(5.0),
        child: Container(
          padding: EdgeInsets.all(10.0),
          child: Row(
            children: <Widget>[
              Expanded(
                  flex: 1,
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: <Widget>[
                      Text(
                        data[index].name,
                        style: TextStyle(
                            fontSize: 16.0,
                            color: Colors.black),
                      ),
                      Padding(
                        padding: EdgeInsets.only(top: 5.0),
                        child: Text(
                          _parseDetail(data[index].children),
                          style: TextStyle(
                              fontSize: 14.0,
                              color: Colors.grey),

                        ),
                      ),
                    ],
                  )),
              Icon(
                Icons.keyboard_arrow_right,
                color: Colors.brown,
              )
            ],
          ),
        ),
      ),

    );
  }


  String _parseDetail(List<Children> children) {
    StringBuffer sb = StringBuffer();
    for (var item in children) {
      sb.write(item.name + "   ");
    }
    return sb.toString();
  }

}
