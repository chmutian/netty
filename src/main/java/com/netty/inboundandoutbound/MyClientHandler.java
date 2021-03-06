package com.netty.inboundandoutbound;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class MyClientHandler extends  SimpleChannelInboundHandler<Long>{

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("client send msg");
		ctx.writeAndFlush(11111L);
	}
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {
		System.out.println("server msg: " + msg);
	}

}
