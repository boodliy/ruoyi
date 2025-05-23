package com.ruoyi.system.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.Askapplystatus;
import com.ruoyi.system.domain.AskapplystatusVo;
import com.ruoyi.system.service.IAskapplystatusService;
import com.ruoyi.system.service.ISysUserService;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * 客资管理Controller
 *
 * @author ruoyi
 * @date 2025-05-23
 */
@RestController
@RequestMapping("/system/askapplystatus")
public class AskapplystatusController extends BaseController
{
    @Autowired
    private IAskapplystatusService askapplystatusService;
    @Autowired
    private ISysUserService iSysUserService;

    /**
     * 查询客资管理列表
     */
    @PreAuthorize("@ss.hasPermi('system:askapplystatus:list')")
    @GetMapping("/list")
    public TableDataInfo list(Askapplystatus askapplystatus)
    {
        // 获取当前登录人姓名
        String username = getUsername();
        Long userId = getUserId();
        logger.info("登录人user为："+username+"用户userid 为"+userId);
        if (!"admin".equals(username)){
            askapplystatus.setUserId(userId+"");
            // 是管理员那么查询所有的订单
        }
        startPage();
        List<AskapplystatusVo> list = askapplystatusService.selectAskapplystatusList(askapplystatus);
        return getDataTable(list);
    }

    /**
     * 导出客资管理列表
     */
    @PreAuthorize("@ss.hasPermi('system:askapplystatus:export')")
    @Log(title = "导出订单功能", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Askapplystatus askapplystatus)
    {
        List<AskapplystatusVo> list = askapplystatusService.selectAskapplystatusList(askapplystatus);
        ExcelUtil<AskapplystatusVo> util = new ExcelUtil<AskapplystatusVo>(AskapplystatusVo.class);
        util.exportExcel(response, list, "客资管理数据");
    }


    /**
     * 获取客资管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:askapplystatus:query')")
    @GetMapping(value = "/{askNo}")
    public AjaxResult getInfo(@PathVariable("askNo") String  askNo)
    {
        return success(askapplystatusService.selectAskapplystatusByAskNo(askNo));
    }

    /**
     * 新增客资管理
     */
    @PreAuthorize("@ss.hasPermi('system:askapplystatus:add')")
    @Log(title = "新增订单内容", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Askapplystatus askapplystatus)
    {
        String username = getUsername();
        Long userId = getUserId();
        if (!"admin".equals(username)){
            askapplystatus.setUserId(userId+"");
            askapplystatus.setUserName(username);
            // 是管理员那么查询所有的订单
        }
        return toAjax(askapplystatusService.insertAskapplystatus(askapplystatus));
    }

    /**
     * 修改客资管理
     */
    @PreAuthorize("@ss.hasPermi('system:askapplystatus:edit')")
    @Log(title = "修改订单功能", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Askapplystatus askapplystatus)
    {
        String userId = askapplystatus.getUserId();
        SysUser sysUser = new SysUser();
        if (!StringUtils.isEmpty(userId)){
            sysUser = iSysUserService.selectUserByUserName(userId);
            if (Objects.isNull(sysUser)){
                if ( !userId.matches("\\d+")) {
                    throw new IllegalArgumentException("userId不是纯数字");
                }
                sysUser = iSysUserService.selectUserById(Long.valueOf(userId));
            }
        }
        askapplystatus.setUserId(sysUser.getUserId()+"");
        askapplystatus.setUserName(sysUser.getUserName());
        return toAjax(askapplystatusService.updateAskapplystatus(askapplystatus));
    }

    /**
     * 删除客资管理
     */
    @PreAuthorize("@ss.hasPermi('system:askapplystatus:remove')")
    @Log(title = "删除订单功能", businessType = BusinessType.DELETE)
	@DeleteMapping("/{askNos}")
    public AjaxResult remove(@PathVariable String [] askNos)
    {
        return toAjax(askapplystatusService.deleteAskapplystatusByAskNos(askNos));
    }
}
