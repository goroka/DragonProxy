/*
 * GNU LESSER GENERAL PUBLIC LICENSE
 *                       Version 3, 29 June 2007
 *
 * Copyright (C) 2007 Free Software Foundation, Inc. <http://fsf.org/>
 * Everyone is permitted to copy and distribute verbatim copies
 * of this license document, but changing it is not allowed.
 *
 * You can view LICENCE file for details. 
 *
 * @author The Dragonet Team
 */
package org.dragonet.proxy.protocol.packet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import lombok.Data;
import org.dragonet.proxy.protocol.inf.mcpe.NetworkChannel;
import org.dragonet.proxy.utilities.io.PEBinaryWriter;

public class UpdateBlockPacket extends PEPacket {

    public final static byte FLAG_NONE = (byte) 0b0000;
    public final static byte FLAG_NEIGHBORS = (byte) 0b0001;
    public final static byte FLAG_NETWORK = (byte) 0b0010;
    public final static byte FLAG_NOGRAPHIC = (byte) 0b0100;
    public final static byte FLAG_PRIORITY = (byte) 0b1000;
    public final static byte FLAG_ALL = (byte) (FLAG_NEIGHBORS | FLAG_NETWORK);
    public final static byte FLAG_ALL_PRIORITY = (byte) (FLAG_ALL | FLAG_PRIORITY);

    public int x;
    public int z;
    public int y;
    public int block;
    public int meta;
    public int flags;

    @Override
    public int pid() {
        return PEPacketIDs.UPDATE_BLOCK_PACKET;
    }

    @Override
    public void encode() {
        setShouldSendImmidate(true);
        try {
            setChannel(NetworkChannel.CHANNEL_BLOCKS);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            PEBinaryWriter writer = new PEBinaryWriter(bos);
            writer.writeByte((byte) (this.pid() & 0xFF));
            writer.writeBlockCoords(x, y, z);
            writer.writeUnsignedVarInt(block);
            writer.writeUnsignedVarInt(flags << 4 | meta);
            this.setData(bos.toByteArray());
        } catch (IOException e) {
        }
    }

    @Override
    public void decode() {
    }

}