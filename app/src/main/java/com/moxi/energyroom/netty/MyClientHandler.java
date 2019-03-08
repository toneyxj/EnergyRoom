package com.moxi.energyroom.netty;

import android.support.annotation.NonNull;

import com.moxi.energyroom.listener.NettyInnerCallback;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class MyClientHandler extends SimpleChannelInboundHandler<String> {
    private NettyInnerCallback callback;
    public MyClientHandler(@NonNull NettyInnerCallback callback){
        this.callback=callback;
    }
    //客户端或者服务端数据接收数据函数
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        callback.onBackMessage(ctx,msg);
    }

    //通道建立处于活动状态可以向服务器发送数据
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        callback.onConnectSucess(ctx);
    }

    /**
     * 连接出现问题
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        callback.onConnectException(ctx,cause);
        ctx.close();
    }

    /**
     * 不活跃的情况
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        callback.onConnextInactive(ctx);
    }
}
