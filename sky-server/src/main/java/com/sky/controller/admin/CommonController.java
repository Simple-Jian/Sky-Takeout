package com.sky.controller.admin;

import com.sky.result.Result;
import com.sky.utils.AliOssUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * 通用Controller
 */
@RestController
@RequestMapping("/admin/common")
@Api(tags = "通用Controller类")
@Slf4j
public class CommonController {
    @Autowired
    private AliOssUtil aliOssUtil;

    @PostMapping("/upload")
    @ApiOperation("通用上传方法")
    public Result<String> upload(MultipartFile file) {
        log.info("文件上传:{}", file);
        try{
            String originalFilename = file.getOriginalFilename();  //获取原始文件名
            String extendname = originalFilename.
                    substring(originalFilename.lastIndexOf("."));//获取文件扩展名
            String fileName = UUID.randomUUID().toString() + extendname;   //使用UUID拼接名字
            String url = aliOssUtil.upload(file.getBytes(), fileName);      //使用工具类获取url
            return Result.success(url);
        }catch (Exception ex){
            log.error("文件上传失败,{}",ex);
        }
        return  null;
    }
}
