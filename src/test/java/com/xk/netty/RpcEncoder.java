package com.xk.netty;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author kai.xu
 * @create 2020-09-10 16:50
 */
public class RpcEncoder extends MessageToByteEncoder<RpcRequest> {

    private Class target;

    public RpcEncoder(Class target){
        this.target = target;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, RpcRequest msg, ByteBuf out) throws Exception {
        byte[] bytes = JSON.toJSONBytes(msg);
        out.writeInt(bytes.length);
        out.writeBytes(bytes);
    }
}
