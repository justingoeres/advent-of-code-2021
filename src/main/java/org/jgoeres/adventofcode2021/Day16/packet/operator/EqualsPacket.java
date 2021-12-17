package org.jgoeres.adventofcode2021.Day16.packet.operator;

import org.jgoeres.adventofcode2021.Day16.packet.OperatorPacket;

public class EqualsPacket extends OperatorPacket {
    public EqualsPacket(Integer version, Integer typeID) {
        super(version, typeID);
    }

    @Override
    public Long getValue() {
        // Packets with type ID 0 are sum packets -
        // their value is the sum of the values of their sub-packets.
        //
        // If they only have a single sub-packet, their value is the
        // value of the sub-packet.
        return (getChildPackets().get(0).getValue()
                .equals(getChildPackets().get(1).getValue()))
                ? 1L : 0L;
    }
}
