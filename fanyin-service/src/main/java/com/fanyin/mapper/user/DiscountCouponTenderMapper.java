package com.fanyin.mapper.user;

import com.fanyin.model.user.DiscountCouponTender;

/**
 * @author 二哥很猛
 */
public interface DiscountCouponTenderMapper {
    /**
     * 根据主键删除数据库的记录
     *
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insert(DiscountCouponTender record);

    /**
     * 插入不为空的记录
     *
     * @param record
     */
    int insertSelective(DiscountCouponTender record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id
     */
    DiscountCouponTender selectByPrimaryKey(Integer id);

    /**
     * 根据主键来更新部分数据库记录
     *
     * @param record
     */
    int updateByPrimaryKeySelective(DiscountCouponTender record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(DiscountCouponTender record);
}