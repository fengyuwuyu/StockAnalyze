package com.stock.dao.eamil;

import java.util.List;

import com.stock.model.email.CecepEmail;

public interface CecepEmailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CecepEmail record);

    int insertSelective(CecepEmail record);

    CecepEmail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CecepEmail record);

    int updateByPrimaryKey(CecepEmail record);

	CecepEmail selectSender();

	List<CecepEmail> selectReveivers();
}