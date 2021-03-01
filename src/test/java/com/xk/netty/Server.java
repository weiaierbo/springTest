package com.xk.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author kai.xu
 * @create 2020-09-10 14:25
 */
public class Server {
    public void bind(int p) throws InterruptedException {
        EventLoopGroup bossLoopGroup = new NioEventLoopGroup();
        EventLoopGroup workerLoopGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossLoopGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG,128)
                .childOption(ChannelOption.SO_KEEPALIVE,true)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new RpcEncoder(RpcResponse.class))
                                .addLast(new RpcDecoder(RpcRequest.class))
                                .addLast(new ServerHandler());
                    }
                });

        ChannelFuture channel = serverBootstrap.bind(p).sync();
        if(channel.isSuccess()){
            System.out.println("服务成功启动---");
        } else {
            channel.cause().printStackTrace();
            bossLoopGroup.shutdownGracefully();
            workerLoopGroup.shutdownGracefully();
        }
        channel.channel().closeFuture().sync();
    }

    public static void main(String[] args) throws Exception {
        new Server().bind(8080);
    }
}
