package com.example.swagger.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.swagger.entity.User;
import com.example.swagger.entity.UserDetail;
import com.example.swagger.mapper.UserMapper;
import com.example.swagger.service.UserDetailService;
import com.example.swagger.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserDetailService userDetailService;


    @Override
    public boolean checkUsernameExists(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        return userMapper.selectCount(queryWrapper) > 0;
    }
    @Override
    public void createUser(List<UserDetail> userDetailList) {
//        userDetailMapper.saveBatch(userDetailList);
    }

    @Transactional
    @Override
    public String testCreate(User user) {

        List<UserDetail> detailList = user.getUserDetailList();
        boolean uc = save(user);
        boolean udc = userDetailService.saveBatch(detailList);
        return uc && udc ? "success" : "failed";
    }
//    @Override
//    public void saveUser(User user, List<UserDetail> userDetailList) {
//        // 保存用户
//        userMapper.insert(user);
//
//        // 保存用户详细信息
//        for (UserDetail userDetail : user.getUserDetailList()) {
//            userDetail.setUserId(user.getId());
//            userDetailMapper.insert(userDetail);
//        }
//    }

}
