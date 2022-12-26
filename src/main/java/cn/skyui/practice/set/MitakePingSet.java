package cn.skyui.practice.set;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class MitakePingSet extends HashSet<SiteFilterBean> {
    private final String TAG = MitakePingSet.class.getSimpleName();
    /**
     * 需求pingIp的所有的市场
     */
    private ConcurrentHashMap<String, String> marketMap;

    public boolean add(String market, String ip) {
        if (ip == null || ip.length() == 0) {
            return false;
        }
        SiteFilterBean object = new SiteFilterBean(market, ip);
        return super.add(object);
    }

    public boolean add(String market, String[] ips) {
        if (ips == null || ips.length == 0) {
            return false;
        }
        if (marketMap == null){
            marketMap = new ConcurrentHashMap<>();
        }
        marketMap.put(market, market);
        for (String ip : ips) {
            SiteFilterBean object = new SiteFilterBean(market, ip);
            super.add(object);
        }
        return true;
    }

    public ConcurrentHashMap<String, String> getMarketMap() {
        return marketMap;
    }


    public static void main(String[] args) {
        Map<String, List<String>> marketSites = new HashMap<>();

        marketSites.put("sh", Arrays.asList("ip1", "ip2", "ip3"));
        marketSites.put("sz", Arrays.asList("ip1", "ip2"));

        MitakePingSet hashSet = new MitakePingSet();
        for(String key : marketSites.keySet()) {
            hashSet.add(key, (String[]) marketSites.get(key).toArray());
        }

        System.out.println(hashSet.toString());
    }
}
