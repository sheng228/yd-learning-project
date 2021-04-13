package org.yd.proxy;

/**
 * @Description 短信发送者
 * @Author XUZS
 * @Date 21-4-9 14:10
 * @Version 1.0
 **/
public class SmsSender implements ISender {
    public boolean send() {
        System.out.println("sending msg");
        return true;
    }
}
