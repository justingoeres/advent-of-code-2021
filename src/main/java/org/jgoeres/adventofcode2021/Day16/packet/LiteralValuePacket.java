package org.jgoeres.adventofcode2021.Day16.packet;

public class LiteralValuePacket extends AbstractPacket {
    private final Long value;

    public LiteralValuePacket(final Long value, Integer version, Integer typeID) {
        this.setVersion(version);
        this.setTypeID(typeID);
        this.value = value;
    }

    @Override
    public Integer getTotalVersion() {
        return getVersion();
    }

    @Override
    public Long getValue() {
        return value;
    }
}
