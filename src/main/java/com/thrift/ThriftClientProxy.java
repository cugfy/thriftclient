package com.thrift;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;

/**
 * Created by fengyong on 2017/07/06.
 */
public class ThriftClientProxy {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private ConnectionManager connectionManager;

    public ConnectionManager getConnectionManager() {
        return connectionManager;
    }

    public void setConnectionManager(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public Object getClient(Class clazz, ThriftTransportTypeEnum transportType) {
        Object result = null;
        try {
            TProtocol protocol = null;
            if (ThriftTransportTypeEnum.TFramedTransport == transportType) {
                protocol = new TBinaryProtocol(new TFramedTransport(connectionManager.getSocket()));
            } else if (ThriftTransportTypeEnum.TSocket == transportType) {
                protocol = new TBinaryProtocol(connectionManager.getSocket());
            }
            Class client = Class.forName(clazz.getName() + "$Client");
            Constructor con = client.getConstructor(TProtocol.class);
            result = con.newInstance(protocol);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return result;
    }
}
