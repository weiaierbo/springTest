package com.xk.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author kai.xu
 * @create 2020-09-10 15:32
 */
public class ClientHandler extends SimpleChannelInboundHandler<RpcResponse> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcResponse msg) throws Exception {
        System.out.println("接收到服务端数据"+msg.toString());
    }
}
