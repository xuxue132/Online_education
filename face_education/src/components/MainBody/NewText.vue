<template>
    <div class="main_body">
        <HeadTitle></HeadTitle>
        <div class="head_picture">
        </div>
        <div class="content row">
            <div class="sub_left">
                <h2 class="title_left flag1">新闻公告</h2>
                <ul class="flag2 sub_menu">
                    <li>
                        <a  title="新闻列表" href="/NewsPage">新闻列表</a>
                    </li>
                    
                    <li>
                        <a  title="公告列表" href="/NoticePage">公告列表</a>
                    </li>
                
                </ul>
            </div>
            <div class="sub_right">
                <div class="bread fs14">
                    <a href="/HomePage">首页</a>
                    &nbsp;-&nbsp;
                    <a href="/NoticePage" v-if="this.$route.query.types === 1">公告列表</a>
                    <a href="/NewsPage" v-if="this.$route.query.types === 0">新闻列表</a>
                    &nbsp;-&nbsp;
                    <span>正文</span>
                </div>
                <div class="page_content">
                    <div class="page_title">
                        <h3 class="fs24">{{NewNotice.title}}</h3>
                    </div>
                </div>
                <p class="newsDetailOneP">
                    作者：{{NewNotice.author}}<i></i>
                    来源：{{NewNotice.sources}}<i></i>
                    日期：{{NewNotice.dates}}<i></i>
                    点击：{{NewNotice.hits}}<i></i>次
                </p>
    
                <div style="margin-top: 50px" >
                    <div class="lists" >
                        <span  class="ql-editor" v-html="NewNotice.texts"></span>
                    </div>
                </div>
                
                <div class="bottoms"></div>
            </div>
        </div>
    
    
    </div>
</template>

<script>
    
    import HeadTitle from "@/components/MainBody/HeadTitle";
    export default {
        name: "NewText",
        components: {HeadTitle},
        data() {
            return {
                fileList:[],
                    NewNotice:{
                        id: 0,
                        title: '',
                        author: '',
                        sources: '',
                        dates: "",
                        hits: '',
                        texts: '',
                        images: '',
                        filename: '',
                        fileurl: '',
                        deletes: '',
                        types: '',
                    },
                // 记录浏览历史
                recordBrowseHistory() {
                    // 只有登录用户才记录浏览历史
                    if (this.$store.state.Authorization) {
                        this.$axios.post('public/RecordBrowseHistory', {
                            token: this.$store.state.Authorization,
                            newsId: parseInt(this.$route.query.id)
                        }, {
                            headers: {'Authorization': this.$store.state.Authorization}
                        }).then(resp => {
                            // 记录成功，不需要处理响应
                        }).catch(resp => {
                            // 记录失败也不影响主业务流程
                            console.log('记录浏览历史失败');
                        })
                    }
                }
                }
            },

            created(){
                this.NewNotices();
                this.recordBrowseHistory();
            },
        
            methods: {
                //格式化时间
                formatDate(date) {
                    if (typeof date === 'string'){
                        return date
                    }

                    let year = date.getFullYear();
                    let month = date.getMonth()+1;
                    let day = date.getDate();
                    if (month < 10) {
                        month = "0" + month;
                    }
                    if (day < 10) {
                        day = "0" + day;
                    }
                    return year + "-" + month + "-" + day;
                },
                NewNotices() {
                    
                    this.$axios.post('public/TheNewNotice', {
                        id: this.$route.query.id,
                        types: this.$route.query.types
                    }, {
                        headers: {'Authorization': this.$store.state.Authorization}
                    }).then(resp => {
                        if (resp.status === 200) {
                            this.NewNotice = resp.data.data
                            this.NewNotice.dates = this.formatDate(new Date(this.NewNotice.dates))

                            this.$axios.post('public/TextPicture', {
                                outId: this.NewNotice.id,
                                types: 1
                            }, {
                                headers: {'Authorization': this.$store.state.Authorization}
                            }).then(resp => {
                                if (resp.status === 200) {
                                    this.fileList = resp.data.data
                                }
                            }).catch(resp => {
                            })
                        }
                    }).catch(resp => {
                    })
                }
            }
            
      
    }
