package com.netty.groupchat;

import java.util.Scanner;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class GroupChatClient {
	
	private String host;
	private int port;
	
	
	public GroupChatClient(String host, int port) {
		this.host = host;
		this.port = port;
	}


	public void run() throws Exception {
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap bootstrap = new Bootstrap();
			bootstrap.group(group)
			.channel(NioSocketChannel.class)
			.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ChannelPipeline pipeline = ch.pipeline();
					pipeline.addLast("decoder", new StringDecoder());
					pipeline.addLast("encoder", new StringEncoder());
					pipeline.addLast(new GroupChatClientHandler());
				}
			});
			ChannelFuture cf = bootstrap.connect(host, port).sync();
//			cf.channel().closeFuture().sync();
			
			Scanner scanner = new Scanner(System.in);
			while (scanner.hasNext()) {
				String data = (String) scanner.next();
				cf.channel().writeAndFlush(data);
			}
			
		} finally {
			group.shutdownGracefully();
		}
	}

	public static void main(String[] args) throws Exception {
		new GroupChatClient("127.0.0.1", 8888).run();
	}

}
