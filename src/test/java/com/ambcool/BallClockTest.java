package com.ambcool;

import org.junit.Assert;
import org.junit.Test;

public class BallClockTest {

    @Test
    public void test30Balls15Days(){
        Assert.assertEquals(15d, new BallClock(new Params(30)).runIterations(), 0.0d);
    }

    @Test
    public void tests45Balls378Days(){
        Assert.assertEquals(378d, new BallClock(new Params(45)).runIterations(),0.0d);
    }
}
