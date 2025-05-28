package com.ruoyi.system.service.impl;

import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.system.domain.Askapplystatus;
import com.ruoyi.system.domain.AskapplystatusVo;
import com.ruoyi.system.mapper.AskapplystatusMapper;
import com.ruoyi.system.service.IAskapplystatusService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
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
@Slf4j
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
     * @param askapplystatusVo 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<AskapplystatusVo> selectAskapplystatusList(AskapplystatusVo askapplystatusVo)
    {
        List<AskapplystatusVo> askapplystatuses = askapplystatusMapper.selectAskapplystatusList(askapplystatusVo);
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

    @Override
    public String  importData(List<AskapplystatusVo> askapplystatusVoList) {
        if (CollectionUtils.isEmpty(askapplystatusVoList))
        {
            throw new ServiceException("导入数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        int rowNum = 1;
        for (AskapplystatusVo askapplystatusVo : askapplystatusVoList)
        {
            try
            {
//                BeanValidators.validateWithException(validator, askapplystatusVo);
                this.insertAskapplystatus(askapplystatusVo);
                successNum++;
                successMsg.append("<br/>第 ").append(successNum).append(" 条导入成功");

            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + " 导入失败：";
                failureMsg.append("<br/>第 ").append(rowNum).append(" 行导入失败：").append(e.getMessage());
                log.error(msg, e);
            }
            rowNum++;
        }
        if (failureNum > 0)
        {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        }
        else
        {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }

    @Override
    public int updateBatchUserInfo(List<String > list ,String userId,String userName) {
        int i = askapplystatusMapper.updateBatchUserInfo(list, userId, userName);
        return i;
    }


}
