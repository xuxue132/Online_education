<template>
    <div class="main_body">
        <HeadTitle></HeadTitle>
        <div class="head_picture">
        </div>
        <div class="content row">
            <div class="sub_right">
                <div class="bread fs14">
                    <a href="/FirstPage">首页</a>
                    &nbsp;-&nbsp;
                    <a href="/HonorDisplay">荣誉展示</a>
                    &nbsp;-&nbsp;
                    <span>正文</span>
                </div>
                <div class="page_content">
                    <div class="page_title">
                        <h3 class="fs24">{{Honor.title}}</h3>
                    </div>
                </div>

                
                <div style="margin-top: 50px">
                    <div class="lists" >
                        <div class="ql-editor" v-html="Honor.texts"></div>
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
        name: "HonorText",
        components: {HeadTitle},
        data(){
            return{
                Honor:{}
            }
        },

        created(){
            this.NewHonor();
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
            NewHonor() {
                this.$axios.post('public/AnHonor', {
                    id: this.$route.query.id,
                }, {
                    headers: {'Authorization': this.$store.state.Authorization}
                }).then(resp => {
                    if (resp.status === 200) {
                        this.Honor = resp.data.data
                        this.Honor.dates = this.formatDate(new Date(this.Honor.dates))
                    }
                }).catch(resp => {
                })
            }
        }
    }
</script>

<style scoped>
    @import '../../editor.css';
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

    .content {
        position: relative;
        z-index: 2;
    }
    
    .row {
        width: 1500px;
        margin: 0 auto;
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
        font-size: 18px;
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

    .bottoms{
        height: 20px;
    }
</style>