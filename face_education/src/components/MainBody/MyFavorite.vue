<template>
    <div class="favorite-container">
        <div class="favorite-header">
            <h3>我的收藏</h3>
            <span class="favorite-count">共 {{total}} 条收藏</span>
        </div>
        
        <div class="favorite-list" v-if="favoriteList.length > 0">
            <div class="favorite-item" v-for="item in favoriteList" :key="item.favoriteId">
                <div class="item-content" @click="goToDetail(item)">
                    <div class="item-title">{{item.title}}</div>
                    <div class="item-meta">
                        <span class="meta-item">
                            <i class="el-icon-user"></i>
                            {{item.author}}
                        </span>
                        <span class="meta-item">
                            <i class="el-icon-view"></i>
                            {{item.hits}}次浏览
                        </span>
                        <span class="meta-item">
                            <i class="el-icon-time"></i>
                            收藏于 {{formatDate(item.createTime)}}
                        </span>
                    </div>
                </div>
                <div class="item-action">
                    <el-button type="danger" size="small" plain @click.stop="removeFavorite(item)">
                        取消收藏
                    </el-button>
                </div>
            </div>
        </div>
        
        <div class="empty-state" v-else>
            <i class="el-icon-star-off"></i>
            <p>暂无收藏内容</p>
            <el-button type="primary" @click="goToNews">去浏览新闻</el-button>
        </div>
        
        <div class="pagination-wrapper" v-if="total > pageSize">
            <el-pagination
                @current-change="handleCurrentChange"
                :current-page="currentPage"
                :page-size="pageSize"
                layout="prev, pager, next"
                :total="total">
            </el-pagination>
        </div>
    </div>
</template>

<script>
export default {
    name: "MyFavorite",
    data() {
        return {
            favoriteList: [],
            total: 0,
            currentPage: 1,
            pageSize: 10,
            userId: null
        }
    },
    created() {
        this.getUserId();
    },
    methods: {
        getUserId() {
            this.$axios.post('public/MyInformation', {
                token: this.$store.state.Authorization
            }, {
                headers: {'Authorization': this.$store.state.Authorization}
            }).then(resp => {
                if (resp.status === 200) {
                    this.userId = resp.data.data.id;
                    this.getFavoriteList();
                }
            }).catch(resp => {
            })
        },
        getFavoriteList() {
            if (!this.userId) return;
            this.$axios.post('public/FavoriteList', {
                userId: this.userId,
                page: this.currentPage,
                pageSize: this.pageSize
            }, {
                headers: {'Authorization': this.$store.state.Authorization}
            }).then(resp => {
                if (resp.status === 200) {
                    this.favoriteList = resp.data.data.list;
                    this.total = resp.data.data.total;
                }
            }).catch(resp => {
            })
        },
        handleCurrentChange(page) {
            this.currentPage = page;
            this.getFavoriteList();
        },
        formatDate(date) {
            if (!date) return '';
            if (typeof date === 'string') {
                return date.substring(0, 10);
            }
            let d = new Date(date);
            let year = d.getFullYear();
            let month = d.getMonth() + 1;
            let day = d.getDate();
            if (month < 10) month = "0" + month;
            if (day < 10) day = "0" + day;
            return year + "-" + month + "-" + day;
        },
        goToDetail(item) {
            this.$router.push({
                path: '/NewText',
                query: {
                    id: item.newsId,
                    types: 0
                }
            });
        },
        goToNews() {
            this.$router.push('/NewsPage');
        },
        removeFavorite(item) {
            this.$confirm('确定要取消收藏该新闻吗？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                this.$axios.post('public/RemoveFavorite', {
                    userId: this.userId,
                    newsId: item.newsId
                }, {
                    headers: {'Authorization': this.$store.state.Authorization}
                }).then(resp => {
                    if (resp.status === 200) {
                        this.$message.success('取消收藏成功');
                        this.getFavoriteList();
                    }
                }).catch(resp => {
                })
            }).catch(() => {});
        }
    }
}
</script>

<style scoped>
.favorite-container {
    padding: 20px;
    background: #fff;
    min-height: 400px;
}

.favorite-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding-bottom: 20px;
    border-bottom: 1px solid #eee;
    margin-bottom: 20px;
}

.favorite-header h3 {
    margin: 0;
    font-size: 20px;
    color: #333;
}

.favorite-count {
    color: #999;
    font-size: 14px;
}

.favorite-list {
    margin-bottom: 20px;
}

.favorite-item {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 20px;
    border: 1px solid #f0f0f0;
    border-radius: 8px;
    margin-bottom: 15px;
    transition: all 0.3s ease;
    background: #fafafa;
}

.favorite-item:hover {
    box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
    border-color: #409EFF;
    background: #fff;
}

.item-content {
    flex: 1;
    cursor: pointer;
}

.item-title {
    font-size: 16px;
    color: #333;
    font-weight: 500;
    margin-bottom: 10px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
}

.item-title:hover {
    color: #409EFF;
}

.item-meta {
    display: flex;
    gap: 20px;
    color: #999;
    font-size: 13px;
}

.meta-item {
    display: flex;
    align-items: center;
    gap: 5px;
}

.item-action {
    margin-left: 20px;
}

.empty-state {
    text-align: center;
    padding: 60px 0;
    color: #999;
}

.empty-state i {
    font-size: 60px;
    color: #ddd;
    margin-bottom: 20px;
}

.empty-state p {
    font-size: 16px;
    margin-bottom: 20px;
}

.pagination-wrapper {
    display: flex;
    justify-content: center;
    padding: 20px 0;
}
</style>
