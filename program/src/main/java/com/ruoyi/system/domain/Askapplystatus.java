package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 【请填写功能名称】对象 askapplystatus
 * 
 * @author ruoyi
 * @date 2025-05-16
 */
@Data
public class Askapplystatus extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 签单流水号 */
    private String  askNo;

    /** 签单日期 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @Excel(name = "签单日期", width = 30, dateFormat = "yyyy-MM-dd  HH:mm")
    private Date qdDate;

    /** 案件姓名 */
    @Excel(name = "案件姓名")
    private String ajName;

    /** 案件渠道 */
    @Excel(name = "案件渠道")
    private String ajQudao;

    /** 姓名 */
    @Excel(name = "姓名")
    private String name;

    /** 手机号 */
    @Excel(name = "手机号")
    private String phone;

    /** 签单金额 */
//    @Excel(name = "签单金额")
    private String qdje;

    /** 后期提成 */
//    @Excel(name = "后期提成")
    private String hqtc;

    /** 实收金额 */
//    @Excel(name = "实收金额")
    private String ssje;

    /** 订单状态 */
    @Excel(name = "订单状态")
    private String status;

    /** 探案律师 */
//    @Excel(name = "探案律师")
    private String taUser;

    /**
     * 软删除
     * @param askNo
     */
    @TableLogic(value = "0", delval = "1")
    private Integer  delFlag;
    /** 探案律师 */
    private String  userId ;
    @Excel(name = "处理人名称")
    private String  userName ;
    @Excel(name = "备注")
    private String  bz ;

    /** 签单日期 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @Excel(name = "签单日期", width = 30, dateFormat = "yyyy-MM-dd  HH:mm")
    private Date gjsj;
    @Excel(name = "跟进时间")
    private String  wxtj ;
    @Excel(name = "跟进状态")
    private String  gjzt ;
    @Excel(name = "案件情况")
    private String  ajqk ;
    @Override
    public String toString() {
        return "Askapplystatus{" +
                "askNo='" + askNo + '\'' +
                ", qdDate=" + qdDate +
                ", ajName='" + ajName + '\'' +
                ", ajQudao='" + ajQudao + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", qdje='" + qdje + '\'' +
                ", hqtc='" + hqtc + '\'' +
                ", ssje='" + ssje + '\'' +
                ", status='" + status + '\'' +
                ", taUser='" + taUser + '\'' +
                ", delFlag=" + delFlag +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", bz='" + bz + '\'' +
                '}';
    }
}
