package org.com.spring.boot.msg;


/**
 *
 * @param <T>
 */
public class ObjectRestResponse<T> {
    /**
     * boolean 状态
     */
    boolean rel;
    /**
     * 返回参数说明
     */
    String msg;
    /**
     * 返回对象
     */
    T result;

    public boolean isRel() {
        return rel;
    }

    public void setRel(boolean rel) {
        this.rel = rel;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public ObjectRestResponse rel(boolean rel) {
        this.setRel(rel);
        return this;
    }

    public ObjectRestResponse msg(String msg) {
        this.setMsg(msg);
        return this;
    }

    public ObjectRestResponse result(T result) {
        this.setResult(result);
        return this;
    }
}
