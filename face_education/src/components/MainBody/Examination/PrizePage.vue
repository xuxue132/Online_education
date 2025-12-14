<template>
    <div class="main_body">
        <HeadTitle></HeadTitle>
        <div class="head_picture"></div>
        <div class="content row">
            <div class="sub_left">
                <h2 class="title_left flag1">有奖竞答</h2>
                <ul class="flag2 sub_menu">
                    <li>
                        <a  title="知识竞答" href="/ExaminationPage">知识竞答</a>
                    </li>
                    
                    <li>
                        <a  title="奖品兑换" href="/PrizePage" class="chs">奖品兑换</a>
                    </li>
                
                </ul>
            </div>
            <div class="sub_right">
                <div class="bread fs14">
                    <a href="/FirstPage">首页</a>
                    &nbsp;-&nbsp;
                    <span>有奖竞答</span>
                </div>
    
    
                <div>
                    <div class="card">
                        <div class="card_list" v-for="Good in Goods">
                            <ul class="card_ul">
                                <div class="card_img">
                                    <img class="card_img_size" :src="Good.picture">
                                </div>
                                <div class="card_list_li"><span>{{Good.tradeName}}</span></div>
                                <div class="card_list_li_two">{{Good.points}}分</div>
                                <div class="but">
                                    <div style="margin: auto">
                                        <el-button type="primary" round @click="TurnPage(Good.id)">立即兑换</el-button>
                                    </div>
                                </div>
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
        name: "PrizePage",
        components: {HeadTitle},
        data(){
            return{
                page:1,
                number:0,
                Goods:[],
            }
        },
        created(){
            this.NewGoods();
        },

        methods: {
            handleCurrentChange(i){
                this.GetGoods(i)
            },
            TurnPage(id){
                this.$router.push({
                    path: '/PrizeText',
                    query: {
                        id: id,
                    }

                })
            },
            NewGoods() {
                this.$axios.post('public/GoodsNumber', {
                }, {
                    headers: {'Authorization': this.$store.state.Authorization}
                }).then(resp => {
                    if (resp.status === 200) {
                        this.number = resp.data.data
                        this.GetGoods(this.page)
                    }
                }).catch(resp => {
                })

            },
            GetGoods(page) {
                this.$axios.post('public/Goods', {
                    NowPage: page
                }, {
                    headers: {'Authorization': this.$store.state.Authorization}
                }).then(resp => {
                    if (resp.status === 200) {
                        this.Goods = resp.data.data
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

    .page_title h3 {
        padding: 25px 0;
        color: #333;
        text-align: center;
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
        margin: 10px;
        width: 241px;
        height: 296px;
        background: #fff;
        border: 1px solid #E2E6ED;
        overflow: hidden;
    }
    .card_img {
        margin: auto;
        width: 221px;
        height: 145px;
        text-align: center;
    }
    .card_img  img{
        height: 80%;
        width: 80%;
        padding: 20px 20px;
    }
    .card_list{
        margin: 20px 20px;
    }
    .card_list_li{
        width: 221px;
        height: 30px;
        font-size: 18px;
        font-weight: bold;
        color: #363636;
        margin: 10px auto;
        overflow: hidden;
        text-align: center;
        text-overflow:ellipsis;
        white-space:nowrap;
        word-break:keep-all;
        line-height: 29px;
        
    }
    .card_list_li span{
        font-size: 18px;
        font-weight: bold;
        color: #363636;
        margin-top: 12px;
    }
    .card_list_li_two{
        width: 221px;
        font-size: 15px;
        color: red;
        margin: 20px auto;
        text-align: center;
        overflow:hidden;
        text-overflow:ellipsis;
        white-space:nowrap;
        word-break:keep-all;
    }
    .card_img_size{
        max-height: 100%;
        max-width: 100%;
    }
    
    .but{
        width: 100%;
        margin-top: 20px;
        margin-left: 66px;
    }
</style>