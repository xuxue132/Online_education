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

                <div class="comment-section">
                    <div class="comment-header">
                        <i class="el-icon-chat-dot-round"></i>
                        <span>评论区</span>
                        <span class="comment-count">{{totalCommentCount}} 条评论</span>
                    </div>

                    <div class="comment-input-wrapper" v-if="isLoggedIn">
                        <div class="input-avatar">
                            <img :src="getMyAvatarUrl()" alt="" class="avatar-img" />
                        </div>
                        <div class="input-box">
                            <textarea v-model="newComment" placeholder="发表你的看法..." rows="3"></textarea>
                            <div class="input-actions">
                                <span class="word-count">{{newComment.length}}/500</span>
                                <button @click="submitComment" :disabled="!newComment.trim()" class="submit-btn">
                                    <i class="el-icon-s-promotion"></i> 发布评论
                                </button>
                            </div>
                        </div>
                    </div>
                    <div v-else class="login-tip">
                        <i class="el-icon-warning-outline"></i>
                        <span>请先登录后发表评论</span>
                    </div>

                    <div class="comment-list">
                        <div v-for="comment in comments" :key="comment.id" class="comment-item">
                            <div class="comment-avatar">
                                <img :src="getAvatarUrl(comment)" alt="" class="avatar-img" />
                            </div>
                            <div class="comment-body">
                                <div class="comment-head">
                                    <span class="comment-username">{{comment.username}}</span>
                                    <span class="comment-time"><i class="el-icon-time"></i> {{formatCommentDate(comment.createdAt)}}</span>
                                </div>
                                <div class="comment-content">{{comment.content}}</div>
                                <div class="comment-actions">
                                    <button class="reply-btn" @click="showReplyInput(comment)" v-if="isLoggedIn">
                                        <i class="el-icon-chat-line-round"></i> 回复
                                    </button>
                                    <button class="delete-btn" @click="deleteMyComment(comment.id)" v-if="isLoggedIn && canDelete(comment)">
                                        <i class="el-icon-delete"></i> 删除
                                    </button>
                                </div>

                            <div class="reply-list" v-if="comment.replies && comment.replies.length > 0">
                                <div v-for="reply in comment.replies" :key="reply.id" class="reply-item">
                                    <div class="reply-avatar">
                                        <img :src="getAvatarUrl(reply)" alt="" class="avatar-img" />
                                    </div>
                                    <div class="reply-body">
                                        <div class="reply-head">
                                            <span class="reply-username">{{reply.username}}</span>
                                            <span class="reply-time">{{formatCommentDate(reply.createdAt)}}</span>
                                        </div>
                                        <div class="reply-content">
                                            <span v-if="reply.replyUsername" class="reply-to">@{{reply.replyUsername}} </span>{{reply.content}}
                                        </div>
                                        <div class="reply-actions">
                                            <button class="reply-btn-small" @click="showReplyInput(comment, reply)" v-if="isLoggedIn">回复</button>
                                            <button class="delete-btn-small" @click="deleteMyComment(reply.id)" v-if="isLoggedIn && canDelete(reply)">删除</button>
                                        </div>
                                    </div>
                                </div>
                            </div>

                                <div class="reply-input-box" v-if="showReplyBox === comment.id">
                                    <div class="reply-input-wrapper">
                                        <span class="reply-to-label">回复 <span class="reply-to-name">{{replyToUsername}}</span>：</span>
                                        <textarea v-model="replyContent" placeholder="输入回复内容..." rows="2"></textarea>
                                        <div class="reply-input-actions">
                                            <button class="cancel-reply-btn" @click="cancelReply"><i class="el-icon-close"></i> 取消</button>
                                            <button class="submit-reply-btn" @click="submitReply(comment)" :disabled="!replyContent.trim()">
                                                <i class="el-icon-check"></i> 发送
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div v-if="comments.length === 0" class="no-comment">
                        暂无评论，快来抢沙发吧~
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
                newComment: '',
                replyContent: '',
                showReplyBox: null,
                replyToUsername: '',
                replyToUserId: null,
                currentUserId: null,
                myAvatar: ''
            }
        },

        computed: {
            isLoggedIn() {
                return this.$store.state.Authorization && this.$store.state.Authorization !== ''
            },
            totalCommentCount() {
                let count = this.comments.length
                for (let comment of this.comments) {
                    if (comment.replies) {
                        count += comment.replies.length
                    }
                }
                return count
            }
        },

        created(){
            this.NewNotices();
            this.getCurrentUser();
        },
    
        methods: {
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

            formatCommentDate(date) {
                if (!date) return ''
                if (typeof date === 'string'){
                    return date.replace('T', ' ').substring(0, 16)
                }
                let d = new Date(date);
                let year = d.getFullYear();
                let month = d.getMonth()+1;
                let day = d.getDate();
                let hour = d.getHours();
                let minute = d.getMinutes();
                if (month < 10) month = "0" + month;
                if (day < 10) day = "0" + day;
                if (hour < 10) hour = "0" + hour;
                if (minute < 10) minute = "0" + minute;
                return year + "-" + month + "-" + day + " " + hour + ":" + minute;
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
                        this.loadComments();

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
            },

            getCurrentUser() {
                if (!this.isLoggedIn) return
                this.$axios.post('public/MyInformation', {
                    token: this.$store.state.Authorization
                }, {
                    headers: {'Authorization': this.$store.state.Authorization}
                }).then(resp => {
                    if (resp.status === 200 && resp.data.data) {
                        this.currentUserId = resp.data.data.id
                        this.myAvatar = resp.data.data.iconUrl || ''
                    }
                }).catch(resp => {
                })
            },

            getAvatarUrl(user) {
                return user.iconUrl || 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png'
            },

            getMyAvatarUrl() {
                return this.myAvatar || 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png'
            },

            loadComments() {
                this.$axios.post('public/GetNewsComments', {
                    newsId: this.NewNotice.id
                }, {
                    headers: {'Authorization': this.$store.state.Authorization}
                }).then(resp => {
                    if (resp.status === 200 && resp.data.data) {
                        this.comments = resp.data.data
                    }
                }).catch(resp => {
                })
            },

            submitComment() {
                if (!this.newComment.trim()) {
                    alert('请输入评论内容')
                    return
                }
                this.$axios.post('public/AddNewsComment', {
                    newsId: this.NewNotice.id,
                    userId: this.currentUserId,
                    parentId: 0,
                    replyUserId: null,
                    content: this.newComment
                }, {
                    headers: {'Authorization': this.$store.state.Authorization}
                }).then(resp => {
                    if (resp.status === 200) {
                        this.newComment = ''
                        this.loadComments()
                    }
                }).catch(resp => {
                })
            },

            showReplyInput(comment, reply) {
                this.showReplyBox = comment.id
                if (reply) {
                    this.replyToUsername = reply.username
                    this.replyToUserId = reply.userId
                } else {
                    this.replyToUsername = comment.username
                    this.replyToUserId = comment.userId
                }
                this.replyContent = ''
            },

            cancelReply() {
                this.showReplyBox = null
                this.replyContent = ''
                this.replyToUsername = ''
                this.replyToUserId = null
            },

            submitReply(comment) {
                if (!this.replyContent.trim()) {
                    alert('请输入回复内容')
                    return
                }
                this.$axios.post('public/AddNewsComment', {
                    newsId: this.NewNotice.id,
                    userId: this.currentUserId,
                    parentId: comment.id,
                    replyUserId: this.replyToUserId,
                    content: this.replyContent
                }, {
                    headers: {'Authorization': this.$store.state.Authorization}
                }).then(resp => {
                    if (resp.status === 200) {
                        this.cancelReply()
                        this.loadComments()
                    }
                }).catch(resp => {
                })
            },

            deleteMyComment(commentId) {
                if (!confirm('确定要删除这条评论吗？')) return
                this.$axios.post('public/DeleteNewsComment', {
                    id: commentId
                }, {
                    headers: {'Authorization': this.$store.state.Authorization}
                }).then(resp => {
                    if (resp.status === 200) {
                        this.loadComments()
                    }
                }).catch(resp => {
                })
            },

            canDelete(comment) {
                return this.currentUserId && comment.userId === this.currentUserId
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

    .comment-section {
        margin-top: 30px;
        padding: 20px;
        background: #fff;
        border-radius: 8px;
        box-shadow: 0 2px 12px rgba(0, 0, 0, 0.08);
    }

    .comment-header {
        display: flex;
        align-items: center;
        padding-bottom: 15px;
        border-bottom: 2px solid #f0f0f0;
        margin-bottom: 20px;
        font-size: 18px;
        font-weight: 600;
        color: #333;
    }

    .comment-header i {
        margin-right: 8px;
        color: #409eff;
        font-size: 20px;
    }

    .comment-count {
        margin-left: auto;
        font-size: 14px;
        font-weight: normal;
        color: #999;
    }

    .comment-input-wrapper {
        display: flex;
        gap: 12px;
        margin-bottom: 25px;
        padding: 15px;
        background: #fafafa;
        border-radius: 8px;
    }

    .input-avatar {
        flex-shrink: 0;
    }

    .avatar-img {
        width: 40px;
        height: 40px;
        border-radius: 50%;
        object-fit: cover;
        border: 2px solid #e8e8e8;
    }

    .input-box {
        flex: 1;
    }

    .input-box textarea {
        width: 100%;
        padding: 12px;
        border: 1px solid #e0e0e0;
        border-radius: 6px;
        resize: none;
        font-size: 14px;
        transition: all 0.3s;
        box-sizing: border-box;
    }

    .input-box textarea:focus {
        border-color: #409eff;
        box-shadow: 0 0 0 2px rgba(64, 158, 255, 0.1);
        outline: none;
    }

    .input-actions {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-top: 10px;
    }

    .word-count {
        font-size: 12px;
        color: #999;
    }

    .submit-btn {
        padding: 8px 20px;
        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
        color: #fff;
        border: none;
        border-radius: 20px;
        cursor: pointer;
        font-size: 14px;
        transition: all 0.3s;
    }

    .submit-btn:hover:not(:disabled) {
        transform: translateY(-2px);
        box-shadow: 0 4px 12px rgba(102, 126, 234, 0.4);
    }

    .submit-btn:disabled {
        opacity: 0.5;
        cursor: not-allowed;
    }

    .login-tip {
        display: flex;
        align-items: center;
        justify-content: center;
        gap: 8px;
        padding: 30px;
        background: #fafafa;
        border-radius: 8px;
        color: #999;
        font-size: 14px;
    }

    .login-tip i {
        font-size: 18px;
    }

    .comment-list {
        margin-top: 20px;
    }

    .comment-item {
        display: flex;
        gap: 12px;
        padding: 15px;
        border-bottom: 1px solid #f0f0f0;
        transition: background 0.3s;
    }

    .comment-item:hover {
        background: #fafafa;
    }

    .comment-avatar {
        flex-shrink: 0;
    }

    .comment-body {
        flex: 1;
        min-width: 0;
    }

    .comment-head {
        display: flex;
        align-items: center;
        gap: 12px;
        margin-bottom: 8px;
    }

    .comment-username {
        font-weight: 600;
        color: #409eff;
        font-size: 14px;
    }

    .comment-time {
        font-size: 12px;
        color: #999;
        display: flex;
        align-items: center;
        gap: 4px;
    }

    .comment-content {
        font-size: 14px;
        color: #333;
        line-height: 1.6;
        margin-bottom: 10px;
        word-break: break-word;
    }

    .comment-actions {
        display: flex;
        gap: 15px;
    }

    .reply-btn, .delete-btn {
        padding: 4px 12px;
        border: none;
        border-radius: 4px;
        cursor: pointer;
        font-size: 12px;
        transition: all 0.3s;
        background: transparent;
        color: #999;
    }

    .reply-btn:hover {
        color: #409eff;
        background: rgba(64, 158, 255, 0.1);
    }

    .delete-btn:hover {
        color: #f56c6c;
        background: rgba(245, 108, 108, 0.1);
    }

    .reply-list {
        margin-top: 12px;
        padding-left: 12px;
        border-left: 2px solid #e8e8e8;
    }

    .reply-item {
        display: flex;
        gap: 10px;
        padding: 10px 0;
        padding-left: 10px;
    }

    .reply-avatar .avatar-img {
        width: 32px;
        height: 32px;
    }

    .reply-body {
        flex: 1;
        min-width: 0;
    }

    .reply-head {
        display: flex;
        align-items: center;
        gap: 10px;
        margin-bottom: 4px;
    }

    .reply-username {
        font-weight: 600;
        color: #67c23a;
        font-size: 13px;
    }

    .reply-time {
        font-size: 11px;
        color: #bbb;
    }

    .reply-content {
        font-size: 13px;
        color: #555;
        line-height: 1.5;
        margin-bottom: 6px;
        word-break: break-word;
    }

    .reply-to {
        color: #409eff;
        font-weight: 500;
    }

    .reply-actions {
        display: flex;
        gap: 10px;
    }

    .reply-btn-small, .delete-btn-small {
        padding: 2px 8px;
        border: none;
        border-radius: 3px;
        cursor: pointer;
        font-size: 11px;
        transition: all 0.3s;
        background: transparent;
        color: #999;
    }

    .reply-btn-small:hover {
        color: #409eff;
    }

    .delete-btn-small:hover {
        color: #f56c6c;
    }

    .reply-input-box {
        margin-top: 12px;
        padding: 15px;
        background: #f8f9fa;
        border-radius: 8px;
        border: 1px solid #e8e8e8;
    }

    .reply-input-wrapper {
        display: flex;
        flex-direction: column;
        gap: 10px;
    }

    .reply-to-label {
        font-size: 13px;
        color: #666;
    }

    .reply-to-name {
        color: #409eff;
        font-weight: 600;
    }

    .reply-input-wrapper textarea {
        width: 100%;
        padding: 10px;
        border: 1px solid #dcdfe6;
        border-radius: 4px;
        resize: none;
        font-size: 13px;
        box-sizing: border-box;
    }

    .reply-input-wrapper textarea:focus {
        border-color: #409eff;
        outline: none;
    }

    .reply-input-actions {
        display: flex;
        justify-content: flex-end;
        gap: 10px;
    }

    .cancel-reply-btn, .submit-reply-btn {
        padding: 6px 16px;
        border: none;
        border-radius: 4px;
        cursor: pointer;
        font-size: 13px;
        transition: all 0.3s;
    }

    .cancel-reply-btn {
        background: #f0f0f0;
        color: #666;
    }

    .cancel-reply-btn:hover {
        background: #e0e0e0;
    }

    .submit-reply-btn {
        background: #409eff;
        color: #fff;
    }

    .submit-reply-btn:hover:not(:disabled) {
        background: #66b1ff;
    }

    .submit-reply-btn:disabled {
        opacity: 0.5;
        cursor: not-allowed;
    }

    .no-comment {
        text-align: center;
        padding: 40px;
        color: #999;
        font-size: 14px;
    }
</style>