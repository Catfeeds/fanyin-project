package com.fanyin.service.system.impl;


import com.alicp.jetcache.anno.Cached;
import com.fanyin.constant.RedisConstant;
import com.fanyin.dto.system.config.ConfigAddRequest;
import com.fanyin.dto.system.config.ConfigEditRequest;
import com.fanyin.dto.system.config.ConfigQueryRequest;
import com.fanyin.enums.ErrorCodeEnum;
import com.fanyin.exception.BusinessException;
import com.fanyin.mapper.system.SystemConfigMapper;
import com.fanyin.model.system.SystemConfig;
import com.fanyin.service.system.SystemConfigService;
import com.fanyin.utils.BeanCopyUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 系统参数配置服务类,系统参数无权限删除
 * @author 二哥很猛
 * @date 2018/1/12 09:46
 */
@Service("systemConfigService")
@Transactional(rollbackFor = RuntimeException.class)
public class SystemConfigServiceImpl implements SystemConfigService {

    @Autowired
    private SystemConfigMapper systemConfigMapper;

    @Override
    @CacheEvict(cacheNames = RedisConstant.SYSTEM_CONFIG,key = "#request.nid")
    public void updateConfig(ConfigEditRequest request) {
        int i = systemConfigMapper.updateConfig(request);
        if(i != 1){
            throw new BusinessException(ErrorCodeEnum.UPDATE_CONFIG_ERROR);
        }
    }

    @Override
    @Transactional(readOnly = true,rollbackFor = RuntimeException.class)
    public PageInfo<SystemConfig> getByPage(ConfigQueryRequest request) {
        PageHelper.startPage(request.getPage(),request.getRows());
        List<SystemConfig> list = systemConfigMapper.getList(request);
        return new PageInfo<>(list);
    }

    @Override
    @Cacheable(cacheNames = RedisConstant.SYSTEM_CONFIG,key = "#p0")
    public SystemConfig getByNid(String nid) {
        return systemConfigMapper.getByNid(nid);
    }

    @Override
    @Cached(name = RedisConstant.SYSTEM_CONFIG,key = "#id")
    public SystemConfig getById(Integer id) {
        return systemConfigMapper.selectByPrimaryKey(id);
    }

    @Override
    public void addConfig(ConfigAddRequest request) {
        SystemConfig copy = BeanCopyUtil.copy(request, SystemConfig.class);
        systemConfigMapper.insertSelective(copy);
    }

}
