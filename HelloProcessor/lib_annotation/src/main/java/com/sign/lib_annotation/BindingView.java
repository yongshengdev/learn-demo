package com.sign.lib_annotation;

import android.app.Activity;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author: yongshengdev@163.com
 * @created on 2022/2/23
 * @description:
 */
public class BindingView {

    public static void init(Activity activity) {
        try {
            Class bindingClass = Class.forName(activity.getClass().getCanonicalName() + "ViewBinding");
            Constructor constructor = bindingClass.getDeclaredConstructor(activity.getClass());
            constructor.newInstance(activity);
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }
}
