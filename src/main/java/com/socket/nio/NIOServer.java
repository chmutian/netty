package com.socket.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NIOServer {
	
	public static void main(String[] args) throws Exception {
		// 创建serversocketchannel
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		//设置为非阻塞
		serverSocketChannel.configureBlocking(false);
		serverSocketChannel.socket().bind(new InetSocketAddress(9999));
		// 创建selectore对象
		Selector selector = Selector.open();
		// 注册到selector，监听连接事件
		serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
		
		while (true) {
			
			if (selector.select(1000) == 0 ) {
				System.out.println("服务器等待1s，无连接。");
				continue;
			}
			// 获取关注的事件
			Set<SelectionKey> selectedKeys = selector.selectedKeys();
			Iterator<SelectionKey> iterator = selectedKeys.iterator();
			while (iterator.hasNext()) {
				SelectionKey selectionKey = (SelectionKey) iterator.next();
				if (selectionKey.isAcceptable()){
					// 连接事件
					SocketChannel channel = serverSocketChannel.accept();
					channel.configureBlocking(false);
					// 注册到selector 监听读取事件
					channel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
				}  else if (selectionKey.isReadable()) {
					//将channel数据读取到buffer
					SocketChannel channel = (SocketChannel)selectionKey.channel();
					ByteBuffer byteBuffer = (ByteBuffer)selectionKey.attachment();
					channel.read(byteBuffer);
					System.out.println("读取的数据为：" + new String(byteBuffer.array()));
				}
				
				iterator.remove(); 
				
			}
			
		}
	}

}
