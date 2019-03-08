package com.moxi.energyroom.listener;

import io.netty.channel.ChannelHandlerContext;

public interface NettyInnerCallback {

    /**
     * 数据返回
     * @param ctx
     * @param msg
     */
    void onBackMessage(ChannelHandlerContext ctx, String msg);

    void onConnectException(ChannelHandlerContext ctx, Throwable cause);

    void onConnectSucess(ChannelHandlerContext ctx);

    void onConnextInactive(ChannelHandlerContext ctx);
}
