package com.yuyuding.tukubackend.api.imagesearch.sub;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpStatus;
import cn.hutool.json.JSONUtil;
import com.yuyuding.tukubackend.api.imagesearch.model.ImageSearchResult;
import com.yuyuding.tukubackend.exception.BusinessException;
import com.yuyuding.tukubackend.exception.ErrorCode;
import io.lettuce.core.ScriptOutputType;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * 获取以图搜图页面地址（step 1）
 */
@Slf4j
public class GetImagePageUrlApi {

    /**
     * 获取以图搜图页面地址
     *
     * @param imageUrl
     * @return
     */
    public static String getImagePageUrl(String imageUrl) {
        //image
        //tn
        //from
        //image_source
        //sdkParams

        // 1. 准备请求参数
        Map<String, Object> formData = new HashMap<>();
        formData.put("image", imageUrl);
        formData.put("tn", "pc");
        formData.put("from", "pc");
        formData.put("image_source", "PC_UPLOAD_URL");
        // 获取当前时间戳
        long uptime = System.currentTimeMillis();
        // 请求地址
        String url = "https://graph.baidu.com/upload?uptime=" + uptime;
        String acsToken = "CUhRWalaHJmm+iZlyxHuBJlJNmKtnStfTm2VnI2g1M40UHhkL7nhVavVTBheIAllu0VzqjkHvnMDX8JmyyVVpjPS1Wv8E9EblfBPGrUrtmwzAveXp1iOT9Uq9i44/21OZVmskgo5w9U4uIujeng93wIonYtzxDB46Rh9KLRLqoh9FX4IVs8himiNqnKNARgBskzC8LyKQKvKZTGmeU9P8xC4/iic64CYAzExx3pMAOPMDdl68t3ufxLVK5vje7eF18rqRozGzyGW7+u0vhhmt3tfbm0NCu6iUhCP83Z+D22naUioSnbqbyxjH/ssa7GqfExEV3PjIsei6DYwI5dDDb0QeovcEvhwtK6iqVylHMbDIsmjCyVbCHSItnjITQWIJAS9WD9y2gxM78e6F1zKOvpMrsa7SZ7lFGM4Jg6IrrVmkllRFTEW9sNnRgR1/S8k";
        try {
            // 2. 发送请求
            HttpResponse httpResponse = HttpRequest.post(url)
                    .form(formData)
                    .header("Asc-Token", acsToken)
                    .timeout(5000)
                    .execute();
            if (httpResponse.getStatus() != HttpStatus.HTTP_OK) {
                throw new BusinessException(ErrorCode.OPERATION_ERROR, "调用接口失败");
            }
            // 解析响应
            String body = httpResponse.body();
            Map<String, Object> result = JSONUtil.toBean(body, Map.class);

            // 3. 处理响应结果
            Map<String, Object> data = (Map<String, Object>) result.get("data");
            // 对 URL 进行解码
            String rawUrl = (String) data.get("url");
            String searchResultUrl = URLUtil.decode(rawUrl, StandardCharsets.UTF_8);
            // 如果 URL 为空
            if (StrUtil.isBlank(searchResultUrl)) {
                throw new BusinessException(ErrorCode.OPERATION_ERROR, "未返回有效的结果地址");
            }
            return searchResultUrl;
        } catch (Exception e) {
            log.error("调用百度以图搜图接口失败", e);
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "搜索失败");
        }
    }

    public static void main(String[] args) {
        // 调试以图搜图功能
        String imageUrl = "https://www.codefather.cn/logo.png";
        String searchResultUrl = getImagePageUrl(imageUrl);
        System.out.println("搜索成功，结果url:" + searchResultUrl);
    }
}
