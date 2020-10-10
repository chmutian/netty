package com.netty.protocoltcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class MyMessageEncoder extends MessageToByteEncoder<MessageProtocol>{

	@Override
	protected void encode(ChannelHandlerContext ctx, MessageProtocol msg, ByteBuf out) throws Exception {
		System.out.println("MyMessageEncoder");
		int len = msg.getLen();
		byte[] content = msg.getContent();
		out.writeInt(len);
		out.writeBytes(content);
	}

}
