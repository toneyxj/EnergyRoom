package com.moxi.energyroom.netty;


import com.moxi.energyroom.Been.transmitData.BaseData;
import com.moxi.energyroom.listener.NettyInnerCallback;
import com.moxi.energyroom.listener.NettyMessageCallback;
import com.moxi.energyroom.utils.APPLog;

import org.json.JSONException;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyClient implements NettyInnerCallback {
    private static NettyClient instance = null;
    private NettyMessageCallback callback;
    private ChannelHandlerContext ctx = null;
    private BaseData sendMessageData = null;
    private boolean isFinish = false;
    private boolean isStart = false;
    private String ip=null;

    private int failIndex = 0;

    /**
     * 发送的数据的数据仓库
     */
    private Map<String, BaseData> messages = new LinkedHashMap();

    public static NettyClient getInstance() {
        if (instance == null) {
            synchronized (NettyClient.class) {
                if (instance == null) {
                    instance = new NettyClient();
                }
            }
        }
        return instance;
    }


    /**
     * 从新设置callback
     *
     * @param callback
     */
    public synchronized void reSetingCallBack(NettyMessageCallback callback) {
        this.callback = callback;
    }

    public synchronized void sendMessages(BaseData data) {
        APPLog.e("添加发送数据唯一标识="+data.getOnlyValue(),data.toJson());
        messages.put(data.getOnlyValue(), data);
        fulshData();
    }

    private synchronized void fulshData() {
        if (ctx == null || isFinish || !isStart) return;
        if (messages.size() == 0) return;
        Iterator iterator = messages.values().iterator();
        if (iterator.hasNext()) {
            sendMessageData = (BaseData) iterator.next();
            String value = sendMessageData.toJson();
            APPLog.e("发送数据",value);
            byte[] req = value.getBytes();
            ByteBuf firstMessage = Unpooled.buffer(req.length);
            firstMessage.writeBytes(req);
            try {
                ctx.writeAndFlush(firstMessage);
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    /**
     * 是否关闭netty
     *
     * @return
     */
    private synchronized boolean isCloseNetty() {
        if (failIndex < 8) {
            //重新连接
            try {
                Thread.sleep(1000);
                startNetty();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            failIndex++;
            return false;
        }
        return true;
    }

    //在这里说明 服务器可以使用handler 或者childHandler
    // 区别在于handler是接收到了事件后交给group里面的第一个参数如bossGroup进行处理
//    childHandler 接收到了事件后交由group的第二个参数workerGroup进行处理，自己本身不处理。
    public void startNetty() {
        startNetty(ip);
    }
    public void startNetty(String ip) {
        getInstance().ip=ip;
        isStart = false;
        new Thread(new Runnable() {
            @Override
            public void run() {
                EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
                try {
                    Bootstrap bootstrap = new Bootstrap();
                    bootstrap.group(eventLoopGroup)
                            .option(ChannelOption.SO_KEEPALIVE, true)
                            .option(ChannelOption.SO_BACKLOG, 128)
                            .option(ChannelOption.TCP_NODELAY, true)
                            .channel(NioSocketChannel.class)
                            .handler(new MyClientInitializer(getInstance()));
                    ChannelFuture channelFuture = bootstrap.connect(getInstance().ip, 9998).sync();
                    channelFuture.channel().closeFuture().sync();
                } catch (Exception e) {
                    e.printStackTrace();
                    APPLog.e("连接不成功");
                    if (null != callback) callback.TCPConnectFail(e);
                } finally {
                    eventLoopGroup.shutdownGracefully();
                }
            }
        }).start();
    }

    public void onDestory() {
        this.callback = null;
        isFinish = true;
    }

    @Override
    public void onBackMessage(ChannelHandlerContext ctx, String msg) {
        if (closeCtx(ctx)) return;
        APPLog.e("ctx", ctx == null);
//        this.ctx=ctx;

        if (callback != null) {
            try {
                callback.backMessage(BaseData.buildModel(msg));
                if (messages!=null&&sendMessageData!=null) {
                    messages.remove(sendMessageData.getOnlyValue());
                    fulshData();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                callback.TCPConnectFail(new Exception("数据解析开小差啦！！！"));
            }
        }
    }

    @Override
    public void onConnectException(ChannelHandlerContext ctx, Throwable cause) {
        APPLog.e("onConnectException");
        APPLog.e("ctx", ctx == null);
        closeCtx(null);
        if (isFinish) return;
        //如果确定连接失败，通知显示出来
        if (isCloseNetty()) {
            if (callback != null) callback.TCPConnectFail(new Exception("控制连接已经终端"));
        }
    }

    @Override
    public void onConnectSucess(ChannelHandlerContext ctx) {
        APPLog.e("onConnectSucess");
        APPLog.e("ctx", ctx == null);
        if (closeCtx(ctx)) return;
        failIndex = 0;
        isStart = true;
        APPLog.e("isStart", isStart);
        this.ctx = ctx;
        if (callback != null) callback.TCPConnectSucess();
    }

    @Override
    public void onConnextInactive(ChannelHandlerContext ctx) {
        if (closeCtx(ctx)) return;
        //如果确定连接失败，通知显示出来
        if (isCloseNetty()) {
            if (callback != null) callback.TCPConnectFail(new Exception("控制连接已经终端"));
        }
        //发送一个获取温湿度的消息
        APPLog.e("当亲程序处于不活跃状态");
//        this.ctx=ctx;
    }

    private boolean closeCtx(ChannelHandlerContext ctx) {
        try {
            if (isFinish || ctx == null) {
                if (this.ctx != null)
                    this.ctx.close();
                isStart = false;
                this.ctx = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isFinish || ctx == null;
    }
}
