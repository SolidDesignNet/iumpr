/**
 * Copyright 2017 Equipment & Tool Institute
 */
package net.soliddesign.iumpr.bus.j1939.packets;

/**
 * The Monitored System Status for DM5 Systems
 *
 * @author Matt Gumbel (matt@soliddesign.net)
 *
 */
public enum DM5MonitoredSystemStatus implements MonitoredSystemStatus {

    NOT_SUPPORTED_COMPLETE(false, true), NOT_SUPPORTED_NOT_COMPLETE(false, false), SUPPORTED_COMPLETE(true,
            true), SUPPORTED_NOT_COMPLETE(true, false);

    /**
     * Flag to indicate if this monitor is complete
     */
    private final boolean complete;

    /**
     * Flag to indicate if this monitor is enabled
     */
    private final boolean enabled;

    /**
     * Constructor
     *
     * @param enabled
     *            true if the monitor is enabled
     * @param complete
     *            true if the monitor is complete
     */
    private DM5MonitoredSystemStatus(boolean enabled, boolean complete) {
        this.enabled = enabled;
        this.complete = complete;
    }

    @Override
    public boolean isComplete() {
        return complete;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public String toString() {
        return (isEnabled() ? "    " : "not ") + "supported, " + (isComplete() ? "    " : "not ") + "complete";
    }

}
