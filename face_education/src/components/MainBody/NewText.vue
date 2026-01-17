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
                
                <!-- 评论区 -->
                <div class="comment-section" style="margin-top: 50px; padding: 20px; background: #f9f9f9; border-radius: 8px;">
                    <h3 style="margin-bottom: 20px; color: #333;">评论区 ({{commentCount}})</h3>
                    
                    <!-- 发表评论 -->
                    <div class="comment-input" style="margin-bottom: 30px;">
                        <textarea 
                            v-model="newCommentContent" 
                            placeholder="请输入您的评论..."
                            style="width: 100%; min-height: 80px; padding: 10px; border: 1px solid #ddd; border-radius: 4px; resize: vertical;"
                        ></textarea>
                        <button 
                            @click="submitComment"
                            style="margin-top: 10px; padding: 8px 20px; background: #007bff; color: #fff; border: none; border-radius: 4px; cursor: pointer;"
                        >发表评论</button>
                    </div>
                    
                    <!-- 评论列表 -->
                    <div class="comment-list">
                        <div 
                            v-for="comment in comments" 
                            :key="comment.id"
                            class="comment-item"
                            style="margin-bottom: 20px; padding: 15px; background: #fff; border-radius: 4px; box-shadow: 0 2px 4px rgba(0,0,0,0.1);"
                        >
                            <div class="comment-header" style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 10px;">
                                <div style="font-weight: bold; color: #333;">{{comment.username}}</div>
                                <div style="font-size: 12px; color: #999;">{{comment.createTime}}</div>
                            </div>
                            <div class="comment-content" style="margin-bottom: 10px; color: #555;">
                                {{comment.content}}
                            </div>
                            <div class="comment-actions" style="display: flex; gap: 15px; font-size: 14px;">
                                <a 
                                    @click="replyComment(comment)"
                                    style="color: #007bff; cursor: pointer; text-decoration: none;"
                                >回复</a>
                                <a 
                                    v-if="comment.userId === userId"
                                    @click="deleteComment(comment.id)"
                                    style="color: #dc3545; cursor: pointer; text-decoration: none;"
                                >删除</a>
                            </div>
                            
                            <!-- 二级评论列表 -->
                            <div class="reply-list" style="margin-top: 15px; margin-left: 40px;">
                                <div 
                                    v-for="reply in comment.replies" 
                                    :key="reply.id"
                                    class="reply-item"
                                    style="margin-bottom: 10px; padding: 10px; background: #f9f9f9; border-radius: 4px;"
                                >
                                    <div class="reply-header" style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 8px;">
                                        <div style="font-weight: bold; color: #333;">{{reply.username}}</div>
                                        <div style="font-size: 12px; color: #999;">{{reply.createTime}}</div>
                                    </div>
                                    <div class="reply-content" style="margin-bottom: 8px; color: #555;">
                                        <span style="color: #007bff;">@{{reply.replyUsername}}</span> {{reply.content}}
                                    </div>
                                    <div class="reply-actions" style="display: flex; gap: 15px; font-size: 14px;">
                                        <a 
                                            @click="replyComment(reply)"
                                            style="color: #007bff; cursor: pointer; text-decoration: none;"
                                        >回复</a>
                                        <a 
                                            v-if="reply.userId === userId"
                                            @click="deleteComment(reply.id)"
                                            style="color: #dc3545; cursor: pointer; text-decoration: none;"
                                        >删除</a>
                                    </div>
                                </div>
                            </div>
                            
                            <!-- 回复输入框 -->
                            <div 
                                v-if="replyToComment && replyToComment.id === comment.id"
                                class="reply-input"
                                style="margin-top: 15px; margin-left: 40px;"
                            >
                                <textarea 
                                    v-model="replyContent" 
                                    :placeholder="'回复 @' + comment.username + '...'"
                                    style="width: 100%; min-height: 60px; padding: 10px; border: 1px solid #ddd; border-radius: 4px; resize: vertical;"
                                ></textarea>
                                <div style="margin-top: 10px; display: flex; gap: 10px;">
                                    <button 
                                        @click="submitReply"
                                        style="padding: 6px 16px; background: #007bff; color: #fff; border: none; border-radius: 4px; cursor: pointer;"
                                    >发送回复</button>
                                    <button 
                                        @click="cancelReply"
                                        style="padding: 6px 16px; background: #6c757d; color: #fff; border: none; border-radius: 4px; cursor: pointer;"
                                    >取消</button>
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
                    // 评论相关数据
                    comments: [],
                    commentCount: 0,
                    newCommentContent: '',
                    replyToComment: null,
                    replyContent: '',
                    userId: this.$store.state.id || 0
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
                },
                // 加载评论
                loadComments() {
                    this.$axios.post('public/GetComments', {
                        newsId: this.$route.query.id
                    }, {
                        headers: {'Authorization': this.$store.state.Authorization}
                    }).then(resp => {
                        if (resp.status === 200) {
                            this.comments = resp.data.data || [];
                        }
                    }).catch(resp => {
                    });
                    
                    // 获取评论数量
                    this.$axios.post('public/GetCommentCount', {
                        newsId: this.$route.query.id
                    }, {
                        headers: {'Authorization': this.$store.state.Authorization}
                    }).then(resp => {
                        if (resp.status === 200) {
                            this.commentCount = resp.data.data || 0;
                        }
                    }).catch(resp => {
                    });
                },
                // 发表评论
                submitComment() {
                    if (!this.newCommentContent.trim()) {
                        alert('请输入评论内容');
                        return;
                    }
                    
                    this.$axios.post('public/AddComment', {
                        newsId: this.$route.query.id,
                        userId: this.userId,
                        username: this.$store.state.username,
                        content: this.newCommentContent,
                        parentId: 0
                    }, {
                        headers: {'Authorization': this.$store.state.Authorization}
                    }).then(resp => {
                        if (resp.status === 200 && resp.data.success) {
                            this.newCommentContent = '';
                            this.loadComments();
                        } else {
                            alert('评论失败');
                        }
                    }).catch(resp => {
                        alert('评论失败');
                    });
                },
                // 回复评论
                replyComment(comment) {
                    this.replyToComment = comment;
                    this.replyContent = '';
                },
                // 提交回复
                submitReply() {
                    if (!this.replyContent.trim()) {
                        alert('请输入回复内容');
                        return;
                    }
                    
                    this.$axios.post('public/AddComment', {
                        newsId: this.$route.query.id,
                        userId: this.userId,
                        username: this.$store.state.username,
                        content: this.replyContent,
                        parentId: this.replyToComment.id,
                        replyUserId: this.replyToComment.userId,
                        replyUsername: this.replyToComment.username
                    }, {
                        headers: {'Authorization': this.$store.state.Authorization}
                    }).then(resp => {
                        if (resp.status === 200 && resp.data.success) {
                            this.replyToComment = null;
                            this.replyContent = '';
                            this.loadComments();
                        } else {
                            alert('回复失败');
                        }
                    }).catch(resp => {
                        alert('回复失败');
                    });
                },
                // 取消回复
                cancelReply() {
                    this.replyToComment = null;
                    this.replyContent = '';
                },
                // 删除评论
                deleteComment(commentId) {
                    if (!confirm('确定要删除这条评论吗？')) {
                        return;
                    }
                    
                    this.$axios.post('public/DeleteComment', {
                        id: commentId,
                        userId: this.userId
                    }, {
                        headers: {'Authorization': this.$store.state.Authorization}
                    }).then(resp => {
                        if (resp.status === 200 && resp.data.success) {
                            this.loadComments();
                        } else {
                            alert('删除失败');
                        }
                    }).catch(resp => {
                        alert('删除失败');
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
</style>