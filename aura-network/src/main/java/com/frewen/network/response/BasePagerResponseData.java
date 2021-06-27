package com.frewen.network.response;

import java.util.List;

/**
 * @filename: AuraNetPagerResponse
 * @author: Frewen.Wong
 * @time: 2021/6/26 12:16
 * @version: 1.0.0
 * @introduction: 网络请求中的分页响应数据
 * @copyright: Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
public class BasePagerResponseData<T> {

    private T dataList;

    private int currentPage;

    private int offset;

    private boolean over;

    private int pageCount;

    private int size;

    private int total;


    public boolean isEmpty() {
        return 0 == ((List) dataList).size();
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public boolean isOver() {
        return over;
    }

    public void setOver(boolean over) {
        this.over = over;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void setDataList(T dataList) {
        this.dataList = dataList;
    }

    public T getDataList() {
        return dataList;
    }

    public boolean hasMoreData() {
        return !over;
    }
}
