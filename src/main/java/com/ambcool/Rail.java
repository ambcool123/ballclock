package com.ambcool;

import java.util.Deque;
import java.util.concurrent.LinkedBlockingDeque;

public final class Rail implements Railable {

    private final Deque<Integer> rail;
    private static FeedRail feed;
    private int capacity;
    private Railable nextRail;

    public Rail(int capacity, Railable nextRail) {
        this.capacity = capacity;
        this.nextRail = nextRail;
        rail = new LinkedBlockingDeque<>(capacity);
    }

    /**
     * This method is used once to set the single FeedRail for each of the Rails. Each rail interacts with the FeedRail in some way or another.
     * @param feedRail
     */
    public static void setFeedRail(FeedRail feedRail) {
        feed = feedRail;
    }

    @Override
    public void setNextRail(Railable nextRail) {
        this.nextRail = nextRail;
    }

    @Override
    public void popToNext() {
        nextRail.flush(rail.pollFirst());
    }

    @Override
    public void flush(Integer ball) {
        BallClockUtils.insertOrFlush(ball, this, feed, nextRail);
    }

    @Override
    public java.util.Deque<Integer> getQueue() {
        return rail;
    }

    @Override
    public boolean isEmpty() {
        return rail.isEmpty();
    }

    @Override
    public boolean isFull() {
        return rail.size() == capacity;
    }

    @Override
    public String toString() {
        return rail.toString();
    }
}
