package cn.skyui.practice;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class Java2Dart {

    public long l;
    public short s;
    public float f;
    public boolean b;
    public Boolean bool;

    protected LinkedHashMap<String, Integer> linkedHashMap;
    private int integerWidth;
    private int intHeight;

    Long l1;
    HashMap<Long, Long> map1;
    HashMap<Long, List<Long>> map2;
    Long forLong;

    StringBuilder stringBuilder;
    StringBuilder abcStringBuilder;

    private String[] column;
    private CopyOnWriteArrayList<OHLCItem> list;
    private byte[] b1 = new byte[18];
    private final HashMap<String, HashMap<String, OHLCItem>> cache = new HashMap<>();
    private static Hashtable<String, OHLCItem> list2 = new Hashtable<>();
    private static ConcurrentMap<String, String> mInfoLevelStatusMap = new ConcurrentHashMap<String, String>();
    private SparseArray<Integer> mSectionPositionCache;//位置缓存
    private static SparseArray<String[]> apis = new SparseArray<>();

    public static byte[] test(String key){
        // Double data8 = FormatUtility.formatStringToDouble(isStrEmpty(model.getSmallSellVolume()));
        SparseIntArray addValueColumnMappingTable = new SparseIntArray();
        Hashtable table3 =new Hashtable();
        ConcurrentMap<String, String> infoLevelStatusMap = new ConcurrentHashMap<>();
        ArrayList<HashMap<String, Object>> infos = new ArrayList<>();
        HashSet<String> market = new HashSet<>();
        StringBuilder builder = new StringBuilder();
        System.currentTimeMillis();

        int rating = Integer.valueOf("1");
        String[] strs = {"2", "3"};
        int lenth = 1 + Integer.parseInt(strs[1]);
        boolean clientAuth = true;
        Boolean.toString(clientAuth);

        // if (mCounterUtil.get() > 0){}
        // get(MarketPermission.getInstance().getMarket(permission), ApiHttp.quote, command, httpCallback, version);
        mInfoLevelStatusMap.get(market);
//        bondItem.setApplyCode((String) map.get("APPLYCODE"));
//        String[] shl2 = Network.getInstance().server.get("SHL2");
//        newsBean.publishDate = map.get("PUBDATE") == null ? "" : (String) map.get("PUBDATE");
//        apis.get(type)[1];
//
//        // Network.getInstance().server.put("tcphkd1", new String[]{});
//        SparseArray statusKeyValues = new SparseArray<>();
//        statusKeyValues.put(1, "停牌");
//        statusKeyValues.put(2, "除权");
//        statusKeyValues.put(3, "除息");
//        Map keyNameMap = new HashMap();
//        keyNameMap.put("EPSBASIC","每股收益");
//        keyNameMap.put(position, i);
//        Network.getInstance().server.put("tcphkaz", new String[]{});
//        Network.getInstance().server.put(MarketSiteType.TCPSH, new String[]{});
//
//
//        FormatUtility.formatVolumeRowData(jsonArray1.optString(j), MarketType.BK);
//        int[] allQuoteColumns = new int[length];
//        List<Integer> plateColumns = new int[linkedSet.length];
//        List<Integer> allAddValueColumns = new int[plateAddValueColumnMappingTable.length];
//        new String[]{plateIndexItem.mainforceMoneyInflow};
//        QuoteTypeCode.getQuoteMustColumns(new int[]{QuoteCustomField.quote_NAME});
//        FormatUtility.formatStringToFloat(chartItemsList.get(i).tradeVolume);
//        JSONObject obj = json0.getJSONObject(i);
//        newShareList.setSubTitles(new String[]{"股票", "发行价", "市盈率", "中签公布"});

        return new byte[168];
    }
}
