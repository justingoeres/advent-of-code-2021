package org.jgoeres.adventofcode2021.Day16.packet;

public class LiteralValuePacket extends AbstractPacket {
    private final Integer value;

    public LiteralValuePacket(final Integer value, Integer version, Integer typeID) {
        this.setVersion(version);
        this.setTypeID(typeID);
        this.value = value;
    }


    public Integer getValue() {
        return value;
    }
}
