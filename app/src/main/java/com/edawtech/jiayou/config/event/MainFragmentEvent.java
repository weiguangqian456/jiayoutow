package com.edawtech.jiayou.config.event;

// Main页面发送到Fragment页面刷新数据
public class MainFragmentEvent {
    private int page;

    public MainFragmentEvent(int page) {
        this.page = page;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
