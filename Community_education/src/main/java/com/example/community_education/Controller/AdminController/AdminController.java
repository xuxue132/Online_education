package com.example.community_education.Controller.AdminController;

import com.example.community_education.Controller.File.Chines2PingUtils;
import com.example.community_education.Controller.File.QiNiuSupport;
import com.example.community_education.Service.impl.*;
import com.example.community_education.Tool.Result;
import com.example.community_education.Tool.ResultTool;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.Array;
import java.sql.Timestamp;
import java.util.*;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Resource
    UserServiceImpl userServiceimpl;
    @Resource
    NewNoticeImpl newNoticeimpl;
    @Resource
    QiNiuSupport qiNiuSupport;
    @Resource
    TextPictureImpl textPictureimpl;
    @Resource
    CharacterProjectImpl characterProjectimpl;
    @Resource
    PaperTitleImpl paperTitleimpl;
    @Resource
    TestQuestionsImpl testQuestionsimpl;
    @Resource
    TestPaperImpl testPaperimpl;
    @Resource
    CommodityImpl commodityimpl;
    @Resource
    HonorImpl honorimpl;
    @Resource
    FileInformationImpl fileInformationimpl;
    @Resource
    FileImpl fileimpl;
    @Resource
    LibraryImpl libraryimpl;
    @Resource
    BookContentImpl bookContentimpl;
    @Resource
    OrderFormImpl orderFormimpl;

    /**
     * 所有用户信息
     *
     * @param map*/
    @RequestMapping(value = "/AllInformation",method = RequestMethod.POST)
    public Result<? extends Object> AllInformation(@RequestBody Map<String, Object> map) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return userServiceimpl.AllInformation(map);
    }
    /**
     * 封禁用户
     *
     * @param map*/
    @RequestMapping(value = "/DeleteUser",method = RequestMethod.POST)
    public Result<? extends Object> DeleteUser(@RequestBody Map<String, Object> map) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return userServiceimpl.DeleteUser(map);
    }
    /**
     * 封禁用户
     *
     * @param map*/
    @RequestMapping(value = "/ChangePasswordPlus",method = RequestMethod.POST)
    public Result ChangePasswordPlus(@RequestBody Map<String, Object> map) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return userServiceimpl.ChangePasswordPlus(map);
    }
    /**
     * 解封用户
     *
     * @param map*/
    @RequestMapping(value = "/UnDeleteUser",method = RequestMethod.POST)
    public Result<? extends Object> UnDeleteUser(@RequestBody Map<String, Object> map) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return userServiceimpl.UnDeleteUser(map);
    }
    /**
     * 查找用户
     *
     * @param map*/
    @RequestMapping(value = "/selectUser",method = RequestMethod.POST)
    public Result<? extends Object> selectUser(@RequestBody Map<String, Object> map) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return userServiceimpl.selectUser(map);
    }
    /**
     * 用户数量
     *
     * @param map*/
    @RequestMapping(value = "/UserSum",method = RequestMethod.POST)
    public Result<? extends Object> UserSum(@RequestBody Map<String, Object> map) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return userServiceimpl.UserSum(map);
    }


    /**
     * 查找新闻公告
     *
     * @param map*/
    @RequestMapping(value = "/selectNewNotice",method = RequestMethod.POST)
    public Result<? extends Object> selectNewNotice(@RequestBody Map<String, Object> map) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return newNoticeimpl.selectNewNotice(map);
    }
    /**
     * 删除新闻公告
     *
     * @param map*/
    @RequestMapping(value = "/DeleteNewNotice",method = RequestMethod.POST)
    public Result<? extends Object> DeleteNewNotice(@RequestBody Map<String, Object> map) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return newNoticeimpl.DeleteNewNotice(map);
    }

    /**
     * 删除文件 到七牛

     */
    @RequestMapping("/DeletePicture")
    public String upload(@RequestBody Map<String, Object> map) throws Exception {

        textPictureimpl.DeletePicture(map);
        return "提交成功";

    }
    /**
     * 上传图片直接传输文件 到七牛

     */
    @RequestMapping("/UpdateUpload")
    public String UpdateUpload(@RequestParam Integer id, @RequestParam MultipartFile file) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("images",qiNiuSupport.uploadFile("特色项目"+id,file));
        map.put("id",id);
        characterProjectimpl.updateImages(map);
        return "提交成功";

    }

    /**
     * 增加新闻公告

     */
    @RequestMapping("/AddNewNotice")
    public String AddNewNotice(@RequestBody Map<String, Object> map) throws Exception {
        map.put("dates",new Timestamp(new Date().getTime()));
        newNoticeimpl.Add(map);
        return "提交成功";

    }
    /**
     * 修改新闻公告

     */
    @RequestMapping("/UpdateNewNotice")
    public String UpdateNewNotice(@RequestBody Map<String, Object> map) throws Exception {
        newNoticeimpl.UpdateNewNotice(map);
        return "提交成功";

    }


    /**
     * 查找特色项目
     *
     * @param map*/
    @RequestMapping(value = "/selectCharacterProject",method = RequestMethod.POST)
    public Result<? extends Object> selectCharacterProject(@RequestBody Map<String, Object> map) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return characterProjectimpl.selectCharacterProject(map);
    }

    /**
     * 新增特色项目
     *
     * @param map*/
    @RequestMapping(value = "/AddCharacterProject",method = RequestMethod.POST)
    public String AddCharacterProject(@RequestBody Map<String, Object> map) throws Exception {
        map.put("dates",new Timestamp(new Date().getTime()));
        characterProjectimpl.AddCharacterProject(map);
        return "新增成功";
    }

    /**
     * 修改特色项目

     */
    @RequestMapping("/UpdateCharacterProject")
    public String UpdateCharacterProject(@RequestBody Map<String, Object> map) throws Exception {
        characterProjectimpl.UpdateCharacterProject(map);
        return "提交成功";

    }
    /**
     * 删除特色项目

     */
    @RequestMapping("/DeleteCharacterProject")
    public String DeleteCharacterProject(@RequestBody Map<String, Object> map) throws Exception {
        characterProjectimpl.DeleteCharacterProject(map);
        return "提交成功";

    }


    /**
     * 查找所有试题题目
     *
     * @param map*/
    @RequestMapping(value = "/AllTitle",method = RequestMethod.POST)
    public Result<? extends Object> AllTitle(@RequestBody Map<String, Object> map) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return paperTitleimpl.AllTitle(map);
    }
    /**
     * 修改试题题目
     *
     * @param map*/
    @RequestMapping(value = "/UpdateTitle",method = RequestMethod.POST)
    public Result<? extends Object> UpdateTitle(@RequestBody Map<String, Object> map) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return paperTitleimpl.UpdateTitle(map);
    }
    /**
     * 查找所有试题数量
     *
     * @param map*/
    @RequestMapping(value = "/AllTitleSum",method = RequestMethod.POST)
    public Result<? extends Object> AllTitleSum(@RequestBody Map<String, Object> map) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return paperTitleimpl.AllTitleSum(map);
    }
    /**
     * 查找所有试题选项
     *
     * @param map*/
    @RequestMapping(value = "/AllQuestion",method = RequestMethod.POST)
    public Result<? extends Object> AllQuestion(@RequestBody Map<String, Object> map) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return testQuestionsimpl.AllQuestion(map);
    }
    /**
     * 查找所有选项数量
     *
     * @param map*/
    @RequestMapping(value = "/AllQuestionSum",method = RequestMethod.POST)
    public Result<? extends Object> AllQuestionSum(@RequestBody Map<String, Object> map) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return testQuestionsimpl.AllQuestionSum(map);
    }
    /**
     * 修改选项
     *
     * @param map*/
    @RequestMapping(value = "/UpdateQuestion",method = RequestMethod.POST)
    public Result<? extends Object> UpdateQuestion(@RequestBody Map<String, Object> map) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return testQuestionsimpl.UpdateQuestion(map);
    }
    /**
     * 删除试题
     *
     * @param map*/
    @RequestMapping(value = "/DeletePaper",method = RequestMethod.POST)
    public String DeletePaper(@RequestBody Map<String, Object> map) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        testPaperimpl.Delete(map);
        testQuestionsimpl.Delete(map);
        paperTitleimpl.Delete(map);
        return "删除成功";
    }
    /**
     * 删除题目
     *
     * @param map*/
    @RequestMapping(value = "/DeleteTiTle",method = RequestMethod.POST)
    public String DeleteTiTle(@RequestBody Map<String, Object> map) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        testQuestionsimpl.DeleteTiTle(map);
        paperTitleimpl.DeleteTitle(map);
        return "删除成功";
    }
    /**
     * 修改试题
     *
     * @param map*/
    @RequestMapping(value = "/UpdatePaper",method = RequestMethod.POST)
    public String UpdatePaper(@RequestBody Map<String, Object> map) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        testPaperimpl.UpdatePaper(map);
        return "修改成功";
    }
    /**
     * 添加试题
     *
     * @param map*/
    @RequestMapping(value = "/AddPaper",method = RequestMethod.POST)
    public String AddPaper(@RequestBody Map<String, Object> map) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        testPaperimpl.AddPaper(map);
        return "修改成功";
    }
    /**
     * 添加试题题目
     *
     * @param map*/
    @RequestMapping(value = "/AddTitle",method = RequestMethod.POST)
    public String AddTitle(@RequestBody Map<String, Object> map) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        paperTitleimpl.AddTitle(map);
        return "修改成功";
    }
    /**
     * 添加试题生效
     *
     * @param map*/
    @RequestMapping(value = "/ChangeEffect",method = RequestMethod.POST)
    public String ChangeEffect(@RequestBody Map<String, Object> map) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        testPaperimpl.ChangeEffect(map);
        return "成功";
    }
    /**
     * 添加题目选项
     *
     * @param map*/
    @RequestMapping(value = "/AddOption",method = RequestMethod.POST)
    public String AddOption(@RequestBody Map<String, Object> map) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        testQuestionsimpl.AddOption(map);
        return "修改成功";
    }
    /**
     * 查找试题
     *
     * @param map*/
    @RequestMapping(value = "/selectPaper",method = RequestMethod.POST)
    public Result selectPaper(@RequestBody Map<String, Object> map) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return testPaperimpl.selectPaper(map);
    }
    /**
     * 查找题目
     *
     * @param map*/
    @RequestMapping(value = "/selectTitle",method = RequestMethod.POST)
    public Result selectTitle(@RequestBody Map<String, Object> map) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return paperTitleimpl.selectTitle(map);
    }
    /**
     * 查找选项
     *
     * @param map*/
    @RequestMapping(value = "/selectQuestions",method = RequestMethod.POST)
    public Result selectQuestions(@RequestBody Map<String, Object> map) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return testQuestionsimpl.selectQuestions(map);
    }




    /**
     * 删除商品
     *
     * @param map*/
    @RequestMapping(value = "/DeleteCommodity",method = RequestMethod.POST)
    public String DeleteCommodity(@RequestBody Map<String, Object> map) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        commodityimpl.DeleteCommodity(map);
        return "删除成功";
    }

    /**
     * 上传商品图片直接传输文件 到七牛

     */
    @RequestMapping("/CommodityUpload")
    public String CommodityUpload(@RequestParam Integer id, @RequestParam MultipartFile file) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("picture",qiNiuSupport.uploadFile("商品图片"+id,file));
        map.put("id",id);
        commodityimpl.updateImages(map);
        return "提交成功";

    }


    /**
     * 修改商品
     *
     * @param map*/
    @RequestMapping(value = "/ChangeCommodity",method = RequestMethod.POST)
    public String ChangeCommodity(@RequestBody Map<String, Object> map) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        commodityimpl.ChangeCommodity(map);
        return "成功";
    }

    /**
     * 添加商品
     *
     * @param map*/
    @RequestMapping(value = "/AddCommodity",method = RequestMethod.POST)
    public String AddCommodity(@RequestBody Map<String, Object> map) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        commodityimpl.AddCommodity(map);
        return "成功";
    }

    /**
     * 查找商品
     *
     * @param map*/
    @RequestMapping(value = "/selectCommodity",method = RequestMethod.POST)
    public Result selectCommodity(@RequestBody Map<String, Object> map) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return commodityimpl.selectCommodity(map);
    }

    /**
     * 删除荣誉
     *
     * @param map*/
    @RequestMapping(value = "/DeleteHonor",method = RequestMethod.POST)
    public String DeleteHonor(@RequestBody Map<String, Object> map) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        honorimpl.DeleteHonor(map);
        return "删除成功";
    }

    /**
     * 上传荣誉图片直接传输文件 到七牛

     */
    @RequestMapping("/HonorUpload")
    public String HonorUpload(@RequestParam Integer id, @RequestParam MultipartFile file) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("images",qiNiuSupport.uploadFile("荣誉图片"+id,file));
        map.put("id",id);
        honorimpl.HonorUpload(map);
        return "提交成功";

    }


    /**
     * 修改荣誉
     *
     * @param map*/
    @RequestMapping(value = "/ChangeHonor",method = RequestMethod.POST)
    public String ChangeHonor(@RequestBody Map<String, Object> map) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        honorimpl.ChangeHonor(map);
        return "成功";
    }

    /**
     * 添加荣誉
     *
     * @param map*/
    @RequestMapping(value = "/AddHonor",method = RequestMethod.POST)
    public String AddHonor(@RequestBody Map<String, Object> map) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        map.put("dates",new Timestamp(new Date().getTime()));
        honorimpl.AddHonor(map);
        return "成功";
    }

    /**
     * 查找荣誉
     *
     * @param map*/
    @RequestMapping(value = "/selectHonor",method = RequestMethod.POST)
    public Result selectHonor(@RequestBody Map<String, Object> map) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return honorimpl.selectHonor(map);
    }

    /**
     * 查找文件信息
     *
     * @param map*/
    @RequestMapping(value = "/selectFileInformation",method = RequestMethod.POST)
    public Result selectFileInformation(@RequestBody Map<String, Object> map) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return fileInformationimpl.selectFileInformation(map);
    }


    /**
     * 添加文件信息
     *
     * @param map*/
    @RequestMapping(value = "/AddFileInformations",method = RequestMethod.POST)
    public String AddFileInformations(@RequestBody Map<String, Object> map) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        map.put("dates",new Timestamp(new Date().getTime()));
        fileInformationimpl.AddFileInformations(map);
        return "成功";
    }
    /**
     * 修改文件信息
     *
     * @param map*/
    @RequestMapping(value = "/UpdateFileInformations",method = RequestMethod.POST)
    public String UpdateFileInformations(@RequestBody Map<String, Object> map) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        fileInformationimpl.UpdateFileInformations(map);
        return "成功";
    }
    /**
     * 修改文件信息
     *
     **/
    @RequestMapping(value = "/FileUpload",method = RequestMethod.POST)
    public String FileUpload(@RequestParam Integer id, @RequestParam MultipartFile file) throws Exception {
        HashMap map = new HashMap();
        map.put("FileName",file.getOriginalFilename());
        map.put("file_id",id);
        map.put("FileUrl",qiNiuSupport.uploadFile("附件"+id,file));
        fileimpl.AddFile(map);

        return "成功";
    }
    /**
     * 删除文件信息
     *
     **/
    @RequestMapping(value = "/DeleteFileName",method = RequestMethod.POST)
    public String DeleteFileName(@RequestBody Map<String, Object> map) throws Exception {
        fileInformationimpl.DeleteFileName(map);
        fileimpl.DeleteFileName(map);

        return "成功";
    }
    /**
     * 删除文件
     *
     **/
    @RequestMapping(value = "/DeleteFile",method = RequestMethod.POST)
    public String DeleteFile(@RequestBody Map<String, Object> map) throws Exception {
        fileimpl.DeleteFileNamePlus(map);
        return "成功";
    }
    /**
     * 查找书籍
     *
     **/
    @RequestMapping(value = "/selectLibrary",method = RequestMethod.POST)
    public Result selectLibrary(@RequestBody Map<String, Object> map) throws Exception {
        return libraryimpl.selectLibrary(map);
    }
    /**
     * 添加书籍
     *
     **/
    @RequestMapping(value = "/AddLibrary",method = RequestMethod.POST)
    public Result AddLibrary(@RequestBody Map<String, Object> map) throws Exception {
        return libraryimpl.AddLibrary(map);
    }
    /**
     * 修改书籍
     *
     **/
    @RequestMapping(value = "/UpdateLibrary",method = RequestMethod.POST)
    public Result UpdateLibrary(@RequestBody Map<String, Object> map) throws Exception {
        return libraryimpl.UpdateLibrary(map);
    }
    /**
     * 修改书籍封面
     *
     **/
    @RequestMapping(value = "/UpdateLibraryPicture",method = RequestMethod.POST)
    public Result UpdateLibraryPicture(@RequestParam Integer id, @RequestParam MultipartFile file) throws Exception {
        HashMap map = new HashMap();
        map.put("id_book",id);
        map.put("picture",qiNiuSupport.uploadFile("书籍封面"+id,file));
        return libraryimpl.UpdateLibraryPicture(map);
    }
    /**
     * 删除书籍
     *
     **/
    @RequestMapping(value = "/DeleteLibrary",method = RequestMethod.POST)
    public Result DeleteLibrary(@RequestBody Map<String, Object> map) throws Exception {
        bookContentimpl.DeleteBookContentPlus(map);
        return libraryimpl.DeleteLibrary(map);
    }
    /**
     * 删除目录
     *
     **/
    @RequestMapping(value = "/DeleteBookContent",method = RequestMethod.POST)
    public Result DeleteBookContent(@RequestBody Map<String, Object> map) throws Exception {
        return bookContentimpl.DeleteBookContent(map);
    }
    /**
     * 增加目录文章
     *
     **/
    @RequestMapping(value = "/AddBookContents",method = RequestMethod.POST)
    public Result AddBookContents(@RequestBody Map<String, Object> map) throws Exception {
        return bookContentimpl.AddBookContents(map);
    }
    /**
     * 修改目录文章
     *
     **/
    @RequestMapping(value = "/ChangeBookContents",method = RequestMethod.POST)
    public Result ChangeBookContents(@RequestBody Map<String, Object> map) throws Exception {
        return bookContentimpl.ChangeBookContents(map);
    }

    /**
     * 查询所有订单
     *
     **/
    @RequestMapping(value = "/SelectAll",method = RequestMethod.POST)
    public Result SelectAll(@RequestBody Map<String, Object> map) throws Exception {
        return orderFormimpl.SelectAll(map);
    }
    /**
     * 查询所有订单数量
     *
     **/
    @RequestMapping(value = "/OrderSum",method = RequestMethod.POST)
    public Result OrderSum(@RequestBody Map<String, Object> map) throws Exception {
        return orderFormimpl.OrderSum(map);
    }
    /**
     * 修改订单
     *
     **/
    @RequestMapping(value = "/updateStates",method = RequestMethod.POST)
    public Result updateStates(@RequestBody Map<String, Object> map) throws Exception {
        return orderFormimpl.updateStates(map);
    }
}
