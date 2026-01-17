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

                <!-- Comment Section -->
                <div class="comment-section">
                    <h3 class="comment-title">评论区</h3>
                    
                    <!-- Comment Form -->
                    <div class="comment-form" v-if="$store.state.Authorization">
                        <div class="comment-input-wrapper">
                            <el-input
                                type="textarea"
                                v-model="newComment.content"
                                :placeholder="replyToUser ? '@' + replyToUser + ' ' : '写下你的评论...'"
                                :rows="3"
                            ></el-input>
                            <div class="comment-actions">
                                <span v-if="replyToUser" class="reply-to">回复: @{{replyToUser}}</span>
                                <el-button type="primary" size="small" @click="submitComment" :disabled="!newComment.content.trim()">
                                    {{replyToUser ? '回复' : '发表评论'}}
                                </el-button>
                                <el-button size="small" @click="cancelReply" v-if="replyToUser">取消</el-button>
                            </div>
                        </div>
                    </div>
                    
                    <div class="comment-form" v-else>
                        <p class="login-prompt">请先<a href="/login">登录</a>后再评论</p>
                    </div>
                    
                    <!-- Comment List -->
                    <div class="comment-list">
                        <div v-for="comment in comments" :key="comment.id" class="comment-item">
                            <div class="comment-header">
                                <span class="comment-username">{{comment.username}}</span>
                                <span class="comment-time">{{formatCommentTime(comment.createTime)}}</span>
                            </div>
                            <div class="comment-content" v-if="!comment.replyToUsername">
                                {{comment.content}}
                            </div>
                            <div class="comment-content reply-content" v-else>
                                <span class="reply-to-username">@{{comment.replyToUsername}}</span> {{comment.content}}
                            </div>
                            <div class="comment-actions">
                                <span class="comment-action" @click="replyComment(comment)">回复</span>
                                <span class="comment-action delete-action" @click="deleteComment(comment)" v-if="$store.state.Authorization && comment.username === $store.state.username">删除</span>
                            </div>
                            
                            <!-- Nested Replies -->
                            <div class="comment-replies" v-for="reply in getReplies(comment.id)" :key="reply.id">
                                <div class="reply-item">
                                    <div class="comment-header">
                                        <span class="comment-username">{{reply.username}}</span>
                                        <span class="comment-time">{{formatCommentTime(reply.createTime)}}</span>
                                    </div>
                                    <div class="comment-content reply-content">
                                        <span class="reply-to-username">@{{reply.replyToUsername}}</span> {{reply.content}}
                                    </div>
                                    <div class="comment-actions">
                                        <span class="comment-action" @click="replyComment(reply)">回复</span>
                                        <span class="comment-action delete-action" @click="deleteComment(reply)" v-if="$store.state.userInfo && reply.username === $store.state.userInfo.username">删除</span>
                                    </div>
                                </div>
                            </div>
                        </div>
                        
                        <div v-if="comments.length === 0" class="no-comments">
                            暂无评论，快来发表第一条评论吧！
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
                    comments: [],
                    newComment: {
                        content: '',
                        parentId: null,
                        replyToUserId: null
                    },
                    replyToUser: null
                }
            },

            created(){
                this.NewNotices();
                this.loadComments();
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
                formatCommentTime(dateStr) {
                    if (!dateStr) return '';
                    const date = new Date(dateStr);
                    const now = new Date();
                    const diff = now - date;
                    
                    const seconds = Math.floor(diff / 1000);
                    const minutes = Math.floor(seconds / 60);
                    const hours = Math.floor(minutes / 60);
                    const days = Math.floor(hours / 24);
                    
                    if (days > 0) return days + '天前';
                    if (hours > 0) return hours + '小时前';
                    if (minutes > 0) return minutes + '分钟前';
                    return '刚刚';
                },
                loadComments() {
                    this.$axios.post('public/GetComments', {
                        newsId: this.$route.query.id
                    }, {
                        headers: {'Authorization': this.$store.state.Authorization}
                    }).then(resp => {
                        if (resp.status === 200 && resp.data.code === 200) {
                            this.comments = resp.data.data;
                        }
                    }).catch(err => {
                        console.error('加载评论失败:', err);
                    });
                },
                submitComment() {
                    if (!this.newComment.content.trim()) return;
                    
                    this.$axios.post('public/AddComment', {
                        token: this.$store.state.Authorization,
                        newsId: this.$route.query.id,
                        content: this.newComment.content,
                        parentId: this.newComment.parentId,
                        replyToUserId: this.newComment.replyToUserId
                    }, {
                        headers: {'Authorization': this.$store.state.Authorization}
                    }).then(resp => {
                        if (resp.status === 200 && resp.data.code === 200) {
                            this.newComment = { content: '', parentId: null, replyToUserId: null };
                            this.replyToUser = null;
                            this.loadComments();
                            this.$message.success('评论发表成功');
                        } else {
                            this.$message.error(resp.data.msg || '发表失败');
                        }
                    }).catch(err => {
                        console.error('发表评论失败:', err);
                        this.$message.error('发表评论失败');
                    });
                },
                replyComment(comment) {
                    this.newComment.parentId = comment.id;
                    this.newComment.replyToUserId = comment.userId;
                    this.replyToUser = comment.username;
                    this.$nextTick(() => {
                        const textarea = this.$el.querySelector('.comment-form textarea');
                        if (textarea) {
                            textarea.focus();
                        }
                    });
                },
                cancelReply() {
                    this.newComment = { content: '', parentId: null, replyToUserId: null };
                    this.replyToUser = null;
                },
                deleteComment(comment) {
                    this.$confirm('确定要删除这条评论吗?', '提示', {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        type: 'warning'
                    }).then(() => {
                        this.$axios.post('public/DeleteComment', {
                            token: this.$store.state.Authorization,
                            id: comment.id
                        }, {
                            headers: {'Authorization': this.$store.state.Authorization}
                        }).then(resp => {
                            if (resp.status === 200 && resp.data.code === 200) {
                                this.loadComments();
                                this.$message.success('删除成功');
                            } else {
                                this.$message.error(resp.data.msg || '删除失败');
                            }
                        }).catch(err => {
                            console.error('删除评论失败:', err);
                            this.$message.error('删除评论失败');
                        });
                    }).catch(() => {
                        // 用户取消删除
                    });
                },
                getReplies(parentId) {
                    return this.comments.filter(c => c.parentId === parentId);
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

    /* Comment Styles */
    .comment-section {
        margin: 40px 20px;
        padding: 20px;
        border-top: 1px solid #e5e5e5;
    }
    
    .comment-title {
        font-size: 18px;
        color: #333;
        margin-bottom: 20px;
        padding-bottom: 10px;
        border-bottom: 2px solid #f0ad4e;
    }
    
    .comment-form {
        margin-bottom: 30px;
    }
    
    .comment-input-wrapper {
        background: #f9f9f9;
        padding: 15px;
        border-radius: 4px;
    }
    
    .comment-actions {
        margin-top: 10px;
        display: flex;
        justify-content: flex-end;
        align-items: center;
        gap: 10px;
    }
    
    .reply-to {
        color: #f0ad4e;
        font-size: 14px;
        margin-right: auto;
    }
    
    .login-prompt {
        text-align: center;
        padding: 20px;
        color: #999;
    }
    
    .login-prompt a {
        color: #f0ad4e;
        text-decoration: none;
    }
    
    .login-prompt a:hover {
        text-decoration: underline;
    }
    
    .comment-list {
        margin-top: 20px;
    }
    
    .comment-item {
        padding: 15px 0;
        border-bottom: 1px solid #f0f0f0;
    }
    
    .comment-header {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-bottom: 8px;
    }
    
    .comment-username {
        font-weight: bold;
        color: #333;
        font-size: 14px;
    }
    
    .comment-time {
        color: #999;
        font-size: 12px;
    }
    
    .comment-content {
        color: #666;
        line-height: 1.6;
        margin-bottom: 10px;
        padding-left: 0;
    }
    
    .reply-content {
        padding-left: 20px;
    }
    
    .reply-to-username {
        color: #f0ad4e;
        font-weight: bold;
    }
    
    .comment-actions {
        display: flex;
        gap: 15px;
    }
    
    .comment-action {
        color: #999;
        font-size: 12px;
        cursor: pointer;
    }
    
    .comment-action:hover {
        color: #f0ad4e;
    }
    
    .delete-action {
        color: #ff4d4f;
    }
    
    .delete-action:hover {
        color: #ff7875;
    }
    
    .comment-replies {
        margin-left: 40px;
        margin-top: 15px;
    }
    
    .reply-item {
        padding: 12px 0;
        border-bottom: 1px solid #f5f5f5;
    }
    
    .no-comments {
        text-align: center;
        color: #999;
        padding: 30px;
        background: #fafafa;
        border-radius: 4px;
    }
</style>