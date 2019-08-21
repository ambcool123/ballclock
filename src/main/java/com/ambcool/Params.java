package com.ambcool;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.Option;

/**
 * This class is used to parse and passing the args
 */
public class Params {

    private static final String MINUTES_ERROR = "Minutes should be greater than zero";
    private static final String BALLS_ERROR = "Count of balls should be between 27 to 127";

    private int balls = 0;
    private int minutes = 0;

    public Params() {
    }

    public Params(int balls) {
        this.balls = balls;
    }

    @Option(name = "-h", aliases = {"-help"}, usage = "display options")
    private boolean help;

    @Option(name = "-v", aliases = {"-verbose"}, usage = "display message")
    private boolean verbose;

    @Option(name = "-b", aliases = {"-balls"}, metaVar = "27", usage = BALLS_ERROR)
    public void setBalls(final int property) throws BadArgument {
        balls = property;
        if (!isBall()) throw new BadArgument(BALLS_ERROR);
    }

    @Option(name = "-t", aliases = {"-minutes", "-time", "-m", "-timeToRun"}, metaVar = "1", usage = MINUTES_ERROR)
    public void setMinutes(final int property) throws BadArgument {
        minutes = property;
        if (!isMinutes()) throw new BadArgument(MINUTES_ERROR);
    }

    public boolean isEmpty() {
        return !isBall() && !isMinutes() && !help;
    }

    public boolean isBall() {
        return balls > 26 && balls < 128;
    }

    public boolean isMinutes() {
        return minutes > 0;
    }

    public int getBalls() {
        return balls;
    }

    public int getMinutes() {
        return minutes;
    }

    public boolean isHelp() {
        return help;
    }

    public boolean isVerbose() {
        return verbose;
    }

    /**
     * This exception is thrown when a bad argument is passed into the program. Note that it extends the CmdLineException
     * by use of a deprecated constructor because the new constructor doesn't have all of it's args present in this
     * class.
     */
    public class BadArgument extends CmdLineException {
        public BadArgument(String message) {
            super(message);
        }
    }
}
