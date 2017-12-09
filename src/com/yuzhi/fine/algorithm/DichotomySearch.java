package com.yuzhi.fine.algorithm;

/**
 * 二分法查找
 * 规则：有序数组（升序），中间值查找，等于返回，小于则在前半段查找，大于在后半段查找
 */
public class DichotomySearch {

    public static void main(String[] args) {
        int[] source1 = new int[] {3};
        System.out.println("source1 search target index = " + search1(source1, 3));

        int[] source2 = new int[] {1, 2, 3, 4, 5, 6, 7, 9, 10};
        System.out.println("source2 search target1 index = " + search1(source2, 2));
        System.out.println("source2 search target2 index = " + search1(source2, 5));
        System.out.println("source2 search target3 index = " + search1(source2, 7));
        System.out.println("source2 search target4 index = " + search1(source2, 8));
    }

    // 递归
    public static int search(int[] source, int target) {
        if(source == null || source.length == 0) {
            return -1;
        }

        int len = source.length;
        if(len == 1) {
            return source[0] == target ? 0 : -1;
        }
        if(target < source[0]) {
            return -1;
        }
        if(target > source[len - 1]) {
            return -1;
        }

        int index = len / 2;
        int value = source[index];
        if(value == target) {
            return index;
        } else if(target > value) {
            int[] rest = new int[len - index - 1];
            for(int i = index + 1; i < len; i++) {
                rest[i - index -1] = source[i];
            }
            int result =  search(rest, target);
            if(result != -1) {
                result += index + 1;
            }
            return result;
        } else {
            int[] rest = new int[index];
            for(int i = 0; i < index; i++) {
                rest[i] = source[i];
            }
            return search(rest, target);
        }
    }

    // 非递归
    public static int search1(int[] source, int target) {
        if(source == null || source.length == 0) {
            return -1;
        }

        int len = source.length;
        if(len == 1) {
            return source[0] == target ? 0 : -1;
        }
        if(target < source[0]) {
            return -1;
        }
        if(target > source[len - 1]) {
            return -1;
        }

        int start = 0, end = len - 1;
        while (start <= end) {
            int middle = (start + end) >> 1;
            if(target > source[middle]) {
                start = middle + 1;
            } else if(target < source[middle]) {
                end = end - 1;
            } else {
                return middle;
            }
        }
        return -1;
    }

}
