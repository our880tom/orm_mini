package cn.swiftdev.example.orm.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Page<T> implements Serializable{

    private static final int DEFAULT_PAGE_SIZE = 20;

    private long start; //在本页中数据库的起始位置

    private long total;

    private int pageSize = DEFAULT_PAGE_SIZE;

    private List<T> rows;

    public Page(){
        this(0, 0, DEFAULT_PAGE_SIZE, new ArrayList<T>());
    }

    public Page(long start, long total, int pageSize, List<T> rows) {
        this.start = start;
        this.total = total;
        this.pageSize = pageSize;

        this.rows = rows;
    }


    public long getTotalPageCount(){
        if (total % pageSize == 0){
            return total / pageSize;
        } else {
            return total / pageSize + 1;
        }
    }

    public long getPageNo(){
        return start / pageSize + 1;
    }

    public boolean hasNextPage(){
        return getPageNo() < getTotalPageCount() - 1;
    }

    public boolean hasPreviosPage(){
        return getPageNo() > 1;
    }

    public static int getStartOfPage(int pageNo){
        return (pageNo - 1) * DEFAULT_PAGE_SIZE;
    }
    public static int getStartOfPage(int pageNo, int pageSize){
        return (pageNo - 1) * pageSize;
    }
    public void setTotal(long total){
        this.total = total;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public long getStart() {
        return start;
    }

    public long getTotal() {
        return total;
    }

    public int getPageSize() {
        return pageSize;
    }

    public List<T> getRows() {
        return rows;
    }
}
