package com.mojang.minecraft.renderer;

import java.util.HashMap;

import com.mojang.util.GLAllocation;
import net.lax1dude.eaglercraft.EagRuntime;
import net.lax1dude.eaglercraft.opengl.ImageData;
import org.lwjgl.opengl.GL11;

import net.lax1dude.eaglercraft.internal.buffer.ByteBuffer;
import net.lax1dude.eaglercraft.internal.buffer.IntBuffer;

public class Textures {
	private HashMap idMap = new HashMap();

	public final int loadTexture(String resourceName, int mode) {
        if(idMap.containsKey(resourceName)) {
            return ((Integer)idMap.get(resourceName)).intValue();
        } else {
            IntBuffer e = GLAllocation.createIntBuffer(1);
            e.clear();
            GL11.glGenTextures(e);
            int id = e.get(0);
            idMap.put(resourceName, Integer.valueOf(id));
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
            int filter = (mode == GL11.GL_NEAREST || mode == GL11.GL_LINEAR) ? mode : GL11.GL_NEAREST;
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, filter);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, filter);
            ImageData img = ImageData.loadImageFile(EagRuntime.getResourceStream(resourceName));
            int w = img.width;
            int h = img.height;
            ByteBuffer pixels = GLAllocation.createByteBuffer(w * h * 4);
            int[] rawPixels = new int[w * h];
            byte[] var6 = new byte[w * h << 2];
            img.getRGB(0, 0, w, h, rawPixels, 0, w);

            for(int i = 0; i < rawPixels.length; ++i) {
                int a = rawPixels[i] >>> 24;
                int r = rawPixels[i] >> 16 & 255;
                int g = rawPixels[i] >> 8 & 255;
                int b = rawPixels[i] & 255;
				var6[i << 2] = (byte)b;
				var6[(i << 2) + 1] = (byte)g;
				var6[(i << 2) + 2] = (byte)r;
				var6[(i << 2) + 3] = (byte)a;
            }

            pixels.put(var6);
            pixels.position(0).limit(var6.length);
            GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, w, h, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, pixels);
            return id;
        }
    }
}