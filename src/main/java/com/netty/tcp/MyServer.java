package com.netty.tcp;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class MyServer {
	
	public static void main(String[] args) throws Exception {
		// 创建bossGroup和workerGroup
		EventLoopGroup bossGroup = new NioEventLoopGroup();//处理连接请求
		EventLoopGroup workerGroup = new NioEventLoopGroup();//业务处理 
		
		try {
			// 设置启动参数
			ServerBootstrap bootstrap = new ServerBootstrap();
			bootstrap.group(bossGroup, workerGroup)
			.channel(NioServerSocketChannel.class)//服务端通道实现类
			.option(ChannelOption.SO_BACKLOG, 128)//线程队列
			.childOption(ChannelOption.SO_KEEPALIVE, true)//保持活动连接状态
			.childHandler(new MyServerInitializer());
			ChannelFuture cf = bootstrap.bind(8888).sync();
			cf.channel().closeFuture().sync();//监听关闭通道
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}

}
