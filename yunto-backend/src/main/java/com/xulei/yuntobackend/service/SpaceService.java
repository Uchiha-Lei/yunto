package com.xulei.yuntobackend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xulei.yuntobackend.model.VO.SpaceVO;
import com.xulei.yuntobackend.model.dto.space.SpaceQueryRequest;
import com.xulei.yuntobackend.model.dto.space.SpaceAddRequest;
import com.xulei.yuntobackend.model.entity.Space;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xulei.yuntobackend.model.entity.User;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xl
 * @description 针对表【space(空间)】的数据库操作Service
 * @createDate 2025-09-14 20:00:36
 */
public interface SpaceService extends IService<Space> {

    /**
     * 空间数据校验
     *
     * @param space
     * @param add
     */
    void validSpace(Space space, boolean add);

    /**
     * 根据空间级别填充空间
     * 该方法用于处理空间对象，并根据其级别进行相应的填充操作
     *
     * @param space 需要填充的空间对象，包含空间级别和相关信息
     */
    void fillSpaceBySpaceLevel(Space space);

    /**
     * 添加空间的方法
     *
     * @param spaceAddRequest 空间添加请求对象，包含需要添加的空间信息
     * @param loginUser       当前登录用户对象，用于验证用户权限
     * @return 返回一个long类型的值，可能表示操作结果或新创建空间的ID
     */
    long addSpace(SpaceAddRequest spaceAddRequest, User loginUser);

    /**
     * 获取空间包装类
     *
     * @param space
     * @param request
     * @return
     */
    SpaceVO getSpaceVO(Space space, HttpServletRequest request);

    /**
     * 分页获取空间封装
     *
     * @param spacePage
     * @param request
     * @return
     */
    Page<SpaceVO> getSpaceVOPage(Page<Space> spacePage, HttpServletRequest request);

    /**
     * 获取查询对象
     *
     * @param spaceQueryRequest 空间查询请求
     * @return
     */
    QueryWrapper<Space> getQueryWrapper(SpaceQueryRequest spaceQueryRequest);

    /**
     * 校验编辑权限
     *
     * @param loginUser
     * @param space
     */
    void checkSpaceAuth(User loginUser, Space space);

}
