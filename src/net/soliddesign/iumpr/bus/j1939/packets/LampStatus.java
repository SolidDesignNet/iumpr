/**
 * Copyright 2017 Equipment & Tool Institute
 */
package net.soliddesign.iumpr.bus.j1939.packets;

/**
 * The Status of Diagnostic Trouble Code Lamps
 *
 * @author Matt Gumbel (matt@soliddesign.net)
 *
 */
public enum LampStatus {
    FAST_FLASH("Fast Flash"), OFF("Off"), ON("On"), OTHER("Other"), SLOW_FLASH("Slow Flash");

    /**
     * Returns a {@link LampStatus} based upon the onOff and flash values as
     * defined by SAE. If an undefined {@link LampStatus} is found, OTHER will
     * be returned
     *
     * @param onOff
     *            the On/Off Value
     * @param flash
     *            the Flash Value
     * @return {@link LampStatus}
     */
    public static LampStatus getStatus(int onOff, int flash) {
        boolean off = (onOff & 0x03) == 0x00;
        boolean on = (onOff & 0x03) == 0x01;

        boolean slowFlash = (flash & 0x03) == 0x00;
        boolean fastFlash = (flash & 0x03) == 0x01;
        boolean dontFlash = (flash & 0x03) == 0x03;

        if (off) {
            return LampStatus.OFF;
        }

        if (on) {
            if (slowFlash) {
                return LampStatus.SLOW_FLASH;
            }
            if (fastFlash) {
                return LampStatus.FAST_FLASH;
            }
            if (dontFlash) {
                return LampStatus.ON;
            }
        }
        return LampStatus.OTHER;
    }

    private final String name;

    private LampStatus(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

}
