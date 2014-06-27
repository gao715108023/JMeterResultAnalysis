package com.gcj.bean;

public class AggregateReport implements Comparable<AggregateReport> {

    private Integer threads;

    private String label;

    private int samples;

    private int average;

    private int median;

    private int ninetyLine;

    private int min;

    private int max;

    private float error;

    private float throughput;

    public Integer getThreads() {
        return threads;
    }

    public void setThreads(Integer threads) {
        this.threads = threads;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getSamples() {
        return samples;
    }

    public void setSamples(int samples) {
        this.samples = samples;
    }

    public int getAverage() {
        return average;
    }

    public void setAverage(int average) {
        this.average = average;
    }

    public int getMedian() {
        return median;
    }

    public void setMedian(int median) {
        this.median = median;
    }

    public int getNinetyLine() {
        return ninetyLine;
    }

    public void setNinetyLine(int ninetyLine) {
        this.ninetyLine = ninetyLine;
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

    public float getError() {
        return error;
    }

    public void setError(float error) {
        this.error = error;
    }

    public float getThroughput() {
        return throughput;
    }

    public void setThroughput(float throughput) {
        this.throughput = throughput;
    }

    @Override
    public int compareTo(AggregateReport arg0) {
        return this.getThreads().compareTo(arg0.getThreads());
    }
}
