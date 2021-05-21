package cn.hanlyjiang.apf_library.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 反射工具类，来源于Android-Plugin-Framework
 */
@SuppressWarnings("unchecked")
public class RefInvoker {

    private static final ClassLoader system = ClassLoader.getSystemClassLoader();
    private static final ClassLoader bootloader = system.getParent();
    private static final ClassLoader application = RefInvoker.class.getClassLoader();

    private static HashMap<String, Class> clazzCache = new HashMap<String, Class>();

    /**
     * 通过String获取Class对象
     *
     * @param clazzName 类名
     * @return
     * @throws ClassNotFoundException
     */
    public static Class<?> forName(String clazzName) throws ClassNotFoundException {
        Class<?> clazz = clazzCache.get(clazzName);
        if (clazz == null) {
            clazz = Class.forName(clazzName);
            ClassLoader cl = clazz.getClassLoader();
            if (cl == system || cl == application || cl == bootloader) {
                clazzCache.put(clazzName, clazz);
            }
        }
        return clazz;
    }

    /**
     * 构造对象
     *
     * @param className   类名
     * @param paramTypes  参数的类型定义数组
     * @param paramValues 参数的值数组
     * @return 新的对象
     */
    public static Object newInstance(String className, Class[] paramTypes, Object[] paramValues) {
        try {
            Class clazz = forName(className);
            Constructor constructor = clazz.getConstructor(paramTypes);
            if (!constructor.isAccessible()) {
                constructor.setAccessible(true);
            }
            return constructor.newInstance(paramValues);
        } catch (ClassNotFoundException e) {
            LogUtil.printException("ClassNotFoundException", e);
        } catch (NoSuchMethodException e) {
            LogUtil.printException("NoSuchMethodException", e);
        } catch (IllegalAccessException e) {
            LogUtil.printException("IllegalAccessException", e);
        } catch (InstantiationException e) {
            LogUtil.printException("InstantiationException", e);
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof RuntimeException) {
                throw (RuntimeException) cause;
            } else if (cause instanceof Error) {
                throw (Error) cause;
            } else {
                throw new RuntimeException("fail to newInstance " + className);
            }
        }
        return null;
    }

    /**
     * 调用方法，通过string指定类
     *
     * @param target      目标对象，如果是调用静态方法，则可以为null
     * @param className   类名
     * @param methodName  方法名
     * @param paramTypes  参数类型数组
     * @param paramValues 参数值数组
     * @return 方法调用返回值
     */
    public static Object invokeMethod(Object target, String className, String methodName, Class[] paramTypes,
                                      Object[] paramValues) {

        try {
            Class clazz = forName(className);
            return invokeMethod(target, clazz, methodName, paramTypes, paramValues);
        } catch (ClassNotFoundException e) {
            LogUtil.printException("ClassNotFoundException", e);
        }
        return null;
    }

    /**
     * 调用方法，通过Class指定类
     *
     * @param target      目标对象，如果是调用静态方法，则可以为null
     * @param clazz       类
     * @param methodName  方法名
     * @param paramTypes  参数类型数组
     * @param paramValues 参数值数组
     * @return
     */
    public static Object invokeMethod(Object target, Class clazz, String methodName, Class[] paramTypes,
                                      Object[] paramValues) {
        try {
            Method method = clazz.getDeclaredMethod(methodName, paramTypes);
            if (!method.isAccessible()) {
                method.setAccessible(true);
            }
            return method.invoke(target, paramValues);
        } catch (SecurityException e) {
            LogUtil.printException("SecurityException", e);
        } catch (IllegalArgumentException e) {
            LogUtil.printException("IllegalArgumentException", e);
        } catch (IllegalAccessException e) {
            LogUtil.printException("IllegalAccessException", e);
        } catch (NoSuchMethodException e) {
            //这个日志...
            LogUtil.e("NoSuchMethodException", methodName);
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof RuntimeException) {
                throw (RuntimeException) cause;
            } else if (cause instanceof Error) {
                throw (Error) cause;
            } else {
                throw new RuntimeException("fail to calling method " + methodName);
            }
        }
        return null;
    }

    /**
     * 获取对象的指定字段
     *
     * @param target    目标对象，如果是获取静态字段，则可为null
     * @param className 类名
     * @param fieldName 字段名称
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static Object getField(Object target, String className, String fieldName) {
        try {
            Class clazz = forName(className);
            return getField(target, clazz, fieldName);
        } catch (ClassNotFoundException e) {
            LogUtil.printException("ClassNotFoundException", e);
        }
        return null;
    }

    /**
     * 获取对象的指定字段
     *
     * @param target    目标对象，如果是获取静态字段，则可为null
     * @param clazz     类
     * @param fieldName 字段名称
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static Object getField(Object target, Class clazz, String fieldName) {
        try {
            Field field = clazz.getDeclaredField(fieldName);
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            return field.get(target);
        } catch (SecurityException | IllegalArgumentException | IllegalAccessException e) {
            LogUtil.printException("RefInvoker.getField", e);
        } catch (NoSuchFieldException e) {
            // try supper for Miui, Miui has a class named MiuiPhoneWindow
            try {
                Field field = clazz.getSuperclass().getDeclaredField(fieldName);
                field.setAccessible(true);
                return field.get(target);
            } catch (Exception superE) {
                LogUtil.printException("RefInvoker.getField", e);
                LogUtil.printException("RefInvoker.getField", superE);
            }
        }
        return null;
    }

    /**
     * 更新字段值
     *
     * @param target     目标对象，如果是静态字段，这可为null
     * @param className  类
     * @param fieldName  字段名
     * @param fieldValue 字段值
     */
    @SuppressWarnings("rawtypes")
    public static void setField(Object target, String className, String fieldName, Object fieldValue) {
        try {
            Class clazz = forName(className);
            setField(target, clazz, fieldName, fieldValue);
        } catch (ClassNotFoundException e) {
            LogUtil.printException("RefInvoker.setField", e);
        }
    }

    /**
     * 更新字段值
     *
     * @param target     目标对象，如果是静态字段，这可为null
     * @param clazz      类
     * @param fieldName  字段名
     * @param fieldValue 字段值
     */
    public static void setField(Object target, Class clazz, String fieldName, Object fieldValue) {
        try {
            Field field = clazz.getDeclaredField(fieldName);
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            field.set(target, fieldValue);
        } catch (SecurityException | IllegalArgumentException | IllegalAccessException e) {
            LogUtil.printException("RefInvoker.setField", e);
        } catch (NoSuchFieldException e) {
            // try supper for Miui, Miui has a class named MiuiPhoneWindow
            try {
                Field field = clazz.getSuperclass().getDeclaredField(fieldName);
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                field.set(target, fieldValue);
            } catch (Exception superE) {
                LogUtil.printException("RefInvoker.setField", e);
                //superE.printStackTrace();
            }
        }
    }

    /**
     * 根据参数类型列表 获取Method
     *
     * @param object       对象
     * @param methodName   方法名
     * @param paramClasses 方法参数类型
     * @return Method对象 or null
     */
    public static Method findMethod(Object object, String methodName, Class[] paramClasses) {
        try {
            return object.getClass().getDeclaredMethod(methodName, paramClasses);
        } catch (NoSuchMethodException e) {
            LogUtil.printException("RefInvoker.findMethod", e);
        }
        return null;
    }

    /**
     * 使用实际参数值的列表 获取Method
     *
     * @param object     对象
     * @param methodName 方法名
     * @param params     方法参数列表
     * @return Method对象 or null
     */
    public static Method findMethod(Object object, String methodName, Object[] params) {
        if (params == null) {
            try {
                return object.getClass().getDeclaredMethod(methodName, (Class[]) null);
            } catch (NoSuchMethodException e) {
                LogUtil.printException("RefInvoker.findMethod", e);
            }
            return null;
        } else {
            Method[] methods = object.getClass().getDeclaredMethods();
            boolean isFound = false;
            Method method = null;
            for (Method m : methods) {
                if (m.getName().equals(methodName)) {
                    Class<?>[] types = m.getParameterTypes();
                    if (types.length == params.length) {
                        isFound = true;
                        for (int i = 0; i < params.length; i++) {
                            if (!(types[i] == params[i].getClass() || (types[i].isPrimitive() && primitiveToWrapper(types[i]) == params[i].getClass()))) {
                                isFound = false;
                                break;
                            }
                        }
                        if (isFound) {
                            method = m;
                            break;
                        }
                    }
                }
            }
            return method;
        }
    }

    private static final Map<Class<?>, Class<?>> primitiveWrapperMap = new HashMap<Class<?>, Class<?>>();

    static {
        primitiveWrapperMap.put(Boolean.TYPE, Boolean.class);
        primitiveWrapperMap.put(Byte.TYPE, Byte.class);
        primitiveWrapperMap.put(Character.TYPE, Character.class);
        primitiveWrapperMap.put(Short.TYPE, Short.class);
        primitiveWrapperMap.put(Integer.TYPE, Integer.class);
        primitiveWrapperMap.put(Long.TYPE, Long.class);
        primitiveWrapperMap.put(Double.TYPE, Double.class);
        primitiveWrapperMap.put(Float.TYPE, Float.class);
        primitiveWrapperMap.put(Void.TYPE, Void.TYPE);
    }

    static Class<?> primitiveToWrapper(final Class<?> cls) {
        Class<?> convertedClass = cls;
        if (cls != null && cls.isPrimitive()) {
            convertedClass = primitiveWrapperMap.get(cls);
        }
        return convertedClass;
    }

    public static ArrayList dumpAllInfo(String className) {
        try {
            Class clazz = Class.forName(className);
            return dumpAllInfo(clazz);
        } catch (ClassNotFoundException e) {
            LogUtil.printException("RefInvoker.dumpAllInfo", e);
        }
        return null;
    }

    public static ArrayList dumpAllInfo(Class clazz) {
        ArrayList arrayList = new ArrayList();

        LogUtil.i("clazz=" + clazz.getName());
        LogUtil.i("Superclass=" + clazz.getSuperclass());

        Constructor[] ctors = clazz.getDeclaredConstructors();
        if (ctors != null) {
            LogUtil.w("DeclaredConstructors--------------------" + ctors.length);
            for (Constructor c : ctors) {
                LogUtil.i(c);
                arrayList.add(c);
            }
        }

        Constructor[] publicCtors = clazz.getConstructors();
        if (publicCtors != null) {
            LogUtil.w("Constructors-------------------------" + publicCtors.length);
            for (Constructor c : publicCtors) {
                LogUtil.i(c);
                arrayList.add(c);
            }
        }

        Method[] mtds = clazz.getDeclaredMethods();
        if (mtds != null) {
            LogUtil.w("DeclaredMethods-------------------------" + mtds.length);
            for (Method m : mtds) {
                LogUtil.i(m);
                arrayList.add(m);
            }
        }

        Method[] mts = clazz.getMethods();
        if (mts != null) {
            LogUtil.w("Methods-------------------------" + mts.length);
            for (Method m : mts) {
                LogUtil.i(m);
                arrayList.add(m);
            }
        }

        Class<?>[] inners = clazz.getDeclaredClasses();
        if (inners != null) {
            LogUtil.w("DeclaredClasses-------------------------" + inners.length);
            for (Class c : inners) {
                LogUtil.i(c.getName());
                arrayList.add(c.getName());
            }
        }

        Class<?>[] classes = clazz.getClasses();
        if (classes != null) {
            LogUtil.w("classes-------------------------" + classes.length);
            for (Class c : classes) {
                LogUtil.i(c.getName());
                arrayList.add(c.getName());
            }
        }

        Field[] dfields = clazz.getDeclaredFields();
        if (dfields != null) {
            LogUtil.w("DeclaredFields-------------------------" + dfields.length);
            for (Field f : dfields) {
                LogUtil.i(f);
                arrayList.add(f);
            }
        }

        Field[] fields = clazz.getFields();
        if (fields != null) {
            LogUtil.w("Fields-------------------------" + fields.length);
            for (Field f : fields) {
                LogUtil.i(f);
                arrayList.add(f);
            }
        }

        Annotation[] anns = clazz.getAnnotations();
        if (anns != null) {
            LogUtil.w("Annotations-------------------------" + anns.length);
            for (Annotation an : anns) {
                LogUtil.i(an);
                arrayList.add(an);
            }
        }
        return arrayList;
    }

    public static ArrayList dumpAllInfo(Object object) {
        Class clazz = object.getClass();
        return dumpAllInfo(clazz);
    }

}