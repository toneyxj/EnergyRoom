package com.moxi.energyroom.model.impl.mian;

import android.content.Context;
import android.os.Message;

import com.moxi.energyroom.Been.transmitData.BaseData;
import com.moxi.energyroom.listener.HandlerMessageInterface;
import com.moxi.energyroom.model.inter.main.IIPAddressModel;
import com.moxi.energyroom.otherPresenter.BaseUtils;
import com.moxi.energyroom.presenter.inter.IIPAddressPresenter;
import com.moxi.energyroom.utils.APPLog;
import com.moxi.energyroom.utils.SharePreferceUtil;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.Enumeration;

public class IPAddressModelImp implements IIPAddressModel, HandlerMessageInterface {
    private static final String IP_ADDRESS = "IP_address";
    private Context context;
    private IIPAddressPresenter presenter;
    private BaseUtils.XJHander hander = new BaseUtils.XJHander(this);
    private String IP = null;

    public IPAddressModelImp(Context context, IIPAddressPresenter presenter) {
        this.context = context;
        this.presenter = presenter;
        IP = SharePreferceUtil.getInstance(context).getString(IP_ADDRESS);
    }

    private void start() {
        presenter.startGetIp();
        new Thread(new Runnable() {
            @Override
            public void run() {
                String curip = getIpAddressString();
                if (null == curip || curip.equals("") || curip.equals("0.0.0.0")) {
                    sendMsgSucess(-1, "无法获取到本机IP地址");
                    return;
                } else if (IPAddressModelImp.this.IP != null
                        && !IPAddressModelImp.this.IP.equals("")
                        && getScoketSucess(IPAddressModelImp.this.IP)) {
                    //连接成功
                    sendMsgSucess(1, IPAddressModelImp.this.IP);
                    return;
                }
                APPLog.e("本机IP地址", curip);
                String[] vas = curip.split("\\.");
                int lastIP = 0;
                String curIP = "";
                for (int i = 0; i < vas.length; i++) {
                    if (i != (vas.length - 1)) {
                        curIP += vas[i] + ".";
                    } else {
                        lastIP = Integer.parseInt(vas[i]);
                    }
                }
                APPLog.e("curIP", curIP);
                APPLog.e("lastIP", lastIP);
                for (int i = 0; i < 255; i++) {
                    if (i == lastIP) continue;//规避本机
                    String scoketIp = curIP + i;
                    if (getScoketSucess(scoketIp)) {
                        sendMsgSucess(1, scoketIp);
                        return;
                    }
                }
                sendMsgSucess(-1, "无法与控制器建立连接，请检查控制器是否开启。");
            }
        }).start();
    }

    private void sendMsgSucess(int what, String ip) {
        Message msg = new Message();
        msg.what = what;
        msg.obj = ip;
        hander.sendMessage(msg);
    }

    private boolean getScoketSucess(String ip) {
        try {
            APPLog.e("getScoketSucess尝试连接ip", ip);
            Socket socket = new Socket();
            SocketAddress socAddress = new InetSocketAddress(ip, 9998);
            socket.connect(socAddress, 800);
            APPLog.e("getScoketSucess-isConnected=" + socket.isConnected(), ip);
            return socket.isConnected();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void handleMessage(Message msg) {
        String obj = msg.obj.toString();
        switch (msg.what) {
            case -1:
                presenter.getIPFail(new Exception(obj));
                break;
            case 1:
                this.IP = obj;

                SharePreferceUtil.getInstance(context).setCache(IP_ADDRESS, this.IP);
                presenter.curIPAddress(this.IP);
                break;
            default:
                break;
        }
    }

    @Override
    public String getIpAddress() {
        return IP;
    }

    @Override
    public void reGetAddress() {
        start();
    }

    @Override
    public void onDestory() {
        hander.removeCallbacksAndMessages(null);
    }

    @Override
    public void backMessage(BaseData baseData) {

    }

    @Override
    public void reSendMessage() {

    }

    private String getIpAddressString() {
        try {
            for (Enumeration<NetworkInterface> enNetI = NetworkInterface
                    .getNetworkInterfaces(); enNetI.hasMoreElements(); ) {
                NetworkInterface netI = enNetI.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = netI
                        .getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (inetAddress instanceof Inet4Address && !inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return null;
    }

}
