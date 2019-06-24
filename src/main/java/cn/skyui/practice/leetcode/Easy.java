package cn.skyui.practice.leetcode;

public class Easy {

    public static int[] twoSum(int[] nums, int target) {
        int[] result = new int[]{0, 0};
        if(nums.length < 2) {
            return result;
        }

        int i = 0;
        int j = 1;
        for(i = 0; i < nums.length - 1; i++) {
            for(j = 1; j < nums.length - 1; j++) {
                if(nums[i] < nums[j]) {
                    int temp = nums[i];
                    nums[i] = nums[j];
                    nums[j] = temp;
                }
            }
        }

        int m = 0, n = nums.length - 1;
        while (m < n) {
            if(nums[m] + nums[n]  == target) {
                result[0] = m;
                result[1] = n;
                break;
            } else if(nums[m] + nums[n] > target) {
                n = n - 1;
            } else {
                m = m + 1;
            }
        }
        System.out.println(result[0]);
        System.out.println(result[1]);
        return result;
    }

    public static void main(String[] args) {
        twoSum(new int[]{1, 2, 7 ,11, 15}, 9);
    }

}
