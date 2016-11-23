package com.stock.dao;

import com.stock.model.JunXian;

public interface JunXianMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(JunXian record);

    int insertSelective(JunXian record);

    JunXian selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(JunXian record);

    int updateByPrimaryKey(JunXian record);
}