package cn.skyui.practice.listener;

/**
 * Created by tiansj on 2017/12/9.
 */
public class ListenerPractice {

    public static void main(String[] args) {

        View view = new View();
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick() {
                System.out.println("on click");
            }
        });

        view.click();
    }

}
