package org.jgoeres.adventofcode2021.Day16.packet.operator;

import org.jgoeres.adventofcode2021.Day16.packet.OperatorPacket;

public class SumPacket extends OperatorPacket {
    public SumPacket(Integer version, Integer typeID) {
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
                .map(packet -> packet.getValue()).reduce(0L, Long::sum);
    }
}
