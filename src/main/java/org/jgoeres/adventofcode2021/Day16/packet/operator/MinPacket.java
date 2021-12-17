package org.jgoeres.adventofcode2021.Day16.packet.operator;

import org.jgoeres.adventofcode2021.Day16.packet.OperatorPacket;

public class MinPacket extends OperatorPacket {
    public MinPacket(Integer version, Integer typeID) {
        super(version, typeID);
    }

    @Override
    public Long getValue() {
        // Packets with type ID 0 are sum packets -
        // their value is the sum of the values of their sub-packets.
        //
        // If they only have a single sub-packet, their value is the
        // value of the sub-packet.
        return getChildPackets().stream()
                .mapToLong(packet -> packet.getValue()).min().getAsLong();
    }
}
