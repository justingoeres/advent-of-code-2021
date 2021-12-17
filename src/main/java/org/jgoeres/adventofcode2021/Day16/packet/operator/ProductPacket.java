package org.jgoeres.adventofcode2021.Day16.packet.operator;

import org.jgoeres.adventofcode2021.Day16.packet.OperatorPacket;

public class ProductPacket extends OperatorPacket {
    public ProductPacket(Integer version, Integer typeID) {
        super(version, typeID);
    }

    @Override
    public Long getValue() {
        // Packets with type ID 1 are product packets - their value is the result of
        // multiplying together the values of their sub-packets.

        // If they only have a single sub-packet, their value is the value of the sub-packet.
        return getChildPackets().stream()
                .map(packet -> packet.getValue()).reduce(1L, (a, b) -> a * b);
    }
}
