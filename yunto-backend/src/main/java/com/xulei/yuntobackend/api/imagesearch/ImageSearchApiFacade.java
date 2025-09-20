package com.xulei.yuntobackend.api.imagesearch;

import com.xulei.yuntobackend.api.imagesearch.model.ImageSearchResult;
import com.xulei.yuntobackend.api.imagesearch.sub.GetImageFirstUrlApi;
import com.xulei.yuntobackend.api.imagesearch.sub.GetImageListApi;
import com.xulei.yuntobackend.api.imagesearch.sub.GetImagePageUrlApi;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class ImageSearchApiFacade {

    /**
     * 搜索图片
     *
     * @param imageUrl
     * @return
     */
    public static List<ImageSearchResult> searchImage(String imageUrl) {
        String imagePageUrl = GetImagePageUrlApi.getImagePageUrl(imageUrl);
        String imageFirstUrl = GetImageFirstUrlApi.getImageFirstUrl(imagePageUrl);
        List<ImageSearchResult> imageList = GetImageListApi.getImageList(imageFirstUrl);
        return imageList;
    }

    public static void main(String[] args) {
        // 测试以图搜图功能
        String imageUrl = "https://pic.code-nav.cn/user_avatar/1842456906084179970/thumbnail/rGEcrU58D5RHMJsL.jpg";
        List<ImageSearchResult> resultList = searchImage(imageUrl);
        System.out.println("结果列表" + resultList);
    }
}
