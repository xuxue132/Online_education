<template>
  <div class="comment-section">
    <h3 class="comment-title">评论 ({{ comments.length }})</h3>
    
    <!-- 评论输入框 -->
    <div class="comment-input" v-if="isLoggedIn">
      <el-input
        type="textarea"
        :rows="3"
        placeholder="发表你的评论..."
        v-model="newComment"
        maxlength="500"
        show-word-limit
      ></el-input>
      <div class="comment-actions">
        <el-button type="primary" size="small" @click="submitComment">发表评论</el-button>
      </div>
    </div>
    
    <!-- 未登录提示 -->
    <div class="login-tip" v-else>
      <el-alert
        title="请先登录后再发表评论"
        type="info"
        center
        show-icon
        :closable="false"
      ></el-alert>
    </div>
    
    <!-- 评论列表 -->
    <div class="comment-list" v-if="comments.length > 0">
      <div v-for="comment in topLevelComments" :key="comment.id" class="comment-item">
        <div class="comment-content">
          <div class="comment-header">
            <span class="username">{{ comment.username }}</span>
            <span class="time">{{ formatTime(comment.createTime) }}</span>
            <el-button
              v-if="isLoggedIn && comment.userId === currentUserId"
              type="text"
              size="mini"
              @click="deleteComment(comment.id)"
              class="delete-btn"
            >删除</el-button>
          </div>
          <div class="comment-text">{{ comment.content }}</div>
          <div class="comment-footer">
            <el-button type="text" size="mini" @click="toggleReply(comment.id)">
              {{ replyTo[comment.id] ? '取消回复' : '回复' }}
            </el-button>
          </div>
          
          <!-- 回复输入框 -->
          <div class="reply-input" v-if="replyTo[comment.id] && isLoggedIn">
            <el-input
              type="textarea"
              :rows="2"
              :placeholder="`回复 @${comment.username}`"
              v-model="replyContent[comment.id]"
              maxlength="300"
              show-word-limit
            ></el-input>
            <div class="reply-actions">
              <el-button type="primary" size="mini" @click="submitReply(comment.id, comment.username)">回复</el-button>
            </div>
          </div>
          
          <!-- 回复列表 -->
          <div class="replies" v-if="getReplies(comment.id).length > 0">
            <div v-for="reply in getReplies(comment.id)" :key="reply.id" class="reply-item">
              <div class="reply-header">
                <span class="username">{{ reply.username }}</span>
                <span class="reply-to">回复 @{{ reply.parentUsername }}</span>
                <span class="time">{{ formatTime(reply.createTime) }}</span>
                <el-button
                  v-if="isLoggedIn && reply.userId === currentUserId"
                  type="text"
                  size="mini"
                  @click="deleteComment(reply.id)"
                  class="delete-btn"
                >删除</el-button>
              </div>
              <div class="reply-text">{{ reply.content }}</div>
              <div class="reply-footer">
                <el-button type="text" size="mini" @click="toggleReply(reply.id)">
                  {{ replyTo[reply.id] ? '取消回复' : '回复' }}
                </el-button>
              </div>
              
              <!-- 二级回复输入框 -->
              <div class="reply-input" v-if="replyTo[reply.id] && isLoggedIn">
                <el-input
                  type="textarea"
                  :rows="2"
                  :placeholder="`回复 @${reply.username}`"
                  v-model="replyContent[reply.id]"
                  maxlength="300"
                  show-word-limit
                ></el-input>
                <div class="reply-actions">
                  <el-button type="primary" size="mini" @click="submitReply(reply.id, reply.username)">回复</el-button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    
    <!-- 无评论提示 -->
    <div class="no-comments" v-else>
      <p>暂无评论，快来发表第一条评论吧！</p>
    </div>
  </div>
</template>

