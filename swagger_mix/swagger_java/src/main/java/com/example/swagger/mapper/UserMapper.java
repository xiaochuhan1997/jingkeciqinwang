package com.example.swagger.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.swagger.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    User selectUserWithDetail(@Param("id") Long id);
}
