package com.ruoyi.system.domain;

import java.util.List;

public class BatchUpdateUserDTO {
    private List<String> askno;
    private String userid;

    // getter 和 setter
    public List<String> getAskno() {
        return askno;
    }
    public void setAskno(List<String> askno) {
        this.askno = askno;
    }
    public String getUserid() {
        return userid;
    }
    public void setUserid(String userid) {
        this.userid = userid;
    }
}
