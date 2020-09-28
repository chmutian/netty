package com.socket.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class NIOClient {
	
	public static void main(String[] args) throws Exception {
		SocketChannel socketChannel = SocketChannel.open();
		socketChannel.configureBlocking(false);
		InetSocketAddress socketAddress = new InetSocketAddress("127.0.0.1", 9999);
		if (!socketChannel.connect(socketAddress)) {
			while (!socketChannel.finishConnect()) {
				System.out.println("连接未完成，可以做其他事情。");
			}
		}
		ByteBuffer buffer = ByteBuffer.wrap("hello,mutian".getBytes());
		// buffer写入到channel
		socketChannel.write(buffer);
//		System.in.read();
	}

}
