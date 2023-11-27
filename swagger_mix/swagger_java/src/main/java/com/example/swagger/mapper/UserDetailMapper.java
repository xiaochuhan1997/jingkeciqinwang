package com.example.swagger.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.swagger.entity.UserDetail;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDetailMapper extends BaseMapper<UserDetail> {
}

