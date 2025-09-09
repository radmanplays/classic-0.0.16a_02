package com.mojang.minecraft.net;

import com.mojang.comm.SocketConnection;
import com.mojang.minecraft.Minecraft;

import net.lax1dude.eaglercraft.internal.EnumEaglerConnectionState;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

public final class ConnectionManager {
	public ByteArrayOutputStream levelBuffer;
	public SocketConnection connection;
	public Minecraft minecraft;
	public HashMap players = new HashMap();
	public boolean successful = false;

	public ConnectionManager(Minecraft var1, String var2, int var3, String var4) throws IOException {
		this.connection = new SocketConnection(var2, var3);
		SocketConnection var5 = this.connection;
		var5.manager = this;
		this.minecraft = var1;
		var1.hideGui = true;
	}
	
	public final void sendBlockChange(int var1, int var2, int var3, int var4, int var5) {
		this.connection.sendPacket(Packet.PLACE_OR_REMOVE_TILE, new Object[]{Integer.valueOf(var1), Integer.valueOf(var2), Integer.valueOf(var3), Integer.valueOf(var4), Integer.valueOf(var5)});
	}
	
	public boolean isConnected() {
		return connection != null && connection.webSocket != null
				&& connection.webSocket.getState() == EnumEaglerConnectionState.CONNECTED;
	}

	public boolean didConnectionClose() {
		return successful && this.connection != null && connection.webSocket != null
				&& connection.webSocket.getState() != EnumEaglerConnectionState.CONNECTED;
	}
}
