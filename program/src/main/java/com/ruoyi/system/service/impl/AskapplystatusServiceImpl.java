package com.ruoyi.system.service.impl;

import com.ruoyi.system.domain.Askapplystatus;
import com.ruoyi.system.domain.AskapplystatusVo;
import com.ruoyi.system.mapper.AskapplystatusMapper;
import com.ruoyi.system.service.IAskapplystatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-05-16
 */
@Service
public class AskapplystatusServiceImpl implements IAskapplystatusService 
{
    @Autowired
    private AskapplystatusMapper askapplystatusMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param askNo 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public Askapplystatus selectAskapplystatusByAskNo(String askNo)
    {
        return askapplystatusMapper.selectAskapplystatusByAskNo(askNo);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param askapplystatus 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<AskapplystatusVo> selectAskapplystatusList(Askapplystatus askapplystatus)
    {
        List<AskapplystatusVo> askapplystatuses = askapplystatusMapper.selectAskapplystatusList(askapplystatus);
        // 判断
        return askapplystatuses;
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param askapplystatus 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertAskapplystatus(Askapplystatus askapplystatus)
    {
        //  进行添加默认的askno
        askapplystatus.setAskNo(UUID.randomUUID().toString());
        return askapplystatusMapper.insertAskapplystatus(askapplystatus);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param askapplystatus 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateAskapplystatus(Askapplystatus askapplystatus)
    {
        return askapplystatusMapper.updateAskapplystatus(askapplystatus);
    }

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param askNos 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteAskapplystatusByAskNos(String [] askNos)
    {
        return askapplystatusMapper.deleteAskapplystatusByAskNos(askNos);
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param askNo 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteAskapplystatusByAskNo(String askNo)
    {
        return askapplystatusMapper.deleteAskapplystatusByAskNo(askNo);
    }

    public static void main(String[] args) {

    }
}
