package com.xk.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * @author kai.xu
 * @create 2020-09-10 14:25
 */
public class Client {

    private String host;
    private Integer port;

    private Channel channel;

    public Client(String host,Integer port){
        this.host = host;
        this.port = port;
    }

    public void start() throws InterruptedException {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        System.out.println("连接中..");
                        ch.pipeline().addLast(new RpcEncoder(RpcRequest.class))
                                .addLast(new RpcDecoder(RpcResponse.class))
                                .addLast(new ClientHandler());

                    }
                });

        ChannelFuture future = bootstrap.connect(new InetSocketAddress(host, port)).sync();
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if(future.isSuccess()){
                    System.out.println("连接成功");
                } else {
                    System.out.println("连接失败");
                    future.cause().printStackTrace();
                    eventLoopGroup.shutdownGracefully();
                }

            }
        });

        this.channel = future.channel();

    }

    public Channel getChannel() {
        return channel;
    }

    public static void main(String[] args) throws Exception {
        Client client = new Client("localhost",8080);
        client.start();
        Channel channel = client.getChannel();
        RpcRequest rpcRequest = new RpcRequest();
        rpcRequest.setData("---ddddd");
        channel.writeAndFlush(rpcRequest);
        /*Executors.newSingleThreadScheduledExecutor(new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                String threadName = "single-me-";
                Thread thread = new Thread(r,threadName);
                return thread;
            }
        });*/
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    for(int i=0;i<10;i++){
                        RpcRequest rpcRequest = new RpcRequest();
                        rpcRequest.setData("---ddddd"+i);
                        channel.writeAndFlush(rpcRequest);
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }
}
