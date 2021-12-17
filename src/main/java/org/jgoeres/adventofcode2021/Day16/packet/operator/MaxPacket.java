package org.jgoeres.adventofcode2021.Day16.packet.operator;

import org.jgoeres.adventofcode2021.Day16.packet.OperatorPacket;

public class MaxPacket extends OperatorPacket {
    public MaxPacket(Integer version, Integer typeID) {
        super(version, typeID);
    }

    @Override
    public Long getValue() {
        // Packets with type ID 3 are maximum packets -
        // their value is the maximum of the values of their sub-packets.
        return getChildPackets().stream()
                .mapToLong(packet -> packet.getValue()).max().getAsLong();
    }
}
