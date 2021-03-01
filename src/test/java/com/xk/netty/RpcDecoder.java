package com.xk.netty;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author kai.xu
 * @create 2020-09-10 20:03
 */
public class RpcDecoder extends ByteToMessageDecoder {

    private Class target;

    public RpcDecoder(Class target){
        this.target = target;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if(in.readableBytes()<4){
            return;
        }
        int length = in.readInt();

        if(in.readableBytes()<length){
            in.resetReaderIndex();
            return;
        }
        byte[] bytes = new byte[length];
        in.readBytes(bytes);

        Object obj = JSON.parseObject(bytes,target);
        out.add(obj);
    }
}
