package com.netty.http;

import java.net.URI;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;

/**
 * HttpObject 客户端和服务端相互通信的数据被封装成HttpObject类型 
 * @author mutian
 *
 */
public class NettyHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
		if (msg instanceof HttpRequest) {
			System.out.println("msg type: " + msg.getClass());
			System.out.println("client address: " + ctx.channel().remoteAddress());
			HttpRequest httpRequest = (HttpRequest)msg;
			URI uri = new URI(httpRequest.uri());
			if (uri.getPath().equals("/favicon.ico")) {
				System.out.println("favicon request");
				return;
			}
			ByteBuf buf = Unpooled.copiedBuffer("hello, client",CharsetUtil.UTF_8);
			FullHttpResponse httpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, buf);
			httpResponse.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
			httpResponse.headers().set(HttpHeaderNames.CONTENT_LENGTH, buf.readableBytes());
			ctx.writeAndFlush(httpResponse);
		}
		
	}

}
