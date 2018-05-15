package com.zxyyb.netty.line;

import java.util.Date;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class LineServerHandle extends ChannelHandlerAdapter{
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println(ctx.channel().localAddress().toString()+" channelActive");
		//通知您已经链接上客户端
		String str = "您已经开启与服务端链接"+" "+new Date()+" "+ctx.channel().localAddress();
		ByteBuf buf = Unpooled.buffer(str.getBytes().length);
		buf.writeBytes(str.getBytes());
		ctx.writeAndFlush(buf);
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf buf = (ByteBuf) msg;

		byte[] bytes = new byte[buf.readableBytes()];
		buf.readBytes(bytes);

		String body = new String(bytes, "UTF-8");
		System.out.println(" Server receive : " + body);

		String response = "receive " + body;
		ByteBuf resp = Unpooled.copiedBuffer(response.getBytes());

		ctx.write(resp);

	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
	
}
