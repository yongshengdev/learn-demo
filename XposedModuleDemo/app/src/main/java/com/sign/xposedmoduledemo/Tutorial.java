package com.sign.xposedmoduledemo;

import android.graphics.Color;
import android.telephony.TelephonyManager;
import android.widget.TextView;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * @author: yongshengdev@163.com
 * @created on 2022/5/17
 * @description:
 */
public class Tutorial implements IXposedHookLoadPackage {


    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        if (!lpparam.packageName.equals("packageName")) return;
        XposedBridge.log("we are in 待拦截的app!");
        XposedHelpers.findAndHookMethod(
                TelephonyManager.class.getName(),
                lpparam.classLoader,
                "getDeviceId",
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) {
                        XposedBridge.log("调用了TelephonyManager.getDeviceId");
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                    }
                }
        );
    }
}
