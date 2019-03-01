package com.zhangqie.wanandroid

import android.Manifest
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.view.animation.AlphaAnimation
import android.widget.LinearLayout
import com.zhangqie.wanandroid.ui.MainFragment

class MainActivity : AppCompatActivity() {

    var handler : Handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(R.layout.activity_main)
        showPermissions()
    }

    fun initView(){
        var animation : AlphaAnimation = AlphaAnimation(0.3f,1.0f)
        animation.duration = 500
        var layout : LinearLayout = findViewById(R.id.frameSplash)
        layout.startAnimation(animation)
        handler.postDelayed({
                intent = Intent(this@MainActivity, MainFragment::class.java)
            startActivityForResult(intent, 1)

        },2000)

    }

    /***
     * 递归实现 权限申请
     *
     */
    protected fun showPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
            } else {
                showPermissionsn()
            }
        } else {
            initView()
        }
    }

    protected fun showPermissionsn() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 2)
        } else {
            initView()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showPermissionsn()
            } else {
                showPermissions()
            }
        } else {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initView()
            } else {
                showPermissionsn()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            if (resultCode == 1) {
                val manager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
                manager.killBackgroundProcesses(packageName)
                finish()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        intent = null
        System.exit(0)
    }
}
