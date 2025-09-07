package com.xulei.yuntobackend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xulei.yuntobackend.model.VO.LoginUserVO;
import com.xulei.yuntobackend.model.VO.UserVO;
import com.xulei.yuntobackend.model.dto.user.UserQueryRequest;
import com.xulei.yuntobackend.model.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author xl
 * @description 针对表【user(用户)】的数据库操作Service
 * @createDate 2025-09-06 22:37:45
 */
public interface UserService extends IService<User> {

    /**
     * 用户注册
     *
     * @param userAccount   用户名
     * @param userPassword  密码
     * @param checkPassword 二次输入密码
     * @return 新用户id
     */
    long userRegister(String userAccount, String userPassword, String checkPassword);

    /**
     * 用户登录
     *
     * @param userAccount  用户名
     * @param userPassword 密码
     * @return 脱敏后的用户信息
     */
    LoginUserVO loginUserVO(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 获取加密密码
     *
     * @param userPassword 用户名密码
     * @return 加密后的密码
     */
    String getEncryptPassword(String userPassword);

    /**
     * 获取当前登录用户
     *
     * @param request 请求
     * @return 登录的用户
     */
    User getLoginUser(HttpServletRequest request);


    /**
     * 请求脱敏后的用户登录信息
     *
     * @param user 需要脱敏的用户
     * @return 脱敏后的用户
     */
    LoginUserVO getLoginUserVO(User user);

    /**
     * 请求脱敏后的用户信息
     *
     * @param user 需要脱敏的用户
     * @return 脱敏后的用户
     */
    UserVO getUserVO(User user);

    /**
     * 请求脱敏后的用户信息列表
     *
     * @param userList 需要脱敏的用户列表
     * @return 脱敏后的用户列表
     */
    List<UserVO> getUserVOList(List<User> userList);

    /**
     * 退出登录
     *
     * @param request 请求
     * @return
     */
    boolean userLogout(HttpServletRequest request);

    /**
     * 获取查询条件
     * 将Java对象转换为mybatis需要的查询对象
     *
     * @param userQueryRequest
     * @return
     */
    QueryWrapper<User> getQueryWrapper(UserQueryRequest userQueryRequest);
}
