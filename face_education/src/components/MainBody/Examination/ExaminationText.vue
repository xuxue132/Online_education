<template>
    <div class="main_body">
        <HeadTitle></HeadTitle>
        <div class="head_picture"></div>
    
        <div class="content row">
            <div class="sub_left">
                <h2 class="title_left flag1">有奖竞答</h2>
                <ul class="flag2 sub_menu">
                    <li>
                        <a  title="新闻列表" href="/ExaminationPage" class="chs">知识竞答</a>
                    </li>
                
                    <li>
                        <a  title="公告列表" href="/PrizePage">奖品兑换</a>
                    </li>
            
                </ul>
            </div>
            <div class="sub_right">
                <div class="bread fs11">
                    <a href="/FirstPage">首页</a>
                    &nbsp;-&nbsp;
                    <a href="/ExaminationPage" >有奖竞答</a>
                    &nbsp;-&nbsp;
                    <span >知识竞答</span>
                </div>
                <div class="page_content">
                    <div class="page_title">
                        <h3 class="fs24">试题列表</h3>
                    </div>
                </div>
            
                <div>
                        <div class="lists">
                            <ul class="ul_list" v-for="(titles,i) in Title">
                                <div class="title1 fs14">{{titles.title}}</div>

                                <li v-for="(Options,h) in Option" v-if="Options.titleId === titles.id">
                                    <el-radio-group  v-model="num[i]">
                                        <el-radio v-if="index === 0 && Options.titleId === titles.id" :label="Options.optionValue" class="blocks">{{Options.optionValue}}.{{Options.optionS}}</el-radio>
                                        <el-radio v-if="index === 1  && Options.titleId === titles.id" disabled :label="Options.optionValue" class="blocks">{{Options.optionValue}}.{{Options.optionS}}</el-radio>
                                    </el-radio-group>
                                </li>
                                <div class="rightKey" v-if="index === 1 && num[i]!== titles.rightKey">
                                    正确答案： {{titles.rightKey}}
                                </div>
                            </ul>
                            <div style="height: 100px">
                                <el-button v-if="index===0" style="margin-top: 30px;float: right" @click="checks()" type="primary">提交</el-button>
                                <el-button disabled v-if="index===1" style="margin-top: 30px;float: right"  type="primary">已提交</el-button>
                            </div>
                            
                        </div>
                </div>
        
        
            </div>
        </div>
    
        
    </div>
</template>

