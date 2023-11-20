package com.example.swagger.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.swagger.entity.UserDetail;
import com.example.swagger.mapper.UserDetailMapper;
import com.example.swagger.service.UserDetailService;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl extends ServiceImpl<UserDetailMapper, UserDetail> implements UserDetailService {
}
