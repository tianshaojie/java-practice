package cn.skyui.practice.number;

import java.math.BigDecimal;

/**
 * Created by tiansj on 16/6/27.
 */
public class BigDecimalUtil {

    private static String roundCoins(long coins, int divisor) {
        BigDecimal bg = new BigDecimal(coins);
        return bg.divide(new BigDecimal(divisor), 1, BigDecimal.ROUND_HALF_UP).toString();
    }

    public static void main(String[] args) {
        System.out.println(roundCoins(10000, 10000));
        System.out.println(System.currentTimeMillis());
        System.out.println(roundCoins(11000, 10000));
        System.out.println(System.currentTimeMillis());
        System.out.println(roundCoins(15600, 10000));
        System.out.println(roundCoins(200000000, 100000000));
        System.out.println(roundCoins(230000000, 100000000));
    }
}
