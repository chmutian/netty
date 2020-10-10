package com.netty.protocoltcp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

public class MyClientHandler extends  SimpleChannelInboundHandler<MessageProtocol>{
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		
		String data = "hello, server";
		for (int i = 0; i < 10; i++) {
			String newData = data + i;
			byte[] bytes = newData.getBytes(CharsetUtil.UTF_8);
			MessageProtocol mp = new MessageProtocol();
			mp.setLen(bytes.length);
			mp.setContent(bytes);
			ctx.writeAndFlush(mp);
		}
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
		byte[] content = msg.getContent();
		String data = new String(content, CharsetUtil.UTF_8);
		System.out.println(data);
	}


}
