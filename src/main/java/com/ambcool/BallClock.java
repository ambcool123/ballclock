package com.ambcool;

import com.google.common.base.Stopwatch;

public class BallClock {

    Params args;
    Railable minuteRail, fiveMinuteRail, oneHourRail;
    FeedRail feedRail;
    boolean verbose;
    int minutes;

    public BallClock(Params args) {
        this.args = args;
        minutes = args.isMinutes() ? args.getMinutes() : 0;
        verbose = this.args.isVerbose();
        feedRail = new FeedRail(this.args.getBalls(), null);
        oneHourRail = new Rail(11, feedRail);
        fiveMinuteRail = new Rail(11, oneHourRail);
        minuteRail = new Rail(4, fiveMinuteRail);
        feedRail.setNextRail(minuteRail);
        Rail.setFeedRail(feedRail);
    }

    /**
     * Run with Balls
     *
     * @throws BallClockException
     */
    public void withBalls() throws BallClockException {
        if (!args.isBall()) {
            throw new BallClockException("Ball parameter should be between 27 and 127");
        }
        Stopwatch stopwatch = Stopwatch.createStarted();
        double days = runIterations();
        stopwatch.stop();

        BallClockUtils.printStats(args, stopwatch, days);
    }

    /**
     * Run for minutes
     * @throws BallClockException
     */
    public void forMinutes() throws BallClockException {
        if (!args.isBall() || !args.isMinutes()) {
            throw new BallClockException("Ball parameter should be between 27 and 127 and minutes should be greater than 0");
        }

        runForMinutes();

        BallClockUtils.printJsonMinutes(this);
    }

    /**
     * Run iterations.
     * @return number of days ran
     */
    public double runIterations() {
        int cycles = 1;
        do {
            feedRail.popToNext();
            if (feedRail.isFirstState()) {
                break;
            }
        } while (cycles++ > 0);

        return cycles / (60d * 24); // days
    }

    /**
     * Run for Minutes.
     *
     * @return number of days in operation
     */
    public double runForMinutes() {
        int minutes = args.getMinutes();
        int cycles = 1;

        while (cycles < minutes + 1) {
            feedRail.popToNext();
            cycles++;
        }

        return cycles / (60d * 24); //days
    }
}