</script>

<style scoped>
    @import '../editor.css';
    .main_body{
        margin: 0;
        padding: 0;
        height: 100%;
        width: 100%;
    }
    .head_picture{
        height: 250px;
        width: 100%;
        background: url("http://rcqmzsxxw.com/rchema/xuexiwang/img/public/head_bg.png");
        /*background: url("https://api.ixiaowai.cn/gqapi/gqapi.php");*/
        background-size: cover;
    }
    .sub_left {
        width: 230px;
        float: left;
        position: relative;
        z-index: 3;
        margin-top: -80px;
        background: url("https://www.cqut.edu.cn/images/sub_left_bg.png") top center no-repeat;
        padding: 0 0 65px;
        background-size: 100% auto;
    }
    .title_left {
        line-height: 50px;
        padding: 15px 10px 15px 40px;
        color: #fff;
        position: relative;
        font-weight: normal;
    }
    .ql-align-center{
        text-align: center;
    }
    .flag1 {
        font-size: 30px;
    }
    .sub_menu {
        margin: 0 0 0 20px;
        background: #fdf4e1;
        height: 199px;
    }
    .content {
        position: relative;
        z-index: 2;
    }
    
    .row {
        width: 1500px;
        margin: 0 auto;
    }
    .flag2 {
        font-size: 18px;
    }
    .sub_menu > li > a {
        display: block;
        line-height: 36px;
        color: #333;
        padding: 17px 0 16px 30px;
        position: relative;
        border: 1px solid #ededed;
        border-top: 0;
    }
    .sub_right {
        margin: 0 0 0 230px;
        background: #FFFFFF;
    }
    .page_content {
        padding: 0 0 0 40px;
        clear: right;
        background: #fff;
    }
    .page_title {
        padding: 0 0 0;
        overflow: hidden;
    }
    .page_title h3 {
        padding: 25px 0;
        color: #333;
        text-align: center;
    }
    .fs24 {
        font-size: 27px;
    }
    
    .lists{
        margin-left: 20px;
        font-size: 20px;
        line-height: 29px;
    }

    /*.time {*/
    /*    color: #999;*/
    /*    padding: 0 0 0 27px;*/
    /*    background: url("https://www.cqut.edu.cn/images/sub07_1_time2.png") left center no-repeat;*/
    /*    line-height: 20px;*/
    /*    font-family: arial;*/
    /*}*/
    /*.title {*/
    /*    display: block;*/
    /*    overflow: hidden;*/
    /*    text-overflow: ellipsis;*/
    /*    margin: 6px 0 0;*/
    /*    height: 26px;*/
    /*    line-height: 26px;*/
    /*    white-space: nowrap;*/
    /*    color: #333;*/
    /*}*/
    

    /*.fs16 {*/
    /*    font-size: 16px;*/
    /*}*/
    
    /*.info{*/
    /*    display: block;*/
    /*    margin: 0 0 30px;*/
    /*    padding: 13px 15px;*/
    /*    border: 1px solid #f2f2f2;*/
    /*    border-left: 5px solid #e2e2e2;*/
    /*}*/
    .bread {
        color: #a6a6a6;
        line-height: 40px;
        text-align: right;
        padding: 0 20px;
        background: #f7f7f7;
        margin-left: 20px;
    }
    .fs14 {
        font-size: 14px;
    }
    .chs{
        background: #f0ad4e;
        color: #fff;
        border-color: #F0AD4E;
    }
    .images{
        margin: 40px auto;
        height: 320px;
        width: 600px
    }
    .pic{
        height:100%;
        max-width: 100%;
        margin: auto
    }
    .newsDetailOneP {
        font-size: 14px;
        color: #969696;
        padding-bottom: 17px;
        border-bottom: 1px solid #d5d9dc;
        text-align: center;
    }
    .bottoms{
        height: 20px;
    }
</style>