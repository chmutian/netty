package com.netty.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

public class WebSocketServer {
	
	public static void main(String[] args) throws Exception {
		// 创建bossGroup和workerGroup
		EventLoopGroup bossGroup = new NioEventLoopGroup();//处理连接请求
		EventLoopGroup workerGroup = new NioEventLoopGroup();//业务处理 
		
		try {
			// 设置启动参数
			ServerBootstrap bootstrap = new ServerBootstrap();
			bootstrap.group(bossGroup, workerGroup)
			.channel(NioServerSocketChannel.class)//服务端通道实现类
			.handler(new LoggingHandler(LogLevel.INFO))
			.option(ChannelOption.SO_BACKLOG, 128)//线程队列
			.childOption(ChannelOption.SO_KEEPALIVE, true)//保持活动连接状态
			.childHandler(new ChannelInitializer<SocketChannel>() {
				
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ChannelPipeline pipeline = ch.pipeline();
					// 基于http协议 设置http编解码
					pipeline.addLast(new HttpServerCodec());
					// 以块的方式写
					pipeline.addLast(new ChunkedWriteHandler());
					// 将http传输的分段数据聚合
					pipeline.addLast(new HttpObjectAggregator(8192));
					// http协议升级为ws协议 
					pipeline.addLast(new WebSocketServerProtocolHandler("/hello")); 
					pipeline.addLast(new WebSocketServerHandler());//设置管道处理器
					
				}
			});
			ChannelFuture cf = bootstrap.bind(8888).sync();
  			cf.channel().closeFuture().sync();//监听关闭通道
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}

}
