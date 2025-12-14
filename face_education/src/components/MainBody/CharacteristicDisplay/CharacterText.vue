<template>
    <div class="main_body">
        <HeadTitle></HeadTitle>
        <div class="head_picture"></div>
    
        <div class="min_body">
            <div class="min_title">
                当前位置：
                <a href="/FirstPage">首页&gt;</a>
                <a href="/CharacteristicProject">特色项目</a>
            </div>
            
            <div class="text_detail">
                <div class="text_content">
                    <h3>{{CharacterProject.title}}</h3>
                    <div class="text_one">
                        <h3>地区：<span class="han">{{CharacterProject.area}}</span></h3>
                        <h3>时间：{{CharacterProject.dates}}</h3>
                        <h3>类别：<span class="han">{{CharacterProject.types}}</span></h3>
                    </div>
                </div>
    
                <h3 style="text-align: center">简介：</h3>
    
                <div style="margin-top: 20px">
                    <div class="lists" >
                        <div class="ql-editor" v-html="CharacterProject.texts"></div>
                    </div>
                </div>
                <div  class="images" v-for="fileLists in fileList">
                    <img :src="fileLists.fileurl" alt="" class="pic">
                </div>
                <div class="bottoms"></div>
                
            </div>
        </div>
    </div>
</template>

<script>
    import HeadTitle from "@/components/MainBody/HeadTitle";
    export default {
        name: "CharacterText",
        components: {HeadTitle},
        data() {
            return {
                fileList:[],
                CharacterProject:{}
            }
        },

        created(){
            this.NewProject();
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
            NewProject() {
                
                
                this.$axios.post('public/AnCharacterProject', {
                    id: this.$route.query.id,
                }, {
                    headers: {'Authorization': this.$store.state.Authorization}
                }).then(resp => {
                    if (resp.status === 200) {
                        this.CharacterProject = resp.data.data
                        this.CharacterProject.dates = this.formatDate(new Date(this.CharacterProject.dates))

                        this.$axios.post('public/TextPicture', {
                            outId: this.CharacterProject.id,
                            types: 2
                        }, {
                            headers: {'Authorization': this.$store.state.Authorization}
                        }).then(resp => {
                            if (resp.status === 200) {
                                this.fileList = resp.data.data
                            }
                        }).catch(resp => {
                        })
                        
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
        background-size: cover;
    }
    .min_title {
        font-size: 14px;
        color: #767676;
        line-height: 70px;
    }
    .min_body{
        width: 1260px;
        margin: 0 auto;
    }
    .text_detail {
        width: 100%;
        background: #f7f7f7;
    }
    .text_content {
        
        padding: 30px 38px;
    }
    .text_one{
        width: 40%;
    }
    .text_detail h3 {
        font-size: 16px;
        color: #363636;
        font-weight: bold;
        margin-top: 20px;
    }
    .han{
        color: #0d74b1;
        font-size: 16px;
    }

    .lists{
        margin-left: 20px;
        font-size: 18px;
        line-height: 29px;
    }

    .images{
        margin: 40px auto;
        height: 461px;
        width: 690px
    }
    
    .images img{
        max-width: 100%;
        max-height: 100%;
    }

    .bottoms{
        height: 20px;
    }
</style>