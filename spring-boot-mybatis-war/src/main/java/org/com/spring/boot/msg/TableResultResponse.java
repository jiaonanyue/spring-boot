package org.com.spring.boot.msg;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author jiaonanyue
 * @create 2017-07-14 22:40
 */
public class TableResultResponse<T> {
    /**
     * 总条数
     */
    Long total;
    /**
     * lsit 数据
     */
    List<T> rows;

    public TableResultResponse(Long total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }

    public TableResultResponse() {
    }

    TableResultResponse<T> total(Long total){
        this.total = total;
        return this;
    }
    TableResultResponse<T> total(List<T> rows){
        this.rows = rows;
        return this;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
