package com.xulei.yuntobackend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xulei.yuntobackend.api.aliyunai.model.CreateOutPaintingTaskResponse;
import com.xulei.yuntobackend.model.VO.PictureVO;
import com.xulei.yuntobackend.model.dto.picture.*;
import com.xulei.yuntobackend.model.entity.Picture;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xulei.yuntobackend.model.entity.User;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author xl
 * @description 针对表【picture(图片)】的数据库操作Service
 * @createDate 2025-09-09 18:12:45
 */
public interface PictureService extends IService<Picture> {

    /**
     * 上传图片
     *
     * @param inputSource          文件
     * @param pictureUploadRequest 上传请求体
     * @param loginUser            登录的用户
     * @return
     */
    PictureVO uploadPicture(Object inputSource, PictureUploadRequest pictureUploadRequest, User loginUser);

    /**
     * 批量抓取和创建图片
     *
     * @param pictureUploadByBatchRequest
     * @param loginUser
     * @return 成功创建的图片数
     */
    Integer uploadPictureByBatch(PictureUploadByBatchRequest pictureUploadByBatchRequest, User loginUser);

    void deletePicture(long pictureId, User loginUser);

    void editPicture(PictureEditRequest pictureEditRequest, User loginUser);

    /**
     * 分页查询图片
     *
     * @param pictureQueryRequest 图片查询请求
     * @return
     */
    QueryWrapper<Picture> getQueryWrapper(PictureQueryRequest pictureQueryRequest);

    /**
     * 获取图片
     *
     * @param picture
     * @param request
     * @return
     */
    PictureVO getPictureVO(Picture picture, HttpServletRequest request);

    /**
     * 分页获取图片封装
     */
    Page<PictureVO> getPictureVOPage(Page<Picture> picturePage, HttpServletRequest request);

    /**
     * 图片数据校验
     *
     * @param picture
     */
    void validPicture(Picture picture);

    /**
     * 图片审核
     *
     * @param pictureReviewRequest
     * @param loginUser
     */
    void doPictureReview(PictureReviewRequest pictureReviewRequest, User loginUser);

    /**
     * 设置审核状态
     *
     * @param picture
     * @param loginUser
     */
    void fillReviewParams(Picture picture, User loginUser);

    /**
     * 清除图片文件的方法
     *
     * @param oldPicture 需要清除的图片对象
     */
    void clearPictureFile(Picture oldPicture);

    /**
     * 检查用户对图片的访问权限
     *
     * @param loginUser 当前登录用户对象，包含用户ID、权限等信息
     * @param picture   目标图片对象，包含图片ID、所有者、隐私设置等信息
     *                  该方法用于验证当前登录用户是否有权限访问指定的图片
     *                  权限检查可能基于用户角色、图片所有者关系、图片隐私设置等因素
     */
    void checkPictureAuth(User loginUser, Picture picture);

    /**
     * 根据颜色搜索图片
     *
     * @param spaceId   空间ID，用于指定要搜索的图片空间
     * @param picColor  图片颜色，用于筛选指定颜色的图片
     * @param loginUser 当前登录用户，用于权限验证
     * @return 返回符合条件的PictureVO对象列表，包含图片的详细信息
     */
    List<PictureVO> searchPictureByColor(Long spaceId, String picColor, User loginUser);

    /**
     * 创建图片出图任务
     *
     * @param createPictureOutPaintingTaskRequest
     * @param loginUser
     * @return
     */
    CreateOutPaintingTaskResponse createPictureOutPaintingTask(CreatePictureOutPaintingTaskRequest createPictureOutPaintingTaskRequest, User loginUser);
}
