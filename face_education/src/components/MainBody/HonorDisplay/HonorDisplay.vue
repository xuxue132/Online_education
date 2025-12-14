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
                    <span>荣誉展示</span>
                </div>
                <div>
                    <div class="card">
                        <div class="card_list" v-for="Honor in Honors">
                            <ul class="card_ul">
                                <li class="card_img">
                                    <a @click="TurnPage(Honor.id)">
                                        <img class="card_img_size" :src="Honor.images">
                                    </a>
                                </li>
                                <li class="card_list_li">{{Honor.title}}</li>
                                
                            </ul>
                        
                        </div>
                    
                    </div>
                </div>
                <div style="clear: left">
                    <el-pagination
                            style="margin-left: 40%"
                            @current-change="handleCurrentChange"
                            background
                            layout="prev, pager, next"
                            :total="this.number">
                    </el-pagination>
                </div>
                <div class="bottoms"></div>
            </div>
        
        </div>
    
    
    </div>
</template>

<script>
    import HeadTitle from "@/components/MainBody/HeadTitle";
    export default {
        name: "HonorDisplay",
        components: {HeadTitle},
        data(){
            return{
                number:0,
                page:1,
                Honors:[]
            }
        },
        created(){
            this.NewHonorPage();
        },

        methods: {
            handleCurrentChange(i){
                this.GetHonor(i)
            },
            TurnPage(id){
                this.$router.push({
                    path: '/HonorText',
                    query: {
                        id: id,
                    }

                })
            },
            NewHonorPage() {
                this.$axios.post('public/AllHonorSum', {
                }, {
                    headers: {'Authorization': this.$store.state.Authorization}
                }).then(resp => {
                    if (resp.status === 200) {
                        this.number = resp.data.data
                        this.GetHonor(this.page)
                    }
                }).catch(resp => {
                })

            },
            GetHonor(page) {
                this.$axios.post('public/AllHonor', {
                    NowPage: page
                }, {
                    headers: {'Authorization': this.$store.state.Authorization}
                }).then(resp => {
                    if (resp.status === 200) {
                        console.log(resp.data.data)
                        this.Honors = resp.data.data
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
    
    .page_title h3 {
        padding: 25px 0;
        color: #333;
        text-align: center;
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
    .card{
        margin-left: 20px;
        width: 100%;
        background: #ff941f;
    }
    
    .card_ul{
        float: left;
        margin: 20px;
        width: 241px;
        height: 246px;
        background: #fff;
        border: 1px solid #E2E6ED;
        border-radius: 8px;
    }
    .card_img {
        width: 241px;
        height: 145px;
    }
    .card_list{
        margin: 20px 20px;
    }
    .card_list_li{
        width: 221px;
        height: 75px;
        font-size: 17px;
        color: black;
        line-height: 23px;
        font-weight: bold;
        margin-top: 20px;
        overflow:hidden;
        text-overflow:ellipsis;
        white-space:nowrap;
        word-break:keep-all;
    }
    .card_img_size{
        height: 100%;
        width: 100%;
    }
</style>