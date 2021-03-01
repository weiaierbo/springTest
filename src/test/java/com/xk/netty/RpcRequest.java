package com.xk.netty;

/**
 * @author kai.xu
 * @create 2020-09-10 15:17
 */
public class RpcRequest {

    private Integer status;

    private String data;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
