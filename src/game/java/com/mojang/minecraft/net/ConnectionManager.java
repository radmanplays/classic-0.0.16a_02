package com.mojang.minecraft.net;

import com.mojang.comm.SocketConnection;
import com.mojang.minecraft.Minecraft;

import net.lax1dude.eaglercraft.internal.EnumEaglerConnectionState;
import net.lax1dude.eaglercraft.internal.PlatformNetworking;
import net.lax1dude.eaglercraft.socket.AddressResolver;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

public final class ConnectionManager {
	public ByteArrayOutputStream levelBuffer;
	public SocketConnection connection;
	public Minecraft minecraft;
	public HashMap players = new HashMap();
    private boolean loginSent = false;

	public ConnectionManager(Minecraft var1, String var2, int var3, String var4) throws IOException {
		this.connection = new SocketConnection(this);
		SocketConnection var5 = this.connection;
		var5.manager = this;
		this.minecraft = var1;
		String address;
		if(var3 != -1) {
			address = AddressResolver.resolveURI(var2).ip;
		} else {
			address = AddressResolver.resolveURI(var2 + ":" + var3).ip;
		}
		this.connection.webSocket = PlatformNetworking.openWebSocket(address);

		if (this.connection.webSocket == null) {
			throw new IOException("Failed to open websocket to: " + address);
		}
	}
	
	public final void sendBlockChange(int var1, int var2, int var3, int var4, int var5) {
		this.connection.sendPacket(Packet.PLACE_OR_REMOVE_TILE, new Object[]{Integer.valueOf(var1), Integer.valueOf(var2), Integer.valueOf(var3), Integer.valueOf(var4), Integer.valueOf(var5)});
	}
	
    public void tick() {
        if (this.connection.webSocket != null
                && this.connection.webSocket.getState() == EnumEaglerConnectionState.CONNECTED
                && !loginSent) {
        	this.connection.sendPacket(Packet.LOGIN, new Object[]{Byte.valueOf((byte)3),minecraft.user != null ? minecraft.user.name : "guest", "--"});
        	loginSent = true;
        }
    }
}
