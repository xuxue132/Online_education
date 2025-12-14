<template>
    <div class="main_body">
        <HeadTitle></HeadTitle>
        <div class="head_picture">
        </div>
        <div class="content row">
            <div class="sub_left">
                <h2 class="title_left flag1">用户中心</h2>
                <ul class="flag2 sub_menu">
                    <li>
                        <a  title="个人信息" href="/MyInformation">个人信息</a>
                    </li>
                    <li>
                        <a  title="阅读打卡" href="/ReadPage">阅读打卡</a>
                    </li>
                    <li>
                        <a  title="我的奖品" href="/MyPrize">我的奖品</a>
                    </li>
                    <li>
                        <a class="chs" title="历史记录" href="/HistoryPage">历史记录</a>
                    </li>
                </ul>
            </div>
            <div class="sub_right">
                <div class="bread fs14">
                    <a href="/HomePage">首页</a>
                    &nbsp;-&nbsp;
                    <span>历史浏览记录</span>
                </div>
                <div class="page_content">
                    <h3 class="fs20" style="margin-left: 20px; margin-top: 20px; margin-bottom: 30px;">最近一周浏览记录</h3>
                    
                    <div v-if="historyList.length === 0" class="no_record">
                        <p>暂无浏览记录</p>
                    </div>
                    
                    <div v-else class="history_list">
                        <div v-for="item in historyList" :key="item.id" class="history_item">
                            <div class="item_title">
                                <a :href="'/NewText?id=' + item.news_id + '&types=' + (item.title.indexOf('公告') >= 0 ? 1 : 0)" :title="item.title">{{ item.title }}</a>
                            </div>
                            <div class="item_info">
                                <span>来源：{{ item.sources }}</span>
                                <span>浏览时间：{{ formatDate(new Date(item.browse_time)) }}</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import HeadTitle from "@/components/MainBody/HeadTitle";
    export default {
        name: "HistoryPage",
        components: {HeadTitle},
        data() {
            return {
                historyList: []
            }
        },
        created() {
            this.getHistoryList();
        },
        methods: {
            formatDate(date) {
                if (typeof date === 'string') {
                    return date;
                }
                let year = date.getFullYear();
                let month = date.getMonth() + 1;
                let day = date.getDate();
                let hour = date.getHours();
                let minute = date.getMinutes();
                let second = date.getSeconds();
                
                if (month < 10) month = "0" + month;
                if (day < 10) day = "0" + day;
                if (hour < 10) hour = "0" + hour;
                if (minute < 10) minute = "0" + minute;
                if (second < 10) second = "0" + second;
                
                return year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second;
            },
            getHistoryList() {
                this.$axios.post('public/GetBrowseHistory', {
                    token: this.$store.state.Authorization
                }, {
                    headers: {'Authorization': this.$store.state.Authorization}
                }).then(resp => {
                    if (resp.status === 200 && resp.data.code === 200) {
                        this.historyList = resp.data.data;
                    }
                }).catch(resp => {
                    console.error("获取浏览记录失败:", resp);
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
        height: auto;
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
    .fs20 {
        font-size: 20px;
    }
    .chs{
        background: #f0ad4e;
        color: #fff;
        border-color: #F0AD4E;
    }
    .no_record {
        text-align: center;
        padding: 100px 0;
        color: #999;
        font-size: 16px;
    }
    .history_list {
        margin-left: 20px;
        margin-right: 20px;
    }
    .history_item {
        padding: 20px;
        border-bottom: 1px solid #eee;
    }
    .history_item:last-child {
        border-bottom: none;
    }
    .item_title a {
        font-size: 18px;
        color: #333;
        line-height: 26px;
        display: block;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
    }
    .item_title a:hover {
        color: #f0ad4e;
        text-decoration: underline;
    }
    .item_info {
        margin-top: 10px;
        color: #999;
        font-size: 14px;
    }
    .item_info span {
        margin-right: 30px;
    }
</style>