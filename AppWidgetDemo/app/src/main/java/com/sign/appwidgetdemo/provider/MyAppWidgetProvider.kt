package com.sign.appwidgetdemo.provider

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.RemoteViews
import com.sign.appwidgetdemo.R

/**
 * @author: yongshengdev@163.com
 * @created on 2022/4/2
 * @description:
 */
class MyAppWidgetProvider : AppWidgetProvider() {

    companion object {

        const val TAG = "MyAppWidgetProvider"
    }

    /**
     * 接收应用微件相关的所有广播，如enabled/disabled/delete...
     * 且会在这些回调之前调用，通常不需要实现，监听相关方法即可
     */
    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        Log.d(TAG, "-----------------onReceive-------------------")
    }

    /**
     * 按AppWidgetInfo中定义的updatePeriodMillis时间间隔来更新应用微件
     */
    override fun onUpdate(context: Context?, appWidgetManager: AppWidgetManager?, appWidgetIds: IntArray?) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        Log.d(TAG, "-----------------onUpdate-------------------")
        if (appWidgetIds == null) {
            return
        }
        context?.let { ctx ->
            appWidgetIds.forEach { appWidgetId ->
                // 获取appWidgetManager
                val appWidgetManager: AppWidgetManager = AppWidgetManager.getInstance(ctx)
                // 获取RemoteViews
                RemoteViews(ctx.packageName, R.layout.my_appwidget).also { remoteView ->
                    // 更新视图
//                    remoteView.setImageViewResource(R.id.image, resourceId)
                    // 更新小组件
                    appWidgetManager.updateAppWidget(appWidgetId, remoteView)
                }
            }

        }
    }

    /**
     * 「首次创建」应用微件时会回调该方法
     * 例如用户添加了两个应用微件的实例，只有「首次添加」会调用该方法
     * 适宜做微件初始化相关工作
     */
    override fun onEnabled(context: Context?) {
        super.onEnabled(context)
        Log.d(TAG, "-----------------onEnabled-------------------")
    }

    /**
     * 删除应用微件的「最后一个实例」时，会回调该方法
     * 用于清理 onEnabled 方法中的初始化操作，如删除临时数据库
     */
    override fun onDisabled(context: Context?) {
        super.onDisabled(context)
        Log.d(TAG, "-----------------onDisabled-------------------")
    }

    /**
     * 删除应用微件时会回调此方法
     */
    override fun onDeleted(context: Context?, appWidgetIds: IntArray?) {
        super.onDeleted(context, appWidgetIds)
        Log.d(TAG, "-----------------onDeleted-------------------")
    }

    /**
     * 当首次放置，或者每当调整微件大小时，会回调此方法。可以根据微件当前大小调整显示内容
     */
    override fun onAppWidgetOptionsChanged(context: Context?, appWidgetManager: AppWidgetManager?, appWidgetId: Int, newOptions: Bundle?) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions)
        Log.d(TAG, "-----------------onAppWidgetOptionsChanged-------------------")
    }
}