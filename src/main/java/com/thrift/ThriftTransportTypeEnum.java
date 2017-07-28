package com.thrift;

/**
 * Created by fengyong on 2017/7/7.
 */
public enum ThriftTransportTypeEnum {
    TSocket("TSocket", "阻塞式I/O"),
    TFramedTransport("TFramedTransport", "非阻塞方式，按块的大小进行传输"),
    TNonblockingTransport("TNonblockingTransport", "非阻塞方式");


    ThriftTransportTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private String code;

    private String desc;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
