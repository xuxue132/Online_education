<template>
    <div class="main_body">
        <HeadTitle></HeadTitle>
        <div class="head_picture">
        </div>
        <div class="content row">

            <div class="sub_right">
                <div class="bread fs14">
                    <a href="/ReadPage">阅读打卡</a>
                    -
                    <span>{{book.nameBook}}</span>
                    &nbsp;-&nbsp;
                    <span>正文</span>
                </div>
                <div class="page_content">
                    <div class="page_title">
                        <h3 class="fs24">{{Content.catalog}}</h3>
                    </div>
                </div>
                <div class="newsDetailOneP">
                    {{book.author}}<i></i>&nbsp;&nbsp;&nbsp;&nbsp;
                </div>
                
                <div style="margin-top: 50px">
                    <div class="lists" >
                        <div class="ql-editor" v-html="Content.content"></div>
                    </div>
                </div>
                
                <div style="width: 100px;margin:50px auto">
                    <el-button type="primary" @click="clock()" v-if="this.clocks">打卡</el-button>
                    <el-button type="success" disabled v-if="!this.clocks">已打卡</el-button>
                </div>
                <div class="bottoms"></div>
            </div>
        </div>
    
    
    </div>
</template>

<script>
    import HeadTitle from "@/components/MainBody/HeadTitle";
    export default {
        name: "ReadText",
        components: {HeadTitle},
        data() {
            return {
                Content:{},
                book:{},
                time:0,
                ReadTime:"",
                clocks: true
            }
        },
        created(){
            this.time = new Date().getTime()
            this.GetContent()
        },
        
        methods:{
          clock(){
              var d = new Date().getTime();
              this.ReadTimes(this.time,d)
              this.$axios.post('public/TextClock', {
                  idBook: this.$route.query.idBook,
                  contentId: this.$route.query.id,
                  token: this.$store.state.Authorization,
                  readTime: this.ReadTime,
                  sorce:1,
              }, {
                  headers: {'Authorization': this.$store.state.Authorization}
              }).then(resp => {
                  if (resp.status === 200) {
                      this.$message({
                          message: '打卡成功，获得1积分',
                          type: 'success'
                      });
                      this.$router.go(0);
                      // this.number = resp.data.data

                  }
              }).catch(resp => {
              })
          },
          ReadTimes (beginTime, endTime) {
                var dateBegin = new Date(beginTime);
                var dateEnd = new Date(endTime);
                var dateDiff = dateEnd.getTime() - dateBegin.getTime();//时间差的毫秒数
                var dayDiff = Math.floor(dateDiff / (24 * 3600 * 1000));//计算出相差天数
                var leave1 = dateDiff % (24 * 3600 * 1000);    //计算天数后剩余的毫秒数
                var hours = Math.floor(leave1 / (3600 * 1000));//计算出小时数
                //计算相差分钟数
                var leave2 = leave1 % (3600 * 1000);   //计算小时数后剩余的毫秒数
                var minutes = Math.floor(leave2 / (60 * 1000)); //计算相差分钟数
                //计算相差秒数
                var leave3 = leave2 % (60 * 1000);     //计算分钟数后剩余的毫秒数
                var seconds = Math.round(leave3 / 1000);
                this.ReadTime = dayDiff + "天" + hours + "小时" + minutes + "分钟" + seconds + "秒";
            },


          GetContent(){

              this.$axios.post('public/SelectClock', {
                  idBook: this.$route.query.idBook,
                  contentId: this.$route.query.id,
                  token: this.$store.state.Authorization,
                  readTime: this.ReadTime,
              }, {
                  headers: {'Authorization': this.$store.state.Authorization}
              }).then(resp => {
                  if (resp.status === 200) {
                      
                      if(resp.data.data.length >= 1){
                          this.clocks = false
                      }

                  }
              }).catch(resp => {
              })
              this.$axios.post('public/GetContent', {
                  id_book: this.$route.query.idBook,
                  id: this.$route.query.id
              }, {
                  headers: {'Authorization': this.$store.state.Authorization}
              }).then(resp => {
                  if (resp.status === 200) {
                      this.Content = resp.data.data
                      // this.number = resp.data.data

                  }
              }).catch(resp => {
              })
              this.$axios.post('public/OneBook', {
                  id_book: this.$route.query.idBook,
              }, {
                  headers: {'Authorization': this.$store.state.Authorization}
              }).then(resp => {
                  if (resp.status === 200) {
                      this.book = resp.data.data
                      // this.number = resp.data.data

                  }
              }).catch(resp => {
              })
          }
          
        }
    }
</script>

<style scoped>
    @import '../editor.css';
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
        margin: 0 0 0 30px;
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
        font-size: 23px;
        line-height: 35px;
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
    .newsDetailOneP {
        font-size: 14px;
        color: #969696;
        padding-bottom: 17px;
        border-bottom: 1px solid #d5d9dc;
        text-align: center;
    }
</style>