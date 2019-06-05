//package cn.skyui.practice.founder;
//
//import android.app.Activity;
//import android.content.Context;
//
//import java.util.Map;
//
///**
// * 小方埋点相关接口
// */
//public interface FZStatisticsInterface {
//
//    /**
//     * 如果未添加“onStart”和“onStop”调用，则某些功能将无效，例如将不会跟踪会话。
//     * 必须在活动“onStart”函数中调用“onStart”，它不能在“onCreate”
//     * 或任何其他地方调用，否则应用程序将收到异常，onResume、onPause同理
//     * @param context
//     */
//    void onResume(Activity context);
//
//    void onPause(Activity context);
//
//    void onStart(Activity context);
//
//    void onStop();
//
//    /**
//     * 事件的计数
//     * @param eventId 埋点对应的事件Id
//     */
//    void onEvent(String eventId);
//
//    /**
//     * 事件带分段的计数
//     * @param eventId 埋点对应的事件Id
//     * @param map     分段对应的键值对的集合
//     */
//    void onEvent(String eventId, Map<String, String> map);
//
//    /**
//     * 事件键，计数和与分段的总和
//     * @param eventId 埋点对应的事件Id
//     * @param map     分段对应的键值对的集合
//     * @param sum
//     */
//    void onEventValue(String eventId, Map<String, String> map, double sum);
//
//    /**
//     * 事件Id，计数，总和和持续时间与分段
//     * @param eventId 埋点对应的事件Id
//     * @param map 分段对应的键值对的集合
//     * @param count 计数
//     * @param sum
//     */
//    void onEventValue(String eventId, Map<String, String> map, int count, double sum);
//
//    /**
//     * 自定义埋点
//     * @param context
//     * @param customData 自定义一些信息的集合
//     */
//    void setUserData(Context context, Map<String, String> customData);
//
//    /**
//     * 定时活动
//     * 通过定义开始和停止时刻，可以创建创建定时事件
//     * 结束活动时，您还可以提供其他信息。但在这种情况下，你必须提供细分，计数和总和。
//     * 这些的默认值是“null”，1和0。
//     */
//    void startEvent(String key);
//
//    void endEvent(String key, Map<String, String> segmentation, double sum);
//
//}
