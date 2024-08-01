package deus.stanleylib.network;

import net.minecraft.core.net.handler.NetHandler;
import net.minecraft.core.net.packet.Packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PacketAddTemperature extends Packet {

	public double temperature;

	public PacketAddTemperature() {
	}

	public PacketAddTemperature(Double temperature) {
		this.temperature = temperature;
	}

	@Override
	public void readPacketData(DataInputStream dataInputStream) throws IOException {
		temperature = dataInputStream.readDouble();
	}

	@Override
	public void writePacketData(DataOutputStream dataOutputStream) throws IOException {
		dataOutputStream.writeDouble(temperature);
	}

	@Override
	public void processPacket(NetHandler netHandler) {
		((INetHandler) netHandler).handleAddTemperaturePacket(this);
	}

	@Override
	public int getPacketSize() {
		return Double.BYTES;
	}

}
