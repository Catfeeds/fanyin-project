package com.fanyin.mapper.system;

import com.fanyin.model.system.SystemMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 二哥很猛
 */
public interface SystemMenuMapper {
    /**
     * 根据主键删除数据库的记录
     *
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 插入数据库记录
     *
     * @param record
     * @return
     */
    int insert(SystemMenu record);

    /**
     * 插入不为空的记录
     *
     * @param record
     * @return
     */
    int insertSelective(SystemMenu record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id
     * @return
     */
    SystemMenu selectByPrimaryKey(Integer id);

    /**
     * 根据主键来更新部分数据库记录
     *
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(SystemMenu record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     * @return
     */
    int updateByPrimaryKey(SystemMenu record);

    /**
     * 查询所有可用的菜单
     * @return
     */
    List<SystemMenu> getAllList();


    /**
     * 获取某用户所能查看的菜单列表  只获取菜单 不进行排序菜单分组等
     * @param userId 用户id
     * @param btnMenu 是否包含按钮菜单 true:全部菜单 false只包含主菜单
     * @return 用户所有可查看菜单列表
     */
    List<SystemMenu> getUserMenuList(@Param("userId") Integer userId, @Param("btnMenu") boolean btnMenu);
}