<script>
export default {
  name: "NewsComment",
  props: {
    newsId: {
      type: Number,
      required: true
    }
  },
  data() {
    return {
      comments: [], // 所有评论
      newComment: '', // 新评论内容
      replyTo: {}, // 记录正在回复的评论ID
      replyContent: {}, // 回复内容
      isLoggedIn: false, // 用户是否登录
      currentUserId: null // 当前登录用户ID
    };
  },
  computed: {
    // 获取一级评论
    topLevelComments() {
      return this.comments.filter(comment => comment.parentId === 0);
    }
  },
  created() {
    this.checkLoginStatus();
    this.loadComments();
  },
  methods: {
    // 检查登录状态
    checkLoginStatus() {
      this.isLoggedIn = !!this.$store.state.Authorization;
      // 从token中获取用户信息
      if (this.isLoggedIn) {
        // 调用API获取当前用户信息
        this.$axios.post('public/MyInformation', {
          token: this.$store.state.Authorization
        }, {
          headers: {'Authorization': this.$store.state.Authorization}
        }).then(resp => {
          if (resp.status === 200 && resp.data.code === 200) {
            this.currentUserId = resp.data.data.id;
          }
        }).catch(error => {
          console.error('获取用户信息失败:', error);
        });
      }
    },
    
    // 加载评论
    loadComments() {
      this.$axios.post('public/GetNewsComments', {
        newsId: this.newsId,
        token: this.$store.state.Authorization
      }, {
        headers: {'Authorization': this.$store.state.Authorization}
      }).then(resp => {
        console.log('获取评论响应:', resp);
        if (resp.status === 200 && resp.data.code === 200) {
          console.log('评论数据:', resp.data.data);
          this.comments = resp.data.data || [];
          console.log('设置后的评论列表:', this.comments);
        }
      }).catch(error => {
        console.error('加载评论失败:', error);
      });
    },
    
    // 提交评论
    submitComment() {
      if (!this.newComment.trim()) {
        this.$message.warning('评论内容不能为空');
        return;
      }
      
      this.$axios.post('public/AddComment', {
        newsId: this.newsId,
        content: this.newComment,
        parentId: 0,
        token: this.$store.state.Authorization
      }, {
        headers: {'Authorization': this.$store.state.Authorization}
      }).then(resp => {
        console.log('发表评论响应:', resp);
        if (resp.status === 200 && resp.data.code === 200) {
          this.$message.success('评论发表成功');
          this.newComment = '';
          console.log('评论发表成功，重新加载评论');
          this.loadComments();
        } else {
          this.$message.error(resp.data.message || '评论发表失败');
        }
      }).catch(error => {
        console.error('发表评论失败:', error);
        this.$message.error('发表评论失败，请稍后重试');
      });
    },
    
    // 提交回复
    submitReply(parentId, parentUsername) {
      const content = this.replyContent[parentId];
      if (!content || !content.trim()) {
        this.$message.warning('回复内容不能为空');
        return;
      }
      
      this.$axios.post('public/AddComment', {
        newsId: this.newsId,
        content: content,
        parentId: parentId,
        parentUsername: parentUsername,
        token: this.$store.state.Authorization
      }, {
        headers: {'Authorization': this.$store.state.Authorization}
      }).then(resp => {
        if (resp.status === 200 && resp.data.code === 200) {
          this.$message.success('回复成功');
          this.$set(this.replyContent, parentId, '');
          this.$set(this.replyTo, parentId, false);
          this.loadComments();
        } else {
          this.$message.error(resp.data.message || '回复失败');
        }
      }).catch(error => {
        console.error('回复失败:', error);
        this.$message.error('回复失败，请稍后重试');
      });
    },
    
    // 删除评论
    deleteComment(commentId) {
      this.$confirm('确定要删除这条评论吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$axios.post('public/DeleteComment', {
          id: commentId,
          token: this.$store.state.Authorization
        }, {
          headers: {'Authorization': this.$store.state.Authorization}
        }).then(resp => {
          if (resp.status === 200 && resp.data.code === 200) {
            this.$message.success('删除成功');
            this.loadComments();
          } else {
            this.$message.error(resp.data.message || '删除失败');
          }
        }).catch(error => {
          console.error('删除评论失败:', error);
          this.$message.error('删除失败，请稍后重试');
        });
      }).catch(() => {
        // 用户取消删除
      });
    },
    
    // 切换回复状态
    toggleReply(commentId) {
      this.$set(this.replyTo, commentId, !this.replyTo[commentId]);
      if (!this.replyTo[commentId]) {
        // 如果取消回复，清空回复内容
        this.$set(this.replyContent, commentId, '');
      }
    },
    
    // 获取某个评论的回复
    getReplies(parentId) {
      return this.comments.filter(comment => comment.parentId === parentId);
    },
    
    // 格式化时间
    formatTime(time) {
      if (!time) return '';
      const date = new Date(time);
      const now = new Date();
      const diff = now - date;
      
      // 如果是同一天
      if (date.toDateString() === now.toDateString()) {
        if (diff < 3600000) { // 小于1小时
          return Math.floor(diff / 60000) + '分钟前';
        } else {
          return Math.floor(diff / 3600000) + '小时前';
        }
      }
      
      // 如果是昨天
      const yesterday = new Date(now);
      yesterday.setDate(yesterday.getDate() - 1);
      if (date.toDateString() === yesterday.toDateString()) {
        return '昨天 ' + (date.getHours() < 10 ? '0' : '') + date.getHours() + ':' + (date.getMinutes() < 10 ? '0' : '') + date.getMinutes();
      }
      
      // 其他情况显示完整日期
      return date.getFullYear() + '-' + 
             (date.getMonth() + 1 < 10 ? '0' : '') + (date.getMonth() + 1) + '-' + 
             (date.getDate() < 10 ? '0' : '') + date.getDate() + ' ' + 
             (date.getHours() < 10 ? '0' : '') + date.getHours() + ':' + 
             (date.getMinutes() < 10 ? '0' : '') + date.getMinutes();
    }
  }
};
</script>

