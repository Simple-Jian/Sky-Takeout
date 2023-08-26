package com.sky.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sky.constant.MessageConstant;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.exception.LoginFailedException;
import com.sky.mapper.UserMapper;
import com.sky.properties.WeChatProperties;
import com.sky.service.UserService;
import com.sky.utils.HttpClientUtil;
import com.sky.vo.UserLoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private WeChatProperties weChatProperties;
    @Autowired
    private UserMapper userMapper;

    public static final String WX_LOGIN="https://api.weixin.qq.com/sns/jscode2session";
    @Override
    public User login(UserLoginDTO userLoginDTO) {
        //调用方法通过微信接口服务获取openid
        String openid = getOpenid(userLoginDTO);
        //判断openId是否为空,如果为空则失败,抛出异常
        if(openid.isEmpty()){
            throw new LoginFailedException(MessageConstant.LOGIN_FAILED);
        }
        //判断当前用户是否为新用户,即openId是否存在
        User user = userMapper.getByOpenId(openid);
        //如果是新用户,则自动注册
        if(user==null){
            user = User.builder().openid(openid).createTime(LocalDateTime.now()).build();
            //创建用户
            userMapper.insert(user);
        }
        return user;
    }

    private String getOpenid(UserLoginDTO userLoginDTO) {
        //调用微信接口服务,获得当前用户的openId
        Map<String,String> params=new HashMap<>();
        params.put("appid",weChatProperties.getAppid());
        params.put("secret",weChatProperties.getSecret());
        params.put("js_code",userLoginDTO.getCode());
        params.put("grant_type","authorization_code");
        String json = HttpClientUtil.doGet(WX_LOGIN, params);
        //使用fastjson解析json字符串
        JSONObject jsonObject = JSON.parseObject(json);
        String openid = jsonObject.getString("openid");
        return openid;
    }
}
