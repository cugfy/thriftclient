package com.thrift;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.pool.ObjectPool;
import org.apache.thrift.transport.TSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 连接池管理
 * Created by fengyong on 2017/07/06.
 */
public class ConnectionManager implements MethodInterceptor {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 保存local对象
     */
    private ThreadLocal<TSocket> socketThreadSafe = new ThreadLocal<TSocket>();

    /**
     * 连接提供池
     */
    private ConnectionProvider connectionProvider;

    public ConnectionProvider getConnectionProvider() {
        return connectionProvider;
    }

    public void setConnectionProvider(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        TSocket socket = null;
        ObjectPool pool = null;
        try {
            pool = connectionProvider.getPool();
            socket = (TSocket) pool.borrowObject();
            socketThreadSafe.set(socket);
            Object ret = methodInvocation.proceed();
            return ret;
        } catch (Exception e) {
            logger.error("error ConnectionManager.invoke()", e);
            throw new Exception(e);
        } finally {
            connectionProvider.returnCon(pool, socket);
            socketThreadSafe.remove();
        }
    }

    /**
     * 取socket
     *
     * @return
     */
    public TSocket getSocket() {
        return socketThreadSafe.get();
    }
}
