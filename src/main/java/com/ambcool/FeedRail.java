package com.ambcool;

import java.util.Arrays;
import java.util.Deque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * FeedRail is the rail that feeds the time-framed rails, specifically the minutes rail.
 */
public final class FeedRail implements Railable {

    private final Object[] firstStateFeedRail;
    private int capacity;
    private static FeedRail feed;
    private Railable nextRail;

    public final Deque<Integer> rail;

    public void setNextRail(Railable nextRail) {
        this.nextRail = nextRail;
    }

    public FeedRail(int capacity, Railable nextRail) {
        this.capacity = capacity;
        this.nextRail = nextRail;
        rail = new LinkedBlockingDeque<>(capacity);
        firstStateFeedRail = BallClockUtils.stockRail(capacity, this);
    }

    @Override
    public void popToNext() {
        nextRail.flush(rail.pollFirst());
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
    public void flush(Integer ball) {
        BallClockUtils.insertOrFlush(ball, this, feed, nextRail);
    }

    @Override
    public String toString() {
        return rail.toString();
    }

    public boolean isFirstState() {
        return rail.size() == capacity && Arrays.equals(rail.toArray(), firstStateFeedRail);
    }
}
