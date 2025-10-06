package com.xulei.yuntobackend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xulei.yuntobackend.model.VO.SpaceUserVO;
import com.xulei.yuntobackend.model.dto.spaceuser.SpaceUserAddRequest;
import com.xulei.yuntobackend.model.dto.spaceuser.SpaceUserQueryRequest;
import com.xulei.yuntobackend.model.entity.SpaceUser;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author xl
* @description 针对表【space_user(空间用户关联)】的数据库操作Service
* @createDate 2025-10-05 19:26:10
*/
public interface SpaceUserService extends IService<SpaceUser> {

    /**
     * 添加空间用户关联
     * @param spaceUserAddRequest 添加空间用户的请求参数
     * @return 返回添加的空间用户ID
     */
    long addSpaceUser(SpaceUserAddRequest spaceUserAddRequest);

    /**
     * 校验空间用户关联数据
     * @param spaceUser 空间用户关联对象
     * @param add 是否为添加操作
     */
    void validSpaceUser(SpaceUser spaceUser, boolean add);

    /**
     * 获取查询条件构造器
     * @param spaceUserQueryRequest 查询空间用户的请求参数
     * @return 返回查询条件构造器
     */
    QueryWrapper<SpaceUser> getQueryWrapper(SpaceUserQueryRequest spaceUserQueryRequest);

    /**
     * 获取空间用户视图对象
     * @param spaceUser 空间用户关联对象
     * @param request HTTP请求对象
     * @return 返回空间用户视图对象
     */
    SpaceUserVO getSpaceUserVO(SpaceUser spaceUser, HttpServletRequest request);

    /**
     * 获取空间用户视图对象列表
     * @param spaceUserList 空间用户关联对象列表
     * @return 返回空间用户视图对象列表
     */
    List<SpaceUserVO> getSpaceUserVOList(List<SpaceUser> spaceUserList);
}
