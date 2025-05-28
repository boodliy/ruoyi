package com.ruoyi.system.domain;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 【请填写功能名称】对象 askapplystatus
 * 
 * @author ruoyi
 * @date 2025-05-16
 */
@Data
public class AskapplystatusVo extends Askapplystatus
{
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @JSONField(format = "yyyy-MM-dd")
    private Date startDate;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @JSONField(format = "yyyy-MM-dd")
    private Date endDate;
    private String userId2;
}
