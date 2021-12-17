package org.jgoeres.adventofcode2021.Day16.packet;

import java.util.ArrayList;
import java.util.List;

public class OperatorPacket extends AbstractPacket implements Packet {
    private final List<Packet> childPackets = new ArrayList<>();

    public OperatorPacket(Integer version, Integer typeID) {
        this.setVersion(version);
        this.setTypeID(typeID);
    }

    @Override
    public Integer getTotalVersion() {
        Integer totalVersion = getVersion();
        for (Packet packet : childPackets) {
            totalVersion += packet.getTotalVersion();
        }
        return totalVersion;
    }

    public List<Packet> getChildPackets() {
        return childPackets;
    }
}
