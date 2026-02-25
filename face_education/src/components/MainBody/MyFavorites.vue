<template>
    <div class="favorites-container">
        <div class="favorites-header">
            <h3 class="favorites-title">
                <i class="el-icon-star-on"></i>
                我的收藏
                <span class="favorites-count" v-if="total > 0">({{total}})</span>
            </h3>
        </div>
        
        <div class="favorites-list" v-if="favorites.length > 0">
            <div 
                class="favorite-item" 
                v-for="item in favorites" 
                :key="item.id"
                @click="goToNews(item.newsId, item.newsInfo.types)">
                <div class="favorite-content">
                    <h4 class="news-title">{{item.newsInfo.title}}</h4>
                    <div class="news-meta">
                        <span class="meta-item">
                            <i class="el-icon-user"></i>
                            {{item.newsInfo.author}}
                        </span>
                        <span class="meta-item">
                            <i class="el-icon-date"></i>
                            {{formatDate(item.newsInfo.dates)}}
                        </span>
                        <span class="meta-item">
                            <i class="el-icon-view"></i>
                            {{item.newsInfo.hits}}次浏览
                        </span>
                    </div>
                </div>
                <div class="favorite-time">
                    <i class="el-icon-time"></i>
                    收藏于 {{formatDateTime(item.createTime)}}
                </div>
                <div class="favorite-actions">
                    <el-button 
                        type="text" 
                        size="small"
                        @click.stop="cancelFavorite(item.newsId)">
                        <i class="el-icon-delete"></i>
                        取消收藏
                    </el-button>
                </div>
            </div>
        </div>
        
        <div class="empty-state" v-else>
            <i class="el-icon-star-off empty-icon"></i>
            <p>您还没有收藏任何新闻</p>
            <el-button type="primary" @click="goToNewsList">去浏览新闻</el-button>
        </div>
        
        <div class="pagination-wrapper" v-if="total > pageSize">
            <el-pagination
                background
                layout="prev, pager, next"
                :total="total"
                :page-size="pageSize"
                :current-page="currentPage"
                @current-change="handlePageChange">
            </el-pagination>
        </div>
    </div>
</template>

<script>
export default {
    name: "MyFavorites",
    data() {
        return {
            favorites: [],
            currentPage: 1,
            pageSize: 10,
            total: 0
        }
    },
    created() {
        this.loadFavorites()
    },
    methods: {
        loadFavorites() {
            this.$axios.post('public/MyFavorites', {
                token: this.$store.state.Authorization,
                page: this.currentPage,
                pageSize: this.pageSize
            }, {
                headers: {'Authorization': this.$store.state.Authorization}
            }).then(resp => {
                if (resp.status === 200 && resp.data.data) {
                    this.favorites = resp.data.data
                }
            }).catch(() => {
                this.$message.error('加载收藏列表失败')
            })
            
            // 获取总数
            this.$axios.post('public/MyFavoriteCount', {
                token: this.$store.state.Authorization
            }, {
                headers: {'Authorization': this.$store.state.Authorization}
            }).then(resp => {
                if (resp.status === 200) {
                    this.total = resp.data.data || 0
                }
            }).catch(() => {})
        },
        cancelFavorite(newsId) {
            this.$confirm('确定取消收藏该新闻吗？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                this.$axios.post('public/CancelFavorite', {
                    token: this.$store.state.Authorization,
                    newsId: newsId
                }, {
                    headers: {'Authorization': this.$store.state.Authorization}
                }).then(resp => {
                    if (resp.status === 200) {
                        this.$message.success('取消收藏成功')
                        this.loadFavorites()
                    }
                }).catch(() => {
                    this.$message.error('取消收藏失败')
                })
            }).catch(() => {})
        },
        goToNews(newsId, types) {
            this.$router.push({
                path: '/NewText',
                query: { id: newsId, types: types }
            })
        },
        goToNewsList() {
            this.$router.push('/NewsPage')
        },
        handlePageChange(page) {
            this.currentPage = page
            this.loadFavorites()
        },
        formatDate(date) {
            if (!date) return ''
            if (typeof date === 'string') return date.split(' ')[0]
            const d = new Date(date)
            return d.getFullYear() + '-' + String(d.getMonth() + 1).padStart(2, '0') + '-' + String(d.getDate()).padStart(2, '0')
        },
        formatDateTime(date) {
            if (!date) return ''
            if (typeof date === 'string') {
                const parts = date.split(' ')
                if (parts.length > 1) return parts[0]
                return date
            }
            const d = new Date(date)
            return d.getFullYear() + '-' + String(d.getMonth() + 1).padStart(2, '0') + '-' + String(d.getDate()).padStart(2, '0')
        }
    }
}
</script>

<style scoped>
.favorites-container {
    padding: 20px;
    background: #fff;
    min-height: 500px;
}

.favorites-header {
    margin-bottom: 20px;
    padding-bottom: 15px;
    border-bottom: 2px solid #f0f0f0;
}

.favorites-title {
    font-size: 20px;
    color: #333;
    margin: 0;
    display: flex;
    align-items: center;
}

.favorites-title i {
    color: #f0ad4e;
    margin-right: 8px;
    font-size: 24px;
}

.favorites-count {
    font-size: 14px;
    color: #999;
    margin-left: 8px;
    font-weight: normal;
}

.favorites-list {
    display: flex;
    flex-direction: column;
    gap: 15px;
}

.favorite-item {
    display: flex;
    align-items: center;
    padding: 20px;
    background: #fafafa;
    border-radius: 8px;
    border: 1px solid #e8e8e8;
    transition: all 0.3s ease;
    cursor: pointer;
}

.favorite-item:hover {
    background: #f0f7ff;
    border-color: #1890ff;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.09);
}

.favorite-content {
    flex: 1;
    min-width: 0;
}

.news-title {
    font-size: 16px;
    color: #333;
    margin: 0 0 10px 0;
    font-weight: 500;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
}

.news-meta {
    display: flex;
    gap: 20px;
    font-size: 13px;
    color: #666;
}

.meta-item {
    display: flex;
    align-items: center;
    gap: 5px;
}

.meta-item i {
    color: #999;
    font-size: 12px;
}

.favorite-time {
    font-size: 13px;
    color: #999;
    margin: 0 20px;
    white-space: nowrap;
    display: flex;
    align-items: center;
    gap: 5px;
}

.favorite-time i {
    color: #1890ff;
}

.favorite-actions {
    white-space: nowrap;
}

.empty-state {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    padding: 80px 20px;
    color: #999;
}

.empty-icon {
    font-size: 80px;
    color: #ddd;
    margin-bottom: 20px;
}

.empty-state p {
    font-size: 16px;
    margin-bottom: 20px;
}

.pagination-wrapper {
    margin-top: 30px;
    display: flex;
    justify-content: center;
}
</style>
