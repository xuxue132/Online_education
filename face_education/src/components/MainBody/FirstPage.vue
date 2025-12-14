<template>
    <div class="main_body">
        <HeadTitle></HeadTitle>
        <div class="head_picture">
        </div>
        <div class="hole_body">
            <div class="block">
                <el-carousel trigger="click" height="390px" style="width: 756px">
                    <el-carousel-item v-for="image in images" :key="image">
                        <img :src="image" class="picture">
                    </el-carousel-item>
                </el-carousel>
            </div>
    
            <div class="mid">
                <div class="mid-title cur1">
                    新闻<a href="/NewsPage">更多</a>
                </div>
                <div class="new-list">
                    <ul v-for="NewNotice in NewNotices">
                        <li>
                            <a style="width: 250px"  @click="TurnPage1(NewNotice.id,NewNotice.type)" :title="NewNotice.title">{{NewNotice.title}}</a>
                            <p>{{NewNotice.dates}}</p>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="hole_body">
            <div class="mid_next">
                <div class="mid-title cur1">
                    公告<a href="/NoticePage">更多</a>
                </div>
                <div class="new-list">
                    <ul v-for="Notice in Notices">
                
                        <li><a @click="TurnPage2(Notice.id,Notice.type)" :title="Notice.title">{{Notice.title}}</a>
                            <p>{{Notice.dates}}</p>
                        </li>
                
                
                       
                    </ul>
                </div>
            </div>
            <div class="mid_next" style="margin-left: 10px">
                <div class="mid-title cur1">
                    阅读文章<a href="/ReadPage">更多</a>
                </div>
                <div class="new-list">
                    <ul v-for="Book in Books">
                
                        <li><a @click="TurnPage3(Book.idBook)" :title="Book.nameBook">{{Book.nameBook}}</a>
                        </li>
                        
                    </ul>
                </div>
            </div>
            <div class="mid_next" style="margin-left: 10px">
                <div class="mid-title cur1">
                    下载中心<a href="/FilePage">更多</a>
                </div>
                <div class="new-list">
                    <ul v-for="FileInformation in FileInformations">
                
                        <li><a @click="TurnPage4(FileInformation.id)" :title="FileInformation.title">{{FileInformation.title}}</a>
                            <p>{{FileInformation.dates}}</p>
                        </li>
                        
                    </ul>
                </div>
            </div>
        </div>
    
        <div class="hole_body">
            <div class="_right">
                <div class="_titleBox">
                    <a href="./sq_project.html?type=1" class="title_left">
                        <div class="title_img"><img src="	http://www.rcqmzsxxw.com/rchema/xuexiwang/img/index/she.png" alt=""></div>
                        <span class="noBorder">区学校特色项目</span>
                    </a>
                    <div class="title_right"><a href="./CharacteristicProject">更多&gt;</a></div>
                </div>
                <div style="width: 100%;height: 200px;">
                    <div class="_project" v-for="CharacterProject in CharacterProjects">
                        <a @click="TurnPage5(CharacterProject.id)" class="_project_item">
                            <div style="float: left"><img :src="CharacterProject.images"></div>
                            <div style="margin-left:20px; width:50%;float: left;cursor:pointer;">
                                <p><span>项目：</span>{{CharacterProject.title}}</p>
                                <p><span>地区：</span>{{CharacterProject.area}}</p>
                                <p><span>时间：</span>{{CharacterProject.dates}}</p>
                            </div>
                        </a>
                    </div>
                </div>
                
           
        </div>
        
    </div>
        <div class="bottom">
            <div class="copyright">
                <div class="txt">
                    <!-- 版权内容请在本组件"内容配置-版权"处填写 -->
                    <p>地址：重庆市重庆理工大学两江校区 </p>
                    <p>版权所有·重庆理工大学·毕业设计</p>
                    <p>邮箱 @ 11823020317@2018.cqut.edu.cn</p>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import HeadTitle from "@/components/MainBody/HeadTitle";
    export default {
        name: "FirstPage",
        components: {HeadTitle},
        data(){
            return{
                images:['http://rcqmzsxxw.com/rchema/images/upload/image/20201028/1603873401899.png',
                    'https://www.cqut.edu.cn/images/20220420.jpg',
                    'https://www.cqut.edu.cn/images/20220428.jpg',
                    'https://www.cqut.edu.cn/images/202203216.jpg',
                    'https://www.cqut.edu.cn/images/20211202.jpg'
                ],
                NewNotices:[],
                Notices:[],
                Books:[],
                FileInformations:[],
                CharacterProjects:[],
            }
        },
        created(){
            this.NewNotice();
            this.Notice();
            this.GetBook();
            this.NewProject();
            this.NewFile();
        },
        methods: {
            TurnPage1(id,types) {
                alert(types)
                this.$router.push({
                    path: '/NewText',
                    query: {
                        id: id,
                        types: types
                    }

                })
            },
            TurnPage2(id,types) {
                this.$router.push({
                    path: '/NewText',
                    query: {
                        id: id,
                        types: types
                    }

                })
            },
            TurnPage3(id,types) {
                this.$router.push({
                    path: '/ReadBookContent',
                    query: {
                        id: id,
                        types: types,
                    }

                })
            },
            TurnPage4(id) {
                this.$router.push({
                    path: '/FileText',
                    query: {
                        id: id,
                    }

                })
            },
            TurnPage5(id) {
                this.$router.push({
                    path: '/CharacterText',
                    query: {
                        id: id,
                    }

                })
            },
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
            NewProject(){
                this.$axios.post('public/CharacterProject', {
                    NowPage: 1
                }, {
                    headers: {'Authorization': this.$store.state.Authorization}
                }).then(resp => {
                    if (resp.status === 200) {
                        if(resp.data.data.length<6){
                            for (var i=0;i<resp.data.data.length;i++) {
                                this.CharacterProjects.push(resp.data.data[i])
                            }
                        }else {
                            for (var i=0;i<6;i++) {
                                this.CharacterProjects.push(resp.data.data[i])
                            }
                        }
                        for (var i=0;i<this.CharacterProjects.length;i++) {
                            this.CharacterProjects[i].dates = this.formatDate(new Date(this.CharacterProjects[i].dates))
                        }

                    }
                }).catch(resp => {
                })
            },
            NewFile(){
                this.$axios.post('public/AllFileInformation', {
                    NowPage: 1
                }, {
                    headers: {'Authorization': this.$store.state.Authorization}
                }).then(resp => {
                    if (resp.status === 200) {
                        if(resp.data.data.length<6){
                            for (var i=0;i<resp.data.data.length;i++) {
                                this.FileInformations.push(resp.data.data[i])
                            }
                        }else {
                            for (var i=0;i<6;i++) {
                                this.FileInformations.push(resp.data.data[i])
                            }
                        }
                        for (var i=0;i<this.FileInformations.length;i++) {
                            this.FileInformations[i].dates = this.formatDate(new Date(this.FileInformations[i].dates))
                        }

                    }
                }).catch(resp => {
                })
            },
            GetBook() {
                this.$axios.post('public/AllBook', {
                    NowPage: 1
                }, {
                    headers: {'Authorization': this.$store.state.Authorization}
                }).then(resp => {
                    if (resp.status === 200) {
                        if(resp.data.data.length<6){
                            for (var i=0;i<resp.data.data.length;i++) {
                                this.Books.push(resp.data.data[i])
                            }
                        }else {
                            for (var i=0;i<6;i++) {
                                this.Books.push(resp.data.data[i])
                            }
                        }
                    }
                }).catch(resp => {
                })
            },
            Notice() {
                this.$axios.post('public/NewNotice', {
                    types: 1,
                    NowPage: 1
                }, {
                    headers: {'Authorization': this.$store.state.Authorization}
                }).then(resp => {
                    if (resp.status === 200) {
                        if(resp.data.data.length<6){
                            for (var i=0;i<resp.data.data.length;i++) {
                                this.Notices.push(resp.data.data[i])
                            }
                        }else {
                            for (var i=0;i<6;i++) {
                                this.Notices.push(resp.data.data[i])
                            }
                        }

                        for (var i=0;i<this.Notices.length;i++) {
                            this.Notices[i].dates = this.formatDate(new Date(this.Notices[i].dates))
                        }
                    }
                }).catch(resp => {
                })
            },
            NewNotice(){
                this.$axios.post('public/NewNotice', {
                    types: 0,
                    NowPage: 1
                }, {
                    headers: {'Authorization': this.$store.state.Authorization}
                }).then(resp => {
                    if (resp.status === 200) {
                        
                        if(resp.data.data.length<6){
                            for (var i=0;i<resp.data.data.length;i++) {
                                this.NewNotices.push(resp.data.data[i])
                            }
                        }else {
                            for (var i=0;i<6;i++) {
                                this.NewNotices.push(resp.data.data[i])
                            }
                        }
                        
                        for (var i=0;i<this.NewNotices.length;i++) {
                            this.NewNotices[i].dates = this.formatDate(new Date(this.NewNotices[i].dates))
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
    .el-carousel__item h3 {
        color: #475669;
        font-size: 14px;
        opacity: 0.75;
        line-height: 150px;
        margin: 0;
    }

    .picture{
        width: 100%;
        height: 100%;
    }
    .hole_body{
        width: 1500px;
        height: 400px;
        margin: 18px auto;
    }
    .block{
        float: left;
    }
    .mid {
        width: 510px;
        float: left;
        margin-left: 40px;
        border: 1px solid #ddd;
    }
    .mid_next {
        width: 430px;
        float: left;
        border: 1px solid #ddd;
    }
    .cur1 {
        color: #9e8841;
        border-bottom: 2px solid #9e8841;
    }

    .mid-title{
        line-height: 52px;
        font-size: 20px;
        padding-left: 20px;
    }

    .mid-title a {
        color: #999;
        font-size: 14px;
        float: right;
        padding-right: 20px;
    }

    .new-list {
        width: 400px;
        margin: 0 auto;
    }

    .new-list ul li {
        float: left;
        width: 400px;
    }
    .new-list ul li a {
        float: left;
        cursor:pointer;
        width: 250px;
        overflow:hidden;
        text-overflow:ellipsis;
        white-space:nowrap;
    }
    .new-list ul li p {
        float: right;
    }
    .new-list ul li, .new-list ul li p {
        line-height: 50px;
    }

    ._titleBox {
        border-bottom: 1px solid #0d74b1;
    }

    ._titleBox {
        height: 58px;
       
    }
    .title_left {
        
        line-height: 60px;
       
    }
    .title_img {
        width: 46px;
        height: 46px;
        float: left;
        background: #0d74b1;
        text-align: center;
        padding: 6px;
    }
    .noBorder {
        border: none;
        float: left;
        background: linear-gradient(
                90deg
                ,rgba(168,207,231,1),rgba(255,255,255,1))!important;
        height: 58px;
        width: 300px;
        line-height: 55px;
        font-size: 20px;
        color: #363636;
        padding: 0 5px;
        letter-spacing: 2px;
    }
    .title_right a {
        font-size: 14px;
        color: #868686;
    }.title_right  {
        float: right;
        line-height: 50px;
    }
    ._right {
        width: 88%;
    }

    ._project {
        margin-top: 20px;
        float: left;
    }
    ._project{
        margin-left: 20px;
        width: 29%;
        height: 122px;
        background: #f5f5f5;
        padding: 20px 0px 15px 30px;
        color: #363636;
        font-size: 14px;
        line-height: 24px;
        overflow: hidden;
        margin-bottom: 6px;
    }

    ._project_item img {
        
        width: 160px;
        height: 90px;
    }

    ._project_item p {
        max-width: 100%;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
    }
    ._project_item span {
        color: #767676;
        padding-right: 5px;
    }
    .bottom {
        width: 100%;
        height: 136px;
        background: #2f2f2f;
        float: left;
        margin-top: 40px;
    }
    .copyright {
        line-height: 30px;
        color: #fff;
        width: 800px;
        margin: 30px auto;
    }
    .txt {
        width: 400px;
        float: left;
        margin-right: 70px;
    }
</style>