package com.stock.dao.eamil;

import java.util.Map;

import com.stock.model.email.CecepEmailLog;

public interface CecepEmailLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CecepEmailLog record);

    int insertSelective(Map<String, Object> map);

    CecepEmailLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CecepEmailLog record);

    int updateByPrimaryKey(CecepEmailLog record);
}