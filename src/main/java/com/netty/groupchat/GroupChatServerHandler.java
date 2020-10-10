package com.netty.groupchat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

public class GroupChatServerHandler extends SimpleChannelInboundHandler<String> {

	private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
	/**
	 *连接后第一个处理器
	 */
	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		Channel channel = ctx.channel();
		String msg = "客户端["+channel.remoteAddress()+"]上线了\n";
		System.out.println(msg);
		channelGroup.writeAndFlush(msg);
		channelGroup.add(channel);
	}
	
	/**
	 *channel处于活动状态
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		
	}
	
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		
	}
	
	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		Channel channel = ctx.channel();
		String msg = "客户端["+channel.remoteAddress()+"]下线了\n";
		System.out.println(msg);
		channelGroup.writeAndFlush(msg);
	}
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
		Channel channel = ctx.channel();
		String content = "客户端["+channel.remoteAddress()+"]说["+msg+"]\n";
		System.out.println(content);
		channelGroup.forEach(ch -> {
			if (ch != channel) {
				// 发送消息
				ch.writeAndFlush(content);
			} else {
//				ch.write("自己说["+msg+"]");
			}
		});
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// TODO Auto-generated method stub
		ctx.close();
	}

}
