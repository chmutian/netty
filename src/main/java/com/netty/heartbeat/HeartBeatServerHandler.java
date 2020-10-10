package com.netty.heartbeat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

public class HeartBeatServerHandler extends ChannelInboundHandlerAdapter {
	
	/**
	 *处理心跳事件
	 */
	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		if (evt instanceof IdleStateEvent) {
			IdleStateEvent idleStateEvent = (IdleStateEvent)evt;
			IdleState state = idleStateEvent.state();
			switch (state) {
			case READER_IDLE:
				System.out.println("读超时");
				break;
			case WRITER_IDLE:
				System.out.println("写超时");
				break;
			case ALL_IDLE:
				System.out.println("读写超时");
				break;
			default:
				break;
			}
			ctx.close();
		}
	}

}
