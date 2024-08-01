package deus.stanleylib.network;

import deus.stanleylib.enums.PlayerTemperatureState;
import net.minecraft.core.net.handler.NetHandler;
import net.minecraft.core.net.packet.Packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PacketSendTemperature extends Packet {

	public double temperature;
	public PlayerTemperatureState state;

	public PacketSendTemperature(double temperature, PlayerTemperatureState state) {
		this.temperature = temperature;
		this.state = state;
	}

	@Override
	public void readPacketData(DataInputStream dataInputStream) throws IOException {
		temperature = dataInputStream.readDouble();
		String stateName = dataInputStream.readUTF();
		state = PlayerTemperatureState.valueOf(stateName);
	}

	@Override
	public void writePacketData(DataOutputStream dataOutputStream) throws IOException {
		dataOutputStream.writeDouble(temperature);
		dataOutputStream.writeUTF(state.name());
	}

	@Override
	public void processPacket(NetHandler netHandler) {
		((INetHandler) netHandler).handleTemperaturePacket(this);
	}

	@Override
	public int getPacketSize() {
		return Double.BYTES + state.name().length() * 2; // Double bytes + length of enum name in bytes (each character is 2 bytes in UTF)
	}
}
