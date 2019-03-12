package com.moxi.energyroom.netty;

import android.support.annotation.NonNull;

import com.moxi.energyroom.Been.EV_Type;
import com.moxi.energyroom.Been.transmitData.BaseData;
import com.moxi.energyroom.Been.transmitData.TemperatureHumidity;
import com.moxi.energyroom.listener.NettyInnerCallback;
import com.moxi.energyroom.utils.APPLog;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class MyClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
    private NettyInnerCallback callback;
    public MyClientHandler(@NonNull NettyInnerCallback callback){
        this.callback=callback;
    }
    //客户端或者服务端数据接收数据函数
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf  msg) throws Exception {
        byte[] req = new byte[msg.readableBytes()]; msg.readBytes(req);
        String body = new String(req, "UTF-8");
        callback.onBackMessage(ctx,body);
        APPLog.e("channelRead0",body);
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
        APPLog.e("exceptionCaught",cause.getMessage());
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
        APPLog.e("channelInactive","不活跃状态");
        callback.onConnextInactive(ctx);
    }
}
