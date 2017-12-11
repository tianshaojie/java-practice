package com.yuzhi.fine.finaltest;

/**
 * Created by tiansj on 17/6/17.
 */
public class TestFinalParam {

    public static void main(String[] args) {

//        A a = new A();
//        C c = new C();
//        c.printC(a.proxyA("1"));

        A a = new A();
        C c = new C();
        c.shoutc(a.shout(5));

    }

    static class A {
        public void shouta() {
            System.out.println("Hello A");
        }

        public A shout(final int arg) {
            class B extends A {
                public void shouta() {
                    System.out.println("Hello B" + arg);
                }
            }
            return new B();
        }
    }

    static class C {
        void shoutc(A a) {
            a.shouta();
        }
    }

    class AA {

        String str = "AA";

        public C c(String a) {
            return new C() {
                private String label = a;
                void value() {

                    System.out.print(str);
                }
            };
        }
    }


//    public static class A {
//
//        public void printA() {
//            System.out.println("A class print");
//        }
//
//
//        public A proxyA(String arg) {
//            class B extends A {
//                public void printA() {
//                    System.out.println("B class print: arg = " + arg);
//                }
//            }
//            return new B();
//        }
//
//    }
//
//    public static class C {
//        public void printC(A a) {
//            a.printA();
//        }
//    }



}
