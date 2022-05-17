package com.sign.xposedmoduledemo;

import android.content.ContentResolver;
import android.location.LocationManager;
import android.net.wifi.WifiInfo;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;

import java.net.NetworkInterface;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class MIITHook implements IXposedHookLoadPackage {

    @Override
    public void handleLoadPackage(LoadPackageParam lpparam) {
        if (lpparam == null) {
            return;
        }

        switch (lpparam.packageName) {
            case "packageName":
                break;
            default:
                return;
        }
        XposedBridge.log("XposedHook装载" + lpparam.packageName);

        XposedHelpers.findAndHookMethod(LocationManager.class.getName(), lpparam.classLoader, "getLastKnownLocation", String.class, new
                XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) {
                        XposedBridge.log("调用getLastKnownLocation获取了GPS地址");
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        printAfterHookedMethod(param);
                    }
                });

        XposedHelpers.findAndHookMethod(
                "android.app.ApplicationPackageManager", // 需要hook的方法所在类的完整类名
                lpparam.classLoader,                            // 类加载器，固定这么写就行了
                "getInstalledPackages",                     // 需要hook的方法名
                int.class,
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) {
                        XposedBridge.log("调用了PackageManager.getInstalledPackages");
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        printAfterHookedMethod(param);
                    }
                }
        );
        XposedHelpers.findAndHookMethod(
                "android.app.ApplicationPackageManager",
                lpparam.classLoader,
                "getInstalledApplications",
                int.class,
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) {
                        XposedBridge.log("调用了PackageManager.getInstalledApplications");
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        printAfterHookedMethod(param);
                    }
                }
        );

        XposedHelpers.findAndHookMethod(
                Secure.class.getName(),
                lpparam.classLoader,
                "getString",
                ContentResolver.class,
                String.class,
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) {
                        XposedBridge.log("调用了Settings.Secure.getString--" + param.args[1]);
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        printAfterHookedMethod(param);
                    }
                }
        );

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
                        printAfterHookedMethod(param);
                    }
                }
        );

        XposedHelpers.findAndHookMethod(
                TelephonyManager.class.getName(),
                lpparam.classLoader,
                "getDeviceId",
                int.class,
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) {
                        XposedBridge.log("调用了TelephonyManager.getDeviceId(int slotIndex)");
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        printAfterHookedMethod(param);
                    }
                }
        );

        XposedHelpers.findAndHookMethod(
                TelephonyManager.class.getName(),
                lpparam.classLoader,
                "getImei",
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) {
                        XposedBridge.log("调用了TelephonyManager.getImei");
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        printAfterHookedMethod(param);
                    }
                }
        );

        XposedHelpers.findAndHookMethod(
                TelephonyManager.class.getName(),
                lpparam.classLoader,
                "getImei",
                int.class,
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) {
                        XposedBridge.log("调用了TelephonyManager.getImei(int slotIndex)");
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        printAfterHookedMethod(param);
                    }
                }
        );

        XposedHelpers.findAndHookMethod(
                TelephonyManager.class.getName(),
                lpparam.classLoader,
                "getSubscriberId",
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) {
                        XposedBridge.log("调用了TelephonyManager.getSubscriberId");
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        printAfterHookedMethod(param);
                    }
                }
        );

        XposedHelpers.findAndHookMethod(
                TelephonyManager.class.getName(),
                lpparam.classLoader,
                "getSubscriberId",
                int.class,
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) {
                        XposedBridge.log("调用了TelephonyManager.getSubscriberId(int slotIndex)");
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        printAfterHookedMethod(param);
                    }
                }
        );

        XposedHelpers.findAndHookMethod(
                WifiInfo.class.getName(),
                lpparam.classLoader,
                "getMacAddress",
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) {
                        XposedBridge.log("调用了WifiInfo.getMacAddress");
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        printAfterHookedMethod(param);
                    }
                });

        XposedHelpers.findAndHookMethod(
                NetworkInterface.class.getName(),
                lpparam.classLoader,
                "getHardwareAddress",
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) {
                        XposedBridge.log("调用了NetworkInterface.getHardwareAddress");
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        printAfterHookedMethod(param);
                    }
                });
    }

    private void printAfterHookedMethod(XC_MethodHook.MethodHookParam param) {
//        // Hook函数之后执行的代码
//        //函数返回值
//        XposedBridge.log("afterHookedMethod args:" + Arrays.toString(param.args));
//
//        // 函数调用完成之后打印堆栈调用的信息
//        // 方法一:
//        XposedBridge.log("Dump Stack: " + "---------------start----------------");
//        Throwable ex = new Throwable();
//        StackTraceElement[] stackElements = ex.getStackTrace();
//        if (stackElements != null) {
//            for (int i = 0; i < stackElements.length; i++) {
//
//                XposedBridge.log("Dump Stack" + i + ": " + stackElements[i].getClassName()
//                        + "----" + stackElements[i].getFileName()
//                        + "----" + stackElements[i].getLineNumber()
//                        + "----" + stackElements[i].getMethodName());
//            }
//        }
//        XposedBridge.log("Dump Stack: " + "---------------over----------------");
    }

}
