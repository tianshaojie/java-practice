package cn.skyui.practice.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class WeixinJavascript {

    @JavascriptInterface
    public void chooseImage() {
    }


    @JavascriptInterface
    public void previewImage() {
    }


    @JavascriptInterface
    public void uploadImage() {
    }

    @JavascriptInterface
    public void downloadImage() {
    }

    public void noAnnotationPublicMethod() {
    }

    private void noAnnotationPrivateMethod() {
    }


    public static void main(String[] args) {

        Method[] methods = WeixinJavascript.class.getMethods();
        for(Method method : methods) {
            Annotation javascriptAnnotation = method.getAnnotation(JavascriptInterface.class);
            if(javascriptAnnotation != null) {
                System.out.println(method.getName());
            }
        }

    }

}