<style scoped>
.comment-section {
  margin-top: 30px;
  padding: 20px;
  background-color: #f9f9f9;
  border-radius: 4px;
}

.comment-title {
  margin-bottom: 20px;
  font-size: 18px;
  color: #333;
  border-bottom: 1px solid #eee;
  padding-bottom: 10px;
}

.comment-input {
  margin-bottom: 20px;
}

.comment-actions {
  margin-top: 10px;
  text-align: right;
}

.login-tip {
  margin-bottom: 20px;
}

.comment-list {
  margin-top: 20px;
}

.comment-item {
  margin-bottom: 20px;
  padding: 15px;
  background-color: #fff;
  border-radius: 4px;
  border: 1px solid #eee;
}

.comment-header {
  display: flex;
  align-items: center;
  margin-bottom: 8px;
}

.username {
  font-weight: bold;
  color: #409EFF;
  margin-right: 10px;
}

.time {
  color: #999;
  font-size: 12px;
  margin-right: 10px;
}

.delete-btn {
  color: #F56C6C;
  margin-left: auto;
}

.comment-text {
  margin-bottom: 8px;
  line-height: 1.5;
}

.comment-footer {
  text-align: right;
}

.reply-input {
  margin-top: 10px;
  padding: 10px;
  background-color: #f5f5f5;
  border-radius: 4px;
}

.reply-actions {
  margin-top: 8px;
  text-align: right;
}

.replies {
  margin-top: 15px;
  padding-left: 20px;
  border-left: 2px solid #eee;
}

.reply-item {
  margin-bottom: 15px;
  padding: 10px;
  background-color: #f9f9f9;
  border-radius: 4px;
}

.reply-header {
  display: flex;
  align-items: center;
  margin-bottom: 5px;
}

.reply-to {
  color: #409EFF;
  font-size: 12px;
  margin-right: 10px;
}

.reply-text {
  margin-bottom: 5px;
  line-height: 1.5;
}

.reply-footer {
  text-align: right;
}

.no-comments {
  text-align: center;
  padding: 20px;
  color: #999;
}
</style>