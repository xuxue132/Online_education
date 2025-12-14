<template>
    <div class="main_body">
        <HeadTitle></HeadTitle>
        <div class="head_picture">
        </div>
        <div class="content row">
            <div class="sub_left">
                <h2 class="title_left flag1">下载中心</h2>
                <ul class="flag2 sub_menu">
                    <li>
                        <a  title="新闻列表" href="/NewsPage" class="chs">文件列表</a>
                    </li>
            
                </ul>
            </div>
            <div class="sub_right">
                <div class="bread fs14">
                    <a href="/FirstPage">首页</a>
                    &nbsp;-&nbsp;
                    <a href="/FilePage">文件列表</a>
                </div>
                <div class="page_content">
                    <div class="page_title">
                        <h3 class="fs24">文件列表</h3>
                    </div>
                </div>
            
                <div>
                    <div class="lists">
                        <ul v-for="FileInformation in FileInformations" >
                            <li >
                                <a class="info" @click="TurnPage(FileInformation.id)">
                                    <div class="time fs14">{{FileInformation.dates}}</div>
                                    <div class="title fs16">{{FileInformation.title}}</div>
                                </a>
                            </li>
                    
                        </ul>
                    </div>
                </div>
                <div>
                    <el-pagination
                            style="margin-left: 40%"
                            @current-change="handleCurrentChange"
                            background
                            layout="prev, pager, next"
                            :total="this.number">
                    </el-pagination>
                </div>
        
            </div>
        </div>
    </div>
</template>

<script>
    import HeadTitle from "@/components/MainBody/HeadTitle";
    export default {
        name: "FilePage",
        components: {HeadTitle},
        data() {
            return {
                FileInformations:[],
                number:0,
                page:1,
            }
        },
        created(){
            this.NewFilePage();
        },
        methods: {
            handleCurrentChange(i){
                this.NewFile(i)
            },
            TurnPage(id,types) {
                this.$router.push({
                    path: '/FileText',
                    query: {
                        id: id,
                    }

                })
            },
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
            NewFile(page){
                this.$axios.post('public/AllFileInformation', {
                    NowPage: page
                }, {
                    headers: {'Authorization': this.$store.state.Authorization}
                }).then(resp => {
                    if (resp.status === 200) {
                        this.FileInformations = resp.data.data
                        for (var i=0;i<this.FileInformations.length;i++) {
                            this.FileInformations[i].dates = this.formatDate(new Date(this.FileInformations[i].dates))
                        }

                    }
                }).catch(resp => {
                })
            },

            NewFilePage() {
                this.$axios.post('public/SumFileInformation', {
                }, {
                    headers: {'Authorization': this.$store.state.Authorization}
                }).then(resp => {
                    if (resp.status === 200) {
                        this.number = resp.data.data
                        this.NewFile(this.page)
                    }
                }).catch(resp => {
                })

            }
        }
    }
</script>

<style scoped>
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
    }
    .fs24 {
        font-size: 24px;
    }
    
    .lists{
        margin-left: 20px;
    }
    .lists li {
        width: 100%;
    }
    .time {
        color: #999;
        padding: 0 0 0 27px;
        background: url("https://www.cqut.edu.cn/images/sub07_1_time2.png") left center no-repeat;
        line-height: 20px;
        font-family: arial;
    }
    .title {
        display: block;
        overflow: hidden;
        text-overflow: ellipsis;
        margin: 6px 0 0;
        height: 26px;
        line-height: 26px;
        white-space: nowrap;
        color: #333;
    }
    
    .sub_list073 ul {
        padding: 12px 0 21px;
    }
    .fs16 {
        font-size: 16px;
    }
    
    .info{
        display: block;
        margin: 0 0 30px;
        padding: 13px 15px;
        border: 1px solid #f2f2f2;
        border-left: 5px solid #e2e2e2;
        cursor:pointer;
    }
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
</style>