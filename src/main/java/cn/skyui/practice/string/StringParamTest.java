package cn.skyui.practice.string;

public class StringParamTest {

    public static void main(String[] args) {
//        String str = new String("abc");
//        char[] ch = {'a', 'b', 'c'};
//        change(str, ch);
//
//        System.out.print(str + " ");
//        System.out.print(ch);

        String ykpid = "ykpid_3550325730_2%7C26%7C81974%7C0____ykchannelid_10005884";
        if(ykpid != null && ykpid.length() > 0 && ykpid.contains("ykpid")) {
            if(ykpid.indexOf("_ykchannelid_") > 0) {
                ykpid = ykpid.substring(0, ykpid.indexOf("_ykchannelid_"));
                System.out.println("ykpid = " + ykpid);
            }
            String mLaifengCps = ykpid.replace("ykpid_", "");
            System.out.println("mLaifengCps = " + mLaifengCps);
        }

//        if(ykpid.indexOf("_ykchannelid_") > 0) {
//            ykpid = ykpid.substring(0, ykpid.indexOf("_ykchannelid_"));
//            System.out.println("ykpid = " + ykpid);
//        }
//        String mLaifengCps = ykpid.replace("ykpid_", "");
//        System.out.println("mLaifengCps = " + mLaifengCps);
    }

    public static void change(String str, char[] ch) {
        str = "gbc";
        ch[0] = 'g';
    }

}
