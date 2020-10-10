package com.netty.tcp;

import java.util.concurrent.TimeUnit;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

public class NettyTcpServerHandler extends ChannelInboundHandlerAdapter{
	/**
	 * 读取通道数据
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		//用户自定义taskQueue 
		ctx.channel().eventLoop().execute(new Runnable() {
			@Override
			public void run() {
				try {
					TimeUnit.SECONDS.sleep(10);
					ctx.writeAndFlush(Unpooled.copiedBuffer("hello client111", CharsetUtil.UTF_8));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		ctx.channel().eventLoop().execute(new Runnable() {
			@Override
			public void run() {
				try {
					TimeUnit.SECONDS.sleep(20);
					ctx.writeAndFlush(Unpooled.copiedBuffer("hello client222", CharsetUtil.UTF_8));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		
		ctx.channel().eventLoop().schedule(new Runnable() {
			
			@Override
			public void run() {
				ctx.writeAndFlush(Unpooled.copiedBuffer("hello client333", CharsetUtil.UTF_8));
			}
		}, 5, TimeUnit.SECONDS);
		
//		System.out.println("server cts:" + ctx);
//		ByteBuf buf = (ByteBuf)msg;
//		System.out.println("client ip: " + ctx.channel().remoteAddress());
//		System.out.println("client msg: " + buf.toString(CharsetUtil.UTF_8));
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
