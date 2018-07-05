package com.yuzhi.fine.annotation;

import com.yuzhi.fine.impl.IInterface;

public class C implements IInterface {
    @Override
    public String eventName() {
        return "C.eventName";
    }
}
