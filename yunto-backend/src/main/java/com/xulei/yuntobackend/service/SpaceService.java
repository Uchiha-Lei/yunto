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

    void fillSpaceBySpaceLevel(Space space);

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
     * @param loginUser
     * @param space
     */
    void checkSpaceAuth(User loginUser, Space space);
}
