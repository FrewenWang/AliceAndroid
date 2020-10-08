package com.frewen.android.demo.model;

import java.util.List;

/**
 * @filename: ExpressInfo 快递信息查询
 * @introduction: 开放免费测试接口：https://www.bejson.com/knownjson/webInterface/
 *         http://www.kuaidi100.com/query?type=快递公司代号&postid=快递单号
 *         PS：快递公司编码:
 *         申通="shentong" EMS="ems" 顺丰="shunfeng" 圆通="yuantong"
 *         中通="zhongtong" 韵达="yunda" 天天="tiantian"
 *         汇通="huitongkuaidi" 全峰="quanfengkuaidi"
 *         德邦="debangwuliu" 宅急送="zhaijisong"
 * @author: Frewen.Wong
 * @time: 2020/9/29 19:15
 *         Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
public class ExpressInfo {

    /**
     * message : ok
     * nu : 11111111111
     * ischeck : 0
     * condition : 00
     * com : yuantong
     * status : 200
     * state : 0
     * data : [{"time":"2020-09-29 17:08:46","ftime":"2020-09-29 17:08:46","context":"运输中,到达中转地[厦门西柯],等待卸车","location":""},{"time":"2020-09-28 22:07:27","ftime":"2020-09-28 22:07:27","context":"运输中,货物由温州前往厦门西柯途中","location":""},{"time":"2020-09-28 21:19:49","ftime":"2020-09-28 21:19:49","context":"货物在[温州]完成装车,等待出发","location":""},{"time":"2020-09-27 19:56:44","ftime":"2020-09-27 19:56:44","context":"货物已承接,于[温州]公司入库","location":""}]
     */

    private String message;
    private String nu;
    private String ischeck;
    private String condition;
    private String com;
    private String status;
    private String state;
    private List<DataBean> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNu() {
        return nu;
    }

    public void setNu(String nu) {
        this.nu = nu;
    }

    public String getIscheck() {
        return ischeck;
    }

    public void setIscheck(String ischeck) {
        this.ischeck = ischeck;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getCom() {
        return com;
    }

    public void setCom(String com) {
        this.com = com;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * time : 2020-09-29 17:08:46
         * ftime : 2020-09-29 17:08:46
         * context : 运输中,到达中转地[厦门西柯],等待卸车
         * location :
         */

        private String time;
        private String ftime;
        private String context;
        private String location;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getFtime() {
            return ftime;
        }

        public void setFtime(String ftime) {
            this.ftime = ftime;
        }

        public String getContext() {
            return context;
        }

        public void setContext(String context) {
            this.context = context;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }
    }
}
