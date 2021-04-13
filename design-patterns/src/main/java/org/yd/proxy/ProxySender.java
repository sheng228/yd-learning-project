package org.yd.proxy;

/**
 * @Description 代理发送者
 * @Author XUZS
 * @Date 21-4-9 14:11
 * @Version 1.0
 **/
public class ProxySender implements ISender {

    private ISender sender;

    public ProxySender(ISender sender){
        this.sender = sender;
    }

    public boolean send() {
        System.out.println("处理前");
        boolean result = sender.send();
        System.out.println("处理后");
        return result;
    }
}
