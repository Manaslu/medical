prompt PL/SQL Developer import file
prompt Created on 2014年6月13日 by Administrator
set feedback off
set define off
prompt Dropping T02_SYS_MENU...
drop table T02_SYS_MENU cascade constraints;
prompt Creating T02_SYS_MENU...
create table T02_SYS_MENU
(
  menu_id       NUMBER,
  par_menu_id   NUMBER,
  menu_title    VARCHAR2(40),
  menu_name     VARCHAR2(40),
  menu_desc     VARCHAR2(200),
  action        VARCHAR2(200),
  state         CHAR(1),
  role_check_id VARCHAR2(20),
  menu_type     CHAR(1),
  indexno       INTEGER,
  deal_id       VARCHAR2(20),
  redirectto    VARCHAR2(200),
  template      VARCHAR2(2000),
  params        VARCHAR2(2000)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt Disabling triggers for T02_SYS_MENU...
alter table T02_SYS_MENU disable all triggers;
prompt Loading T02_SYS_MENU...
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (108, 99999, '知识库', '知识库', '知识库', 'repository', null, null, null, 8, null, 'share', null, null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (10801, 108, '共享知识库', '共享知识库', '共享知识库', 'share', null, null, null, 1, null, null, 'templates/repository/share.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (10802, 108, '知识库管理', '知识库管理', '知识库管理', 'manage', null, null, null, 2, null, null, 'templates/repository/manage.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (10803, 108, '知识库审核', '知识库审核', '知识库审核', 'examine', null, null, null, 3, null, null, 'templates/repository/examine.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (10804, 108, '标签库管理', '标签库管理', '标签库管理', 'tagLibrary', null, null, null, 4, null, null, 'templates/repository/labelLib.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (109, 99999, '系统运行管理', '系统运行管理', '系统运行管理', 'oms', null, null, null, 9, null, 'institution', null, null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (10901, 109, '机构管理', '机构管理', '机构管理', 'institution', null, null, null, 1, null, null, 'templates/oms/institution.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (10902, 109, '用户管理', '用户管理', '用户管理', 'user', null, null, null, 2, null, null, 'templates/oms/user.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (10903, 109, '角色管理', '角色管理', '角色管理', 'role', null, null, null, 3, null, null, 'templates/oms/role.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (10904, 109, '公告管理', '公告管理', '公告管理', 'announcement', null, null, null, 4, null, 'list', null, null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1090401, 10904, '公告列表', '公告列表', '公告列表', 'list', null, null, null, 1, null, null, 'templates/oms/announcement/list.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1090402, 10904, '公告审核', '公告审核', '公告审核', 'eap', null, null, null, 2, null, null, 'templates/oms/announcement/eap.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (10905, 109, '运行监控', '运行监控', '运行监控', 'move', null, null, null, 5, null, null, 'templates/oms/move.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (10906, 109, '用户操作日志', '用户操作日志', '用户操作日志', 'journal', null, null, null, 6, null, null, 'templates/oms/journal.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1020101, 10201, '数据字典管理', '数据字典管理', '数据字典管理', 'dataDictionary', null, null, null, 1, null, null, 'templates/receiveDownload/manage/dataDictionary.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1020102, 10201, 'E-R 图', 'E-R 图', 'E-R 图', 'er', null, null, null, 2, null, null, 'templates/receiveDownload/manage/er.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1020208, 10202, '数据加载总体情况', '数据加载总体情况', '数据加载总体情况', 'loadState', null, null, null, 8, null, null, 'templates/receiveDownload/esb/loadState.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (10302, 103, '实验数据', '实验数据', '实验数据', 'testData', null, null, null, 2, null, null, null, null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1030303, 10302, '数据下载记录', '数据下载记录', '数据下载记录', 'log', null, null, null, 3, null, null, 'templates/dataIntegration/download/log.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1030601, 10306, '清洗管理', '清洗管理', '清洗管理', 'cleanManage', null, null, null, 1, null, null, 'templates/dataIntegration/dataClean/manage.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1030602, 10306, '排重管理', '排重管理', '排重管理', 'filterManage', null, null, null, 2, null, null, 'templates/dataIntegration/dataClean/filterManage.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (101, 99999, '首页', '首页', '首页', 'index', '1', '1', '1', 1, '1', null, 'templates/index/index.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (10101, 101, '公告栏', '公告栏', '公告栏', 'notice', '1', '1', '1', 1, '1', null, 'templates/index/notice.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (10102, 101, '领导驾驶舱', '领导驾驶舱', '领导驾驶舱', 'cockpit', '1', '1', '1', 1, '1', null, 'templates/index/cockpit.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (10103, 101, '任务列表', '任务列表', '任务列表', 'task', '1', '1', '1', 1, '1', 'list', null, null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1010301, 10103, '任务列表', '任务列表', '任务列表', 'list', '1', '1', '1', 1, '1', null, 'templates/index/task/list.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1010302, 10103, '待办任务', '待办任务', '待办任务', 'todo', '1', '1', '1', 1, '1', null, 'templates/index/task/todo.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1010303, 10103, '历史任务', '历史任务', '历史任务', 'history', '1', '1', '1', 1, '1', null, 'templates/index/task/history.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1010304, 10103, '完成任务', '完成任务', '完成任务', 'complete', '1', '1', '1', 1, '1', null, 'templates/index/task/complete.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (102, 99999, '数据接收与下载', '数据接收与下载', '数据接收与下载', 'receiveDownload', '2', '2', '2', 2, '2', null, null, null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (10201, 102, '数据字典管理', '数据字典管理', '数据字典管理', 'dataDictionary', '2', '2', '2', 1, '2', null, null, null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (10202, 102, 'ESB监控报表', 'ESB监控报表', 'ESB监控报表', 'esb', '2', '2', '2', 2, '2', 'nationwideReceive', null, null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1020201, 10202, '全国中心接收监控', '全国中心接收监控', '全国中心接收监控', 'nationwideReceive', '2', '2', '2', 1, '2', null, 'templates/receiveDownload/esb/nationwideReceive.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1020202, 10202, '省中心上传监控', '省中心上传监控', '省中心上传监控', 'provinceUpload', '2', '2', '2', 3, '2', null, 'templates/receiveDownload/esb/provinceUpload.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1020203, 10202, '全国中心下载监控', '全国中心下载监控', '全国中心下载监控', 'nationwideDownload', '2', '2', '2', 2, '2', null, 'templates/receiveDownload/esb/nationwideDownload.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1020204, 10202, '省中心接收监控', '省中心接收监控', '省中心接收监控', 'provinceReceive', '2', '2', '2', 4, '2', null, 'templates/receiveDownload/esb/provinceReceive.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1020205, 10202, '异常信息监控', '异常信息监控', '异常信息监控', 'abnormal', '2', '2', '2', 5, '2', null, 'templates/receiveDownload/esb/abnormal.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1020206, 10202, '数据核对', '数据核对', '数据核对', 'nationwideProvince', '2', '2', '2', 6, '2', null, 'templates/receiveDownload/esb/nationwideProvince.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1060501, 10605, '国内小包专题分析1', '国内小包专题分析1', '国内小包专题分析1', 'analysis1', null, null, null, 1, null, null, 'templates/result/internalProject/results.html', 'filename=gnxb1.xml&index=8&type=month');
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1020207, 10202, '数据下发总体情况', '数据下发总体情况', '数据下发总体情况', 'dataDownloadSituation', '2', '2', '2', 7, '2', null, 'templates/receiveDownload/esb/dataDownloadSituation.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1060503, 10605, '国内小包专题分析3', '国内小包专题分析3', '国内小包专题分析3', 'analysis3', null, null, null, 3, null, null, 'templates/result/internalProject/results.html', 'filename=gnxb3.xml&index=8&type=month');
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1060504, 10605, '国内小包专题分析4', '国内小包专题分析4', '国内小包专题分析4', 'analysis4', null, null, null, 4, null, null, 'templates/result/internalProject/results.html', 'filename=gnxb4.xml&index=8&type=month');
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1060505, 10605, '国内小包专题分析5', '国内小包专题分析5', '国内小包专题分析5', 'analysis5', null, null, null, 5, null, null, 'templates/result/internalProject/results.html', 'filename=gnxb5.xml&index=8&type=month');
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1060506, 10605, '国内小包专题分析6', '国内小包专题分析6', '国内小包专题分析6', 'analysis6', null, null, null, 6, null, null, 'templates/result/internalProject/results.html', 'filename=gnxb6.xml&index=8&type=month');
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1060507, 10605, '国内小包专题分析7', '国内小包专题分析7', '国内小包专题分析7', 'analysis7', null, null, null, 7, null, null, 'templates/result/internalProject/results.html', 'filename=gnxb7.xml&index=8&type=month');
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1060508, 10605, '国内小包专题分析8', '国内小包专题分析8', '国内小包专题分析8', 'analysis8', null, null, null, 8, null, null, 'templates/result/internalProject/results.html', 'filename=gnxb8.xml&index=8&type=month');
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1060509, 10605, '国内小包专题分析9', '国内小包专题分析9', '国内小包专题分析9', 'analysis9', null, null, null, 9, null, null, 'templates/result/internalProject/results.html', 'filename=gnxb9.xml&index=8&type=month');
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1060510, 10605, '国内小包专题分析10', '国内小包专题分析10', '国内小包专题分析10', 'analysis10', null, null, null, 10, null, null, 'templates/result/internalProject/results.html', 'filename=gnxb10.xml&index=8&type=month');
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1060511, 10605, '国内小包专题分析11', '国内小包专题分析11', '国内小包专题分析11', 'analysis11', null, null, null, 11, null, null, 'templates/result/internalProject/results.html', 'filename=gnxb11.xml&index=8&type=month');
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1060512, 10605, '国内小包专题分析12', '国内小包专题分析12', '国内小包专题分析12', 'analysis12', null, null, null, 12, null, null, 'templates/result/internalProject/results.html', 'filename=gnxb13.xml&index=8&type=month');
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1060513, 10605, '国内小包专题分析13', '国内小包专题分析13', '国内小包专题分析13', 'analysis13', null, null, null, 13, null, null, 'templates/result/internalProject/results.html', 'filename=gnxb13.xml&index=8&type=month');
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1060514, 10605, '国内小包专题分析14', '国内小包专题分析14', '国内小包专题分析14', 'analysis14', null, null, null, 14, null, null, 'templates/result/internalProject/results.html', 'filename=gnxb14.xml&index=8&type=month');
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (10606, 106, '国际小包专题分析', '国际小包专题分析', '国际小包专题分析', 'gjxb', null, null, null, 5, null, null, null, null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1060601, 10606, '国际小包专题分析1', '国际小包专题分析1', '国际小包专题分析1', 'analysis1', null, null, null, 1, null, null, 'templates/result/internalProject/results.html', 'filename=gjxb1.xml&index=8&type=month');
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1060603, 10606, '国际小包专题分析3', '国际小包专题分析3', '国际小包专题分析3', 'analysis3', null, null, null, 3, null, null, 'templates/result/internalProject/results.html', 'filename=gjxb3.xml&index=8&type=month');
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1060604, 10606, '国际小包专题分析4', '国际小包专题分析4', '国际小包专题分析4', 'analysis4', null, null, null, 4, null, null, 'templates/result/internalProject/results.html', 'filename=gjxb4.xml&index=8&type=month');
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1060605, 10606, '国际小包专题分析5', '国际小包专题分析5', '国际小包专题分析5', 'analysis5', null, null, null, 5, null, null, 'templates/result/internalProject/results.html', 'filename=gjxb5.xml&index=8&type=month');
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1060606, 10606, '国际小包专题分析6', '国际小包专题分析6', '国际小包专题分析6', 'analysis6', null, null, null, 6, null, null, 'templates/result/internalProject/results.html', 'filename=gjxb6.xml&index=8&type=month');
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1060607, 10606, '国际小包专题分析7', '国际小包专题分析7', '国际小包专题分析7', 'analysis7', null, null, null, 7, null, null, 'templates/result/internalProject/results.html', 'filename=gjxb7.xml&index=8&type=month');
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1060608, 10606, '国际小包专题分析8', '国际小包专题分析8', '国际小包专题分析8', 'analysis8', null, null, null, 8, null, null, 'templates/result/internalProject/results.html', 'filename=gjxb8.xml&index=8&type=month');
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1060609, 10606, '国际小包专题分析9', '国际小包专题分析9', '国际小包专题分析9', 'analysis9', null, null, null, 9, null, null, 'templates/result/internalProject/results.html', 'filename=gjxb9.xml&index=8&type=month');
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1060610, 10606, '国际小包专题分析10', '国际小包专题分析10', '国际小包专题分析10', 'analysis10', null, null, null, 10, null, null, 'templates/result/internalProject/results.html', 'filename=gjxb10.xml&index=8&type=month');
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1060611, 10606, '国际小包专题分析11', '国际小包专题分析11', '国际小包专题分析11', 'analysis11', null, null, null, 11, null, null, 'templates/result/internalProject/results.html', 'filename=gjxb11.xml&index=8&type=pdate');
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1060612, 10606, '国际小包专题分析12', '国际小包专题分析12', '国际小包专题分析12', 'analysis12', null, null, null, 12, null, null, 'templates/result/internalProject/results.html', 'filename=gjxb12.xml&index=8&type=pdate');
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1060613, 10606, '国际小包专题分析13', '国际小包专题分析13', '国际小包专题分析13', 'analysis13', null, null, null, 13, null, null, 'templates/result/internalProject/results.html', 'filename=gjxb13.xml&index=8&type=pdate');
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1060614, 10606, '国际小包专题分析14', '国际小包专题分析14', '国际小包专题分析14', 'analysis14', null, null, null, 14, null, null, 'templates/result/internalProject/results.html', 'filename=gjxb14.xml&index=8&type=pdate');
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1060602, 10606, '国际小包专题分析2', '国际小包专题分析2', '国际小包专题分析2', 'analysis2', null, null, null, 2, null, null, 'templates/result/internalProject/results.html', 'filename=gjxb2.xml&index=8&type=month');
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1060701, 10607, '订阅管理', '订阅管理', '订阅管理', 'manage', null, null, null, 1, null, null, 'templates/result/subscriptions/manage.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1060702, 10607, '用户订阅', '用户订阅', '用户订阅', 'user', null, null, null, 2, null, null, 'templates/result/subscriptions/user.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1060703, 10607, '订阅日志', '订阅日志', '订阅日志', 'log', null, null, null, 3, null, null, 'templates/result/subscriptions/log.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1030302, 10302, '实验数据下载', '实验数据下载', '实验数据下载', 'download', null, null, null, 2, null, 'download', 'templates/dataIntegration/download/download.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1030201, 10302, '实验数据上传', '实验数据上传', '实验数据上传', 'upload', null, null, null, 1, null, null, 'templates/dataIntegration/upload.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (10301, 103, '数据定义', '数据定义', '数据定义', 'defined', null, null, null, 1, null, null, 'templates/dataIntegration/defined.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (103, 99999, '数据整合', '数据整合', '数据整合', 'dataIntegration', null, null, null, 3, null, 'defined', null, null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1030304, 10302, '实验数据管理', '实验数据管理', '实验数据管理', 'ds', null, null, null, 4, null, null, 'templates/dataIntegration/ds.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (10305, 103, '数据评估', '数据评估', '数据评估', 'assessment', null, null, null, 3, null, null, 'templates/dataIntegration/assessment.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (10306, 103, '数据清洗', '数据清洗', '数据清洗', 'dataClean', null, null, null, 4, null, 'cleanManage', null, null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (10307, 103, '地址清洗匹配', '地址清洗匹配', '地址清洗匹配', 'addressClean', null, null, null, 5, null, null, 'templates/dataIntegration/addressClean.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (10308, 103, '数据重构', '数据重构', '数据重构', 'restructure', null, null, null, 6, null, null, 'templates/dataIntegration/rebuild.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (10309, 103, '数据整合', '数据整合', '数据整合', 'integration', null, null, null, 7, null, null, 'templates/dataIntegration/integration.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1030302, 10303, '数据下载记录', '数据下载记录', '数据下载记录', 'log', null, null, null, 2, null, null, 'templates/dataIntegration/download/log.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1030301, 10303, '实验数据下载', '实验数据下载', '实验数据下载', 'download', null, null, null, 1, null, null, 'templates/dataIntegration/download/download.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (104, 99999, '专题流程管理', '专题流程管理', '专题流程管理', 'technologicalProcess', null, null, null, 4, null, 'demandManage', null, null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (10401, 104, '需求管理', '需求管理', '需求管理', 'demandManage', null, null, null, 1, null, 'list', null, null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1040101, 10401, '需求申请', '需求申请', '需求申请', 'list', null, null, null, 1, null, null, 'templates/technologicalProcess/demandManage/req.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1040102, 10401, '需求审批', '需求审批', '需求审批', 'eap', null, null, null, 2, null, null, 'templates/technologicalProcess/demandManage/eap.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1040103, 10401, '需求分配', '需求分配', '需求分配', 'distribution', null, null, null, 3, null, null, 'templates/technologicalProcess/demandManage/list.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1040104, 10401, '需求确认', '需求确认', '需求确认', 'affirm', null, null, null, 4, null, null, 'templates/technologicalProcess/demandManage/affirm.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (10402, 104, '过程管理', '过程管理', '过程管理', 'process', null, null, null, 2, null, 'list', null, null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1040201, 10402, '项目列表', '项目列表', '项目列表', 'list', null, null, null, 1, null, null, 'templates/technologicalProcess/process/list.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1040202, 10402, '需求量化', '需求量化', '需求量化', 'quantization', null, null, null, 6, null, null, 'templates/technologicalProcess/process/quantization.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1040203, 10402, '数据质量管理', '数据质量管理', '数据质量管理', 'quality', null, null, null, 2, null, null, 'templates/technologicalProcess/process/quality.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1040204, 10402, '分析模型管理', '分析模型管理', '分析模型管理', 'model', null, null, null, 3, null, null, 'templates/technologicalProcess/process/model.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1040205, 10402, '项目结题', '项目结题', '项目结题', 'end', null, null, null, 4, null, null, 'templates/technologicalProcess/process/end.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (10403, 104, '成果管理', '成果管理', '成果管理', 'achievement', null, null, null, 3, null, 'list', null, null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1040301, 10403, '查看成果', '查看成果', '查看成果', 'list', null, null, null, 2, null, null, 'templates/technologicalProcess/achievement/list.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1040302, 10403, '发布成果', '发布成果', '发布成果', 'publish', null, null, null, 1, null, null, 'templates/technologicalProcess/achievement/publish.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (105, 99999, '数据分析', '数据分析', '数据分析', 'dataAnalyse', null, null, null, 5, null, 'quota', null, null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (10501, 105, '指标维护', '指标维护', '指标维护', 'quota', null, null, null, 1, null, null, 'templates/dataAnalyse/quota.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (10502, 105, '数据同步', '数据同步', '数据同步', 'synchronization', null, null, null, 2, null, 'list', null, null);
commit;
prompt 100 records committed...
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1050201, 10502, '数据同步列表', '数据同步列表', '数据同步列表', 'list', null, null, null, 1, null, null, 'templates/dataAnalyse/synchronization/list.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1050202, 10502, '数据同步审核', '数据同步审核', '数据同步审核', 'examine', null, null, null, 2, null, null, 'templates/dataAnalyse/synchronization/examine.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (10503, 105, '分析过程参数化', '分析过程参数化', '分析过程参数化', 'processParametric', null, null, null, 3, null, null, 'templates/dataAnalyse/processParametric.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (10504, 105, '分析流程固化和结果发布', '分析流程固化和结果发布', '分析流程固化和结果发布', 'resultsPublishing', null, null, null, 4, null, 'list', null, null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1050401, 10504, '分析流程固化列表', '分析流程固化列表', '分析流程固化列表', 'list', null, null, null, 1, null, null, 'templates/dataAnalyse/resultsPublishing/list.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1050402, 10504, '分析流程固化审核', '分析流程固化审核', '分析流程固化审核', 'eap', null, null, null, 2, null, null, 'templates/dataAnalyse/resultsPublishing/eap.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1050403, 10504, '结果发布', '结果发布', '结果发布', 'results', null, null, null, 3, null, null, 'templates/dataAnalyse/resultsPublishing/results.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (106, 99999, '成果展示', '成果展示', '成果展示', 'result', null, null, null, 6, null, 'groupProject', null, null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (10601, 106, '报刊集团专题分析', '报刊集团专题分析', '报刊集团专题分析', 'groupProject', null, null, null, 3, null, 'overall', null, null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1060101, 10601, '报刊订阅总体情况分析', '报刊订阅总体情况分析', '报刊订阅总体情况分析', 'overall', null, null, null, 1, null, null, 'templates/result/groupProject/overall.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (10602, 106, '报刊八大专题分析', '报刊八大专题分析', '报刊八大专题分析', 'eightProjects', null, null, null, 1, null, 'trend', null, null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1060201, 10602, '报刊业务发展趋势分析', '报刊业务发展趋势分析', '报刊业务发展趋势分析', 'trend', null, null, null, 1, null, null, 'templates/result/eightProjects/trend.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (10603, 106, '单一报刊专题分析', '单一报刊专题分析', '单一报刊专题分析', 'singleProjects', null, null, null, 2, null, 'overall', null, null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1060301, 10603, '总体情况分析', '总体情况分析', '总体情况分析', 'overall', null, null, null, 1, null, null, 'templates/result/singleProjects/overall.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (10604, 106, '约投挂号专题分析', '约投挂号专题分析', '约投挂号专题分析', 'registeredProject', null, null, null, 4, null, 'timeLimit', null, null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1060401, 10604, '约投挂号时限分析', '约投挂号时限分析', '约投挂号时限分析', 'timeLimit', null, null, null, 1, null, null, 'templates/result/registeredProject/timeLimit.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (10605, 106, '国内小包专题分析', '国内小包专题分析', '国内小包专题分析', 'smallProject', null, null, null, 8, null, 'completion', null, null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1060502, 10605, '国内小包专题分析2', '国内小包专题分析2', '国内小包专题分析2', 'analysis2', null, null, null, 2, null, null, 'templates/result/internalProject/results.html', 'filename=gnxb2.xml&index=8&type=month');
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (10607, 106, '订阅通知', '订阅通知', '订阅通知', 'subscriptions', null, null, null, 7, null, 'manage', null, null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (107, 99999, '对内对外服务', '对内对外服务', '对内对外服务', 'innerService', null, null, null, 7, null, 'defined', null, null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (10701, 107, '数据定义', '数据定义', '数据定义', 'defined', null, null, null, 1, null, null, 'templates/dataIntegration/defined.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (10702, 107, '数据上传', '数据上传', '数据上传', 'upload', null, null, null, 2, null, null, 'templates/dataIntegration/upload.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (10703, 107, '数据下载', '数据下载', '数据下载', 'download', null, null, null, 3, null, 'download', null, null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1070301, 10703, '数据下载', '数据下载', '数据下载', 'download', null, null, null, 1, null, null, 'templates/dataIntegration/download/download.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1070302, 10703, '下载记录', '下载记录', '下载记录', 'log', null, null, null, 2, null, null, 'templates/dataIntegration/download/log.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (10704, 107, '数据管理', '数据管理', '数据管理', 'ds', null, null, null, 4, null, null, 'templates/dataIntegration/ds.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (10705, 107, '数据评估', '数据评估', '数据评估', 'assessment', null, null, null, 5, null, null, 'templates/dataIntegration/assessment.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (10706, 107, '数据清洗', '数据清洗', '数据清洗', 'dataClean', null, null, null, 6, null, 'cleanManage', null, null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1070601, 10706, '清洗管理', '清洗管理', '清洗管理', 'cleanManage', null, null, null, 1, null, null, 'templates/dataIntegration/dataClean/manage.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1070602, 10706, '排重管理', '排重管理', '排重管理', 'filterManage', null, null, null, 2, null, null, 'templates/dataIntegration/dataClean/filterManage.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (10707, 107, '地址清洗匹配', '地址清洗匹配', '地址清洗匹配', 'addressClean', null, null, null, 7, null, null, 'templates/dataIntegration/addressClean.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (10708, 107, '邮编匹配', '邮编匹配', '邮编匹配', 'postMatch', null, null, null, 8, null, null, 'templates/innerService/postMatch.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (10709, 107, '客户管理', '客户管理', '客户管理', 'client', null, null, null, 9, null, null, 'templates/innerService/client.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (10710, 107, '数据服务流程', '数据服务流程', '数据服务流程', 'dataServiceProcess', null, null, null, 10, null, 'manage', null, null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1071001, 10710, '数据服务流程管理', '数据服务流程管理', '数据服务流程管理', 'manage', null, null, null, 1, null, null, 'templates/innerService/dataServiceProcess/manage.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1071002, 10710, '流程规则定制', '流程规则定制', '流程规则定制', 'roleDefine', null, null, null, 2, null, null, 'templates/innerService/dataServiceProcess/roleDefine.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1071003, 10710, '运行流程', '运行流程', '运行流程', 'run', null, null, null, 3, null, null, 'templates/innerService/dataServiceProcess/run.html', null);
commit;
prompt 137 records loaded
prompt Enabling triggers for T02_SYS_MENU...
alter table T02_SYS_MENU enable all triggers;
set feedback on
set define on
prompt Done.
