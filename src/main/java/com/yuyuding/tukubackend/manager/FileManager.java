//package com.yuyuding.tukubackend.manager;
//
//import cn.hutool.core.date.DateUtil;
//import cn.hutool.core.io.FileUtil;
//import cn.hutool.core.util.NumberUtil;
//import cn.hutool.core.util.RandomUtil;
//import cn.hutool.core.util.StrUtil;
//import cn.hutool.http.HttpResponse;
//import cn.hutool.http.HttpStatus;
//import cn.hutool.http.HttpUtil;
//import cn.hutool.http.Method;
//import com.qcloud.cos.model.PutObjectResult;
//import com.qcloud.cos.model.ciModel.persistence.ImageInfo;
//import com.yuyuding.tukubackend.config.CosClientConfig;
//import com.yuyuding.tukubackend.exception.BusinessException;
//import com.yuyuding.tukubackend.exception.ErrorCode;
//import com.yuyuding.tukubackend.exception.ThrowUtils;
//import com.yuyuding.tukubackend.model.dto.file.UploadPictureResult;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.annotation.Resource;
//import java.io.File;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.util.Arrays;
//import java.util.Date;
//import java.util.List;
//
///**
// * 文件服务
// * @deprecated 已废弃，改为使用 upload 包的模版方法优化
// */
//@Slf4j
//@Service
//@Deprecated
//public class FileManager {
//
//    @Resource
//    private CosClientConfig cosClientConfig;
//
//    @Resource
//    private CosManager cosManager;
//
//    /**
//     * 上传图片
//     *
//     * @param multipartFile
//     * @param uploadPathPrefix
//     * @return
//     */
//    public UploadPictureResult uploadPicture(MultipartFile multipartFile, String uploadPathPrefix) {
//
//        // 校验图片
//        validPicture(multipartFile);
//        // 图片上传地址
//        String uuid = RandomUtil.randomString(16);
//        String originalFilename = multipartFile.getOriginalFilename();
//        // 自己拼接文件上传路径，而不是使用原始文件名称，可以增强安全性
//        String uploadFilename = String.format("%s_%s.%s", DateUtil.formatDate(new Date()), uuid, FileUtil.getSuffix(originalFilename));
//        String uploadPath = String.format("%s/%s", uploadPathPrefix, uploadFilename);
//        // 解析结果并返回
//        File file = null;
//        try {
//            // 上传文件
//            file = File.createTempFile(uploadPath, null);
//            multipartFile.transferTo(file);
//            PutObjectResult putObjectResult = cosManager.putPictureObject(uploadPath, file);
//            // 获取图片信息对象
//            ImageInfo imageInfo = putObjectResult.getCiUploadResult().getOriginalInfo().getImageInfo();
//
//            // 计算宽高
//            int picWidth = imageInfo.getWidth();
//            int picHeight = imageInfo.getHeight();
//            double picScale = NumberUtil.round(picWidth * 1.0 / picHeight, 2).doubleValue();
//
//            // 封装返回结果
//            UploadPictureResult uploadPictureResult = new UploadPictureResult();
//            uploadPictureResult.setUrl(cosClientConfig.getHost() + "/" + uploadPath);
//            uploadPictureResult.setPicName(FileUtil.mainName(originalFilename));
//            uploadPictureResult.setPicSize(FileUtil.size(file));
//            uploadPictureResult.setPicWidth(picWidth);
//            uploadPictureResult.setPicHeight(picHeight);
//            uploadPictureResult.setPicScale(picScale);
//            uploadPictureResult.setPicFormat(imageInfo.getFormat());
//
//            // 返回可访问的地址
//            return uploadPictureResult;
//        } catch (Exception e) {
//            log.error("图片上传到对象存储失败", e);
//            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "上传失败");
//        } finally {
//            // 临时文件清理
//            this.deleteTempFile(file);
//        }
//    }
//
//    /**
//     * 校验文件
//     *
//     * @param multipartFile
//     */
//    private void validPicture(MultipartFile multipartFile) {
//        ThrowUtils.throwIf(multipartFile == null, ErrorCode.PARAMS_ERROR, "文件不能为空");
//        // 1. 校验文件大小
//        long fileSize = multipartFile.getSize();
//        final long ONE_MB = 1024 * 1024;
//        ThrowUtils.throwIf(fileSize > 2 * ONE_MB, ErrorCode.PARAMS_ERROR, "文件大小不能超过 2MB");
//        // 2. 校验文件后缀
//        String fileSuffix = FileUtil.getSuffix(multipartFile.getOriginalFilename());
//        // 允许上传的文件后缀列表（或者集合）
//        final List<String> ALLOW_FORMAT_LIST = Arrays.asList("jpeg", "jpg", "png", "gif", "bmp", "tif", "tiff");
//        ThrowUtils.throwIf(!ALLOW_FORMAT_LIST.contains(fileSuffix), ErrorCode.PARAMS_ERROR, "文件类型错误");
//    }
//
//    /**
//     * 清理临时文件
//     *
//     * @param file
//     */
//    private void deleteTempFile(File file) {
//        if (file == null) {
//            return;
//        }
//        //删除临时文件
//        boolean deleteResult = file.delete();
//        if (!deleteResult) {
//            log.error("file deleteResult error, filePath = " + file.getAbsolutePath());
//        }
//    }
//
//    // todo 新增的方法
//
//    /**
//     * 通过 url 上传图片
//     *
//     * @param fileUrl
//     * @param uploadPathPrefix
//     * @return
//     */
//    public UploadPictureResult uploadPictureByUrl(String fileUrl, String uploadPathPrefix) {
//
//        // 校验图片
//        // todo
//        validPicture(fileUrl);
//        // 图片上传地址
//        String uuid = RandomUtil.randomString(16);
////        String originalFilename = multipartFile.getOriginalFilename();
//        // 自己拼接文件上传路径，而不是使用原始文件名称，可以增强安全性
//        String uploadFilename = String.format("%s_%s.%s", DateUtil.formatDate(new Date()), uuid, FileUtil.getSuffix(originalFilename));
//        String uploadPath = String.format("%s/%s", uploadPathPrefix, uploadFilename);
//        // 解析结果并返回
//        File file = null;
//        try {
//            // 上传文件
//            file = File.createTempFile(uploadPath, null);
//            multipartFile.transferTo(file);
//            PutObjectResult putObjectResult = cosManager.putPictureObject(uploadPath, file);
//            // 获取图片信息对象
//            ImageInfo imageInfo = putObjectResult.getCiUploadResult().getOriginalInfo().getImageInfo();
//
//            // 计算宽高
//            int picWidth = imageInfo.getWidth();
//            int picHeight = imageInfo.getHeight();
//            double picScale = NumberUtil.round(picWidth * 1.0 / picHeight, 2).doubleValue();
//
//            // 封装返回结果
//            UploadPictureResult uploadPictureResult = new UploadPictureResult();
//            uploadPictureResult.setUrl(cosClientConfig.getHost() + "/" + uploadPath);
//            uploadPictureResult.setPicName(FileUtil.mainName(originalFilename));
//            uploadPictureResult.setPicSize(FileUtil.size(file));
//            uploadPictureResult.setPicWidth(picWidth);
//            uploadPictureResult.setPicHeight(picHeight);
//            uploadPictureResult.setPicScale(picScale);
//            uploadPictureResult.setPicFormat(imageInfo.getFormat());
//
//            // 返回可访问的地址
//            return uploadPictureResult;
//        } catch (Exception e) {
//            log.error("图片上传到对象存储失败", e);
//            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "上传失败");
//        } finally {
//            // 临时文件清理
//            this.deleteTempFile(file);
//        }
//    }
//
//    /**
//     * 根据 url 校验文件
//     *
//     * @param fileUrl
//     */
//    private void validPicture(String fileUrl) {
//        // 1. 校验非空
//        ThrowUtils.throwIf(fileUrl == null, ErrorCode.PARAMS_ERROR, "文件地址为空");
//
//        // 2. 校验 URL 格式
//        try {
//            new URL(fileUrl);
//        } catch (MalformedURLException e) {
//            throw new BusinessException(ErrorCode.PARAMS_ERROR, "文件地址格式不正确");
//        }
//
//        // 3. 校验 URL 的协议
//        ThrowUtils.throwIf(!fileUrl.startsWith("https://") && !fileUrl.startsWith("http://"),
//                ErrorCode.PARAMS_ERROR, "仅支持 HTTP 或 HTTPS 协议的文件地址");
//
//        // 4. 发送 HEAD 请求验证文件是否存在
//        HttpResponse httpResponse = null;
//        try {
//            httpResponse = HttpUtil.createRequest(Method.HEAD, fileUrl).execute();
//            // 未正常返回，无需执行其他判断
//            if (httpResponse.getStatus() != HttpStatus.HTTP_OK) {
//                return;
//            }
//            // 5. 文件存在，文件类型校验
//            String contentType = httpResponse.header("Content-Type");
//            // 不为空，才校验是否合法，这样校验规则相对宽松
//            if (StrUtil.isNotBlank(contentType)) {
//                // 允许的图片类型
//                final List<String> ALLOW_CONTENT_TYPES = Arrays.asList("image/jpeg", "image/jpg", "image/png", "image/webp");
//                ThrowUtils.throwIf(!ALLOW_CONTENT_TYPES.contains(contentType.toLowerCase()), ErrorCode.PARAMS_ERROR, "文件类型错误");
//            }
//            // 6. 文件存在，文件大小校验
//            String contentLengthStr = httpResponse.header("Content-Length");
//            if (StrUtil.isNotBlank(contentLengthStr)) {
//                try {
//                    long contentLength = Long.parseLong(contentLengthStr);
//                    final long ONE_MB = 1024 * 1024;
//                    ThrowUtils.throwIf(contentLength > 2 * ONE_MB, ErrorCode.PARAMS_ERROR, "文件大小不能超过 2MB");
//                } catch (NumberFormatException e) {
//                    throw new BusinessException(ErrorCode.PARAMS_ERROR, "文件大小格式异常");
//                }
//            }
//        } finally {
//            if (httpResponse != null) {
//                // 释放资源
//                httpResponse.close();
//            }
//        }
//    }
//}
