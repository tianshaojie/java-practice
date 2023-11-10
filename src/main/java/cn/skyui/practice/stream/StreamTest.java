package cn.skyui.practice.stream;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class StreamTest {

    public static class Fund {
        public String fundName;
        public Boolean canExchange;

        public Fund(String fundName, Boolean canExchange) {
            this.fundName = fundName;
            this.canExchange = canExchange;
        }


        public Boolean canExchange() {
            return canExchange;
        }
    }

    public static void main(String[] args) {

        Fund fund1 = new Fund("1", false);
        Fund fund2 = new Fund("2", true);
        Fund fund3 = new Fund("3", false);

        List<Fund> list = new ArrayList<>();
        list.add(fund1);
        list.add(fund2);
        list.add(fund3);

        list = list.stream().sorted(Comparator.comparing(Fund::canExchange,Comparator.reverseOrder())).collect(Collectors.toList());
        System.out.println(JSON.toJSON(list));
    }
}
