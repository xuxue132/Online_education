package com.example.community_education.Controller.File;

import com.example.community_education.Dao.UserMapper;
import com.example.community_education.Service.impl.UserServiceImpl;
import com.qiniu.common.QiniuException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/public")
public class files {

    @Resource
    QiNiuSupport qiNiuSupport;

    @Resource
    UserServiceImpl userService;


        /**
         * 根据文件名删除

         */
        @RequestMapping("delete")
        public String delete(@RequestBody Map<String,String> data) throws QiniuException {
            return "删除成功";
        }

        /**
         * 直接传输文件 到七牛

         */
        @RequestMapping("/upload")
        public String upload(@RequestParam String telephone, @RequestParam MultipartFile file) throws Exception {
            userService.headURl(qiNiuSupport.uploadFile(telephone,file),telephone);
            return "提交成功";

        }

        /**
         * 七牛云文件下载
         *
         *
         */
        @RequestMapping("/file/{filename}")
        public String download(@PathVariable("filename") String filename) {
            if (filename.isEmpty()) {
                return "null";
            }
            try {
                String privateFile = qiNiuSupport.getPublicFile(filename);
                return privateFile;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }



}
