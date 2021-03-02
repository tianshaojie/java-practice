package cn.skyui.practice.tcpping1;

public class Statistics {

    private long totalCycleTime = 0;
    private long CycleTime = 0;
    private long ABTime = 0;
    private long BATime = 0;

    private Statistics(){
    }

    public static Statistics getInstance() {
        Statistics S = new Statistics();
        return S;
    }

    public synchronized void resetTimes() {
        CycleTime = 0;
        ABTime = 0;
        BATime = 0;
    }

    public void updateStatistics(long timestampA, long timestampB, long currentA) {
        long cycleTime = currentA - timestampA;
        totalCycleTime+=cycleTime;
        CycleTime+=cycleTime;

        ABTime+= Math.abs(timestampB - timestampA);
        BATime+= Math.abs(currentA - timestampB);
    }

    public long getCycleTime() {
        return CycleTime;
    }

    public long getABTime() {
        return ABTime;
    }

    public long getBATime(){
        return BATime;
    }

    public long getTotalCycleTime() {
        return totalCycleTime;
    }
}
