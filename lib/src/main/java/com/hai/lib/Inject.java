package com.hai.lib;

import android.app.Activity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * ${desc}
 * create by huanghp at 2018/7/9
 * email 1132760021@qq.com
 */
public class Inject {
    public static final String PROXY = "Proxy";

    public static String inject(Activity activity) {
        String packageName = activity.getClass().getPackage().getName();
        String proxyName = activity.getClass().getSimpleName() + "$$" + PROXY;
        try {
            Class<?> proxyClass = Class.forName(packageName+"."+proxyName);
            if (proxyClass != null) {
                Object o = proxyClass.newInstance();
                if (o != null) {
                    Method method = o.getClass().getDeclaredMethod("getString");
                    if (method != null) {
                        return (String) method.invoke(o);
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
