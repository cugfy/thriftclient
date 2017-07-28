package com.thrift;

import org.apache.commons.pool.ObjectPool;
import org.apache.thrift.transport.TSocket;

/**
 * Created by fengyong on 2017/07/06.
 */
public interface ConnectionProvider {
    /**
     * 随机获取一个连接池
     *
     * @return TSocket
     */
    ObjectPool getPool();

    /**
     * 返回链接
     * @param pool
     * @param socket
     */
    void returnCon(ObjectPool pool, TSocket socket);
}
