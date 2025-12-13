<template>
    <div class="main_body">
        <div class="head_min">
            <ul class="ul_list" v-for="Good in Goods">
                <li>
                    <div class="head_min_left">
                        <img :src="Good.picture" alt="">
                    </div>
                    <div class="head_min_right">
                        <p>商品名: {{Good.tradeName}}</p>
                        <span>联系人姓名: {{Good.userName}}</span>
                        <div>
                            <div class="_project_left">
                                <span>联系电话： {{Good.phone}}</span>
                            </div>
                            <div class="_project_left">
                                <span>地址： {{Good.address}}</span>
                            </div>
                        </div>
                    </div>
            
                </li>
            </ul>
    
    
        </div>
    </div>
</template>

<script>
    export default {
        name: "MyPrize",
        data(){
            return{
                Goods:{},
            }
        },
        created(){
            this.GetMyGoods()
        },
        methods:{
          
          GetMyGoods(){
              this.$axios.post('public/MyGoods', {
                  token: this.$store.state.Authorization
              }, {
                  headers: {'Authorization': this.$store.state.Authorization}
              }).then(resp => {
                  if (resp.status === 200) {
                      this.Goods = resp.data.data
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
   
    .head_min{
        width: 100%;
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
    ._project_left  span{
        font-size: 17px;

    }

    
    .ul_list{
        border-bottom: 1px solid #d9d9d9;
        width: 100%;
        height: 190px
    }
    
    ._project_left span i {
        color: #868686;
        padding-right: 5px;
    }
</style>