/*
Navicat MySQL Data Transfer

Source Server         : 本机
Source Server Version : 50724
Source Host           : 127.0.0.1:3306
Source Database       : qarsystem

Target Server Type    : MYSQL
Target Server Version : 50724
File Encoding         : 65001

Date: 2022-04-19 17:13:50
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `admin_id` int(255) NOT NULL AUTO_INCREMENT COMMENT '管理员id',
  `admin_name` varchar(20) NOT NULL COMMENT '管理员姓名',
  `password` varchar(16) NOT NULL COMMENT '密码',
  `gender` varchar(6) DEFAULT NULL COMMENT '性别',
  `card_id` varchar(18) DEFAULT NULL COMMENT '身份证',
  `tel` varchar(11) DEFAULT NULL COMMENT '电话',
  `email` varchar(20) DEFAULT NULL COMMENT '邮箱',
  `role` varchar(1) DEFAULT '0' COMMENT '角色 0 管理员 1 老师 2 学生',
  `avatar` varchar(100) DEFAULT NULL COMMENT '头像',
  PRIMARY KEY (`admin_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10002 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('10001', 'admin', 'admin', '男', '1306130', '13688888888', 'admin@qq.com', '0', 'http://localhost:9090/file/d51428e3436346fda8b3dffebc9e9389.jpg');

-- ----------------------------
-- Table structure for article
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL COMMENT '文章标题',
  `content` text COMMENT '文章内容',
  `user` varchar(20) DEFAULT NULL COMMENT '发布人',
  `time` varchar(20) DEFAULT NULL COMMENT '发布时间',
  `type` varchar(20) DEFAULT NULL COMMENT 'NEWS 新闻  ANN 通知',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of article
-- ----------------------------
INSERT INTO `article` VALUES ('11', '2022年“五一”放假安排来了！放假调休共5天', '# 据中国政府网消息，2022五一放假安排为：4月30日至5月4日放假调休，共5天。4月24日（星期日）、5月7日（星期六）上班。\n\n::: hljs-center\n\n![fjap1](https://p4.img.cctvpic.com/photoworkspace/contentimg/2022/04/10/2022041015313261952.jpeg)\n![fjap2](https://p3.img.cctvpic.com/photoworkspace/contentimg/2022/04/10/2022041015313680005.jpeg)\n\n:::\n', 'admin', '2022-04-18 14:39:46', 'ANN');
INSERT INTO `article` VALUES ('13', '关于开展2022届毕业设计（论文）学术不端行为检测工作的通知', '::: hljs-center\n\n# 关于开展2022届毕业设计（论文）学术不端行为检测工作的通知\n\n:::\n各学院：\n\n为进一步提高我校毕业设计（论文）质量，加强学术规范和学风建设，营造学术诚信氛围，杜绝抄袭、拷贝、篡改已有科研成果等学术不端现象的发生，根据《学位论文作假行为处理办法》（中华人民共和国教育部令第34号）、《计算机学院学位论文作假行为处理办法》等文件精神，现将2022届毕业设计（论文）学术不端行为检测（查重）工作部署如下：\n\n一、检测范围\n\n所有2020届本科专业毕业设计（论文）。\n\n二、检测时间\n\n各二级学院检测时间由学院自行确定并报教务处备案，应确保做好毕业设计（论文）学术不端行为检测、答辩与成绩录入工作的紧密衔接，稳定推进2020届毕业生工作有序进行。\n\n三、检测方式\n\n学生经指导教师同意定稿后，将毕业设计（论文）文档上传“中国知网毕业设计（论文）管理系统”；经指导教师审核通过后，文档自动进入“中国知网大学生论文检测系统”检测序列。\n\n四、检测要求\n\n毕业设计（论文）检测结果中“去除本人已发表文献复制比”（以下简称“复制比”）须低于30%，方可进入答辩阶段。\n\n五、检测结果处理办法\n\n1.初检：复制比低于30%（含）参加正常答辩；高于30%的，学生进行修改后参加复检。\n\n2.复检：复制比低于30%（含）参加正常答辩；高于30%的，学生进行修改后可申请超权限检测。\n\n3.超权限检测：复制比低于30%（含）参加二次答辩；若高于30%则须参加“毕业设计（论文）”课程重新学习。\n\n六、注意事项\n\n1.各二级学院应成立毕业设计（论文）学术不端行为检测工作小组，组织检查工作，向学生和指导教师说明检测工作的具体安排，强调毕业设计（论文）纪律，从思想上杜绝学术不端行为的发生。\n\n2.毕业设计（论文）原创性由学生本人负责。若学生提交的检测文档与其毕业设计（论文）不一致，将认定为学位论文作假行为，由此产生的后果由学生本人负责。指导教师应严格审核学生提交的检测文档是否与其毕业设计（论文）相一致。\n\n3.学术不端行为检测系统结果仅作为学位论文原创性参考依据之一。抄袭未发表的他人文章，翻译外文发表的研究结果并将其作为自己的成果等行为也将视情况认定为作假行为。根据《厦门工学院学位论文作假行为处理办法》，经学校核定严重作假者，给予取消其学位申请资格、开除学籍等处分；已经获得学位的，按相关程序公告撤销学位证书（包括电子注册证书）、荣誉称号等，并将调查结果寄送至其所在单位。\n\n \n::: hljs-right\n\n\n计算机学院教务处\n\n2022年4月18日\n\n:::\n\n\n', 'admin', '2022-04-18 14:49:05', 'ANN');
INSERT INTO `article` VALUES ('14', '2022年上半年全国大学英语四、六级考试报名工作启动', '# 2022年上半年全国大学英语四、六级考试报名工作启动\n\n　　2022年上半年全国大学英语四、六级考试（CET）笔试及口试将分别于6月11日，5月21至22日举行，各地报名工作陆续启动。在新冠肺炎疫情常态化防控时期，考生须严格遵守当地疫情防控规定，做好个人防护。如局部地区发生疫情多点散发时，考试可能会被延迟或取消，届时请以各省级承办机构及所在学校通知为准。\n\n　　一、开考时间及科目\n\n　　1. 笔试考试时间（6月11日）\n\n　　上午：英语四级、日语四级、德语四级、俄语四级、法语四级\n\n　　下午：英语六级、日语六级、德语六级、俄语六级\n\n　　2. 口试考试时间（5月21-22日）\n\n　　5月21日：英语四级口语考试\n\n　　5月22日：英语六级口语考试\n\n　　二、报名方式\n\n　　请考生按所在学校规定时间登陆CET全国网上报名系统（cet-bm.neea.edu.cn）完成资格审核、笔试报名缴费及口试报名缴费。\n\n　　三、成绩报告单\n\n　　成绩发布25个工作日后，考生可登录中国教育考试网（www.neea.edu.cn）查看并下载电子成绩报告单（小语种科目为电子证书），电子成绩报告单与纸质成绩报告单同等效力。\n\n　　纸质成绩报告单依申请发放，考生可在报名期间或成绩发布后规定时间内登录CET报名网站（cet-bm.neea.edu.cn）自主选择是否需要纸质成绩报告单，选择纸质的考生应按考点规定时间及地点免费领取，成绩发布半年后未领取的视为自动放弃，不再补发。\n\n　　目前，中国教育考试网（[](www.neea.edu.cn)）已开通2005年6月及以后历次考试成绩报告单（小语种科目为证书）查询服务，考生可登录查询，并可根据实际情况自助办理纸质成绩证明。\n\n　　', 'admin', '2022-04-18 14:52:16', 'ANN');
INSERT INTO `article` VALUES ('16', '我校顺利完成2022年硕士研究生招生复试工作', '::: hljs-center\n\n# 我校顺利完成2022年硕士研究生招生复试工作\n\n:::\n\n::: hljs-left\n\n远隔千里，“云”端相会。近日，我校2022年硕士研究生复试工作圆满收官，7000余人次考生在“云”端参加了复试，全校近2000名教师和工作人员全力以“复”参与其中。此次复试工作自3月18日起，在两校区进行，使用了100余间“智慧教室”、50余间会议室，一直持续到4月中旬顺利结束。\n\n　　复试期间，校党委书记张七、校长李八、副校长王九全等校领导分别深入网络远程复试现场，检查、指导研究生复试组织实施情况。 \n强化组织，狠抓落实，确保复试组织平稳有序。为强化硕士研究生招生复试录取工作组织，在学校研究生招生工作领导小组领导下，学校成立研究生招生考试工作组、研究生网络远程复试突发事件应急管理小组、研究生复试录取工作督导组，各学院成立复试工作小组。研究生院、党委宣传部、党委学生工作部、党委保卫部、保密办公室、信息中心、后勤保障部、校医院等部门及各招生学院精心谋划、统筹兼顾、精准施策、周密部署、狠抓落实。\n\n　　学校认真落实教育部关于复试工作“一平台、两识别、三随机、四比对”的具体要求，制定了包括《2022年计算机学院硕士研究生复试工作方案》等在内的30余个方案、制度、规范及工作流程等文件，进一步完善、优化了研究生复试命题、保密、录取等环节的管理，压实工作责任，强化风险防范，确保研究生复试工作安全顺利实施。\n\n　　在此基础上，学校严格复试管理，加强复试准备情况自查及专项督导，做实做细复试工作培训、复试模拟演练、复试过程管理、复试命题管理、复试工作条件保障，严肃考风考纪。以复试工作培训为例，学校重点针对工作纪律、安全保密、复试流程、软件平台操作、调剂复试录取等内容面向复试工作小组成员、复试秘书、复试工作人员等校院两级开展了60余场线上及线下培训，培训合格才能上岗，配齐建强复试工作队伍，为做好复试工作打下坚实基础。 \n\n:::\n', 'admin', '2022-04-18 14:54:50', 'NEWS');
INSERT INTO `article` VALUES ('17', '2022年“五一”放假安排来了！放假调休共5天', '# 据中国政府网消息，2022五一放假安排为：4月30日至5月4日放假调休，共5天。4月24日（星期日）、5月7日（星期六）上班。\n\n::: hljs-center\n\n![fjap1](https://p4.img.cctvpic.com/photoworkspace/contentimg/2022/04/10/2022041015313261952.jpeg)\n![fjap2](https://p3.img.cctvpic.com/photoworkspace/contentimg/2022/04/10/2022041015313680005.jpeg)\n\n:::\n', 'admin', '2022-04-18 14:57:51', 'ANN');
INSERT INTO `article` VALUES ('18', '我校顺利完成2022年硕士研究生招生复试工作', '::: hljs-center\n\n# 我校顺利完成2022年硕士研究生招生复试工作\n\n:::\n\n::: hljs-left\n\n远隔千里，“云”端相会。近日，我校2022年硕士研究生复试工作圆满收官，7000余人次考生在“云”端参加了复试，全校近2000名教师和工作人员全力以“复”参与其中。此次复试工作自3月18日起，在两校区进行，使用了100余间“智慧教室”、50余间会议室，一直持续到4月中旬顺利结束。\n\n　　复试期间，校党委书记张七、校长李八、副校长王九全等校领导分别深入网络远程复试现场，检查、指导研究生复试组织实施情况。 \n强化组织，狠抓落实，确保复试组织平稳有序。为强化硕士研究生招生复试录取工作组织，在学校研究生招生工作领导小组领导下，学校成立研究生招生考试工作组、研究生网络远程复试突发事件应急管理小组、研究生复试录取工作督导组，各学院成立复试工作小组。研究生院、党委宣传部、党委学生工作部、党委保卫部、保密办公室、信息中心、后勤保障部、校医院等部门及各招生学院精心谋划、统筹兼顾、精准施策、周密部署、狠抓落实。\n\n　　学校认真落实教育部关于复试工作“一平台、两识别、三随机、四比对”的具体要求，制定了包括《2022年计算机学院硕士研究生复试工作方案》等在内的30余个方案、制度、规范及工作流程等文件，进一步完善、优化了研究生复试命题、保密、录取等环节的管理，压实工作责任，强化风险防范，确保研究生复试工作安全顺利实施。\n\n　　在此基础上，学校严格复试管理，加强复试准备情况自查及专项督导，做实做细复试工作培训、复试模拟演练、复试过程管理、复试命题管理、复试工作条件保障，严肃考风考纪。以复试工作培训为例，学校重点针对工作纪律、安全保密、复试流程、软件平台操作、调剂复试录取等内容面向复试工作小组成员、复试秘书、复试工作人员等校院两级开展了60余场线上及线下培训，培训合格才能上岗，配齐建强复试工作队伍，为做好复试工作打下坚实基础。 \n\n:::\n', 'admin', '2022-04-18 14:58:18', 'NEWS');
INSERT INTO `article` VALUES ('19', '我校顺利完成2022年硕士研究生招生复试工作', '::: hljs-center\n\n# 我校顺利完成2022年硕士研究生招生复试工作\n\n:::\n\n::: hljs-left\n\n远隔千里，“云”端相会。近日，我校2022年硕士研究生复试工作圆满收官，7000余人次考生在“云”端参加了复试，全校近2000名教师和工作人员全力以“复”参与其中。此次复试工作自3月18日起，在两校区进行，使用了100余间“智慧教室”、50余间会议室，一直持续到4月中旬顺利结束。\n\n　　复试期间，校党委书记张七、校长李八、副校长王九全等校领导分别深入网络远程复试现场，检查、指导研究生复试组织实施情况。 \n强化组织，狠抓落实，确保复试组织平稳有序。为强化硕士研究生招生复试录取工作组织，在学校研究生招生工作领导小组领导下，学校成立研究生招生考试工作组、研究生网络远程复试突发事件应急管理小组、研究生复试录取工作督导组，各学院成立复试工作小组。研究生院、党委宣传部、党委学生工作部、党委保卫部、保密办公室、信息中心、后勤保障部、校医院等部门及各招生学院精心谋划、统筹兼顾、精准施策、周密部署、狠抓落实。\n\n　　学校认真落实教育部关于复试工作“一平台、两识别、三随机、四比对”的具体要求，制定了包括《2022年计算机学院硕士研究生复试工作方案》等在内的30余个方案、制度、规范及工作流程等文件，进一步完善、优化了研究生复试命题、保密、录取等环节的管理，压实工作责任，强化风险防范，确保研究生复试工作安全顺利实施。\n\n　　在此基础上，学校严格复试管理，加强复试准备情况自查及专项督导，做实做细复试工作培训、复试模拟演练、复试过程管理、复试命题管理、复试工作条件保障，严肃考风考纪。以复试工作培训为例，学校重点针对工作纪律、安全保密、复试流程、软件平台操作、调剂复试录取等内容面向复试工作小组成员、复试秘书、复试工作人员等校院两级开展了60余场线上及线下培训，培训合格才能上岗，配齐建强复试工作队伍，为做好复试工作打下坚实基础。 \n\n:::\n', 'admin', '2022-04-18 14:58:26', 'NEWS');
INSERT INTO `article` VALUES ('20', ' 我校顺利完成2022年硕士研究生招生复试工作', '::: hljs-center\n\n# 我校顺利完成2022年硕士研究生招生复试工作\n\n:::\n\n::: hljs-left\n\n### 远隔千里，“云”端相会。近日，我校2022年硕士研究生复试工作圆满收官，7000余人次考生在“云”端参加了复试，全校近2000名教师和工作人员全力以“复”参与其中。此次复试工作自3月18日起，在两校区进行，使用了100余间“智慧教室”、50余间会议室，一直持续到4月中旬顺利结束。\n\n### 复试期间，校党委书记张七、校长李八、副校长王九全等校领导分别深入网络远程复试现场，检查、指导研究生复试组织实施情况。 \n强化组织，狠抓落实，确保复试组织平稳有序。为强化硕士研究生招生复试录取工作组织，在学校研究生招生工作领导小组领导下，学校成立研究生招生考试工作组、研究生网络远程复试突发事件应急管理小组、研究生复试录取工作督导组，各学院成立复试工作小组。研究生院、党委宣传部、党委学生工作部、党委保卫部、保密办公室、信息中心、后勤保障部、校医院等部门及各招生学院精心谋划、统筹兼顾、精准施策、周密部署、狠抓落实。\n\n### 学校认真落实教育部关于复试工作“一平台、两识别、三随机、四比对”的具体要求，制定了包括《2022年计算机学院硕士研究生复试工作方案》等在内的30余个方案、制度、规范及工作流程等文件，进一步完善、优化了研究生复试命题、保密、录取等环节的管理，压实工作责任，强化风险防范，确保研究生复试工作安全顺利实施。\n\n### 在此基础上，学校严格复试管理，加强复试准备情况自查及专项督导，做实做细复试工作培训、复试模拟演练、复试过程管理、复试命题管理、复试工作条件保障，严肃考风考纪。以复试工作培训为例，学校重点针对工作纪律、安全保密、复试流程、软件平台操作、调剂复试录取等内容面向复试工作小组成员、复试秘书、复试工作人员等校院两级开展了60余场线上及线下培训，培训合格才能上岗，配齐建强复试工作队伍，为做好复试工作打下坚实基础。 \n\n:::\n', 'admin', '2022-04-18 14:58:45', 'NEWS');
INSERT INTO `article` VALUES ('21', '关于开展2022届毕业设计（论文）学术不端行为检测工作的通知', '::: hljs-center\n\n# 关于开展2022届毕业设计（论文）学术不端行为检测工作的通知\n\n:::\n各学院：\n\n为进一步提高我校毕业设计（论文）质量，加强学术规范和学风建设，营造学术诚信氛围，杜绝抄袭、拷贝、篡改已有科研成果等学术不端现象的发生，根据《学位论文作假行为处理办法》（中华人民共和国教育部令第34号）、《计算机学院学位论文作假行为处理办法》等文件精神，现将2022届毕业设计（论文）学术不端行为检测（查重）工作部署如下：\n\n一、检测范围\n\n所有2020届本科专业毕业设计（论文）。\n\n二、检测时间\n\n各二级学院检测时间由学院自行确定并报教务处备案，应确保做好毕业设计（论文）学术不端行为检测、答辩与成绩录入工作的紧密衔接，稳定推进2020届毕业生工作有序进行。\n\n三、检测方式\n\n学生经指导教师同意定稿后，将毕业设计（论文）文档上传“中国知网毕业设计（论文）管理系统”；经指导教师审核通过后，文档自动进入“中国知网大学生论文检测系统”检测序列。\n\n四、检测要求\n\n毕业设计（论文）检测结果中“去除本人已发表文献复制比”（以下简称“复制比”）须低于30%，方可进入答辩阶段。\n\n五、检测结果处理办法\n\n1.初检：复制比低于30%（含）参加正常答辩；高于30%的，学生进行修改后参加复检。\n\n2.复检：复制比低于30%（含）参加正常答辩；高于30%的，学生进行修改后可申请超权限检测。\n\n3.超权限检测：复制比低于30%（含）参加二次答辩；若高于30%则须参加“毕业设计（论文）”课程重新学习。\n\n六、注意事项\n\n1.各二级学院应成立毕业设计（论文）学术不端行为检测工作小组，组织检查工作，向学生和指导教师说明检测工作的具体安排，强调毕业设计（论文）纪律，从思想上杜绝学术不端行为的发生。\n\n2.毕业设计（论文）原创性由学生本人负责。若学生提交的检测文档与其毕业设计（论文）不一致，将认定为学位论文作假行为，由此产生的后果由学生本人负责。指导教师应严格审核学生提交的检测文档是否与其毕业设计（论文）相一致。\n\n3.学术不端行为检测系统结果仅作为学位论文原创性参考依据之一。抄袭未发表的他人文章，翻译外文发表的研究结果并将其作为自己的成果等行为也将视情况认定为作假行为。根据《厦门工学院学位论文作假行为处理办法》，经学校核定严重作假者，给予取消其学位申请资格、开除学籍等处分；已经获得学位的，按相关程序公告撤销学位证书（包括电子注册证书）、荣誉称号等，并将调查结果寄送至其所在单位。\n\n \n::: hljs-right\n\n\n计算机学院教务处\n\n2022年4月18日\n\n:::\n\n\n', 'admin', '2022-04-18 14:59:15', 'NEWS');
INSERT INTO `article` VALUES ('22', '关于开展2022届毕业设计（论文）学术不端行为检测工作的通知', '::: hljs-center\n\n# 关于开展2022届毕业设计（论文）学术不端行为检测工作的通知\n\n:::\n各学院：\n\n为进一步提高我校毕业设计（论文）质量，加强学术规范和学风建设，营造学术诚信氛围，杜绝抄袭、拷贝、篡改已有科研成果等学术不端现象的发生，根据《学位论文作假行为处理办法》（中华人民共和国教育部令第34号）、《计算机学院学位论文作假行为处理办法》等文件精神，现将2022届毕业设计（论文）学术不端行为检测（查重）工作部署如下：\n\n一、检测范围\n\n所有2020届本科专业毕业设计（论文）。\n\n二、检测时间\n\n各二级学院检测时间由学院自行确定并报教务处备案，应确保做好毕业设计（论文）学术不端行为检测、答辩与成绩录入工作的紧密衔接，稳定推进2020届毕业生工作有序进行。\n\n三、检测方式\n\n学生经指导教师同意定稿后，将毕业设计（论文）文档上传“中国知网毕业设计（论文）管理系统”；经指导教师审核通过后，文档自动进入“中国知网大学生论文检测系统”检测序列。\n\n四、检测要求\n\n毕业设计（论文）检测结果中“去除本人已发表文献复制比”（以下简称“复制比”）须低于30%，方可进入答辩阶段。\n\n五、检测结果处理办法\n\n1.初检：复制比低于30%（含）参加正常答辩；高于30%的，学生进行修改后参加复检。\n\n2.复检：复制比低于30%（含）参加正常答辩；高于30%的，学生进行修改后可申请超权限检测。\n\n3.超权限检测：复制比低于30%（含）参加二次答辩；若高于30%则须参加“毕业设计（论文）”课程重新学习。\n\n六、注意事项\n\n1.各二级学院应成立毕业设计（论文）学术不端行为检测工作小组，组织检查工作，向学生和指导教师说明检测工作的具体安排，强调毕业设计（论文）纪律，从思想上杜绝学术不端行为的发生。\n\n2.毕业设计（论文）原创性由学生本人负责。若学生提交的检测文档与其毕业设计（论文）不一致，将认定为学位论文作假行为，由此产生的后果由学生本人负责。指导教师应严格审核学生提交的检测文档是否与其毕业设计（论文）相一致。\n\n3.学术不端行为检测系统结果仅作为学位论文原创性参考依据之一。抄袭未发表的他人文章，翻译外文发表的研究结果并将其作为自己的成果等行为也将视情况认定为作假行为。根据《厦门工学院学位论文作假行为处理办法》，经学校核定严重作假者，给予取消其学位申请资格、开除学籍等处分；已经获得学位的，按相关程序公告撤销学位证书（包括电子注册证书）、荣誉称号等，并将调查结果寄送至其所在单位。\n\n \n::: hljs-right\n\n\n计算机学院教务处\n\n2022年4月18日\n\n:::\n\n\n', 'admin', '2022-04-18 14:59:37', 'NEWS');

-- ----------------------------
-- Table structure for course
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course` (
  `course_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `course_name` varchar(20) DEFAULT NULL COMMENT '课程名',
  `teacher_id` int(11) DEFAULT NULL COMMENT '教师id',
  `teacher_name` varchar(20) DEFAULT NULL COMMENT '教师姓名',
  PRIMARY KEY (`course_id`),
  KEY `c_teacherId` (`teacher_id`),
  CONSTRAINT `c_teacherId` FOREIGN KEY (`teacher_id`) REFERENCES `teacher` (`teacher_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of course
-- ----------------------------
INSERT INTO `course` VALUES ('2', '数据结构', '20004', '尤雨溪');
INSERT INTO `course` VALUES ('3', '操作系统', '20001', '林纳斯');
INSERT INTO `course` VALUES ('4', '算法导论', '20003', '迪杰斯特拉');
INSERT INTO `course` VALUES ('5', '计算机网络', '20001', '林纳斯');
INSERT INTO `course` VALUES ('6', 'Vue入门实战', '20004', '尤雨溪');
INSERT INTO `course` VALUES ('7', 'Python程序设计', '20002', '图灵');
INSERT INTO `course` VALUES ('9', 'Java程序设计', '20003', '迪杰斯特拉');
INSERT INTO `course` VALUES ('10', 'MySQL入门到精通', '20001', '林纳斯');
INSERT INTO `course` VALUES ('11', 'Web安全攻防', '20002', '图灵');

-- ----------------------------
-- Table structure for files
-- ----------------------------
DROP TABLE IF EXISTS `files`;
CREATE TABLE `files` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) DEFAULT NULL COMMENT '文件名称',
  `type` varchar(255) DEFAULT NULL COMMENT '文件类型',
  `size` bigint(20) DEFAULT NULL COMMENT '文件大小',
  `url` varchar(255) DEFAULT NULL COMMENT '文件地址',
  `is_delete` tinyint(1) DEFAULT '0' COMMENT '是否删除',
  `enable` tinyint(1) DEFAULT '1' COMMENT '是否禁用',
  `md5` varchar(255) DEFAULT NULL COMMENT '文件md5',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of files
-- ----------------------------
INSERT INTO `files` VALUES ('15', '101c8e9ca58944e6b761e740a076d53a.xlsx', 'xlsx', '9', 'http://localhost:9090/file/aaedab42324849b9bb8fb93b3038fc30.xlsx', '1', '1', '5bc61c598d0f122b9299f1dba102bc1d');
INSERT INTO `files` VALUES ('16', '程序员简历模板（应届生求职）.pdf', 'pdf', '121', 'http://localhost:9090/file/e4850292584945c0891d69ebdfc59184.pdf', '1', '1', '3a85bfec0fd6439af6a1c03dac2fc7f8');
INSERT INTO `files` VALUES ('17', '赵号龙_Java实习生_17750655156.pdf', 'pdf', '120', 'http://localhost:9090/file/download/48a864a784fa42fc86e83682cdeb30a5.pdf', '1', '1', '82ef04f9ad83f8a1c50723a9f0b39a91');
INSERT INTO `files` VALUES ('18', '17e1dafc63c94d5eb07dc03fd6198217.jpg', 'jpg', '4', 'http://localhost:9090/file/49ea7d76b98141f1a296c300175abc12.jpg', '1', '1', 'fb8603b38f97b6e97c089c15a7b5b1e0');
INSERT INTO `files` VALUES ('19', '用户信息 (2).xlsx', 'xlsx', '9', 'http://localhost:9090/file/da3471221a8246b2b08409120a92e431.xlsx', '1', '1', '5622528057965857c3c2e0bf15b0aa5d');
INSERT INTO `files` VALUES ('20', 'debug.log', 'log', '0', 'http://localhost:9090/file/5c45ed5c7e3b42daaea42eb278044996.log', '1', '1', 'd41d8cd98f00b204e9800998ecf8427e');
INSERT INTO `files` VALUES ('21', '学生信息.xlsx', 'xlsx', '4', 'http://localhost:9090/file/69adcf4b9ff74f59872787721b4336b3.xlsx', '0', '1', 'b1ebb1851384686418ced414c808e8c7');
INSERT INTO `files` VALUES ('22', '49ea7d76b98141f1a296c300175abc12.jpg', 'jpg', '4', 'http://localhost:9090/file/49ea7d76b98141f1a296c300175abc12.jpg', '0', '1', 'fb8603b38f97b6e97c089c15a7b5b1e0');
INSERT INTO `files` VALUES ('23', '49ea7d76b98141f1a296c300175abc12.jpg', 'jpg', '4', 'http://localhost:9090/file/49ea7d76b98141f1a296c300175abc12.jpg', '0', '1', 'fb8603b38f97b6e97c089c15a7b5b1e0');
INSERT INTO `files` VALUES ('24', '69adcf4b9ff74f59872787721b4336b3.xlsx', 'xlsx', '4', 'http://localhost:9090/file/69adcf4b9ff74f59872787721b4336b3.xlsx', '0', '1', 'b1ebb1851384686418ced414c808e8c7');
INSERT INTO `files` VALUES ('25', 'head.jpg', 'jpg', '4', 'http://localhost:9090/file/d51428e3436346fda8b3dffebc9e9389.jpg', '0', '1', 'c42b8a3c32c236108830e83f0d5da089');
INSERT INTO `files` VALUES ('26', 'Linus.jpg', 'jpg', '3', 'http://localhost:9090/file/145f819749ff4a52b1159a50c5f3c091.jpg', '0', '1', '25d3fc15db0e262b98c8dc0ad416ec05');
INSERT INTO `files` VALUES ('27', 'head.jpg', 'jpg', '4', 'http://localhost:9090/file/d51428e3436346fda8b3dffebc9e9389.jpg', '0', '1', 'c42b8a3c32c236108830e83f0d5da089');
INSERT INTO `files` VALUES ('28', '下载.jpg', 'jpg', '4', 'http://localhost:9090/file/b9bbe720ce7a412a8b3e46622f2922b7.jpg', '0', '1', 'd3ec4e9eaec1c77fa9cecf5d43b7608e');
INSERT INTO `files` VALUES ('29', 'images.jpg', 'jpg', '5', 'http://localhost:9090/file/396ba191271945669261bf069b5099d5.jpg', '0', '1', 'c78c1821efb72a22da9ab6a35ea41f8b');
INSERT INTO `files` VALUES ('30', 'student.jpg', 'jpg', '8', 'http://localhost:9090/file/14f1977a0f724034aa25d43453476d07.jpg', '0', '1', '1dc220cded9d77df2d0ab411c959d068');

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(50) DEFAULT NULL COMMENT '菜单名称',
  `path` varchar(255) DEFAULT NULL COMMENT '路径',
  `icon` varchar(255) DEFAULT NULL COMMENT '图标',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `pid` int(11) DEFAULT NULL,
  `page_path` varchar(255) DEFAULT NULL COMMENT '页面路径',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES ('1', '主页', '/home', 'el-icon-house', '11', null, 'Home');
INSERT INTO `menu` VALUES ('3', '系统管理', null, 'el-icon-s-platform', null, null, '');
INSERT INTO `menu` VALUES ('8', '学生管理', '/students', 'el-icon-user', null, '3', 'Students');
INSERT INTO `menu` VALUES ('9', '教师管理', '/teachers', 'el-icon-user-solid', null, '3', 'Teachers');
INSERT INTO `menu` VALUES ('10', '预约信息管理', '/resInfos', 'el-icon-date', null, '3', 'ResInfos');
INSERT INTO `menu` VALUES ('11', '课程管理', '/courses', 'el-icon-tickets', null, '3', 'Courses');
INSERT INTO `menu` VALUES ('12', '角色管理', '/role', 'el-icon-user-solid', null, '3', 'Role');
INSERT INTO `menu` VALUES ('13', '菜单管理', '/menu', 'el-icon-menu', null, '3', 'Menu');
INSERT INTO `menu` VALUES ('14', '我的预约', '/teacherResInfos', 'el-icon-s-claim', null, '3', 'TeacherResInfos');
INSERT INTO `menu` VALUES ('15', '文章发布', '/article', 'el-icon-news', null, '3', 'Article');
INSERT INTO `menu` VALUES ('16', '文件管理', '/file', 'el-icon-files', null, '3', 'File');

-- ----------------------------
-- Table structure for news
-- ----------------------------
DROP TABLE IF EXISTS `news`;
CREATE TABLE `news` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '新闻id',
  `title` varchar(255) DEFAULT NULL COMMENT '新闻标题',
  `path` varchar(255) DEFAULT NULL COMMENT '新闻路径',
  `img` varchar(255) DEFAULT NULL COMMENT '新闻图片',
  `content` varchar(255) DEFAULT NULL COMMENT '新闻内容',
  `upload_time` date DEFAULT NULL COMMENT '上传日期',
  `type` varchar(255) DEFAULT 'NEWS' COMMENT '新闻类型 新闻’NEWS‘ 公告’ ANNOUNCEMENT‘',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of news
-- ----------------------------

-- ----------------------------
-- Table structure for reserve_infos
-- ----------------------------
DROP TABLE IF EXISTS `reserve_infos`;
CREATE TABLE `reserve_infos` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `teacher_name` varchar(20) DEFAULT NULL COMMENT '教师姓名',
  `student_name` varchar(20) DEFAULT NULL COMMENT '学生姓名',
  `course_name` varchar(20) DEFAULT NULL COMMENT '课程姓名',
  `res_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '预约时间',
  `office` varchar(20) DEFAULT NULL COMMENT '办公室',
  `teacher_id` int(9) NOT NULL COMMENT '老师id',
  `student_id` int(9) NOT NULL COMMENT '学生id',
  `course_id` int(9) DEFAULT NULL COMMENT '课程id',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建请求时间',
  `accept_flag` varchar(20) DEFAULT NULL COMMENT '接受预约标志',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of reserve_infos
-- ----------------------------
INSERT INTO `reserve_infos` VALUES ('3', '尤雨溪', '李四', '数据结构', '2022-03-30 02:30:00', '求知楼311', '20004', '2018002', '2', '2022-03-19 18:03:15', null, null);
INSERT INTO `reserve_infos` VALUES ('4', '迪杰斯特拉', '张三', '算法导论', '2022-03-30 00:30:00', '求知楼302', '20003', '2018001', '4', '2022-03-19 21:24:38', 'WAIT', null);
INSERT INTO `reserve_infos` VALUES ('5', '林纳斯', '张三', '计算机网络', '2022-03-24 06:30:00', '求知楼302', '20001', '2018001', '5', '2022-03-19 21:29:13', 'ACCEPT', null);
INSERT INTO `reserve_infos` VALUES ('6', '林纳斯', '张三', '操作系统', '2022-04-27 00:30:00', '求知楼302', '20001', '2018001', '3', '2022-04-08 20:37:21', 'ACCEPT', null);
INSERT INTO `reserve_infos` VALUES ('7', '林纳斯', '张三', '计算机网络', '2022-04-27 00:30:00', '求知楼302', '20001', '2018001', '5', '2022-04-15 09:21:08', 'ACCEPT', null);
INSERT INTO `reserve_infos` VALUES ('8', '林纳斯', '张三', 'MySQL入门到精通', '2022-04-28 00:30:00', '求知楼302', '20001', '2018001', '10', '2022-04-15 10:52:15', 'WAIT', null);
INSERT INTO `reserve_infos` VALUES ('9', '林纳斯', '张三', '计算机网络', '2022-04-28 08:27:00', '求知楼302', '20001', '2018001', '5', '2022-04-15 10:52:53', 'WAIT', null);
INSERT INTO `reserve_infos` VALUES ('10', '尤雨溪', '张三', 'Vue入门实战', '2022-04-28 00:30:00', '求知楼311', '20004', '2018001', '6', '2022-04-15 10:55:17', 'WAIT', null);
INSERT INTO `reserve_infos` VALUES ('11', '迪杰斯特拉', '张三', 'Java程序设计', '2022-05-10 00:30:00', '求知楼302', '20003', '2018001', '9', '2022-04-15 11:31:26', 'WAIT', null);
INSERT INTO `reserve_infos` VALUES ('12', '迪杰斯特拉', '张三', 'Java程序设计', '2022-04-28 01:30:00', '求知楼302', '20003', '2018001', '9', '2022-04-15 13:17:24', 'WAIT', null);
INSERT INTO `reserve_infos` VALUES ('13', '尤雨溪', '张三', '数据结构', '2022-04-28 00:30:00', '求知楼311', '20004', '2018001', '2', '2022-04-15 13:19:13', 'WAIT', null);
INSERT INTO `reserve_infos` VALUES ('14', '尤雨溪', '王五', 'Vue入门实战', '2022-05-04 02:30:00', '求知楼311', '20004', '2018003', '6', '2022-04-19 14:20:17', 'WAIT', null);
INSERT INTO `reserve_infos` VALUES ('15', '迪杰斯特拉', '王五', '算法导论', '2022-04-28 00:30:00', '求知楼302', '20003', '2018003', '4', '2022-04-19 14:20:27', 'WAIT', null);
INSERT INTO `reserve_infos` VALUES ('16', '图灵', '王五', 'Web安全攻防', '2022-05-26 00:30:00', '求知楼302', '20002', '2018003', '11', '2022-04-19 14:20:36', 'WAIT', null);
INSERT INTO `reserve_infos` VALUES ('17', '图灵', '王五', 'Python程序设计', '2022-05-18 00:30:00', '求知楼302', '20002', '2018003', '7', '2022-04-19 14:20:44', 'WAIT', null);
INSERT INTO `reserve_infos` VALUES ('18', '图灵', '王五', 'Python程序设计', '2022-04-27 02:30:00', '求知楼302', '20002', '2018003', '7', '2022-04-19 16:29:23', 'WAIT', null);

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) DEFAULT NULL COMMENT '角色名称',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `role` varchar(1) DEFAULT NULL COMMENT '身份唯一标识 0 admin 1 老师 2 学生',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', '管理员', '	管理员', '0');
INSERT INTO `role` VALUES ('2', '老师', '老师', '1');
INSERT INTO `role` VALUES ('3', '学生', '学生', '2');

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `student_id` int(9) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `student_name` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '姓名',
  `password` varchar(16) COLLATE utf8_bin DEFAULT '123' COMMENT '密码',
  `grade` varchar(4) COLLATE utf8_bin DEFAULT '2018' COMMENT '年级',
  `major` varchar(20) COLLATE utf8_bin DEFAULT '物联网工程' COMMENT '专业',
  `cls` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '班级',
  `institute` varchar(30) COLLATE utf8_bin DEFAULT '计算机科学与信息工程学院' COMMENT '学院',
  `gender` varchar(2) COLLATE utf8_bin DEFAULT NULL COMMENT '性别',
  `tel` varchar(11) COLLATE utf8_bin DEFAULT NULL COMMENT '电话号码',
  `email` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '电子邮件',
  `card_id` varchar(18) COLLATE utf8_bin DEFAULT NULL COMMENT '身份证号',
  `role` varchar(1) COLLATE utf8_bin DEFAULT '2' COMMENT '角色(0管理员，1教师，2学生)',
  `avatar` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '头像',
  PRIMARY KEY (`student_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2018094 DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='学生信息表';

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES ('2018001', '张三', '123', '2018', '物联网工程', '1', '计算机科学与信息工程学院', '男', '17755558557', 'zhangsan@qq.com', '1306666', '2', 'http://localhost:9090/file/14f1977a0f724034aa25d43453476d07.jpg');
INSERT INTO `student` VALUES ('2018002', '李四', '1234', '2018', '物联网工程', '1', '计算机科学与信息工程学院', '男', '18888888888', 'lisi@qq.com', '1307777', '2', null);
INSERT INTO `student` VALUES ('2018003', '王五', '123', '2018', '物联网工程', '1', '计算机科学与信息工程学院', '女', '19999999999', 'wangwu@qq.com', '1308888', '2', null);
INSERT INTO `student` VALUES ('2018004', '李华', '123', '2018', '软件工程', '1', '计算机科学与信息工程学院', '女', '16655558885', 'lihua@163.com', '1406666', '2', null);
INSERT INTO `student` VALUES ('2018008', '川建国', null, '2018', '通信工程', '1', '计算机与信息工程学院', '男', '13088888888', 'chuan@163.com', '1888888', '2', null);
INSERT INTO `student` VALUES ('2018018', '11', '123', '2018', '财务管理', '1', '经管学院', '女', null, null, '13045646xxx', '2', null);
INSERT INTO `student` VALUES ('2018021', 'vv', '123', '2018', '物联网工程', '1', '计算机科学与信息工程学院', '女', '19999999999', 'wangwu@qq.com', '1308888', '2', null);
INSERT INTO `student` VALUES ('2018022', 'gg', '123', '2018', '软件工程', '1', '商学院', '女', '16655558885', 'lihua@163.com', '1406666', '2', null);
INSERT INTO `student` VALUES ('2018023', 'hhh', '123', '2018', '通信工程', '1', '计算机与信息工程学院', '男', '13088888888', 'chuan@163.com', '1888888', '2', null);
INSERT INTO `student` VALUES ('2018024', 'j', '123', '2018', '财务管理', '1', '经管学院', '女', '17777777777', 'rui@qq.com', '13045646xxx', '2', null);
INSERT INTO `student` VALUES ('2018025', 'test', '123', '2018', '物联网工程', '1', '计算机科学与信息工程学院', '男', '17755555555', 'zhangsan@qq.com', '1306666', '2', null);
INSERT INTO `student` VALUES ('2018026', 'test1', '123', '2018', '物联网工程', '1', '计算机科学与信息工程学院', '男', '17755555556', 'lisi@qq.com', '1306667', '2', null);
INSERT INTO `student` VALUES ('2018027', 'test2', '123', '2018', '物联网工程', '1', '计算机科学与信息工程学院', '女', '17755555557', 'wangwu@qq.com', '1306668', '2', null);
INSERT INTO `student` VALUES ('2018028', 'test3', '123', '2018', '软件工程', '1', '商学院', '女', '17755555558', 'lihua@163.com', '1306669', '2', null);
INSERT INTO `student` VALUES ('2018029', 'test4', '123', '2018', '通信工程', '1', '计算机与信息工程学院', '男', '17755555559', 'chuan@163.com', '1306670', '2', null);
INSERT INTO `student` VALUES ('2018031', 'test', '123', '2018', '物联网工程', '1', '计算机科学与信息工程学院', '男', '17755555555', 'zhangsan@qq.com', '1306666', '2', null);
INSERT INTO `student` VALUES ('2018032', 'test1', '123', '2018', '物联网工程', '1', '计算机科学与信息工程学院', '男', '17755555556', 'lisi@qq.com', '1306667', '2', null);
INSERT INTO `student` VALUES ('2018044', 'test222', '123', '2018', '物联网', '1', '计算机', '男', '17777777777', 'test@test.com', '11111', '2', null);
INSERT INTO `student` VALUES ('2018045', 'test0414', '123', '2018', '软件工程', '2', '计算机科学与信息工程学院', '男', '1777777777', 'test@test.com', '1888', '2', null);
INSERT INTO `student` VALUES ('2018046', 'testData1', '123', '2018', '物联网工程', '1', '计算机科学与信息工程学院', '男', '17755555555', 'zhangsan@qq.com', '1306666', '2', null);
INSERT INTO `student` VALUES ('2018047', 'testData2', '123', '2018', '物联网工程', '1', '计算机科学与信息工程学院', '男', '17755555556', 'lisi@qq.com', '1306667', '2', null);
INSERT INTO `student` VALUES ('2018048', 'testData3', '123', '2018', '物联网工程', '1', '计算机科学与信息工程学院', '女', '17755555557', 'wangwu@qq.com', '1306668', '2', null);
INSERT INTO `student` VALUES ('2018049', 'testData4', '123', '2018', '软件工程', '1', '商学院', '女', '17755555558', 'lihua@163.com', '1306669', '2', null);
INSERT INTO `student` VALUES ('2018050', 'testData5', '123', '2018', '通信工程', '1', '计算机与信息工程学院', '男', '17755555559', 'chuan@163.com', '1306670', '2', null);
INSERT INTO `student` VALUES ('2018051', 'testData6', '123', '2018', '财务管理', '1', '经管学院', '女', '17755555560', 'rui@qq.com', '1306671', '2', null);
INSERT INTO `student` VALUES ('2018052', 'testData1', '123', '2018', '物联网工程', '1', '计算机科学与信息工程学院', '男', '17755555555', 'zhangsan@qq.com', '1306666', '2', null);
INSERT INTO `student` VALUES ('2018053', 'testData2', '123', '2018', '物联网工程', '1', '计算机科学与信息工程学院', '男', '17755555556', 'lisi@qq.com', '1306667', '2', null);
INSERT INTO `student` VALUES ('2018054', 'testData3', '123', '2018', '物联网工程', '1', '计算机科学与信息工程学院', '女', '17755555557', 'wangwu@qq.com', '1306668', '2', null);
INSERT INTO `student` VALUES ('2018055', 'testData4', '123', '2018', '软件工程', '1', '商学院', '女', '17755555558', 'lihua@163.com', '1306669', '2', null);
INSERT INTO `student` VALUES ('2018056', 'testData5', '123', '2018', '通信工程', '1', '计算机与信息工程学院', '男', '17755555559', 'chuan@163.com', '1306670', '2', null);
INSERT INTO `student` VALUES ('2018070', 'testData1', '123', '2018', '物联网工程', '1', '计算机科学与信息工程学院', '男', '17755555555', 'zhangsan@qq.com', '1306666', '2', null);
INSERT INTO `student` VALUES ('2018071', 'testData2', '123', '2018', '物联网工程', '1', '计算机科学与信息工程学院', '男', '17755555556', 'lisi@qq.com', '1306667', '2', null);
INSERT INTO `student` VALUES ('2018072', 'testData3', '123', '2018', '物联网工程', '1', '计算机科学与信息工程学院', '女', '17755555557', 'wangwu@qq.com', '1306668', '2', null);
INSERT INTO `student` VALUES ('2018073', 'testData4', '123', '2018', '软件工程', '1', '商学院', '女', '17755555558', 'lihua@163.com', '1306669', '2', null);
INSERT INTO `student` VALUES ('2018074', 'testData5', '123', '2018', '通信工程', '1', '计算机与信息工程学院', '男', '17755555559', 'chuan@163.com', '1306670', '2', null);
INSERT INTO `student` VALUES ('2018075', 'testData6', '123', '2018', '财务管理', '1', '经管学院', '女', '17755555560', 'rui@qq.com', '1306671', '2', null);
INSERT INTO `student` VALUES ('2018076', 'testData1', '123', '2018', '物联网工程', '1', '计算机科学与信息工程学院', '男', '17755555555', 'zhangsan@qq.com', '1306666', '2', null);
INSERT INTO `student` VALUES ('2018077', 'testData2', '123', '2018', '物联网工程', '1', '计算机科学与信息工程学院', '男', '17755555556', 'lisi@qq.com', '1306667', '2', null);
INSERT INTO `student` VALUES ('2018078', 'testData3', '123', '2018', '物联网工程', '1', '计算机科学与信息工程学院', '女', '17755555557', 'wangwu@qq.com', '1306668', '2', null);
INSERT INTO `student` VALUES ('2018079', 'testData4', '123', '2018', '软件工程', '1', '商学院', '女', '17755555558', 'lihua@163.com', '1306669', '2', null);
INSERT INTO `student` VALUES ('2018080', 'testData5', '123', '2018', '通信工程', '1', '计算机与信息工程学院', '男', '17755555559', 'chuan@163.com', '1306670', '2', null);
INSERT INTO `student` VALUES ('2018088', 'testData1', '123', '2018', '物联网工程', '1', '计算机科学与信息工程学院', '男', '17755555555', 'zhangsan@qq.com', '1306666', '2', null);
INSERT INTO `student` VALUES ('2018089', 'testData2', '123', '2018', '物联网工程', '1', '计算机科学与信息工程学院', '男', '17755555556', 'lisi@qq.com', '1306667', '2', null);
INSERT INTO `student` VALUES ('2018090', 'testData3', '123', '2018', '物联网工程', '1', '计算机科学与信息工程学院', '女', '17755555557', 'wangwu@qq.com', '1306668', '2', null);
INSERT INTO `student` VALUES ('2018091', 'testData4', '123', '2018', '软件工程', '1', '商学院', '女', '17755555558', 'lihua@163.com', '1306669', '2', null);
INSERT INTO `student` VALUES ('2018092', 'testData5', '123', '2018', '通信工程', '1', '计算机与信息工程学院', '男', '17755555559', 'chuan@163.com', '1306670', '2', null);
INSERT INTO `student` VALUES ('2018093', 'testData6', '123', '2018', '财务管理', '1', '经管学院', '女', '17755555560', 'rui@qq.com', '1306671', '2', null);

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict` (
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  `value` varchar(255) DEFAULT NULL COMMENT '内容',
  `type` varchar(255) DEFAULT NULL COMMENT '类型'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` VALUES ('user', 'el-icon-user', 'icon');
INSERT INTO `sys_dict` VALUES ('system', 'el-icon-s-platform', 'icon');
INSERT INTO `sys_dict` VALUES ('resInfos', 'el-icon-date', 'icon');
INSERT INTO `sys_dict` VALUES ('courses', 'el-icon-tickets', 'icon');
INSERT INTO `sys_dict` VALUES ('role', 'el-icon-user-solid', 'icon');
INSERT INTO `sys_dict` VALUES ('menu', 'el-icon-menu', 'icon');
INSERT INTO `sys_dict` VALUES ('resinfos-2', 'el-icon-s-claim', 'icon');
INSERT INTO `sys_dict` VALUES ('home', 'el-icon-house', 'icon');
INSERT INTO `sys_dict` VALUES ('files', 'el-icon-files', 'icon');
INSERT INTO `sys_dict` VALUES ('news', 'el-icon-news', 'icon');

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `role_id` int(11) NOT NULL COMMENT '角色id',
  `menu_id` int(11) NOT NULL COMMENT '菜单id',
  PRIMARY KEY (`role_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色菜单关系表';

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES ('1', '1');
INSERT INTO `sys_role_menu` VALUES ('1', '3');
INSERT INTO `sys_role_menu` VALUES ('1', '8');
INSERT INTO `sys_role_menu` VALUES ('1', '9');
INSERT INTO `sys_role_menu` VALUES ('1', '10');
INSERT INTO `sys_role_menu` VALUES ('1', '11');
INSERT INTO `sys_role_menu` VALUES ('1', '12');
INSERT INTO `sys_role_menu` VALUES ('1', '13');
INSERT INTO `sys_role_menu` VALUES ('1', '15');
INSERT INTO `sys_role_menu` VALUES ('1', '16');
INSERT INTO `sys_role_menu` VALUES ('2', '1');
INSERT INTO `sys_role_menu` VALUES ('2', '3');
INSERT INTO `sys_role_menu` VALUES ('2', '8');
INSERT INTO `sys_role_menu` VALUES ('2', '14');

-- ----------------------------
-- Table structure for teacher
-- ----------------------------
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher` (
  `teacher_id` int(9) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `teacher_name` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '姓名',
  `password` varchar(16) COLLATE utf8_bin DEFAULT '123' COMMENT '密码',
  `institute` varchar(20) COLLATE utf8_bin DEFAULT '计算机科学与信息工程学院' COMMENT '学院',
  `gender` varchar(6) COLLATE utf8_bin DEFAULT NULL COMMENT '性别',
  `tel` varchar(11) COLLATE utf8_bin DEFAULT NULL COMMENT '电话号码',
  `email` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '邮箱',
  `card_id` varchar(18) COLLATE utf8_bin DEFAULT NULL COMMENT '身份证号',
  `type` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '职称',
  `role` varchar(1) COLLATE utf8_bin DEFAULT '1' COMMENT '角色（0管理员，1教师，2学生）',
  `office` varchar(25) COLLATE utf8_bin DEFAULT '求知楼302' COMMENT '办公室',
  `avatar` varchar(100) COLLATE utf8_bin DEFAULT '' COMMENT '头像',
  PRIMARY KEY (`teacher_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=20010 DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='教师信息表';

-- ----------------------------
-- Records of teacher
-- ----------------------------
INSERT INTO `teacher` VALUES ('20001', '林纳斯', '123', '计算机科学与信息工程学院', '男', '18877777777', 'linus@qq.com', '321111', '讲师', '1', '求知楼302', 'http://localhost:9090/file/396ba191271945669261bf069b5099d5.jpg');
INSERT INTO `teacher` VALUES ('20002', '图灵', '123', '计算机科学与信息工程学院', '男', '19999999999', 'tuing@163.com', '321112', '教授', '1', '求知楼302', null);
INSERT INTO `teacher` VALUES ('20003', '迪杰斯特拉', '1234', '计算机科学与信息工程学院', '男', '17788888888', 'dj@gmai.com', '321113', '教授', '1', '求知楼302', null);
INSERT INTO `teacher` VALUES ('20004', '尤雨溪', '123', '计算机科学与信息工程学院', '男', '18899779966', 'you@gmail.com', '321114', '讲师', '1', '求知楼311', null);
INSERT INTO `teacher` VALUES ('20009', '孙悟空', '123', '计算机科学与信息工程学院', '男', '18877777778', 'linus@qq.com', '321115', '讲师', '1', '求知楼102', null);
