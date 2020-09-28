package com.netty.basic;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

public class NettyServerHandler extends ChannelInboundHandlerAdapter{
	/**
	 * 读取通道数据
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println("server cts:" + ctx);
		ByteBuf buf = (ByteBuf)msg;
		System.out.println("client ip: " + ctx.channel().remoteAddress());
		System.out.println("client msg: " + buf.toString(CharsetUtil.UTF_8));
	}
	
	/**
	 * 读取通道数据完成之后
	 */
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.writeAndFlush(Unpooled.copiedBuffer("hello client", CharsetUtil.UTF_8));
	}
	
	/**
	 * 异常处理，关闭通道
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.close();
	}
	
}
