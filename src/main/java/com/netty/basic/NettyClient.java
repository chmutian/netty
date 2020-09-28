package com.netty.basic;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyClient {
	
	public static void main(String[] args) throws Exception {
		NioEventLoopGroup group = new NioEventLoopGroup();
		try {
			// 客户端配置
			Bootstrap bootstrap = new Bootstrap();
			bootstrap.group(group)
			.channel(NioSocketChannel.class)
			.handler(new ChannelInitializer<SocketChannel>() {

				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(new NettyClientHandler());
					
				}
			});
			ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 6666).sync();
			channelFuture.channel().closeFuture().sync();//监听通道关闭
		} finally {
			group.shutdownGracefully();
		}
		
	}

}
