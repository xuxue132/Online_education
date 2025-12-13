<template>
    <div class="main_body">
        <HeadTitle></HeadTitle>
        <div class="head_picture">
        </div>

        <div class="intro">
            <div class="brea">
                <el-breadcrumb separator-class="el-icon-arrow-right">
                    <el-breadcrumb-item :to="{ path: '/ReadPage' }">阅读打卡</el-breadcrumb-item>
                    <el-breadcrumb-item>目录</el-breadcrumb-item>
                </el-breadcrumb>
            </div>
    
            <div class="booklist">
                <div class="bheadr">
                    <h1>{{this.book.nameBook}}</h1>
                    <img :src="this.book.picture" class="book-img">
                    <p>发布时间: {{this.book.timeBook}}</p>
                    <p>{{this.book.author}}</p>
                    <p class="des">简介{{this.book.introduce}}</p>
                </div>
        
        
                <div class="line"></div>
                <div class="book_mulu">
                    <ul>
                        <li v-for="cb in Content">
                            <div >
                                <el-link class="con01" :underline="false" @click="content(cb)">{{cb.catalog}}</el-link>
                            </div>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
        
    </div>
</template>

<script>
    import HeadTitle from "@/components/MainBody/HeadTitle";
    export default {
        name: "ReadBookContent",
        components: {HeadTitle},
        data() {
            return {
                book:{},
                Content:[],
                // Notices:[],
                // number:0,
                // page:1,
            }
        },
        created(){
            this.OneBook()
            this.TheBook();
        },
        methods: {
            content(cbook) {
                this.$router.push({
                    path: '/ReadText',
                    query: {
                        id: cbook.id,
                        idBook: cbook.idBook,
                    }

                })
            },
            TheBook() {
                this.$axios.post('public/TheBook', {
                    id_book: this.$route.query.id
                }, {
                    headers: {'Authorization': this.$store.state.Authorization}
                }).then(resp => {
                    if (resp.status === 200) {
                        this.Content = resp.data.data
                        // this.number = resp.data.data

                    }
                }).catch(resp => {
                })
            },
            OneBook() {
                this.$axios.post('public/OneBook', {
                    id_book: this.$route.query.id
                }, {
                    headers: {'Authorization': this.$store.state.Authorization}
                }).then(resp => {
                    if (resp.status === 200) {
                        this.book = resp.data.data
                  

                    }
                }).catch(resp => {
                })
            },
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
        /*background: url("https://api.ixiaowai.cn/gqapi/gqapi.php");*/
        background-size: cover;
    }
    .intro {
        margin: auto;
        width: 70%;
        padding: 38px;
    }
    ._left {
        float: left;
        margin-right: 30px;
        height: 160px;
    }
    ._left img {
        max-width: 80px;
        min-width: 140px;
        height: auto;
    }
    .con01{
        font-size: 20px;
        line-height: 25px;
    }
    .title {
        padding: 0;
        font-size: 20px;
    }
    .title h3 {
        font-size: 32px;
    }
    .kecheng_info {
        padding: 10px 0;
        line-height: 22px;
    }
    ._item {
        margin-bottom: 25px;
    }

    .info_top {
        width: 200px;
    }
    ._item p {
        font-size: 15px;
        color: #565656;
    }
    .con01 .intro .kecheng_info ._item span {
        color: #969696;
    }
    ._right{
        float: left;
        width: 80%;
        margin-left: 20px;
    }

    
    
    .book-img {
        width: 100px;
        float: left;
        margin: 0 10px;
    }

    .booklist {
        overflow: hidden;
        box-shadow: 0 0 2px #ccc;
        padding: 10px;
        background-color: #ffffff;
    }

    .des {
        font-size: 13px;
        color: #666;
        margin-bottom: 10px;
        line-height: 23px;
    }

    .catalog {
        width: 654px;
        margin: 0 auto;
    }

    .line {
        height: 2px;
        background: #eee;
        margin: 20px 0;
    }

    .book_mulu {
        padding: 10px;
    }

    .book_mulu ul li div {
        display: block;
        padding: 5px;
        color: black;
    }

    li {
        list-style: none;
    }

    .bheadr {
        overflow: hidden;
        padding: 20px 20px ;
    }
    .bheadr h1{
        overflow: hidden;
        padding: 20px 10px ;
    }

    .bheadr p{
        overflow: hidden;
        padding: 5px 5px ;
        font-size: 16px;
    }

    .brea {
        height: 20px;
        margin-top: 20px;
    }
</style>