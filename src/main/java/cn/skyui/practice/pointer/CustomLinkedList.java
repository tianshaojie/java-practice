package cn.skyui.practice.pointer;

import java.util.ArrayList;

/**
 * Created by tiansj on 16/4/26.
 */
public class CustomLinkedList {

    static ArrayList<LinkedObject> poolDataList = new ArrayList<>();

    static LinkedObject first;
    static LinkedObject last;

    public static void init(LinkedObject initObj) {
        first = last = initObj;
    }

    public static void initPoll() {
        poolDataList.add(new LinkedObject(1));
        poolDataList.add(new LinkedObject(2));
        poolDataList.add(new LinkedObject(3));
    }


    public static LinkedObject prev(LinkedObject curr) {
        if(curr.prev != null) {
            return curr.prev;
        } else {
            if(!poolDataList.isEmpty()) { // 数据池不为空
                LinkedObject item = poolDataList.remove(0);
                curr.prev = item; // 建立关联
                item.next = curr;
                item.prev = null;
                first = item; // 改变指针位置
//                System.out.println("first-" + first.id + " > ");
//                System.out.println("last-" + last.id + " > ");
            } else {  // 数据池为空，返回最后一条
//                initPoll();
                // 链表不止一条，把最后条移动到第一条
                if(first.next != null) {
                    LinkedObject lastPrev = last.prev;
                    lastPrev.next = null;
                    last.prev = null;  // 断开连接
                    last.next = first; // 最后一条移动到第一条
                    first.prev = last;
                    first = last;
                    last = lastPrev;
                }
            }
            return first;
        }
    }

    public static LinkedObject next(LinkedObject curr) {
        if(curr.next != null) {
            return curr.next;
        } else {
            if(!poolDataList.isEmpty()) { // 数据池不为空
                LinkedObject item = poolDataList.remove(0);
                curr.next = item; // 建立关联
                item.prev = curr;
                item.next = null;
                last = item; // 改变指针位置
                return item;
            } else {  // 数据池为空，返回第一条
//                initPoll();
                // 链表不止一条，把第一条移动到最后一条
                if(last.prev != null) {
                    LinkedObject firstNext = first.next;
                    firstNext.prev = null;
                    first.next = null;
                    first.prev = last;
                    last.next = first;
                    last = first;
                    first = firstNext;
                }
                return last;
            }
        }
    }

    public static void printStr() {
        LinkedObject pointer = first;
        do {
            System.out.print(pointer.id + " > ");
            pointer = pointer.next;
        } while (pointer != null);
        System.out.println("");
        System.out.println("first-id-" + first.id);
        System.out.println("last-id-" + last.id);
        System.out.println("");
    }

    public static void main(String[] args) {
        LinkedObject curr = new LinkedObject(0);
        init(curr);
        printStr();
//        curr = prev(curr);
//        initPoll();
//        curr = prev(curr);
//        printStr();
//
//        curr = prev(curr);
//        printStr();
//
//        curr = prev(curr);
//        printStr();
//
//        curr = prev(curr);
//        printStr();

        curr = next(curr);
        initPoll();
        curr = next(curr);
        printStr();

        curr = next(curr);
        printStr();

        curr = next(curr);
        printStr();

        curr = next(curr);
        printStr();
    }

}
