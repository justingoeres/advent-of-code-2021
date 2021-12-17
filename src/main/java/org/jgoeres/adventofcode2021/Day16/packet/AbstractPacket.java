package org.jgoeres.adventofcode2021.Day16.packet;

public abstract class AbstractPacket implements Packet {
    private Integer version = null;
    private Integer typeID = null;
    private Integer bitsConsumed = 0;

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getTypeID() {
        return typeID;
    }

    public void setTypeID(Integer typeID) {
        this.typeID = typeID;
    }

    public Integer getBitsConsumed() {
        return bitsConsumed;
    }

    public void setBitsConsumed(Integer bitsConsumed) {
        this.bitsConsumed = bitsConsumed;
    }
}
