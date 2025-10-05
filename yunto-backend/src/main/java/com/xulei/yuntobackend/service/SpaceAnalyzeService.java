package com.xulei.yuntobackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xulei.yuntobackend.model.VO.space.analyze.*;
import com.xulei.yuntobackend.model.dto.space.analyze.*;
import com.xulei.yuntobackend.model.entity.Space;
import com.xulei.yuntobackend.model.entity.User;

import java.util.List;

/**
 * 空间分析服务接口
 * 该接口继承自IService<Space>，提供各种空间数据分析功能
 *
 * @author xl
 * @createDate 2025-09-14 20:00:36
 */
public interface SpaceAnalyzeService extends IService<Space> {

    /**
     * 获取空间使用情况分析
     * @param spaceUsageAnalyzeRequest 空间使用分析请求参数
     * @param loginUser 当前登录用户信息
     * @return SpaceUsageAnalyzeResponse 空间使用分析结果
     */
    SpaceUsageAnalyzeResponse getSpaceUsageAnalyze(SpaceUsageAnalyzeRequest spaceUsageAnalyzeRequest, User loginUser);

    /**
     * 获取空间类别分析
     * @param spaceCategoryAnalyzeRequest 空间类别分析请求参数
     * @param loginUser 当前登录用户信息
     * @return List<SpaceCategoryAnalyzeResponse> 空间类别分析结果列表
     */
    List<SpaceCategoryAnalyzeResponse> getSpaceCategoryAnalyze(SpaceCategoryAnalyzeRequest spaceCategoryAnalyzeRequest, User loginUser);

    /**
     * 获取空间标签分析
     * @param spaceTagAnalyzeRequest 空间标签分析请求参数
     * @param loginUser 当前登录用户信息
     * @return List<SpaceTagAnalyzeResponse> 空间标签分析结果列表
     */
    List<SpaceTagAnalyzeResponse> getSpaceTagAnalyze(SpaceTagAnalyzeRequest spaceTagAnalyzeRequest, User loginUser);

    /**
     * 获取空间大小分析
     * @param spaceSizeAnalyzeRequest 空间大小分析请求参数
     * @param loginUser 当前登录用户信息
     * @return List<SpaceSizeAnalyzeResponse> 空间大小分析结果列表
     */
    List<SpaceSizeAnalyzeResponse> getSpaceSizeAnalyze(SpaceSizeAnalyzeRequest spaceSizeAnalyzeRequest, User loginUser);

    /**
     * 获取空间用户分析
     * @param spaceUserAnalyzeRequest 空间用户分析请求参数
     * @param loginUser 当前登录用户信息
     * @return List<SpaceUserAnalyzeResponse> 空间用户分析结果列表
     */
    List<SpaceUserAnalyzeResponse> getSpaceUserAnalyze(SpaceUserAnalyzeRequest spaceUserAnalyzeRequest, User loginUser);

    /**
     * 获取空间排名分析
     * @param spaceRankAnalyzeRequest 空间排名分析请求参数
     * @param loginUser 当前登录用户信息
     * @return List<Space> 空间排名分析结果列表
     */
    List<Space> getSpaceRankAnalyze(SpaceRankAnalyzeRequest spaceRankAnalyzeRequest, User loginUser);
}
