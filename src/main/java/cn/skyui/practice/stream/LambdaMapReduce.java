package cn.skyui.practice.stream;

import cn.skyui.practice.pinger.Connector;
import com.alibaba.fastjson.JSON;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LambdaMapReduce {

    public static class User {
        enum Sex {
            MALE,
            FEMALE
        }

        int id;
        String name;
        int age;
        Sex gender;
        int score;

        public User(int id, String name, int age, Sex sex, int score) {
            this.id = id;
            this.name = name;
            this.age = age;
            this.gender = sex;
            this.score = score;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public Sex getGender() {
            return gender;
        }

        public void setGender(Sex gender) {
            this.gender = gender;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }
    }


    private static List<User> users = Arrays.asList(
            new User(1, "张三", 12,User.Sex.MALE, 50),
            new User(2, "李四", 21, User.Sex.FEMALE, 60),
            new User(3,"王五", 32, User.Sex.MALE, 70),
            new User(4, "赵六", 32, User.Sex.FEMALE, 80));


    public static void main(String[] args) {

        Map<String, Long> customMultiRangeKey = users.parallelStream().collect(Collectors.groupingBy(u -> {
                if (u.getAge() < 18) {
                    return "C";
                } else if (u.getAge() < 30) {
                    return "B";
                } else {
                    return "A";
                }
            }, Collectors.counting())
        );

        Map<String, Long> customMultiRangeKey1 = users.parallelStream().collect(Collectors.groupingBy(u -> {
                if (u.getGender() == User.Sex.MALE) {
                    return "MALE";
                } else if (u.getGender() == User.Sex.FEMALE) {
                    return "FEMALE";
                }
                return "";
            },  Collectors.counting())
        );

        System.out.println(JSON.toJSONString(customMultiRangeKey));
        System.out.println(JSON.toJSONString(customMultiRangeKey1));



        //多级分组
        //1.先根据年龄分组,然后再根据成绩分组
        //分析:第一个Collectors.groupingBy() 使用的是(年龄+成绩)两个维度分组,所以使用两个参数 groupingBy()方法
        //    第二个Collectors.groupingBy() 就是用成绩分组,使用一个参数 groupingBy() 方法
        Map<Object, Map<Object, Map<Object, Long>>> map = users.parallelStream().collect(
                Collectors.groupingBy(str -> str.getAge(),
                        Collectors.groupingBy(str -> str.getScore(), Collectors.groupingBy((student) -> {
                                    if (student.getScore() >= 60) {
                                        return "及格";
                                    } else {
                                        return "不及格";
                                    }
        }, Collectors.counting()))));
        System.out.println(JSON.toJSONString(map));

//        map.forEach((key,value)->{
//            System.out.println("年龄:" + key);
//            value.forEach((k2,v2)->{
//                System.out.println("\t" + v2);
//            });
//        });



//        //lambda表达式实现
//        Map<String, Long> ageCountMap = users.stream().collect(
//                Collectors.groupingBy(x -> x.getAge() + "#" + x.getGender() ,
//                        Collectors.counting()));
//        System.out.println(JSON.toJSONString(ageCountMap));




//        Map<Integer, Long> collect =
//                users.stream().collect(Collectors.groupingBy(User::getAge, Collectors.counting()));
//        System.out.println(JSON.toJSONString(collect));
//
//        Map<Integer, Map<Boolean, Long>> collect1 =
//                users.stream().collect(Collectors.groupingBy(User::getAge,
//                        Collectors.partitioningBy(s -> s.getAge() > 18, Collectors.counting())));
//        System.out.println(JSON.toJSONString(collect1));
//
//        Map<Integer, Map<Boolean, Long>> groupPartition = users.stream()
//                .collect(Collectors.groupingBy(User::getAge,
//                        Collectors.partitioningBy(s -> s.getAge() > 60, Collectors.counting()))
//                );
//
//        System.out.println(JSON.toJSONString(groupPartition));

//        reduceAvg();
//        reduceSum();


        //与stream.reduce方法不同，Stream.collect修改现存的值，而不是每处理一个元素，创建一个新值
        //获取所有男性用户的平均年龄
//        Averager averageCollect = users.parallelStream()
//                .filter(p -> p.getGender() == User.Sex.MALE)
//                .map(User::getAge)
//                .collect(Averager::new, Averager::accept, Averager::combine);
//
//        System.out.println("Average age of male members: "
//                + averageCollect.average());

//        //获取年龄大于12的用户列表
//        List<User> list = users.parallelStream().filter(p -> p.age > 12)
//                .collect(Collectors.toList());
//        System.out.println(list);
//
//        //按性别统计用户数
//        Map<User.Sex, Integer> map = users.parallelStream().collect(
//                Collectors.groupingBy(User::getGender,
//                        Collectors.summingInt(p -> 1)));
//        System.out.println(map);
//
//        //按性别获取用户名称
//        Map<User.Sex, List<String>> map2 = users.stream()
//                .collect(
//                        Collectors.groupingBy(
//                                User::getGender,
//                                Collectors.mapping(User::getName,
//                                        Collectors.toList())));
//        System.out.println(map2);
//
//        //按性别求年龄的总和
//        Map<User.Sex, Integer> map3 = users.stream().collect(
//                Collectors.groupingBy(User::getGender,
//                        Collectors.reducing(0, User::getAge, Integer::sum)));
//
//        System.out.println(map3);
//
//        //按性别求年龄的平均值
//        Map<User.Sex, Double> map4 = users.stream().collect(
//                Collectors.groupingBy(User::getGender,
//                        Collectors.averagingInt(User::getAge)));
//        System.out.println(map4);

    }

    // 注意，reduce操作每处理一个元素总是创建一个新值，
    // Stream.reduce适用于返回单个结果值的情况
    //获取所有用户的平均年龄
    private static void reduceAvg() {
        // mapToInt的pipeline后面可以是average,max,min,count,sum
        double avg = users.parallelStream().mapToInt(User::getAge)
                .average().getAsDouble();

        System.out.println("reduceAvg User Age: " + avg);
    }

    //获取所有用户的年龄总和
    private static void reduceSum() {
        double sum = users.parallelStream().mapToInt(User::getAge)
                .reduce(0, (x, y) -> x + y); // 可以简写为.sum()

        System.out.println("reduceSum User Age: " + sum);
    }

}
