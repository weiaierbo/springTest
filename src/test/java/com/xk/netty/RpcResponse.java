package com.xk.netty;

/**
 * @author kai.xu
 * @create 2020-09-10 15:17
 */
public class RpcResponse<T> {

    private T data;

    private Integer status;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
