package com.example.controller;

import com.example.entity.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


@Api(value = "文件上传，下载相关功能")
@RestController
@RequestMapping("upload")
public class UploadController {
    // 设置固定的日期格式
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    // 将 yml 中的自定义配置注入到这里
    private String filePath="D://Pictures/img/";
    // 日志打印
    private Logger log = LoggerFactory.getLogger("FileController");

    // 文件上传 （可以多文件上传）
    @PostMapping("/upload")
    public Result fileUploads(HttpServletRequest request,@RequestParam("file") MultipartFile file) throws IOException {
        // 得到格式化后的日期
        String format = sdf.format(new Date());
        // 获取上传的文件名称
        String fileName = file.getOriginalFilename();
        // 时间 和 日期拼接
        String newFileName = format + "_" + fileName;
        // 得到文件保存的位置以及新文件名
        File dest = new File(filePath + newFileName);
        try {
            // 上传的文件被保存了
            file.transferTo(dest);
            // 打印日志
            log.info("上传成功，当前上传的文件保存在 {}",filePath + newFileName);
            // 自定义返回的统一的 JSON 格式的数据，可以直接返回这个字符串也是可以的。
            return Result.succ("上传成功");
        } catch (IOException e) {
            log.error(e.toString());
        }
        // 待完成 —— 文件类型校验工作
        return Result.fail("上传错误");
    }
}
