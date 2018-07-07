package com.paascloud.provider.mapper;

import com.paascloud.provider.model.domain.PcUacUser;
import java.util.List;
import java.util.Map;

public interface PcUacUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PcUacUser record);

    int insertSelective(PcUacUser record);

    PcUacUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PcUacUser record);

    int updateByPrimaryKey(PcUacUser record);

    int insertBatch(List recordList);

    int selectCount(Map map);

    List selectBeans(Map map);
}