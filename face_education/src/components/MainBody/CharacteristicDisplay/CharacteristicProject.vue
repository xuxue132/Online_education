<template>
    <div class="main_body">
        <HeadTitle></HeadTitle>
        <div class="head_picture"></div>
        
        <div class="min_body">
            
            <div class="min_title">当前位置：<a href="/FirstPage">首页&gt;</a><a href="">特色项目</a></div>
    
            <div class="head_min">
                <ul class="ul_list" v-for="CharacterProject in CharacterProjects">
                    <li>
                        <div class="head_min_left">
                            <img :src="CharacterProject.images" alt="">
                        </div>
                        <div class="head_min_right">
                            <h3>{{CharacterProject.title}}</h3>
                            <div>
                                <div class="_project_left">
                                    <span>地区： {{CharacterProject.area}}</span>
                                    <span style="margin-left: 20px">时间：{{CharacterProject.dates}}</span>
                                </div>
                                <div class="_project_left">
                                    <span>类别：{{CharacterProject.types}}</span>
                                </div>
                            </div>
                            <el-button type="primary" round @click="InTo(CharacterProject.id)">详情</el-button>
                        </div>
                        
                    </li>
                </ul>
              
                
            </div>
            <div>
                <el-pagination
                        style="margin-left: 40%"
                        @current-change="handleCurrentChange"
                        background
                        layout="prev, pager, next"
                        :total="this.number">
                </el-pagination>
            </div>
            
            <div style="height: 30px"></div>
        </div>
        
    </div>
</template>

<script>
    import HeadTitle from "@/components/MainBody/HeadTitle";
    export default {
        name: "CharacteristicProject",
        components: {HeadTitle},
        data() {
            return {
                activeIndex: '1',
                CharacterProjects:[],
                number:0,
                page:1,
            };
        },
        created(){
            this.NewPage();
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
            InTo(id){
                this.$router.push({
                    path: '/CharacterText',
                    query: {
                        id: id,
                    }

                })
            },
            
            handleCurrentChange(i){
                this.NewProject(i)
            },
            NewPage() {
                this.$axios.post('public/CharacterProjectNumber', {
                }, {
                    headers: {'Authorization': this.$store.state.Authorization}
                }).then(resp => {
                    if (resp.status === 200) {
                        this.number = resp.data.data
                        this.NewProject(this.page)
                    }
                }).catch(resp => {
                })

            },
            NewProject(page){
                this.$axios.post('public/CharacterProject', {
                    NowPage: page
                }, {
                    headers: {'Authorization': this.$store.state.Authorization}
                }).then(resp => {
                    if (resp.status === 200) {
                        this.CharacterProjects = resp.data.data
                        for (var i=0;i<this.CharacterProjects.length;i++) {
                            this.CharacterProjects[i].dates = this.formatDate(new Date(this.CharacterProjects[i].dates))
                        }

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
    .min_body{
        width: 1260px;
        margin: 0 auto;
    }

    .min_title {
        font-size: 14px;
        color: #767676;
        line-height: 70px;
    }
   

    .head_min{
        width: 100%;
        margin-top: 50px;
    }
    .head_min ul{
        margin-top: 50px;
    }

    .head_min_left{
        float: left;
        min-width: 222px;
        height: 172px;
        max-width: 222px;
        margin-right: 24px;
        overflow: hidden;
        padding: 10px 10px;
    }
    .head_min_left img{
        width: 100%;
        height: 100%;
    }
   
    .head_min_right{
        display: inline;
        width: 70%;
        float: left;
        margin-left: 20px;
        line-height: 40px;
    }
    ._project_left {
        font-size: 14px;
        color: #363636;
        margin-right: 10px;
    }
    .intro{
        width:100%;
        overflow:hidden;
        text-overflow:ellipsis;
        white-space:nowrap;
        word-break:keep-all;
        font-size: 14px;
        color: #868686;
        
    }
    
    .ul_list{
        border-bottom: 1px solid #d9d9d9;
        width: 100%;height: 230px
    }

    ._project_left span i {
        color: #868686;
        padding-right: 5px;
    }
</style>