package com.netty.basic;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer {
	
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
			.childHandler(new ChannelInitializer<SocketChannel>() {
				
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(new NettyServerHandler());//设置管道处理器
					
				}
			});
			ChannelFuture cf = bootstrap.bind(6666).sync();
			cf.addListener(new ChannelFutureListener() {
				
				@Override
				public void operationComplete(ChannelFuture future) throws Exception {
					if (future.isSuccess()) {
						System.out.println("start server success");
					} else {
						System.out.println("start server fail");
					}
				}
			});
			cf.channel().closeFuture().sync();//监听关闭通道
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}

}
