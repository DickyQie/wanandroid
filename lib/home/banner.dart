import 'package:flutter/material.dart';

import 'package:banner_view/banner_view.dart';

import 'Pair.dart';
import 'BannerItemFactory.dart';

import 'BannerBean.dart';

/***
 * Banner
 */

class BannerPage extends StatelessWidget {
  List<DataBean> dataBean;

  BannerPage(this.dataBean);

  @override
  Widget build(BuildContext context) {
    // TODO: implement build
    return new Scaffold(
      body: new Container(
          alignment: Alignment.center, height: 200.0, child: _bannerView0()),
    );
  }

  /**
   * 第一种方式
   */
  BannerView _bannerView0() {
    // 盛放图片的 List
    List<Pair<String, Color>> param = new List();
    for (int i = 0; i < (dataBean.length > 4 ? 4 : dataBean.length ); i++) {
      param.add(
        Pair.create(dataBean[i].imagePath, Colors.red[500]),
      );
    }

 /*   [
      Pair.create(
          'https://p5.ssl.qhimg.com/dm/456_209_/t01f43c5849ef5f521a.jpg',
          Colors.red[500]),
      Pair.create(
          'https://p.ssl.qhimg.com/t0171bb61911ebe8899.jpg', Colors.green[500]),
      Pair.create(
          'https://p.ssl.qhimg.com/t01ee77978d3a95a3ae.jpg', Colors.blue[500]),
    ];*/

    return new BannerView(
      BannerItemFactory.banners(param),
      animationDuration: const Duration(milliseconds: 2000),

    );
  }
}
