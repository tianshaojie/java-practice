package com.yuzhi.fine.impl;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class ClassUtil {


    /**
     * @Description: 根据一个接口返回该接口的所有类
     * @param c 接口
     * @return List<Class>    实现接口的所有类
     */
    @SuppressWarnings("unchecked")
    public static List<Class> getAllClassByInterface(Class c){
        List returnClassList = new ArrayList<Class>();
        //判断是不是接口,不是接口不作处理
        if(c.isInterface()){
//            String packageName = c.getPackage().getName();  //获得当前包名
            String packageName = "com.yuzhi.fine";
            try {
                List<Class> allClass = getClasses(packageName);//获得当前包以及子包下的所有类

                //判断是否是一个接口
                for(int i = 0; i < allClass.size(); i++){
                    if(c.isAssignableFrom(allClass.get(i))){
                        if(!c.equals(allClass.get(i))){
                            returnClassList.add(allClass.get(i));
                        }
                    }
                }
            } catch (Exception e) {
            }
        }
        return returnClassList;
    }

    /**
     *
     * @Description: 根据包名获得该包以及子包下的所有类不查找jar包中的
     * @param packageName 包名
     * @return List<Class>    包下所有类
     */
    private static List<Class> getClasses(String packageName) throws ClassNotFoundException,IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = packageName.replace(".", "/");
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<File>();
        while(resources.hasMoreElements()){
            URL resource = resources.nextElement();
            String newPath = resource.getFile().replace("%20", " ");
            dirs.add(new File(newPath));
        }
        ArrayList<Class> classes = new ArrayList<Class>();
        for(File directory:dirs){
            classes.addAll(findClass(directory, packageName));
        }
        return classes;
    }

    private static  List<Class> findClass(File directory, String packageName)
            throws ClassNotFoundException{
        List<Class> classes = new ArrayList<Class>();
        if(!directory.exists()){
            return classes;
        }
        File[] files = directory.listFiles();
        for(File file:files){
            if(file.isDirectory()){
                assert !file.getName().contains(".");
                classes.addAll(findClass(file, packageName+"."+file.getName()));
            }else if(file.getName().endsWith(".class")){
                classes.add(Class.forName(packageName+"."+file.getName().substring(0,file.getName().length()-6)));
            }
        }
        return classes;
    }

    @SuppressWarnings("unchecked")
    public static List<Class> getAllClassByAnnotation(Class annotationClass){
        List returnClassList = new ArrayList<Class>();
        //判断是不是注解
        if(annotationClass.isAnnotation()){
            String packageName = annotationClass.getPackage().getName();    //获得当前包名
            try {
                List<Class> allClass = getClasses(packageName);//获得当前包以及子包下的所有类

                for(int i = 0; i < allClass.size(); i++){
                    if(allClass.get(i).isAnnotationPresent(annotationClass)){
                        returnClassList.add(allClass.get(i));
                    }
                }
            } catch (Exception e) {
            }
        }
        return returnClassList;
    }



    public static void main(String[] args) throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
//      List<Class> list = ClassUtil.getAllClassByInterface(SignInterface.class);
//      for(Class c : list){
//          String name = c.getAnnotation(SignAnnotation.class).getClass().
//          System.out.println(name);
//          SignInterface sign = (SignInterface)c.newInstance();
//          System.out.println(sign.returnStr());
//      }
        List<Class> list = ClassUtil.getAllClassByInterface(IInterface.class);
        for(Class c : list){
            Method method = c.getDeclaredMethod("eventName");
            System.out.println(method.invoke(c.newInstance()));
        }

    }



}
