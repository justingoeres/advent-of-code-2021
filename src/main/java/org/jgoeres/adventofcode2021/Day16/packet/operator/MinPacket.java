package org.jgoeres.adventofcode2021.Day16.packet.operator;

import org.jgoeres.adventofcode2021.Day16.packet.OperatorPacket;

public class MinPacket extends OperatorPacket {
    public MinPacket(Integer version, Integer typeID) {
        super(version, typeID);
    }

    @Override
    public Long getValue() {
        // Packets with type ID 2 are minimum packets -
        // their value is the minimum of the values of their sub-packets.
        return getChildPackets().stream()
                .mapToLong(packet -> packet.getValue()).min().getAsLong();
    }
}
