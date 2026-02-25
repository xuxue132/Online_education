<template>
    <div class="my-collection">
        <div class="collection-header">
            <h3>我的收藏</h3>
            <span class="collection-count">共 {{ favorites.length }} 条收藏</span>
        </div>
        <div class="collection-list" v-if="favorites.length > 0">
            <div 
                class="collection-item" 
                v-for="item in favorites" 
                :key="item.id"
                @click="goToDetail(item)">
                <div class="item-left">
                    <div class="item-icon">
                        <i class="el-icon-document"></i>
                    </div>
                </div>
                <div class="item-content">
                    <div class="item-title">{{ item.title }}</div>
                    <div class="item-info">
                        <span class="info-item">
                            <i class="el-icon-time"></i>
                            收藏于 {{ item.favoriteTime }}
                        </span>
                        <span class="info-item">
                            <i class="el-icon-star-on"></i>
                            {{ item.favoriteCount }} 人收藏
                        </span>
                    </div>
                </div>
                <div class="item-right">
                    <i class="el-icon-arrow-right"></i>
                </div>
            </div>
        </div>
        <div class="empty-state" v-else>
            <i class="el-icon-folder-opened"></i>
            <p>暂无收藏的新闻</p>
            <el-button type="primary" size="small" @click="goToNews">去看看新闻</el-button>
        </div>
    </div>
</template>

<script>
export default {
    name: "MyCollection",
    data() {
        return {
            favorites: []
        }
    },
    created() {
        this.loadMyFavorites();
    },
    methods: {
        loadMyFavorites() {
            this.$axios.post('public/MyFavorites', {
                token: this.$store.state.Authorization
            }, {
                headers: {'Authorization': this.$store.state.Authorization}
            }).then(resp => {
                if (resp.status === 200 && resp.data.data) {
                    this.favorites = resp.data.data.map(item => ({
                        ...item,
                        favoriteTime: this.formatDate(item.createTime)
                    }));
                }
            }).catch(resp => {
                console.error('加载收藏列表失败', resp);
            });
        },
        formatDate(date) {
            if (!date) return '';
            if (typeof date === 'string') {
                if (date.includes('T')) {
                    date = new Date(date);
                } else {
                    return date.split(' ')[0];
                }
            }
            const year = date.getFullYear();
            let month = date.getMonth() + 1;
            let day = date.getDate();
            if (month < 10) month = "0" + month;
            if (day < 10) day = "0" + day;
            return year + "-" + month + "-" + day;
        },
        goToDetail(item) {
            this.$router.push({
                path: '/NewText',
                query: {
                    id: item.newsId,
                    types: item.type
                }
            });
        },
        goToNews() {
            this.$router.push('/NewsPage');
        }
    }
}
</script>

<style scoped>
.my-collection {
    padding: 20px;
    background: #fff;
}
.collection-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding-bottom: 15px;
    border-bottom: 1px solid #eee;
    margin-bottom: 15px;
}
.collection-header h3 {
    margin: 0;
    font-size: 18px;
    color: #333;
}
.collection-count {
    font-size: 14px;
    color: #999;
}
.collection-list {
    display: flex;
    flex-direction: column;
    gap: 12px;
}
.collection-item {
    display: flex;
    align-items: center;
    padding: 15px;
    background: #fafafa;
    border-radius: 8px;
    cursor: pointer;
    transition: all 0.3s;
    border: 1px solid #f0f0f0;
}
.collection-item:hover {
    background: #f0f7ff;
    border-color: #409EFF;
    box-shadow: 0 2px 12px rgba(64, 158, 255, 0.15);
}
.item-left {
    margin-right: 15px;
}
.item-icon {
    width: 50px;
    height: 50px;
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    border-radius: 8px;
    display: flex;
    align-items: center;
    justify-content: center;
}
.item-icon i {
    font-size: 24px;
    color: #fff;
}
.item-content {
    flex: 1;
    min-width: 0;
}
.item-title {
    font-size: 16px;
    color: #333;
    font-weight: 500;
    margin-bottom: 8px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
}
.item-info {
    display: flex;
    gap: 20px;
    font-size: 13px;
    color: #999;
}
.info-item {
    display: flex;
    align-items: center;
    gap: 4px;
}
.info-item i {
    color: #c0c4cc;
}
.item-right {
    color: #c0c4cc;
    font-size: 18px;
}
.empty-state {
    text-align: center;
    padding: 60px 20px;
    color: #999;
}
.empty-state i {
    font-size: 80px;
    color: #e0e0e0;
    margin-bottom: 15px;
    display: block;
}
.empty-state p {
    font-size: 16px;
    margin-bottom: 20px;
}
</style>
