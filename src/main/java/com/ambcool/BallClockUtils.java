package com.ambcool;

import com.google.common.base.Stopwatch;
import com.google.gson.Gson;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static java.lang.System.out;

public class BallClockUtils {

    private BallClockUtils() { }

    /**
     * Used by the FeedRail to stock it's rail and store it's first sequence of balls.
     *
     * @param capacity
     * @param rail
     * @return
     */
    static Object[] stockRail(int capacity, Railable rail) {
        for (int ballId = capacity; ballId > 0; ballId--) { // load balls into feed
            rail.getQueue().offerFirst(ballId);
        }
        return rail.getQueue().toArray(); // we use this to compare our feed Rail state with
    }

    /**
     * This method intends to insert a ball into a rail, but when it's full it will empty the rail and pass the ball to the next rail.
     *
     * @param ball
     * @param rail
     * @param feed
     * @param nextRail
     */
    static void insertOrFlush(Integer ball, Railable rail, FeedRail feed, Railable nextRail) {
        if (!rail.isFull()) {
            rail.getQueue().offer(ball);
        } else {
            while (!rail.isEmpty()) {
                feed.getQueue().offer(rail.getQueue().pollLast());
            }
            nextRail.flush(ball);
        }
    }

    static void printStats(Params args, Stopwatch stopwatch, double days) {
        out.println(String.format("%d balls cycle after %.0f days.", args.getBalls(), days));
        out.println(String.format("Completed in %s milliseconds (%.3f seconds)",
                stopwatch.elapsed(TimeUnit.MILLISECONDS),
                stopwatch.elapsed(TimeUnit.MILLISECONDS) / 1000d));
    }

    static void printJsonMinutes(BallClock bc) {
        String jsonOutput = getJson(bc);
        System.out.println(jsonOutput);
    }

    static String getJson(BallClock bc) {
        Map<String, Object[]> map = new LinkedHashMap<>();
        map.put("Minutes", bc.minuteRail.getQueue().toArray());
        map.put("FiveMin", bc.fiveMinuteRail.getQueue().toArray());
        map.put("Hours", bc.oneHourRail.getQueue().toArray());
        map.put("Main", bc.feedRail.getQueue().toArray());
        Gson gson = new Gson();
        return gson.toJson(map);
    }
}
