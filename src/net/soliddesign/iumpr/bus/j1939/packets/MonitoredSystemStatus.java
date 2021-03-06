/**
 * Copyright 2017 Equipment & Tool Institute
 */
package net.soliddesign.iumpr.bus.j1939.packets;

import java.util.Arrays;

/**
 * The interface for a Monitored System Status
 *
 * @author Matt Gumbel (matt@soliddesign.net)
 *
 */
public interface MonitoredSystemStatus {

    /**
     * Helper method to find a {@link MonitoredSystemStatus}
     *
     * @param isDm5
     *            true if the monitor is from a DM5 Packet
     * @param enabled
     *            true if the monitor is enabled
     * @param complete
     *            true if the monitor is complete
     * @return the {@link MonitoredSystemStatus} that matches the criteria
     */
    public static MonitoredSystemStatus findStatus(boolean isDm5, boolean enabled, boolean complete) {
        MonitoredSystemStatus[] values = isDm5 ? DM5MonitoredSystemStatus.values() : DM26MonitoredSystemStatus.values();
        return Arrays.stream(values)
                .filter(s -> s.isEnabled() == enabled && s.isComplete() == complete).findFirst().orElse(null);
    }

    /**
     * Indicates if the monitor is complete
     *
     * @return true if the monitor is complete
     */
    boolean isComplete();

    /**
     * Indicates if the monitor is enabled/supported
     *
     * @return true if the monitor is enabled/supported
     */
    boolean isEnabled();

    @Override
    String toString();
}
