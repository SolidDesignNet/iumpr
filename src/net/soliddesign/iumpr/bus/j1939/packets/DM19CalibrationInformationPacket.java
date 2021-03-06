/**
 * Copyright 2017 Equipment & Tool Institute
 */
package net.soliddesign.iumpr.bus.j1939.packets;

import static net.soliddesign.iumpr.IUMPR.NL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import net.soliddesign.iumpr.bus.Packet;

/**
 * Parses the Calibration Information Packet (DM19)
 *
 * @author Matt Gumbel (matt@soliddesign.net)
 *
 */
public class DM19CalibrationInformationPacket extends ParsedPacket {
    /**
     * Contains the Calibration Identification and Calibration Verification
     * Number
     *
     * @author Matt Gumbel (matt@soliddesign.net)
     *
     */
    public static class CalibrationInformation {

        private final String calibrationIdentification;
        private final String calibrationVerificationNumber;

        public CalibrationInformation(String id, String cvn) {
            calibrationIdentification = id;
            calibrationVerificationNumber = cvn;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof CalibrationInformation)) {
                return false;
            }

            CalibrationInformation that = (CalibrationInformation) obj;

            return Objects.equals(getCalibrationIdentification(), that.getCalibrationIdentification())
                    && Objects.equals(getCalibrationVerificationNumber(), that.getCalibrationVerificationNumber());
        }

        /**
         * Returns the Calibration Identification
         *
         * @return String
         */
        public String getCalibrationIdentification() {
            return calibrationIdentification;
        }

        /**
         * Returns the Calibration Verification Number
         *
         * @return String
         */
        public String getCalibrationVerificationNumber() {
            return calibrationVerificationNumber;
        }

        @Override
        public int hashCode() {
            return Objects.hash(getCalibrationIdentification(), getCalibrationVerificationNumber());
        }

        @Override
        public String toString() {
            return "CAL ID of " + getCalibrationIdentification() + " and CVN of " + getCalibrationVerificationNumber();
        }
    }

    public static final int PGN = 54016;

    private List<CalibrationInformation> info;

    public DM19CalibrationInformationPacket(Packet packet) {
        super(packet);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof ParsedPacket)) {
            return false;
        }

        ParsedPacket that = (ParsedPacket) obj;
        return getPacket().equals(that.getPacket());
    }

    /**
     * Returns the {@link CalibrationInformation} from the controller
     *
     * @return List of {@link CalibrationInformation}
     */
    public List<CalibrationInformation> getCalibrationInformation() {
        if (info == null) {
            info = parseAllInformation();
        }
        return info;
    }

    @Override
    public String getName() {
        return "DM19";
    }

    @Override
    public int hashCode() {
        return getPacket().hashCode();
    }

    /**
     * Parses all the data to return all the {@link CalibrationInformation} sent
     *
     * @return a List of {@link CalibrationInformation}
     */
    private List<CalibrationInformation> parseAllInformation() {
        List<CalibrationInformation> result = new ArrayList<>();
        final int length = getPacket().getLength();
        for (int i = 0; i + 20 <= length; i = i + 20) {
            CalibrationInformation info = parseInformation(i);
            result.add(info);
        }
        return result;
    }

    /**
     * Parses one calibration information from the packet
     *
     * @param startingIndex
     *            the index of the data to start the parsing at
     * @return The parsed {@link CalibrationInformation}
     */
    private CalibrationInformation parseInformation(int startingIndex) {
        String cvn = String.format("%08X", getPacket().get32(startingIndex) & 0xFFFFFFFFL);
        byte[] bytes = getPacket().getBytes();
        byte[] idBytes = Arrays.copyOfRange(bytes, startingIndex + 4, startingIndex + 20);
        String calId = format(idBytes).trim();
        return new CalibrationInformation(calId, "0x" + cvn);
    }

    @Override
    public String toString() {
        final boolean moreThanOne = getCalibrationInformation().size() > 1;
        StringBuilder sb = new StringBuilder();
        sb.append(getStringPrefix());
        sb.append(moreThanOne ? "[" + NL : "");
        for (CalibrationInformation info : getCalibrationInformation()) {
            sb.append(moreThanOne ? "  " : "");
            sb.append(info.toString());
            sb.append(moreThanOne ? NL : "");
        }
        sb.append(moreThanOne ? "]" : "");
        return sb.toString();
    }
}
