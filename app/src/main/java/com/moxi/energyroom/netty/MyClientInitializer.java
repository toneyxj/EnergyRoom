package com.moxi.energyroom.netty;

import android.support.annotation.NonNull;

import com.moxi.energyroom.listener.NettyInnerCallback;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.CharsetUtil;

public class MyClientInitializer extends ChannelInitializer<SocketChannel> {
    private NettyInnerCallback callback;
    public MyClientInitializer(@NonNull NettyInnerCallback callback){
        this.callback=callback;
    }
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline=ch.pipeline();

        pipeline.addLast(new LoggingHandler(LogLevel.INFO));    // 开启日志，可以设置日志等级
//        pipeline.addLast("lengthFieldBasedFrameDecoder",new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,0,4,0,4));
//        pipeline.addLast("lengthFieldPrepender",new LengthFieldPrepender(4));
//        添加字符串编解码器
//        pipeline.addLast("stringDecoder",new StringDecoder(CharsetUtil.UTF_8));
//        pipeline.addLast("stringEncoder",new StringEncoder(CharsetUtil.UTF_8));
        pipeline.addLast("myClientInitializer",new MyClientHandler(callback));
    }
}
