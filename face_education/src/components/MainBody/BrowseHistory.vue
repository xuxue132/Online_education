<template>
    <div class="main_body">
        <HeadTitle></HeadTitle>
        <div class="head_picture">
        </div>
        <div class="content row">
            <div class="sub_left">
                <h2 class="title_left flag1">个人中心</h2>
                <ul class="flag2 sub_menu">
                    <li>
                        <a title="我的信息" href="/MyInformation">我的信息</a>
                    </li>
                    <li>
                        <a title="历史浏览" href="/BrowseHistory">历史浏览</a>
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
                    <div class="page_title">
                        <h3 class="fs24">历史浏览记录</h3>
                    </div>
                </div>
                
                <div style="margin-top: 30px; padding: 0 20px;">
                    <div v-if="browseHistory.length === 0" class="no-history">
                        <p>暂无浏览记录</p>
                    </div>
                    <div v-else>
                        <div v-for="item in browseHistory" :key="item.id" class="history-item">
                            <div class="history-content">
                                <h4 class="news-title">
                                    <a @click="goToNews(item.newsId, item.types)" style="cursor: pointer; color: #333;">
                                        {{item.newsTitle}}
                                    </a>
                                </h4>
                                <p class="news-meta">
                                    <span>来源：{{item.newsSources}}</span>
                                    <span>浏览时间：{{formatDate(item.browseTime)}}</span>
                                </p>
                            </div>
                        </div>
                        
                        <div class="pagination" v-if="total > 0">
                            <el-pagination
                                @current-change="handleCurrentChange"
                                :current-page="currentPage"
                                :page-size="pageSize"
                                layout="total, prev, pager, next"
                                :total="total">
                            </el-pagination>
                        </div>
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
        name: "BrowseHistory",
        components: {HeadTitle},
        data() {
            return {
                browseHistory: [],
                currentPage: 1,
                pageSize: 10,
                total: 0
            }
        },
        created(){
            this.getBrowseHistory();
            this.getBrowseHistoryCount();
        },
        methods: {
            // 格式化时间
            formatDate(dateStr) {
                if (!dateStr) return '';
                const date = new Date(dateStr);
                const year = date.getFullYear();
                const month = String(date.getMonth() + 1).padStart(2, '0');
                const day = String(date.getDate()).padStart(2, '0');
                const hours = String(date.getHours()).padStart(2, '0');
                const minutes = String(date.getMinutes()).padStart(2, '0');
                return `${year}-${month}-${day} ${hours}:${minutes}`;
            },
            // 获取浏览历史
            getBrowseHistory() {
                this.$axios.post('public/UserBrowseHistory', {
                    token: this.$store.state.Authorization,
                    NowPage: this.currentPage
                }, {
                    headers: {'Authorization': this.$store.state.Authorization}
                }).then(resp => {
                    if (resp.status === 200 && resp.data.code === 200) {
                        this.browseHistory = resp.data.data;
                    } else {
                        this.$message.error('获取浏览历史失败');
                    }
                }).catch(resp => {
                    this.$message.error('获取浏览历史失败');
                })
            },
            // 获取浏览历史总数
            getBrowseHistoryCount() {
                this.$axios.post('public/UserBrowseHistoryCount', {
                    token: this.$store.state.Authorization
                }, {
                    headers: {'Authorization': this.$store.state.Authorization}
                }).then(resp => {
                    if (resp.status === 200 && resp.data.code === 200) {
                        this.total = resp.data.data.total;
                    }
                }).catch(resp => {
                    console.log('获取浏览历史数量失败');
                })
            },
            // 跳转到新闻详情页
            goToNews(newsId, types) {
                this.$router.push({
                    path: '/NewText',
                    query: { id: newsId, types: types }
                });
            },
            // 分页切换
            handleCurrentChange(val) {
                this.currentPage = val;
                this.getBrowseHistory();
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
        text-align: center;
    }
    .fs24 {
        font-size: 24px;
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
    
    .no-history {
        text-align: center;
        padding: 50px 0;
        color: #999;
        font-size: 16px;
    }
    
    .history-item {
        border-bottom: 1px solid #eee;
        padding: 20px 0;
        transition: background-color 0.3s;
    }
    
    .history-item:hover {
        background-color: #f9f9f9;
    }
    
    .history-content {
        padding: 0 10px;
    }
    
    .news-title {
        margin: 0 0 10px 0;
        font-size: 18px;
        font-weight: normal;
    }
    
    .news-title a:hover {
        color: #0d74b1 !important;
        text-decoration: underline;
    }
    
    .news-meta {
        margin: 0;
        color: #999;
        font-size: 14px;
    }
    
    .news-meta span {
        margin-right: 20px;
    }
    
    .pagination {
        margin-top: 30px;
        text-align: center;
    }
    
    .bottoms{
        height: 20px;
    }
</style>