<script>
    import HeadTitle from "@/components/MainBody/HeadTitle";
    export default {
        name: "ExaminationText",
        components: {HeadTitle},
        data(){
          return{
              Title:[],
              num:[],
              Option:[],
              Option1:[],
              radio:['A','B','C','D'],
              All:[],
              Option5:[],
              Option2:[],
              Option3:[],
              Option4:[],
              index: 0,
              answers: 0,
              NewAnswer:[]
          }
        },
        created(){
            this.Options()
        },
        methods:{
            checks(){
                if(this.index === 0){
                    for(let b=0;b<this.num.length;b++){
                        if(this.Title[b].rightKey === this.num[b]){
                            this.answers++
                        }
                    }
                    this.$message({
                        message: '答对'+this.answers+'成功，获得'+this.answers+'积分',
                        type: 'success'
                    });
                   

                    for(let y=0;y<this.num.length;y++){
                        if(this.num[y] !== null || this.num[y] !== '') {
                            this.$axios.post('public/InsertAnswer', {
                                paperId: this.$route.query.id,
                                titleId: this.Title[y].id,
                                answers: this.num[y],
                                token: this.$store.state.Authorization
                            }, {
                                headers: {'Authorization': this.$store.state.Authorization}
                            }).then(resp => {
                                if (resp.status === 200) {
                                }
                            }).catch(resp => {
                            })
                        }
                    }
                    this.$axios.post('public/AddAnswer', {
                        sorce: this.answers,
                        token: this.$store.state.Authorization
                    }, {
                        headers: {'Authorization': this.$store.state.Authorization}
                    }).then(resp => {
                        if (resp.status === 200) {
                        }
                    }).catch(resp => {
                    })
                    this.$router.go(0);
                }
            },
            
            Options(){
                
                this.$axios.post('public/PaperTitle', {
                    id_paper: this.$route.query.id
                }, {
                    headers: {'Authorization': this.$store.state.Authorization}
                }).then(resp => {
                    if (resp.status === 200) {
                        this.Title = resp.data.data
                        this.OptionPlus()

                    }
                }).catch(resp => {
                })

                this.$axios.post('public/PaperOptions', {
                    paper_id: this.$route.query.id
                }, {
                    headers: {'Authorization': this.$store.state.Authorization}
                }).then(resp => {
                    if (resp.status === 200) {
                        this.Option = resp.data.data
                        let j=0,h=0,c=0,m=0,k=0
                        
                        for(let i=0;i<resp.data.data.length;i++){
                            if(this.Option[i].titleId === this.Title[0].id){
                                this.Option1[j++] = (this.Option[i])
                            }
                            else if(this.Option[i].titleId === this.Title[1].id){
                                this.Option2[h++] = this.Option[i]
                            }
                            else if(this.Option[i].titleId === this.Title[2].id){
                                this.Option3[c++] = this.Option[i]
                            }
                            else if(this.Option[i].titleId === this.Title[3].id){
                                this.Option4[m++] = this.Option[i]
                            } else if(this.Option[i].titleId === this.Title[4].id) {
                                this.Option5[k++] = this.Option[i]
                            }
                            
                        }
                        this.All.push(this.Option1)
                        this.All.push(this.Option2)
                        this.All.push(this.Option3)
                        this.All.push(this.Option4)
                        this.All.push(this.Option5)
                 
                    }
                }).catch(resp => {
                
                })
            },

            OptionPlus(){
                this.$axios.post('public/PaperAnswer', {
                    paperId: this.$route.query.id,
                    token: this.$store.state.Authorization
                }, {
                    headers: {'Authorization': this.$store.state.Authorization}
                }).then(resp => {
                    if (resp.status === 200) {
                        this.NewAnswer = resp.data.data

                        if(this.NewAnswer.length !== 0){
                            this.index = 1
                            let q = 0
                            for(let l = 0;l<this.Title.length;l++){
                                for(let r = 0 ;r<this.NewAnswer.length;r++){
                                    if(this.Title[l].id === this.NewAnswer[r].titleId)
                                        if(this.NewAnswer[r].answers !== null || this.NewAnswer[r].answers !== ''){
                                            this.num[q++] = this.NewAnswer[r].answers
                                        }

                                }
                            }

                        }

                    }
                }).catch(resp => {
                })
            }
          
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
    }
    .fs24 {
        font-size: 24px;
    }
    
    .lists{
        margin-left: 20px;
    }
    .lists li {
        width: 100%;
    }
    .time {
        color: #999;
        padding: 0 0 0 27px;
        background: url("https://www.cqut.edu.cn/images/sub07_1_time2.png") left center no-repeat;
        line-height: 20px;
        font-family: arial;
    }
    .title {
        display: block;
        overflow: hidden;
        text-overflow: ellipsis;
        margin: 6px 0 0;
        height: 26px;
        line-height: 26px;
        white-space: nowrap;
        color: #333;
    }
    .title1 {
        margin: 6px 0 0;
        line-height: 46px;
        color: #333;
    }
    
    .sub_list073 ul {
        padding: 12px 0 21px;
    }
    .fs16 {
        font-size: 16px;
    }
    
    .info{
        display: block;
        margin: 0 0 30px;
        padding: 13px 15px;
        border: 1px solid #f2f2f2;
        border-left: 5px solid #e2e2e2;
        cursor:pointer;
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
        font-size: 28px;
        color: black;
    }
    .chs{
        background: #f0ad4e;
        color: #fff;
        border-color: #F0AD4E;
    }
    .ul_list{
        width: 100%;
        margin-top: 30px;
    }
    .blocks{
        display:block;
        font-size: 34px;
    }
    .rightKey{
        float: right;
        color: red;
    }
</style>