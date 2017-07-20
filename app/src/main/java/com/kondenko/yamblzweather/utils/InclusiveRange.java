package com.kondenko.yamblzweather.utils;


public class InclusiveRange {

    private int min;
    private int max;

    public InclusiveRange(int num) {
        this.min = num;
        this.max = num;
    }

    public InclusiveRange(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public boolean inRange(int num) {
        return num >= min && num <= max;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InclusiveRange that = (InclusiveRange) o;
        return min == that.min && max == that.max;

    }

    @Override
    public int hashCode() {
        int result = min;
        result = 31 * result + max;
        return result;
    }

}
