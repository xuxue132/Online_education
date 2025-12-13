package com.example.community_education.Controller.publicController;

import com.example.community_education.Service.impl.*;
import com.example.community_education.Tool.Result;
import com.example.community_education.Tool.Token.TokenUtil;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

@RestController
@RequestMapping("/public")
public class PublicController {
    @Resource
    UserServiceImpl userServiceimpl;
    @Resource
    NewNoticeImpl newNoticeimpl;
    @Resource
    LibraryImpl libraryimpl;
    @Resource
    BookContentImpl bookContentimpl;
    @Resource
    ReadClockImpl readClockimpl;
    @Resource
    CharacterProjectImpl characterProjectimpl;
    @Resource
    TestPaperImpl testPaperimpl;
    @Resource
    TestQuestionsImpl testQuestionsimpl;
    @Resource
    PaperTitleImpl paperTitleimpl;
    @Resource
    PaperAnswerImpl paperAnswerimpl;
    @Resource
    CommodityImpl commodityimpl;
    @Resource
    OrderFormImpl orderFormimpl;
    @Resource
    HonorImpl honorimpl;
    @Resource
    FileImpl fileimpl;
    @Resource
    FileInformationImpl fileInformationimpl;
    @Resource
    TextPictureImpl textPictureimpl;

    /**
     * 用户信息
     *
     * @param map*/
    @RequestMapping(value = "/MyInformation",method = RequestMethod.POST)
    public Result MyInformation(@RequestBody Map<String, Object> map) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return userServiceimpl.UserInformation(map);
    }


    /**
     * 用户信息修改
     *
     * @param map*/
    @RequestMapping(value = "/UpdateMyInformation",method = RequestMethod.POST)
    public Result UpdateMyInformation(@RequestBody Map<String, Object> map) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return userServiceimpl.updateUserInformation(map);
    }

    /**
     * 用户手机修改
     *
     * @param map*/
    @RequestMapping(value = "/ChangePhone",method = RequestMethod.POST)
    public Result ChangePhone(@RequestBody Map<String, Object> map) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return userServiceimpl.updateUserPhone(map);
    }

    /**
     * 用户邮箱修改
     *
     * @param map*/
    @RequestMapping(value = "/ChangeEmail",method = RequestMethod.POST)
    public Result ChangeEmail(@RequestBody Map<String, Object> map) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return userServiceimpl.updateUserEmail(map);
    }

    /**
     * 用户密码修改
     *
     * @param map*/
    @RequestMapping(value = "/ChangePassword",method = RequestMethod.POST)
    public Result ChangePassword(@RequestBody Map<String, Object> map) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return userServiceimpl.updateUserPassword(map);
    }

    /**
     * 新闻公告数量
     *
     * @param map*/
    @RequestMapping(value = "/NewNoticeSum",method = RequestMethod.POST)
    public Result NewNoticeSum(@RequestBody Map<String, Object> map) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return newNoticeimpl.ToTalData(map);
    }

    /**
     * 所有新闻公告
     *
     * @param map*/
    @RequestMapping(value = "/NewNotice",method = RequestMethod.POST)
    public Result NewNotices(@RequestBody Map<String, Object> map) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return newNoticeimpl.AllData(map);
    }
    /**
     * 所有新闻公告plus
     *
     * @param map*/
    @RequestMapping(value = "/NewNotices",method = RequestMethod.POST)
    public Result NewNotice(@RequestBody Map<String, Object> map) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return newNoticeimpl.AllDatas(map);
    }

    /**
     * 单个新闻公告
     *
     * @param map*/
    @RequestMapping(value = "/TheNewNotice",method = RequestMethod.POST)
    public Result TheNewNotice(@RequestBody Map<String, Object> map) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return newNoticeimpl.TheData(map);
    }

    /**
     * 所有书籍
     *
     * @param map*/
    @RequestMapping(value = "/AllBook",method = RequestMethod.POST)
    public Result AllBook(@RequestBody Map<String, Object> map) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return libraryimpl.Books(map);
    }
    /**
     * 所有书籍目录
     *
     * @param map*/
    @RequestMapping(value = "/AllBookContent",method = RequestMethod.POST)
    public Result AllBookContent(@RequestBody Map<String, Object> map) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return bookContentimpl.AllBookContent(map);
    }


    /**
     * 书籍数量
     *
     * @param map*/
    @RequestMapping(value = "/AllBookSum",method = RequestMethod.POST)
    public Result AllBookSum(@RequestBody Map<String, Object> map) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return libraryimpl.ToTalData(map);
    }
    /**
     * 书籍目录数量
     *
     * @param map*/
    @RequestMapping(value = "/AllBookContentSum",method = RequestMethod.POST)
    public Result AllBookContentSum(@RequestBody Map<String, Object> map) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return bookContentimpl.AllBookContentSum(map);
    }
    /**
     * 书籍信息
     *
     * @param map*/
    @RequestMapping(value = "/OneBook",method = RequestMethod.POST)
    public Result OneBooks(@RequestBody Map<String, Object> map) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return libraryimpl.TheBook(map);
    }


    /**
     * 书籍目录信息
     *
     * @param map*/
    @RequestMapping(value = "/TheBook",method = RequestMethod.POST)
    public Result TheBook(@RequestBody Map<String, Object> map){
        return bookContentimpl.OneBook(map);
    }
    /**
     * 书籍内容信息
     *
     * @param map*/
    @RequestMapping(value = "/GetContent",method = RequestMethod.POST)
    public Result GetContent(@RequestBody Map<String, Object> map) {
        return bookContentimpl.BookText(map);
    }

    /**
     * 阅读打卡
     *
     * @param map*/
    @RequestMapping(value = "/TextClock",method = RequestMethod.POST)
    public Result TextClock(@RequestBody Map<String, Object> map) {
        map.put("telephone", TokenUtil.getUsername((String) map.get("token")));
        return readClockimpl.Clock(map);
    }
    /**
     * 查询阅读打卡
     *
     * @param map*/
    @RequestMapping(value = "/SelectClock",method = RequestMethod.POST)
    public Result SelectClock(@RequestBody Map<String, Object> map) {
        map.put("telephone", TokenUtil.getUsername((String) map.get("token")));
        return readClockimpl.ClockSelect(map);
    }

    /**
     * 特色项目
     *
     * @param map*/
    @RequestMapping(value = "/CharacterProject",method = RequestMethod.POST)
    public Result CharacterProject(@RequestBody Map<String, Object> map) {
        return characterProjectimpl.selectAll(map);
    }
    /**
     * 特色项目数量
     *
     * @param map*/
    @RequestMapping(value = "/CharacterProjectNumber",method = RequestMethod.POST)
    public Result CharacterProjectNumber(@RequestBody Map<String, Object> map) {
        return characterProjectimpl.Number();
    }
    /**
     * 具体特色项目
     *
     * @param map*/
    @RequestMapping(value = "/AnCharacterProject",method = RequestMethod.POST)
    public Result AnCharacterProject(@RequestBody Map<String, Object> map) {
        return characterProjectimpl.TheProject(map);
    }
    /**
     * 查找所有试卷
     *
     * @param map*/
    @RequestMapping(value = "/AllPaper",method = RequestMethod.POST)
    public Result AllPapers(@RequestBody Map<String, Object> map) {
        return testPaperimpl.Papers(map);
    }

    /**
     * 试卷数量
     *
     * */
    @RequestMapping(value = "/PaperSum",method = RequestMethod.POST)
    public Result PaperSum() {
        return testPaperimpl.Number();
    }


    /**
     * 查找试题
     *
     * @param map*/
    @RequestMapping(value = "/PaperTitle",method = RequestMethod.POST)
    public Result PaperTitle(@RequestBody Map<String, Object> map) {
        return paperTitleimpl.Paper(map);
    }
    /**
     * 查找试题选项
     *
     * @param map*/
    @RequestMapping(value = "/PaperOptions",method = RequestMethod.POST)
    public Result PaperOptions(@RequestBody Map<String, Object> map) {
        return testQuestionsimpl.Question(map);
    }
    /**
     * 根据题号查找试题选项
     *
     * @param map*/
    @RequestMapping(value = "/PaperOptionsPlus",method = RequestMethod.POST)
    public Result PaperOptionsPlus(@RequestBody Map<String, Object> map) {
        return testQuestionsimpl.PaperOptionsPlus(map);
    }

    /**
     * 查找用户试题回答
     *
     * @param map*/
    @RequestMapping(value = "/PaperAnswer",method = RequestMethod.POST)
    public Result PaperAnswer(@RequestBody Map<String, Object> map) {
        return paperAnswerimpl.Answer(map);
    }
    /**
     * 用户试题回答
     *
     * @param map*/
    @RequestMapping(value = "/InsertAnswer",method = RequestMethod.POST)
    public Result InsertAnswer(@RequestBody Map<String, Object> map) {
        return paperAnswerimpl.AnswerInsert(map);
    }

    /**
     * 用户试题回答积分
     *
     * @param map*/
    @RequestMapping(value = "/AddAnswer",method = RequestMethod.POST)
    public Result AddAnswer(@RequestBody Map<String, Object> map) {
        return paperAnswerimpl.AnswerSorce(map);
    }


    /**
     * 商品
     *
     * @param map*/
    @RequestMapping(value = "/Goods",method = RequestMethod.POST)
    public Result Goods(@RequestBody Map<String, Object> map) {
        return commodityimpl.GetGoods(map);
    }
    /**
     * 单个商品
     *
     * @param map*/
    @RequestMapping(value = "/AnGoods",method = RequestMethod.POST)
    public Result AnGoods(@RequestBody Map<String, Object> map) {
        return commodityimpl.AnGoods(map);
    }

    /**
     * 商品数量
     *
     * @param map*/
    @RequestMapping(value = "/GoodsNumber",method = RequestMethod.POST)
    public Result GoodsNumber(@RequestBody Map<String, Object> map) {
        return commodityimpl.GoodsNumber(map);
    }

    /**
     * 商品兑换
     *
     * @param map*/
    @RequestMapping(value = "/GoodsAdd",method = RequestMethod.POST)
    public Result GoodsAdd(@RequestBody Map<String, Object> map) {
        return orderFormimpl.GoodsAdd(map);
    }

    /**
     * 查看已兑换的商品
     *
     * @param map*/
    @RequestMapping(value = "/MyGoods",method = RequestMethod.POST)
    public Result MyGoods(@RequestBody Map<String, Object> map) {
        return orderFormimpl.MyGoods(map);
    }

    /**
     * 荣誉数量
     *
     * @param map*/
    @RequestMapping(value = "/AllHonorSum",method = RequestMethod.POST)
    public Result AllHonorSum(@RequestBody Map<String, Object> map) {
        return honorimpl.AllHonorSum(map);
    }

    /**
     * 全部荣誉
     *
     * @param map*/
    @RequestMapping(value = "/AllHonor",method = RequestMethod.POST)
    public Result AllHonor(@RequestBody Map<String, Object> map) {
        return honorimpl.AllHonor(map);
    }

    /**
     * 单个荣誉
     *
     * @param map*/
    @RequestMapping(value = "/AnHonor",method = RequestMethod.POST)
    public Result AnHonor(@RequestBody Map<String, Object> map) {
        return honorimpl.AnHonor(map);
    }

    /**
     * 所有文件信息
     *
     * @param map*/
    @RequestMapping(value = "/AllFileInformation",method = RequestMethod.POST)
    public Result AllFileInformation(@RequestBody Map<String, Object> map) {
        return fileInformationimpl.AllFileInformation(map);
    }
    /**
     * 单个文件信息
     *
     * @param map*/
    @RequestMapping(value = "/AnFileInformation",method = RequestMethod.POST)
    public Result AnFileInformation(@RequestBody Map<String, Object> map) {
        return fileInformationimpl.AnFileInformation(map);
    }
    /**
     * 文件信息数量
     *
     * @param map*/
    @RequestMapping(value = "/SumFileInformation",method = RequestMethod.POST)
    public Result SumFileInformation(@RequestBody Map<String, Object> map) {
        return fileInformationimpl.SumFileInformation(map);
    }
    /**
     * 文件数量
     *
     * @param map*/
    @RequestMapping(value = "/SumFile",method = RequestMethod.POST)
    public Result SumFile(@RequestBody Map<String, Object> map) {
        return fileimpl.SumFile(map);
    }
    /**
     * 文件链接
     *
     * @param map*/
    @RequestMapping(value = "/TheFile",method = RequestMethod.POST)
    public Result TheFile(@RequestBody Map<String, Object> map) {
        return fileimpl.TheFile(map);
    }
    /**
     * 所有文件
     *
     * @param map*/
    @RequestMapping(value = "/AllFile",method = RequestMethod.POST)
    public Result AllFile(@RequestBody Map<String, Object> map) {
        return fileimpl.AllFile(map);
    }
    /**
     * 正文图片
     *
     * @param map*/
    @RequestMapping(value = "/TextPicture",method = RequestMethod.POST)
    public Result TextPicture(@RequestBody Map<String, Object> map) {
        return textPictureimpl.TextPicture(map);
    }

    /**
     * 新闻公告点击增加
     *
     * @param map*/
    @RequestMapping(value = "/NewNoticeHit",method = RequestMethod.POST)
    public void NewNoticeHit(@RequestBody Map<String, Object> map) {
        newNoticeimpl.NewNoticeHit(map);
    }




}
