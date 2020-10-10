package com.netty.protocoltcp;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

public class MyMessageDecoder extends ReplayingDecoder<Void>{

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		System.out.println("MyMessageDecoder");
		int len = in.readInt();
		byte[] content = new byte[len];
		in.readBytes(content);
		MessageProtocol mp = new MessageProtocol();
		mp.setLen(len);
		mp.setContent(content);
		out.add(mp);
	}



}
