package com.netty.tcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

public class MyServerHandler extends SimpleChannelInboundHandler<ByteBuf>{
	
	private int count;

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
		byte[] bytes = new byte[msg.readableBytes()];
		msg.readBytes(bytes);
		String data = new String(bytes, CharsetUtil.UTF_8 );
		System.out.println("client msg: " + data);
		System.out.println(count++ );
		
		ctx.writeAndFlush(Unpooled.copiedBuffer("hello, client " + count, CharsetUtil.UTF_8));
		
	}
	


}
