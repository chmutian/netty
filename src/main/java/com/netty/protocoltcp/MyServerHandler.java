package com.netty.protocoltcp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

public class MyServerHandler extends SimpleChannelInboundHandler<MessageProtocol>{
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
		byte[] content = msg.getContent();
		String data = new String(content, CharsetUtil.UTF_8 );
		System.out.println("client msg: " + data);
		
		MessageProtocol mp = new MessageProtocol();
		byte[] bytes = "hello,client".getBytes(CharsetUtil.UTF_8);
		mp.setLen(bytes.length);
		mp.setContent(bytes);
		ctx.writeAndFlush(mp);
	}
	


}
