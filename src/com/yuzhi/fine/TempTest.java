package com.yuzhi.fine;

/**
 * Created by tiansj on 2017/10/1.
 */
public class TempTest {

    public static void main(String[] args) {
        IntegerGetInt integerGetInt = new IntegerGetInt();
        int a = integerGetInt.a;
        System.out.print("a="+a);
    }

    public static class IntegerGetInt {
        public Integer a;
    }



}
