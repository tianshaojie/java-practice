package cn.skyui.practice.set;

import java.util.HashSet;

public class SiteFilterBean {
    String ip;
    HashSet<String> market = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (ip == null || market == null) {
            return true;
        }
        SiteFilterBean test = (SiteFilterBean) o;
        if (ip.trim().equals(test.ip.trim())) {
            test.getMarket().addAll(market);
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        if (ip == null || market == null) {
            return -1;
        }
        int result = ip.hashCode();
        return result;
    }

    public SiteFilterBean() {
    }

    public SiteFilterBean(String market, String ip) {
        this.market.add(market);
        this.ip = ip;
    }


    @Override
    public String toString() {
        return "SiteFilterBean{" +
                "market='" + market + '\'' +
                ", ip=" + ip +
                '}';
    }

    public HashSet<String> getMarket() {
        return market;
    }


    public String getIp() {
        return ip;
    }
}
