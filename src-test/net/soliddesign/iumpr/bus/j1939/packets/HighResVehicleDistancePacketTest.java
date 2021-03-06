/**
 * Copyright 2017 Equipment & Tool Institute
 */
package net.soliddesign.iumpr.bus.j1939.packets;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import net.soliddesign.iumpr.bus.Packet;

/**
 * Unit tests for the {@link HighResVehicleDistancePacket} class
 *
 * @author Matt Gumbel (matt@soliddesign.net)
 *
 */
public class HighResVehicleDistancePacketTest {

    @Test
    public void testGetTotalVehicleDistanceAndToString() {
        int[] data = new int[] { 0x80, 0x14, 0x06, 0x00, 0x11, 0x22, 0x33, 0x44 };
        Packet packet = Packet.create(0, 0, data);
        HighResVehicleDistancePacket instance = new HighResVehicleDistancePacket(packet);
        assertEquals(1992.32, instance.getTotalVehicleDistance(), 0.0);
        assertEquals("High Resolution Vehicle Distance from Engine #1 (0): 1,992.32 km (1,237.97 mi)",
                instance.toString());
    }

    @Test
    public void testGetTotalVehicleDistanceAndToStringAtError() {
        int[] data = new int[] { 0x00, 0x00, 0x00, 0xFE, 0x11, 0x22, 0x33, 0x44 };
        Packet packet = Packet.create(0, 0, data);
        HighResVehicleDistancePacket instance = new HighResVehicleDistancePacket(packet);
        assertEquals(ParsedPacket.ERROR, instance.getTotalVehicleDistance(), 0.0);
        assertEquals("High Resolution Vehicle Distance from Engine #1 (0): error", instance.toString());
    }

    @Test
    public void testGetTotalVehicleDistanceAndToStringAtNotAvailable() {
        int[] data = new int[] { 0xFF, 0xFF, 0xFF, 0xFF, 0x11, 0x22, 0x33, 0x44 };
        Packet packet = Packet.create(0, 0, data);
        HighResVehicleDistancePacket instance = new HighResVehicleDistancePacket(packet);
        assertEquals(ParsedPacket.NOT_AVAILABLE, instance.getTotalVehicleDistance(), 0.0);
        assertEquals("High Resolution Vehicle Distance from Engine #1 (0): not available", instance.toString());
    }

    @Test
    public void testGetTotalVehicleDistanceAtMax() {
        int[] data = new int[] { 0xFF, 0xFF, 0xFF, 0xFA, 0x11, 0x22, 0x33, 0x44 };
        Packet packet = Packet.create(0, 0, data);
        HighResVehicleDistancePacket instance = new HighResVehicleDistancePacket(packet);
        assertEquals(21055406.075, instance.getTotalVehicleDistance(), 0.0);
    }

    @Test
    public void testGetTotalVehicleDistanceAtZero() {
        int[] data = new int[] { 0x00, 0x00, 0x00, 0x00, 0x11, 0x22, 0x33, 0x44 };
        Packet packet = Packet.create(0, 0, data);
        HighResVehicleDistancePacket instance = new HighResVehicleDistancePacket(packet);
        assertEquals(0, instance.getTotalVehicleDistance(), 0.0);
    }

    @Test
    public void testPGN() {
        assertEquals(65217, HighResVehicleDistancePacket.PGN);
    }

}
