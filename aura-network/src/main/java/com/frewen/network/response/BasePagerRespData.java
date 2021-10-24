package com.frewen.network.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @filename: AuraNetPagerResponse
 * @author: Frewen.Wong
 * @time: 2021/6/26 12:16
 * @version: 1.0.0
 * @introduction: 网络请求中的分页响应数据
 * @copyright: Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
public class BasePagerRespData<T> {
    @SerializedName("datas")
    private T datas;

    private int curPage;

    private int offset;

    private boolean over;

    private int pageCount;

    private int size;

    private int total;


    public boolean isEmpty() {
        return 0 == ((List) datas).size();
    }

    public int getCurrentPage() {
        return curPage;
    }

    public void setCurrentPage(int currentPage) {
        this.curPage = currentPage;
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
        this.datas = dataList;
    }

    public T getDataList() {
        return datas;
    }

    public boolean hasMoreData() {
        return !over;
    }

    @Override
    public String toString() {
        return "BasePagerResponseData{" +
                "dataList=" + datas +
                ", currentPage=" + curPage +
                ", offset=" + offset +
                ", over=" + over +
                ", pageCount=" + pageCount +
                ", size=" + size +
                ", total=" + total +
                '}';
    }
}
