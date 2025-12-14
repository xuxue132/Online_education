<template>
    <div class="_head_nav ">
        <div class="head_top">
            <div class="head_top_next">
                <img src="http://eucation.i8329.cn/learning.jpg">
                <div class="top_right">
                    <div class="right_one" style="align-items: center;">
                        <a href="/MyInformation" class="userMobileName">{{this.user.username}}</a>
                        <a  style="float: left;font-size: 20px;color: #868686;margin-top: 1px">你好  |</a>
                    </div>
                    <div class="right_one">
                        <el-link :underline="false" class="outThisUser" @click="LoginOut()">退出</el-link>
                    </div>
                    
                </div>
            </div>
        </div>
        <div class="flex  width1260">
            <ul class="flex ">
                <li><a href="/FirstPage">首页</a></li>
                <li><a href="/NewsPage">新闻公告</a></li>
                <li><a href="/CharacteristicProject">特色项目</a></li>
                <li><a href="/ExaminationPage">有奖竞答</a></li>
                <li><a href="/ReadPage">阅读打卡</a></li>
                <li><a href="/FilePage">下载中心</a></li>
                <li><a href="/HonorDisplay">荣誉展示</a></li>
            </ul>
 
        </div>
    </div>
</template>

<script>
    export default {
        name: "HeadTitle",
        data(){
            return{
                user:{}
            }
        },
        created(){
          this.getUser()
        },
        methods:{
            LoginOut() {
                this.$router.push("/login");
            },
            getUser() {
                this.$axios.post('public/MyInformation', {
                    token: this.$store.state.Authorization
                }, {
                    headers: {'Authorization': this.$store.state.Authorization}
                }).then(resp => {
                    if (resp.status === 200) {
                        this.user = resp.data.data
                    }
                }).catch(resp => {
                })
            },
        }
    }
</script>

<style scoped>
    ._head_nav {
        background: #0d74b1;
    }
    ._head_nav ul li a {
        font-size: 21px;
        color: #fff;
        display: block;
        height: 50px;
        padding: 2px 22px;
        line-height: 50px;
    }

    .flex {
        display: flex;
        display: -webkit-flex;
        display: -ms-flex;
    }
    .width1260 {
        width: 1260px;
        margin: 0 auto;
        justify-content: space-between;
        align-items: center;
    }
    ._head_nav ul li a:hover {
        background: #0c6297;
    }
    .head_top{

        background: #fff;
        box-shadow: 0 3px 0 0 #dfdfdf;
        margin: 10px 0;
        padding: 0;
        width: 100%;
        height: 105px;
    }
    .head_top_next{
        width: 80%;
        margin: 0 auto;
        height: 95px;
        justify-content: space-between;
        align-items: center;
    
    }
    .head_top_next img{
        float: left;
        max-height: 100%;
        max-width: 100%;
        display: block;
        margin: auto;
    }

    .top_right{
        align-items: center;
        height: 75px;
        line-height: 95px;
        color: #868686;
        font-size: 22px;
        float: right;
        margin-right: 78px;
    }
    .right_one{
        margin-right: 11px;
        font-size: 22px;
        float: left;
        color: #666666;
    }
    .right_one a{
        margin-left: 11px;
        color: #666666;
    }
    .userMobileName{
        font-size: 22px;
        float: left;
        color: #666666;
    
    
    }
    .outThisUser{
        font-size: 22px;
        color: #666666;
    }
    .right_three{
        height: 75px;
        font-size: 22px;
        line-height: 95px;
        float: left;
        color: #666666;
    }
    .right_three a{
        color: #666666;
        line-height: 95px;
    }
    a span{
        display: inline-block
    }

    .head_min_left img{
        width: 108px;
        height: 108px;
        border-radius: 50%;
    }

    .head_min_right div{
        font-size: 20px;
        color: #000000;
    }
    .deleteBorder .el-tabs__nav-wrap::after{
        height: 0 !important;
    }

</style>