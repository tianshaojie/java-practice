package cn.skyui.practice.annotation;

import cn.skyui.practice.impl.IInterface;

public class C implements IInterface {
    @Override
    public String eventName() {
        return "C.eventName";
    }
}
