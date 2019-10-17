### Lhfly-BBS 论坛

受GitHub的一个BBS项目Genesis的启发，重新写了一个BBS论坛，实现了基本功能
##### 技术栈

* 本项目基于Spring、Spring MVC、MyBatis框架实现
* 数据库采用MySql 5.7.22
* 前端采用HTML、CSS、jQuery、Bootstrap

##### 功能实现

* 发布主题、回复主题
* 按照活跃度、最后更新时间、是否为精品对帖子进行排序展示
* 管理员账户能够实现设置主题为精品、删除主题、删除回复功能
* 后天统计访客信息、统计用户登陆信息
* 用户登陆与注册、用户信息界面展示、用户修改头像
* 回复消息后发帖者能够收到消息提醒、未读消息和已读消息展示
* 在页面展示全站最热议的主题
* 用户发帖、登陆、回帖行为的积分系统
* 翻页功能

##### 待更新的功能

* 使用Redis实现缓存
* 发帖人编辑主题
* 使用Nginx实现动静分离、负载均衡
* 可以发送图片
* 用户回帖记录展示
* 主题里用户之间的回复

##### 界面展示

首页
![微信截图_20191017204848.png](https://i.loli.net/2019/10/17/jse3GRl4Npkhr5g.png)

发帖界面
![微信截图_20191017205022.png](https://i.loli.net/2019/10/17/Mo9AB4fOWqbctPw.png)

用户信息界面
![微信截图_20191017205044.png](https://i.loli.net/2019/10/17/OCMEWNpb2DFmzRw.png)

用户消息列表
![微信截图_20191017205011.png](https://i.loli.net/2019/10/17/XGohLU24Wmkxby5.png)

主题详情
![微信截图_20191017204928.png](https://i.loli.net/2019/10/17/yAUk7D6finR18jF.png)

管理员的主题详情界面
![微信截图_20191017204948.png](https://i.loli.net/2019/10/17/DvEIS4utr3RlQjn.png)

网站信息展示
![微信截图_20191017212355.png](https://i.loli.net/2019/10/17/JHWnvM9uCYQ2qoa.png)