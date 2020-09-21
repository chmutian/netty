package com.netty.nio;

import java.nio.IntBuffer;

public class BasicBuffer {

	public static void main(String[] args) {
		IntBuffer buffer = IntBuffer.allocate(5);
		for (int i = 0; i < buffer.capacity(); i++) {
			buffer.put(i * 5);
		}

		// buffer 读写切换
		buffer.flip();

		while (buffer.hasRemaining()) {
			int i = buffer.get();
			System.out.println(i);
		}
	}

}
