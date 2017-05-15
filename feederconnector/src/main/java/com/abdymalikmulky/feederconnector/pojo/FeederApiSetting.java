package com.abdymalikmulky.feederconnector.pojo;

/**
 * Created by efishery on 25/04/17.
 */

public class FeederApiSetting {

    private boolean isFeedingProgram;
    private int fishType;
    private double fishWeight;
    private int fishCount;
    private int frequency;
    private double feedType;
    private double feedWeight;

    public FeederApiSetting() {
    }


    public boolean isFeedingProgram() {
        return isFeedingProgram;
    }

    public void setFeedingProgram(boolean feedingProgram) {
        isFeedingProgram = feedingProgram;
    }

    public int getFishType() {
        return fishType;
    }

    public void setFishType(int fishType) {
        this.fishType = fishType;
    }

    public double getFishWeight() {
        return fishWeight;
    }

    public void setFishWeight(double fishWeight) {
        this.fishWeight = fishWeight;
    }

    public int getFishCount() {
        return fishCount;
    }

    public void setFishCount(int fishCount) {
        this.fishCount = fishCount;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public double getFeedType() {
        return feedType;
    }

    public void setFeedType(double feedType) {
        this.feedType = feedType;
    }

    public double getFeedWeight() {
        return feedWeight;
    }

    public void setFeedWeight(double feedWeight) {
        this.feedWeight = feedWeight;
    }

    @Override
    public String toString() {
        return "FeederApiSetting{" +
                "isFeedingProgram=" + isFeedingProgram +
                ", fishType=" + fishType +
                ", fishWeight=" + fishWeight +
                ", fishCount=" + fishCount +
                ", frequency=" + frequency +
                ", feedType=" + feedType +
                ", feedWeight=" + feedWeight +
                '}';
    }
}
