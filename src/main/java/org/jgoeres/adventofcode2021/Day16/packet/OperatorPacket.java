package org.jgoeres.adventofcode2021.Day16.packet;

import java.util.ArrayList;
import java.util.List;

public class OperatorPacket implements Packet{
    private final List<Packet> childPackets = new ArrayList<>();

    public List<Packet> getChildPackets() {
        return childPackets;
    }
}
