# 校园商铺O2O



# 技术栈

前台：



后端：



# 项目工具

idea开发、maven构建项目、Git代码管理、墨刀设计产品交互模型。

## maven的安装配置

> TODO



# 校园商铺o2o 

# 1. 实体类分析

首先对o2o涉及到的实体类进行一定的分析，绘制了不太规范的ER图，这里省略了各个属性，主要是把握全体。然后是借助数据库MySQL WorkBench工具生成的一张关于实体类表的ER图。

![](https://img-blog.csdnimg.cn/img_convert/7041532c062d7cac3ab09d742180cc73.png)

<div align="center"><font color="gray">o2o实体类相关ER图</div>

![](https://img-blog.csdnimg.cn/img_convert/d4dce1d5967f057765e602b92b4c6b86.png)

<div align="center"><font color="gray">o2o表相关ER图</div>

因为后期要做**无限分级**板块，比如现在有一级Category为美食饮品、广告印刷，那么可以在一级Category上细分，如美食饮品可以细分蛋糕甜点、火锅、小吃快餐等。所以要在Category表中增加parent属性，表明该类别的上一级别，从而实现无限分级功能。

**权重**，大部分表都涉及到了权重，权重的作用是什么呢？展示商家店铺的时候，往往我们需要对店铺排序，排序的依据是什么，可以用权重作为排序的依据，权重越大，优先展示。**权重高往往优先级越高，所享受的权利就多。**

用户表相关，`tb_user`表是存储真实用户信息的，而本地账户是通过注册产生，微信账户通过扫码产生，这两张表并不存放用户的具体信息，只是存放与平台账户相关的信息，两表通过外键`user_id`与用户表相互关联。两者的Java实体类中持有用户的引用。另外，本地账户的创建依赖于微信账号，只有通过微信登录后，才有权限绑定本地账户，以后可以直接使用本地账户登录。

其他的关联信息都可以通过阅读上图提取到，这里不再陈述。

# 2. 店家管理系统的开发

## 2.1 店铺的注册

dao：`insertShop(Shop shop)`

店铺注册的时候会有一个店铺类别的选择，我们后台通过dao`selectByShopCategory`查询经过一层层的封装，返回给前台的类别数据是父类别不为空的，这是实现二级分类的条件。可以把一级分类看成是一个大类，二级分类是一级分类里面的一个小类。

## 2.2 店铺的修改

dao：`updateShop(Shop shop)`

mapper映射文件中利用了MyBatis的动态SQL特性，有条件的更新，`<set> <if>`标签实现动态SQL，更好的复用了`updateShop`。

店铺一旦创建，约定店铺类别不能更改，前台不会提供编辑类别的接口，其余信息可以更改。

## 2.3 Thumbnailator

1、使用Thumbnailator给店铺添加水印，并输出缩略图，保存到webapp外部的指定路径，为什么不将图片保存到webapp目录下：

（1）tomcat目录下的webapps 文件夹是部署目录，当项目重新部署（Tomcat重启、代码改变时的自动部署）,上传的文件不在部署文件的范畴内，即此时开发工具中没有上传的文件，所以上传的文件会被自动删除。

清理缓存也会使上传到webapps目录下的文件文件消失。

（2）如果能保存的话，随着用户上传图片，webapp项目体积就会增大，影响部署。并且图片和webapp耦合在一起，如果webapp删除了，那么图片也就会丢失。

2、更改项目的`SimpleDateFormat`，使用Java8新的日期类`LocaDateTime`类产生指定日期的格式。

主要涉及类

`ImageUtil`：保存图片到指定路径下

`PathUtil`：被ImageUtil和其他类所调用，主要用于产生图片保存的根路径、指定商铺ID的目录路径等。

3、`CommonsMultipartFile` service测试相关

因为`CommonsMultipartFile`是由前台传递过来并且由`SpringMVC`封装得来的，我们如果使用`CommonsMultipartFile`进行`JUnit`测试，很难构造出`CommonsMultipartFile`这个对象，所以我们在上传图片的方法上将参数设置为`File`或者`InputStream`类型，这样能够构造File对象并对service层店铺注册逻辑进行测试。

## 2.4 dto

dto：数据传输对象的使用，当调用service对店铺进行操作的时候，单单返回一个纯JavaBean对象，对于controller层来说，接下来的处理不能根据纯JavaBean对象作出判断，因为它只包含对象默认的属性，对于操作成功与否并不包含在其中，所以我们给纯的JavaBean对象封装一层，在这个更高层的对象上不仅包含纯JavaBean，另外还需要加上状态标识、状态信息等内容。

## 2.5 自定义页面路由

我们不希望用户可以通过类似`url/index.html`的路径直接访问到页面，所以我们制定了自己的路由访问规则，并将静态资源文件放在`WEB-INF`目录下，放置用户直接访问。

## 2.6 主从同步

有时间会做下

## 2.7 产品类别的增删

（1）商品类别的增加：支持批量增加产品类别功能，具体实现就是当用户新增一个产品类别时，并不是立即插入数据库，当用户点击提交后，才会将店主新建的产品类别插入数据库。

（2）商品类别的删除：ProductDao在实现删除功能的时候，传递商品类别ID和店铺ID，商品类别ID字段在数据库中设计的是自增主键，既然能保证唯一性，为什么还要传递店铺ID呢？不仅仅是商品类别的删除，后面也可能会遇到在修改时传递店铺ID的方法，这样做的原因就是保证当前操作都是在当前店铺下进行的，而不会去操作到别的店铺下面。

解除与该删除商品类别有关联的商品，

```markdown
# 删除商品类别
* 因为某个店铺下的商品属于商品类品 一旦删除类别，则应将商品与商品类别失去关联
* 这里我们只需要要更新product表 将其中的product_category_id置为null 就可以与
* product_category表解除关联 而不是去删除该类别下的所有商品
* 当删除商品类别时，若该商品类别下有商品则应该将商品置空
```

## 2.8 产品的增删改

（1）商品的增加：增加产品功能，需要在前端某店铺下填写产品的信息，然后从该店铺下定义的产品类别中选择一个产品类别，之后上传缩略图，产品缩略图只能上传一张，产品详情图片可以上传多张，上传图片的接口依赖于后端的`ProductImageDao`，详情查看`ProductService`下的`addProduct(Product product)`方法。对于增加的每一个商品的缩略图和详情图片都放在了以店铺id为名的文件夹下，并没有做分类，我觉得其实应该讲商品相关的图片放在以商品id为名的文件夹下，这样删除的时候可以直接删除该商品ID的文件夹，这样做也是有点缺陷，缩略图还是和产品图片放在一起。最终当对商品进行编辑，重新上传缩略图或者图片的时候进行删除缩略图或者图片，还是要依次遍历。

（2）商品的编辑：

如何处理之前上传的图片，如果店主从新上传了图片（缩略图或者商品详情图片），则将之前的图片一律删除。代码实现就是通过`ProductImgDao`查询出`productId`关联的商品详情图片，将其遍历删除在物理磁盘上的文件。然后从数据库中删除记录。

`编辑`、`上下架`功能都是商品编辑的范畴，因为上下架功能不需要输入验证码，商品信息的编辑是需要验证码的，后台是根据前台传递过来的一个`statusChange`来判断当前进行的操作是`上下架`还是`信息编辑`操作。

（3）商品列表的获取：

商品列表信息的接口不仅用于店家后台管理，也用于当顾客浏览门户网站时，需要请求该商品列表接口，所以说接口多用。dao底层通过调用`selectAllByProduct`和`selectCountByProduct`两个方法实现分页查询，并在service层将查询结果封装到ProductExecution中。

# 3. 前端展示系统

## 3.1 头条列表和一级level

在没有使用redis之前，service层`getHeadlineList`只是简单的调用了一下dao获取头条列表的接口，在使用redis之后，service层的作用就真正的体现出来了。解耦合，加缓存。

一级level，如果查询的category为空，则查询parentId为空的条目。

## 3.2 店铺列表页的开发

前端门户的开发，当点击全部商店时，查询的是一级level下的Category，即`parentId`为空的类别。当点击某一个分类，比如美食饮品则查询的是二级level，即parentId不为空（美食饮品下的小分类）。

## 3.3 修复部分bug

1、无限滚动的bug

2、点击全部商店后，显示的并不是一级leve，而是二级level，后端代码有误，已更正。点击全部商店后，然后选择一个店铺分类，第一次调用分页查询接口`parentId`是不为空的，可以查询到一级level下的所有店铺，当向下滑动时，触发无极滚动后，再次请求分页接口时传递过去`parentId`为空，bug存在于前端。因为我们一开始点击的是全部商店，而不是详细的某个分类，shoplist页面会根据url是否含有`parentId`来展示是一级level菜单还是二级level菜单，所以在进入全部商店页面再点击某个分类然后下滑后，会将`parentId`置为空，以表示这是一个一级level下的shoplist页面。于是我增加了一个boolean类型的变量以改正这个小问题。

详细请看`shoplist.js`文件。

3、redis缓存空条目，数据库发生了变动，还是会走redis缓存，导致获取到的不是数据库变动之后的数据。注意变动数据库之后，记得清一下缓存`flushdb`。

## 3.4 店铺和产品详情页的开发

需求：点击店铺列表的某个店铺，进入到该店铺的详细页面。

实现：每个店铺条链接下都会有一个shopId链接到url后面，当请求后台的时候，会根据这个ID去查询该店铺信息。

产品详情页的实现也是同样的原理。



## 3.5 微信公众号的接入流程

> **[微信测试公众号平台官网](http://mp.weixin.qq.com/debug/cgi-bin/sandboxinfo?action=showinfo&t=sandbox/index)**

当用户扫描二维码之后，授予对该公众号的相应权限，然后跳转到我们所开发的界面，而不是在WeChat公众平台提供的组件基础上搭建的界面，这就是脱离公众号的平台界面而去跳转到我们所开发的界面，这就是WeChat的**网页服务**，通过**回调接口**去**请求我们所开发的API**，从而响应客户端，可以是一个网页图片音频等等。越来越感觉到服务器可以做成个提供API的机器，请求API得到数据，然后终端显示，这个终端可以是PC、M端、小程序端等等多端应用。

**配置流程**

1）扫描二维码，得到一个`appID`,`appsecret` ，代码中修改为自己的`appID`,`appsecret`，曾经就是没有修改，导致访问出错。详情见代码！

2）接入概述

接入微信公众平台开发，开发者需要按照如下步骤完成：

具体请参考[开发者接入指南](https://developers.weixin.qq.com/doc/offiaccount/Basic_Information/Access_Overview.html)

这里列举出当时比较重要的两个点，一个是接口配置信息

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201003195053743.png#pic_center)


点击提交后，微信那边会发起一个`get`请求到服务器端，自己的服务器端需要能正常的响应才能成功。无论成功与否都会立即得到响应。

另一个是网页服务

我们的需求是要获得微信用户信息，然后创建该用户并我们的服务器中保存，**找到网页服务，然后修改回调域名**

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201003195008728.png#pic_center)


![在这里插入图片描述](https://img-blog.csdnimg.cn/20201003195030171.png#pic_center)


另外关于[**静默登录和授权登录**](https://developers.weixin.qq.com/doc/offiaccount/OA_Web_Apps/Wechat_webpage_authorization.html)的介绍，文档也有说明。

3）测试微信公众号

文档在静默登录之后的章节介绍了接入如何获得用户信息，用户json信息。借助**微信开发者工具**,公众号开发，访问测试链接，默认在浏览器访问是不会成功的。

会给予提示

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201003194946695.png#pic_center)


用微信开发者工具打开该网址

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201003194929614.png#pic_center)




# 4. 敏感信息的加密与解密

## 项目配置信息

像数据库账户和密码敏感信息之前都是是明文暴露在外面的，对于那些破坏分子就有机可乘，所以可以通过加密来实现一定程度的保护。

项目中采用DES加密对称加密技术，加密和解密使用同一把秘钥。另外还需要自定义一个类继承自`PropertyPlaceholderConfigurer`动态将加密后的配置信息解密后去连接数据库，并将其放入容器中。

```xml
<bean id="encryptPropertyPlaceholderConfigurer" class="ssm.wjx.util.EncryptPropertyPlaceholderConfigurer">
    <property name="locations">
        <list>
            <value>classpath:jdbc.properties</value>
            <value>classpath:redis.properties</value>
        </list>
    </property>
</bean>
```

## 用户信息

使用MD5对用户信息进行了加密，用户的登录都是用加密后的密码和数据库中的密码进行的比较，没有写额外的解密算法。

# 5. 平台账号体系

`LocalAuth`和`WechatAuth`通过userId外键关联于`user`表，**本地账户的绑定或者说创建依赖于微信账户**，用户只有通过微信登录后，才有权限绑定本地账号。如果是第一次登录，用户予以授权，则后端会新增一条记录到user表和WeChatAuth表中，user表中记录用户的详细信息，信息的来源全都是通过微信后台获取到的，`WechatAuth`主要记录`openid`、`userId`等关键信息，并不保留用户的详细信息。

用户微信登录后，后台会将记录保存在session中，如果用户已经绑定了本地账户，如果再次执行绑定本地账户操作，则会给予“用户不唯一”提示，**后台对同一微信账户多次绑定操作做出了相应的处理**。

# 6. 引入缓存

许多web应用都将数据存在关系型数据库当中，应用数据服务器从中读取数据并在浏览器中显示，但随着数据量的增大，访问的集中，就会出现RDBMS的负担加重，数据库响应恶化，网站响应延时。

Redis是高性能的内存缓存服务器，将数据保存在内存中，提高了数据的读写效率。

Redis会周期性的将记录写入磁盘，或者将记录追加进日志文件。分别对应的RDB和AOF两种持久化方式。

缓存的应用场景：热点数据、不经常发生变化的数据。

在这个项目中，我们在区域信息、头条信息、店铺类别上面使用缓存来存储这些数据。数据的访问首先到缓存中查找，如果查找成功，则直接返回，否则的话则连接数据库进行查找，如果查找成功，则将查找到的结果存入缓存。

# 7. 拦截器



# 8. 定期备份

导出数据库命令 ``mysqldump -uname -ppassword DB_name > /dirname` （Linux指令）

导出数据库结构和数据命令

```bash
# 注意date后跟空格，引号是tab键上面的
mysqldump -uroot -proot! o2o > /root/backup/sql/o2o`date +%Y%m%d%H%M%S`.sql
```

图片压缩

```bash
tar -zcvf /root/backup/image/image`date +%Y%m%d%H%M%S`.tar.gz /user/upload/
```

CentOS 7下默认自带cond定时任务服务，我们只需要编写相应的脚本在利用crond服务执行即可。

**步骤：**

1）编写backup.sh脚本，需要[Shell编程]()相关知识

```bash
#!/bin/bash
mysqldump -uroot -proot! o2o > /root/backup/sql/o2o`date +%Y%m%d%H%M%S`.sql
tar -zcvf /root/backup/image/image`date +%Y%m%d%H%M%S`.tar.gz /user/upload/
```

2）测试执行以下，看是脚本是否能够执行

```bash
sh backup.sh
bash backup.sh
source backup.sh
/bin/bash backup.sh
/bin/sh backup.sh
# 将文件变为可执行程序
chmod +x backup.sh
#执行 ./命令
./backup.sh
```

3）使用crontab编写定时任务

```bash
[root@iz2zehjjhi300kynwdk13iz backup]# crontab -e
no crontab for root - using an empty one
#每周一凌晨备份一次数据
0 0 * * 1 sh /root/backup/backup.sh 
```

4）`tail -f /var/log/cron` 查看crontab下实时的日志



# 项目收获

## 1、如何选择合适的JDK版本进行学习？

JDK每月发布一次功能，每个季度更新一次，这频率学习的速度远远赶不上它的频率，并且人的精力有限，所以说我们应该选择那些稳定精简，长期维护的版本（LTS）学习。

> JDK8.0 维护到 2025 JDK11 维护到2026

## 2、前后端联调

chrome和idea调试工具，调试按钮都是相通的，熟悉一种另一种就迎刃而解了

各个按钮的介绍：

![image-20200822202034727](https://img-blog.csdnimg.cn/img_convert/11363aaf4fda8a74416b09864c148010.png)

(1)执行到下一个断点处，若后面没有断点，则直接执行所有到完毕

![image-20200822202133075](https://img-blog.csdnimg.cn/img_convert/319f86cd6a88c413c6f6b1ab90d362e5.png)

(2)如果有函数体或者条件循环语句块不跳进语句块内，而是直接跳到执行完毕后的下一行代码，就是**略过方法体执行**

![image-20200822202343037](https://img-blog.csdnimg.cn/img_convert/05734221899399d56a506529fc8dedb0.png)

(3)跳进方法体和跳出方法体

![image-20200822202407858](https://img-blog.csdnimg.cn/img_convert/962fc6a942ec9ae5b523b3dbadc6398d.png)

(4)使所有断点失效

![image-20200822202425618](https://img-blog.csdnimg.cn/img_convert/fd03f910944a8f6216d8e0d7f41349cf.png)

(5)watch可以查看已定义对象的实时变化，call stack可以查看方法栈

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201003194902449.png#pic_center)


## 3. Idea设置tomcat虚拟路径？

开发首页的时候，图片请求不到，数据库中存放的是相对地址，需要借助tomcat虚拟路径来将相对地址映射为真是路径。具体配置如下：

1、使用Tomcat自己的虚拟路径

```markdown
# 1.在tomcat\config\server.xml中配置
* path：虚拟路径 docBase：真实路径
<Context  path="/upload"  docBase="E:\photo\upload" reloadable="true"/>
# 2.在Idea tomcat配置面板中将Deploy applications configured in Tomcat instance勾上
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201003194847152.png#pic_center)


2、用Idea设置虚拟路径

(1)不要勾选上图红线的配置

(2)如下图

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201003194823751.png#pic_center)


示例：配置后的路径如下

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201003194808134.png#pic_center)


## 4. 微信公众号

微信公众平台，简称公众号，**公众号包含服务号、订阅号、小程序、企业微信四种类型**。

服务号与订阅号的区别如下：

|                | 服务号                         | 订阅号                                           |
| -------------- | ------------------------------ | ------------------------------------------------ |
| 消息限制       | 1个月内仅可以发送4条群发消息   | 每天可以发送1条群发消息                          |
| 微信支付       | 认证成功后可以使用微信支付功能 | 无论认证成功与否都不支持微信支付                 |
| 消息的所在界面 | 显示在对方的聊天列表中         | 显示在对方的“订阅号”文件夹中，点击两次才可以打开 |
| 自定义菜单功能 | 支持                           | 完成认证后才支持自定义菜单                       |

微信公众号用户授权、网页服务的接入流程。

## 5. IDEA远程调试tomcat

1、配置IDEA`tomcat`选项

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201003194720774.png#pic_center)


![在这里插入图片描述](https://img-blog.csdnimg.cn/2020100319474955.png#pic_center)


2、服务器配置，修改tomcat bin目录下的 Catalina.sh 添加，**双引号**

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201003194700715.png#pic_center)

3、远程调试，打断点后，debug启动，若启动成功，console会显示连接成功。