package com.ruoyi.system.service;

import com.ruoyi.system.domain.Askapplystatus;
import com.ruoyi.system.domain.AskapplystatusVo;

import java.util.List;

/**
 * 【请填写功能名称】Service接口
 * 
 * @author ruoyi
 * @date 2025-05-16
 */
public interface IAskapplystatusService 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param askNo 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public Askapplystatus selectAskapplystatusByAskNo(String  askNo);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param askapplystatus 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<AskapplystatusVo> selectAskapplystatusList(Askapplystatus askapplystatus);

    /**
     * 新增【请填写功能名称】
     * 
     * @param askapplystatus 【请填写功能名称】
     * @return 结果
     */
    public int insertAskapplystatus(Askapplystatus askapplystatus);

    /**
     * 修改【请填写功能名称】
     * 
     * @param askapplystatus 【请填写功能名称】
     * @return 结果
     */
    public int updateAskapplystatus(Askapplystatus askapplystatus);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param askNos 需要删除的【请填写功能名称】主键集合
     * @return 结果
     */
    public int deleteAskapplystatusByAskNos(String [] askNos);

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param askNo 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteAskapplystatusByAskNo(String  askNo);

    String  importData(List<AskapplystatusVo> askapplystatusVoList);
    int updateBatchUserInfo(List<String > list ,String userId,String userName );

}
