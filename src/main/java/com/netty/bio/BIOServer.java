package com.netty.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BIOServer {
	
	public static void main(String[] args) throws Exception {
		// 创建线程池处理
		ExecutorService threadPool = Executors.newCachedThreadPool();
		// 创建serversocket
		ServerSocket serverSocket = new ServerSocket(8888);
		while(true) {
			System.out.println("线程"+Thread.currentThread().getId() + "->" +Thread.currentThread().getName()+"等待连接");
			Socket socket = serverSocket.accept();
			System.out.println("连接一个客户端");
			threadPool.execute(() -> {
				try {
					byte[] bytes = new byte[1024];
					InputStream inputStream = socket.getInputStream();
					System.out.println("线程"+Thread.currentThread().getId() + "->" +Thread.currentThread().getName()+"等待接收数据");
					int read = 0;
					while (read != -1) {
						read = inputStream.read(bytes);
						String data = new String(bytes, 0, read);
						System.out.println("线程"+Thread.currentThread().getId() + "->" +Thread.currentThread().getName()+"接收的数据："+data);
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					try {
						socket.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
			});
		}
	}

}
