package cn.skyui.practice.pointer;

/**
 * Created by tiansj on 16/4/26.
 */
public class LinkedObject {
    public int id;
    public LinkedObject prev;
    public LinkedObject next;

    public LinkedObject(){}
    public LinkedObject(int id){
        this.id = id;
    }
}
