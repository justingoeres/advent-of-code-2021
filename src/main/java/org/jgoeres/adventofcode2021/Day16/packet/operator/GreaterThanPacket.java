package org.jgoeres.adventofcode2021.Day16.packet.operator;

import org.jgoeres.adventofcode2021.Day16.packet.OperatorPacket;

public class GreaterThanPacket extends OperatorPacket {
    public GreaterThanPacket(Integer version, Integer typeID) {
        super(version, typeID);
    }

    @Override
    public Long getValue() {
        // Packets with type ID 5 are greater than packets -
        // their value is 1 if the value of the first sub-packet is
        // greater than the value of the second sub-packet; otherwise, their value is 0.
        //
        // These packets always have exactly two sub-packets.
        return (getChildPackets().get(0).getValue() > getChildPackets().get(1).getValue())
                ? 1L : 0L;
    }
}
