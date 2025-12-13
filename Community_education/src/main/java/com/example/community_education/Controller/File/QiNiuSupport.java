package com.example.community_education.Controller.File;


import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;


@Service
public class QiNiuSupport {

    @Resource
    private UploadManager uploadManager;

    @Resource
    private BucketManager bucketManager;

    @Resource
    private Auth auth;


    /**
     * 最大尝试次数
     */
    public static final Integer maxReTry = 3;
    /**
     * 七牛云操作成功状态码
     */
    public static final Integer successCode = 200;



    /**
     * 以文件的形式上传 自定获取文件名转换为拼音形式 存储到七牛云
     *
     * @param multipartFile
     * @return
     * @throws QiniuException
     */
    public String uploadFile(String id, MultipartFile multipartFile) throws Exception {
        File file = multipartFileToFile(id,multipartFile);
        String pinyinName = file.getName();
        Response response = this.uploadManager.put(file, pinyinName, getUploadToken());
        int retry = 0;
        while (response.needRetry() && retry < maxReTry) {
            response = this.uploadManager.put(file, pinyinName, getUploadToken());
            retry++;
        }
        delteTempFile(file);
        if (response.statusCode == successCode) {
            return new StringBuffer().append(QiNiuConfigBean.getProtocol())
                    .append(QiNiuConfigBean.getCdnProfile()).append("/").append(pinyinName).toString();
        }
        return "上传失败!";
    }


    /**
     * 删除七牛云上的相关文件
     *
     * @param key
     * @return
     * @throws QiniuException
     */
    public String delete(String key) throws QiniuException {
        Response response = bucketManager.delete(QiNiuConfigBean.getBucket(), key);
        int retry = 0;
        //判断是否需要 重试 删除 且重试次数为3
        while (response.needRetry() && retry++ < maxReTry) {
            response = bucketManager.delete(QiNiuConfigBean.getBucket(), key);
        }
        return response.statusCode == successCode ? "删除成功!" : "删除失败!";
    }


    /**
     * 获取上传凭证
     *
     * @return
     */
    private String getUploadToken() {
        return this.auth.uploadToken(QiNiuConfigBean.getBucket());
    }

    /**
     * 定义七牛云上传的相关策略
     */
    public StringMap getPutPolicy() {
        StringMap stringMap = new StringMap();
        stringMap.put("returnBody",
                "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"width\":$(imageInfo.width), \"height\":${imageInfo.height}}");
        return stringMap;
    }

    /**
     * 获取公共空间文件
     *
     * @param fileKey 要下载的文件名
     * @return
     */
    public String getPublicFile(String fileKey) throws Exception {
        String encodedFileName = URLEncoder.encode(fileKey, "utf-8").replace("+", "%20");
        String url = String
                .format("%s%s/%s", QiNiuConfigBean.getProtocol(), QiNiuConfigBean.getCdnProfile(),
                        encodedFileName);
        return url;
    }



    /**
     * MultipartFile 转file
     *
     * @param file
     * @return
     * @throws Exception
     */
    public File multipartFileToFile(String id, MultipartFile file) throws Exception {

        File toFile = null;
        if (file.equals("") || file.getSize() <= 0) {
            file = null;
        } else {
            InputStream ins = null;
            ins = file.getInputStream();
            toFile = new File(id+"-"+file.getOriginalFilename());
            inputStreamToFile(ins, toFile);
            ins.close();
        }
        return toFile;
    }

    //获取流文件
    private void inputStreamToFile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除本地临时文件
     *
     * @param file
     */
    public static void delteTempFile(File file) {
        if (file != null) {
            File del = new File(file.toURI());
            del.delete();

        }
    }

}
