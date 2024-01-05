package cn.skyui.practice.utils;

public class SortBean {

    public String date;
    public int type;

    public long time;

    public SortBean(String date, int type) {
        this.date = date;
        this.type = type;
        time = System.nanoTime();
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
