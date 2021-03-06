/**
 * Copyright 2017 Equipment & Tool Institute
 */
package net.soliddesign.iumpr.bus.j1939.packets;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Unit tests for the {@link SupportedSPN} class
 *
 * @author Matt Gumbel (matt@soliddesign.net)
 *
 */
public class SupportedSPNTest {

    @Test
    public void testError() {
        SupportedSPN instance = new SupportedSPN(new int[] { 0xFE, 0xFE, 0xFE, 0xFE });
        assertEquals(524030, instance.getSpn());
        assertEquals((byte) 254, instance.getLength());
        assertEquals(false, instance.supportsDataStream());
        assertEquals(true, instance.supportsExpandedFreezeFrame());
        assertEquals(false, instance.supportsScaledTestResults());
        String expected = "SPN 524030 - Manufacturer Assignable SPN 524030";
        assertEquals(expected, instance.toString());
    }

    @Test
    public void testMax() {
        SupportedSPN instance = new SupportedSPN(new int[] { 0xFF, 0xFF, 0xFF, 0xFA });
        assertEquals(524287, instance.getSpn());
        assertEquals((byte) 250, instance.getLength());
        assertEquals(false, instance.supportsDataStream());
        assertEquals(false, instance.supportsExpandedFreezeFrame());
        assertEquals(false, instance.supportsScaledTestResults());
        String expected = "SPN 524287 - Manufacturer Assignable SPN 524287";
        assertEquals(expected, instance.toString());
    }

    @Test
    public void testMin() {
        SupportedSPN instance = new SupportedSPN(new int[] { 0x00, 0x00, 0x00, 0x00 });
        assertEquals(0, instance.getSpn());
        assertEquals(0, instance.getLength());
        assertEquals(true, instance.supportsDataStream());
        assertEquals(true, instance.supportsExpandedFreezeFrame());
        assertEquals(true, instance.supportsScaledTestResults());
        String expected = "SPN 0 - Unknown";
        assertEquals(expected, instance.toString());
    }

    @Test
    public void testNotAvailable() {
        SupportedSPN instance = new SupportedSPN(new int[] { 0xFF, 0xFF, 0xFF, 0xFF });
        assertEquals(524287, instance.getSpn());
        assertEquals((byte) 255, instance.getLength());
        assertEquals(false, instance.supportsDataStream());
        assertEquals(false, instance.supportsExpandedFreezeFrame());
        assertEquals(false, instance.supportsScaledTestResults());
        String expected = "SPN 524287 - Manufacturer Assignable SPN 524287";
        assertEquals(expected, instance.toString());
    }

    @Test
    public void testParseSPN() {
        int[] data = new int[] { 0x61, 0x02, 0x13, 0x81 };
        int actual = SupportedSPN.parseSPN(data);
        assertEquals(609, actual);
    }

    @Test
    public void testSupportsDataStream() {
        SupportedSPN instance = new SupportedSPN(new int[] { 0x01, 0x02, 0x1D, 8 });
        assertEquals(513, instance.getSpn());
        assertEquals(8, instance.getLength());
        assertEquals(true, instance.supportsDataStream());
        assertEquals(false, instance.supportsExpandedFreezeFrame());
        assertEquals(false, instance.supportsScaledTestResults());
        String expected = "SPN 513 - Actual Engine - Percent Torque";
        assertEquals(expected, instance.toString());
    }

    @Test
    public void testSupportsFreezeFrame() {
        SupportedSPN instance = new SupportedSPN(new int[] { 0x03, 0x04, 0x1E, 1 });
        assertEquals(1027, instance.getSpn());
        assertEquals(1, instance.getLength());
        assertEquals(false, instance.supportsDataStream());
        assertEquals(true, instance.supportsExpandedFreezeFrame());
        assertEquals(false, instance.supportsScaledTestResults());
        String expected = "SPN 1027 - Trip Time in Derate by Engine";
        assertEquals(expected, instance.toString());
    }

    @Test
    public void testSupportsScaledTestsResults() {
        SupportedSPN instance = new SupportedSPN(new int[] { 0x05, 0x06, 0x1B, 16 });
        assertEquals(1541, instance.getSpn());
        assertEquals(16, instance.getLength());
        assertEquals(false, instance.supportsDataStream());
        assertEquals(false, instance.supportsExpandedFreezeFrame());
        assertEquals(true, instance.supportsScaledTestResults());
        String expected = "SPN 1541 - Reel Speed";
        assertEquals(expected, instance.toString());
    }

}
