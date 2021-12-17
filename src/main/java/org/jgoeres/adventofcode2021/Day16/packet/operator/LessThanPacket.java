package org.jgoeres.adventofcode2021.Day16.packet.operator;

import org.jgoeres.adventofcode2021.Day16.packet.OperatorPacket;

public class LessThanPacket extends OperatorPacket {
    public LessThanPacket(Integer version, Integer typeID) {
        super(version, typeID);
    }

    @Override
    public Long getValue() {
        // Packets with type ID 6 are less than packets -
        // their value is 1 if the value of the first sub-packet is
        // less than the value of the second sub-packet; otherwise, their value is 0.
        //
        // These packets always have exactly two sub-packets.
        return (getChildPackets().get(0).getValue() < getChildPackets().get(1).getValue())
                ? 1L : 0L;
    }
}
