create table FileInformation
(
    id      int auto_increment
        primary key,
    title   varchar(255)  null,
    dates   timestamp     null,
    deletes int default 0 null
);

create table New_Notice
(
    id      int auto_increment
        primary key,
    title   varchar(100)  null,
    author  varchar(255)  null,
    sources varchar(255)  null,
    dates   timestamp     null,
    hits    int           null,
    texts   longtext      null,
    deletes int default 0 null,
    types   int           null
);

create table ReadClock
(
    id         int auto_increment
        primary key,
    content_id int          null,
    id_book    int          null,
    telephone  varchar(255) null,
    read_time  varchar(255) null comment '阅读时长'
)
    comment '阅读打卡';

create table book_content
(
    id      int auto_increment
        primary key,
    id_book int      null,
    Catalog tinytext null,
    content longtext null
);

create table character_project
(
    id      int auto_increment
        primary key,
    title   varchar(100)                                                                                         null comment '题目',
    area    varchar(100)                                                                                         null comment '地区',
    dates   timestamp                                                                                            null,
    texts   longtext                                                                                             null,
    images  varchar(1000) default 'http://rcqmzsxxw.com/rchema/images/upload/article/20210906/1630893858248.jpg' null,
    deletes int           default 0                                                                              null,
    types   varchar(100)                                                                                         null
)
    comment '特色项目';

create table commodity
(
    id         int auto_increment
        primary key,
    trade_name varchar(255)                                                                                        null comment '商品名',
    points     int                                                                                                 null comment '积分',
    introduce  longtext                                                                                            null,
    picture    varchar(255) default 'http://rcqmzsxxw.com/rchema/images/upload/article/20210416/1618515916476.jpg' null,
    deletes    int          default 0                                                                              null
)
    comment '商品';

create table file
(
    id       int auto_increment
        primary key,
    file_id  int           null,
    FileName varchar(1000) null,
    FileUrl  varchar(255)  null
);

create table honor
(
    id      int auto_increment
        primary key,
    title   varchar(255)                                                      null,
    dates   timestamp                                                         null,
    texts   longtext                                                          null,
    images  varchar(100) default 'http://eucation.i8329.cn/荣誉图片2-library.jpg' null,
    deletes int          default 0                                            null
)
    comment '荣誉展示
';

create table library
(
    id_book   int auto_increment
        primary key,
    name_book tinytext      null,
    time_book tinytext      null,
    author    tinytext      null,
    introduce varchar(1024) null,
    picture   tinytext      null,
    deletes   int default 0 null
);

create table order_form
(
    id          int auto_increment
        primary key,
    commodityId int           null,
    user_name   varchar(255)  null,
    telephone   varchar(255)  null,
    phone       varchar(255)  null,
    address     varchar(255)  null,
    remark      varchar(255)  null,
    dates       timestamp     null,
    states      int default 0 null
);

create table paper_answer
(
    id        int auto_increment
        primary key,
    paper_id  int          null,
    title_id  int          null,
    answers   varchar(255) null,
    telephone varchar(255) null
)
    comment '试题回答表
';

create table paper_title
(
    id        int auto_increment
        primary key,
    id_paper  int           null,
    title     varchar(1000) null,
    right_key varchar(244)  null,
    deleted   int default 0 null
)
    comment '题目';

create table permission
(
    ID         int auto_increment
        primary key,
    permission varchar(30) null,
    port       varchar(30) null
)
    comment '权限表';

create table role
(
    ID       int auto_increment
        primary key,
    roleName varchar(30) not null
)
    comment '角色表';

create table role_permission
(
    ID               int auto_increment
        primary key,
    permissionStatus varchar(20) null,
    roleName         varchar(30) null
)
    comment '角色权限表';

create table test_paper
(
    id      int auto_increment
        primary key,
    title   varchar(255)  null,
    dates   timestamp     null,
    deleted int default 0 null,
    effect  int default 1 null
)
    comment '试题
';

create table test_questions
(
    id           int auto_increment
        primary key,
    paper_id     int           not null,
    title_id     int           null,
    option_s     varchar(1000) null,
    option_value varchar(233)  null comment '选项题号',
    deleted      int default 0 null
)
    comment '试题';

create table text_picture
(
    id      int auto_increment
        primary key,
    out_id  int           null,
    name    varchar(255)  null,
    url     varchar(1000) null,
    types   int           null comment '1-新闻公告
2-特色项目',
    deletes int default 0 null
)
    comment '文章中的图片地址';

create table user_inf
(
    id         int auto_increment comment '主键'
        primary key,
    username   varchar(255)  null comment '姓名',
    telephone  varchar(255)  not null comment '电话号码',
    sex        varchar(25)   null comment '性别',
    birthday   timestamp     null comment '生日',
    email      varchar(255)  null comment '邮箱',
    area       varchar(255)  null comment '所属社区',
    background varchar(25)   null comment '学历',
    job        varchar(255)  null comment '职业',
    hobby      varchar(255)  null comment '爱好',
    integral   int default 0 null,
    password   varchar(1255) not null comment '密码',
    deleted    int default 0 null comment '假删除',
    createTime timestamp     null comment '创建时间',
    picture    varchar(255)  null
)
    comment '用户信息表';

create table user_role
(
    ID        int auto_increment
        primary key,
    telephone varchar(20)   null,
    roleName  varchar(30)   null,
    deleted   int default 0 null comment '0'
)
    comment '用户角色表';


