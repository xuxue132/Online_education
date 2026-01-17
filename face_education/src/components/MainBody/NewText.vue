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
                
                <div class="comment-section">
                    <div class="comment-title">评论区 ({{comments.length}})</div>
                    
                    <div class="comment-input">
                        <textarea v-model="commentContent" placeholder="请输入您的评论..." rows="3"></textarea>
                        <button class="submit-btn" @click="submitComment" :disabled="!commentContent.trim()">发表评论</button>
                    </div>

                    <div class="comment-list">
                        <div v-for="comment in comments" :key="comment.id" class="comment-item">
                            <div class="comment-header">
                                <span class="comment-username">{{comment.username}}</span>
                                <span class="comment-time">{{formatTime(comment.createTime)}}</span>
                                <button v-if="isCurrentUser(comment.userId)" class="delete-btn" @click="deleteComment(comment.id)">删除</button>
                                <button class="reply-btn" @click="toggleReply(comment.id)">回复</button>
                            </div>
                            <div class="comment-content">{{comment.content}}</div>
                            
                            <div v-if="showReplyId === comment.id" class="reply-input">
                                <textarea v-model="replyContent" :placeholder="'回复 @' + comment.username + '...'" rows="2"></textarea>
                                <div class="reply-buttons">
                                    <button class="submit-btn" @click="submitReply(comment.id, comment.userId)">回复</button>
                                    <button class="cancel-btn" @click="showReplyId = null">取消</button>
                                </div>
                            </div>

                            <div v-if="comment.replies && comment.replies.length > 0" class="reply-list">
                                <div v-for="reply in comment.replies" :key="reply.id" class="reply-item">
                                    <div class="reply-header">
                                        <span class="comment-username">{{reply.username}}</span>
                                        <span v-if="reply.replyUsername" class="reply-to">@{{reply.replyUsername}}</span>
                                        <span class="comment-time">{{formatTime(reply.createTime)}}</span>
                                        <button v-if="isCurrentUser(reply.userId)" class="delete-btn" @click="deleteComment(reply.id)">删除</button>
                                        <button class="reply-btn" @click="toggleReplyTo(comment.id, reply.userId, reply.username)">回复</button>
                                    </div>
                                    <div class="comment-content">{{reply.content}}</div>
                                </div>
                            </div>
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
                    commentContent: '',
                    replyContent: '',
                    showReplyId: null,
                    replyToUserId: null,
                    replyToUsername: ''
                }
            },

            created(){
                this.NewNotices();
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

                            // 加载评论
                            this.loadComments();
                        }
                    }).catch(resp => {
                    })
                },
                loadComments() {
                    this.$axios.post('public/GetComments', {
                        newsId: this.$route.query.id,
                        token: this.$store.state.Authorization
                    }, {
                        headers: {'Authorization': this.$store.state.Authorization}
                    }).then(resp => {
                        if (resp.status === 200 && resp.data.code === 200) {
                            this.comments = resp.data.data;
                        }
                    }).catch(resp => {
                    })
                },
                submitComment() {
                    if (!this.commentContent.trim()) return;

                    this.$axios.post('public/AddComment', {
                        newsId: this.$route.query.id,
                        content: this.commentContent,
                        parentId: 0,
                        replyToId: 0,
                        token: this.$store.state.Authorization
                    }, {
                        headers: {'Authorization': this.$store.state.Authorization}
                    }).then(resp => {
                        if (resp.status === 200 && resp.data.code === 200) {
                            this.commentContent = '';
                            this.loadComments();
                        } else {
                            alert(resp.data.message || '发表评论失败');
                        }
                    }).catch(resp => {
                        alert('发表评论失败');
                    })
                },
                toggleReply(commentId) {
                    if (this.showReplyId === commentId) {
                        this.showReplyId = null;
                        this.replyToUserId = null;
                        this.replyToUsername = '';
                        this.replyContent = '';
                    } else {
                        this.showReplyId = commentId;
                        this.replyToUserId = null;
                        this.replyToUsername = '';
                        this.replyContent = '';
                    }
                },
                toggleReplyTo(commentId, userId, username) {
                    this.showReplyId = commentId;
                    this.replyToUserId = userId;
                    this.replyToUsername = username;
                    this.replyContent = '@' + username + ' ';
                },
                submitReply(commentId, userId) {
                    if (!this.replyContent.trim()) return;

                    this.$axios.post('public/AddComment', {
                        newsId: this.$route.query.id,
                        content: this.replyContent,
                        parentId: commentId,
                        replyToId: this.replyToUserId || userId,
                        token: this.$store.state.Authorization
                    }, {
                        headers: {'Authorization': this.$store.state.Authorization}
                    }).then(resp => {
                        if (resp.status === 200 && resp.data.code === 200) {
                            this.replyContent = '';
                            this.showReplyId = null;
                            this.replyToUserId = null;
                            this.replyToUsername = '';
                            this.loadComments();
                        } else {
                            alert(resp.data.message || '回复失败');
                        }
                    }).catch(resp => {
                        alert('回复失败');
                    })
                },
                deleteComment(commentId) {
                    if (!confirm('确定要删除这条评论吗?')) return;

                    this.$axios.post('public/DeleteComment', {
                        commentId: commentId,
                        token: this.$store.state.Authorization
                    }, {
                        headers: {'Authorization': this.$store.state.Authorization}
                    }).then(resp => {
                        if (resp.status === 200 && resp.data.code === 200) {
                            this.loadComments();
                        } else {
                            alert(resp.data.message || '删除失败');
                        }
                    }).catch(resp => {
                        alert('删除失败');
                    })
                },
                isCurrentUser(userId) {
                    return this.$store.state.user && this.$store.state.user.telephone === userId;
                },
                formatTime(time) {
                    if (!time) return '';
                    const date = new Date(time);
                    return date.toLocaleString('zh-CN', {
                        year: 'numeric',
                        month: '2-digit',
                        day: '2-digit',
                        hour: '2-digit',
                        minute: '2-digit'
                    });
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

    /* 评论区样式 */
    .comment-section {
        margin-top: 50px;
        padding: 0 20px;
    }

    .comment-title {
        font-size: 20px;
        font-weight: bold;
        margin-bottom: 20px;
        color: #333;
    }

    .comment-input {
        margin-bottom: 30px;
    }

    .comment-input textarea {
        width: 100%;
        padding: 12px;
        border: 1px solid #ddd;
        border-radius: 4px;
        resize: vertical;
        font-size: 14px;
        box-sizing: border-box;
    }

    .submit-btn {
        background-color: #f0ad4e;
        color: white;
        border: none;
        padding: 8px 20px;
        border-radius: 4px;
        cursor: pointer;
        font-size: 14px;
        margin-top: 10px;
        float: right;
    }

    .submit-btn:hover {
        background-color: #ec971f;
    }

    .submit-btn:disabled {
        background-color: #ccc;
        cursor: not-allowed;
    }

    .comment-list {
        margin-top: 20px;
    }

    .comment-item {
        margin-bottom: 25px;
        padding-bottom: 20px;
        border-bottom: 1px solid #eee;
    }

    .comment-header {
        display: flex;
        align-items: center;
        margin-bottom: 8px;
    }

    .comment-username {
        font-weight: bold;
        color: #333;
        margin-right: 15px;
    }

    .comment-time {
        color: #999;
        font-size: 12px;
        margin-right: 15px;
    }

    .delete-btn {
        background-color: #dc3545;
        color: white;
        border: none;
        padding: 4px 12px;
        border-radius: 3px;
        cursor: pointer;
        font-size: 12px;
        margin-left: auto;
        margin-right: 10px;
    }

    .delete-btn:hover {
        background-color: #c82333;
    }

    .reply-btn {
        background-color: #5bc0de;
        color: white;
        border: none;
        padding: 4px 12px;
        border-radius: 3px;
        cursor: pointer;
        font-size: 12px;
    }

    .reply-btn:hover {
        background-color: #31b0d5;
    }

    .comment-content {
        color: #333;
        line-height: 1.6;
        margin-bottom: 15px;
    }

    .reply-input {
        margin-top: 15px;
        padding-left: 50px;
    }

    .reply-input textarea {
        width: 100%;
        padding: 10px;
        border: 1px solid #ddd;
        border-radius: 4px;
        resize: vertical;
        font-size: 14px;
        box-sizing: border-box;
    }

    .reply-buttons {
        margin-top: 10px;
        text-align: right;
    }

    .cancel-btn {
        background-color: #999;
        color: white;
        border: none;
        padding: 6px 16px;
        border-radius: 4px;
        cursor: pointer;
        font-size: 14px;
        margin-right: 10px;
    }

    .cancel-btn:hover {
        background-color: #777;
    }

    .reply-list {
        margin-top: 20px;
        padding-left: 50px;
    }

    .reply-item {
        margin-bottom: 15px;
        padding-bottom: 15px;
        border-bottom: 1px solid #f0f0f0;
    }

    .reply-header {
        display: flex;
        align-items: center;
        margin-bottom: 8px;
    }

    .reply-to {
        color: #f0ad4e;
        margin-left: 5px;
        margin-right: 10px;
    }

    /* 清除浮动 */
    .submit-btn::after {
        content: '';
        display: table;
        clear: both;
    }
</style>