prompt PL/SQL Developer import file
prompt Created on 2014��6��13�� by Administrator
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
values (108, 99999, '֪ʶ��', '֪ʶ��', '֪ʶ��', 'repository', null, null, null, 8, null, 'share', null, null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (10801, 108, '����֪ʶ��', '����֪ʶ��', '����֪ʶ��', 'share', null, null, null, 1, null, null, 'templates/repository/share.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (10802, 108, '֪ʶ�����', '֪ʶ�����', '֪ʶ�����', 'manage', null, null, null, 2, null, null, 'templates/repository/manage.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (10803, 108, '֪ʶ�����', '֪ʶ�����', '֪ʶ�����', 'examine', null, null, null, 3, null, null, 'templates/repository/examine.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (10804, 108, '��ǩ�����', '��ǩ�����', '��ǩ�����', 'tagLibrary', null, null, null, 4, null, null, 'templates/repository/labelLib.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (109, 99999, 'ϵͳ���й���', 'ϵͳ���й���', 'ϵͳ���й���', 'oms', null, null, null, 9, null, 'institution', null, null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (10901, 109, '��������', '��������', '��������', 'institution', null, null, null, 1, null, null, 'templates/oms/institution.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (10902, 109, '�û�����', '�û�����', '�û�����', 'user', null, null, null, 2, null, null, 'templates/oms/user.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (10903, 109, '��ɫ����', '��ɫ����', '��ɫ����', 'role', null, null, null, 3, null, null, 'templates/oms/role.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (10904, 109, '�������', '�������', '�������', 'announcement', null, null, null, 4, null, 'list', null, null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1090401, 10904, '�����б�', '�����б�', '�����б�', 'list', null, null, null, 1, null, null, 'templates/oms/announcement/list.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1090402, 10904, '�������', '�������', '�������', 'eap', null, null, null, 2, null, null, 'templates/oms/announcement/eap.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (10905, 109, '���м��', '���м��', '���м��', 'move', null, null, null, 5, null, null, 'templates/oms/move.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (10906, 109, '�û�������־', '�û�������־', '�û�������־', 'journal', null, null, null, 6, null, null, 'templates/oms/journal.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1020101, 10201, '�����ֵ����', '�����ֵ����', '�����ֵ����', 'dataDictionary', null, null, null, 1, null, null, 'templates/receiveDownload/manage/dataDictionary.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1020102, 10201, 'E-R ͼ', 'E-R ͼ', 'E-R ͼ', 'er', null, null, null, 2, null, null, 'templates/receiveDownload/manage/er.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1020208, 10202, '���ݼ����������', '���ݼ����������', '���ݼ����������', 'loadState', null, null, null, 8, null, null, 'templates/receiveDownload/esb/loadState.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (10302, 103, 'ʵ������', 'ʵ������', 'ʵ������', 'testData', null, null, null, 2, null, null, null, null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1030303, 10302, '�������ؼ�¼', '�������ؼ�¼', '�������ؼ�¼', 'log', null, null, null, 3, null, null, 'templates/dataIntegration/download/log.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1030601, 10306, '��ϴ����', '��ϴ����', '��ϴ����', 'cleanManage', null, null, null, 1, null, null, 'templates/dataIntegration/dataClean/manage.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1030602, 10306, '���ع���', '���ع���', '���ع���', 'filterManage', null, null, null, 2, null, null, 'templates/dataIntegration/dataClean/filterManage.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (101, 99999, '��ҳ', '��ҳ', '��ҳ', 'index', '1', '1', '1', 1, '1', null, 'templates/index/index.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (10101, 101, '������', '������', '������', 'notice', '1', '1', '1', 1, '1', null, 'templates/index/notice.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (10102, 101, '�쵼��ʻ��', '�쵼��ʻ��', '�쵼��ʻ��', 'cockpit', '1', '1', '1', 1, '1', null, 'templates/index/cockpit.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (10103, 101, '�����б�', '�����б�', '�����б�', 'task', '1', '1', '1', 1, '1', 'list', null, null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1010301, 10103, '�����б�', '�����б�', '�����б�', 'list', '1', '1', '1', 1, '1', null, 'templates/index/task/list.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1010302, 10103, '��������', '��������', '��������', 'todo', '1', '1', '1', 1, '1', null, 'templates/index/task/todo.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1010303, 10103, '��ʷ����', '��ʷ����', '��ʷ����', 'history', '1', '1', '1', 1, '1', null, 'templates/index/task/history.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1010304, 10103, '�������', '�������', '�������', 'complete', '1', '1', '1', 1, '1', null, 'templates/index/task/complete.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (102, 99999, '���ݽ���������', '���ݽ���������', '���ݽ���������', 'receiveDownload', '2', '2', '2', 2, '2', null, null, null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (10201, 102, '�����ֵ����', '�����ֵ����', '�����ֵ����', 'dataDictionary', '2', '2', '2', 1, '2', null, null, null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (10202, 102, 'ESB��ر���', 'ESB��ر���', 'ESB��ر���', 'esb', '2', '2', '2', 2, '2', 'nationwideReceive', null, null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1020201, 10202, 'ȫ�����Ľ��ռ��', 'ȫ�����Ľ��ռ��', 'ȫ�����Ľ��ռ��', 'nationwideReceive', '2', '2', '2', 1, '2', null, 'templates/receiveDownload/esb/nationwideReceive.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1020202, 10202, 'ʡ�����ϴ����', 'ʡ�����ϴ����', 'ʡ�����ϴ����', 'provinceUpload', '2', '2', '2', 3, '2', null, 'templates/receiveDownload/esb/provinceUpload.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1020203, 10202, 'ȫ���������ؼ��', 'ȫ���������ؼ��', 'ȫ���������ؼ��', 'nationwideDownload', '2', '2', '2', 2, '2', null, 'templates/receiveDownload/esb/nationwideDownload.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1020204, 10202, 'ʡ���Ľ��ռ��', 'ʡ���Ľ��ռ��', 'ʡ���Ľ��ռ��', 'provinceReceive', '2', '2', '2', 4, '2', null, 'templates/receiveDownload/esb/provinceReceive.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1020205, 10202, '�쳣��Ϣ���', '�쳣��Ϣ���', '�쳣��Ϣ���', 'abnormal', '2', '2', '2', 5, '2', null, 'templates/receiveDownload/esb/abnormal.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1020206, 10202, '���ݺ˶�', '���ݺ˶�', '���ݺ˶�', 'nationwideProvince', '2', '2', '2', 6, '2', null, 'templates/receiveDownload/esb/nationwideProvince.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1060501, 10605, '����С��ר�����1', '����С��ר�����1', '����С��ר�����1', 'analysis1', null, null, null, 1, null, null, 'templates/result/internalProject/results.html', 'filename=gnxb1.xml&index=8&type=month');
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1020207, 10202, '�����·��������', '�����·��������', '�����·��������', 'dataDownloadSituation', '2', '2', '2', 7, '2', null, 'templates/receiveDownload/esb/dataDownloadSituation.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1060503, 10605, '����С��ר�����3', '����С��ר�����3', '����С��ר�����3', 'analysis3', null, null, null, 3, null, null, 'templates/result/internalProject/results.html', 'filename=gnxb3.xml&index=8&type=month');
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1060504, 10605, '����С��ר�����4', '����С��ר�����4', '����С��ר�����4', 'analysis4', null, null, null, 4, null, null, 'templates/result/internalProject/results.html', 'filename=gnxb4.xml&index=8&type=month');
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1060505, 10605, '����С��ר�����5', '����С��ר�����5', '����С��ר�����5', 'analysis5', null, null, null, 5, null, null, 'templates/result/internalProject/results.html', 'filename=gnxb5.xml&index=8&type=month');
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1060506, 10605, '����С��ר�����6', '����С��ר�����6', '����С��ר�����6', 'analysis6', null, null, null, 6, null, null, 'templates/result/internalProject/results.html', 'filename=gnxb6.xml&index=8&type=month');
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1060507, 10605, '����С��ר�����7', '����С��ר�����7', '����С��ר�����7', 'analysis7', null, null, null, 7, null, null, 'templates/result/internalProject/results.html', 'filename=gnxb7.xml&index=8&type=month');
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1060508, 10605, '����С��ר�����8', '����С��ר�����8', '����С��ר�����8', 'analysis8', null, null, null, 8, null, null, 'templates/result/internalProject/results.html', 'filename=gnxb8.xml&index=8&type=month');
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1060509, 10605, '����С��ר�����9', '����С��ר�����9', '����С��ר�����9', 'analysis9', null, null, null, 9, null, null, 'templates/result/internalProject/results.html', 'filename=gnxb9.xml&index=8&type=month');
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1060510, 10605, '����С��ר�����10', '����С��ר�����10', '����С��ר�����10', 'analysis10', null, null, null, 10, null, null, 'templates/result/internalProject/results.html', 'filename=gnxb10.xml&index=8&type=month');
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1060511, 10605, '����С��ר�����11', '����С��ר�����11', '����С��ר�����11', 'analysis11', null, null, null, 11, null, null, 'templates/result/internalProject/results.html', 'filename=gnxb11.xml&index=8&type=month');
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1060512, 10605, '����С��ר�����12', '����С��ר�����12', '����С��ר�����12', 'analysis12', null, null, null, 12, null, null, 'templates/result/internalProject/results.html', 'filename=gnxb13.xml&index=8&type=month');
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1060513, 10605, '����С��ר�����13', '����С��ר�����13', '����С��ר�����13', 'analysis13', null, null, null, 13, null, null, 'templates/result/internalProject/results.html', 'filename=gnxb13.xml&index=8&type=month');
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1060514, 10605, '����С��ר�����14', '����С��ר�����14', '����С��ר�����14', 'analysis14', null, null, null, 14, null, null, 'templates/result/internalProject/results.html', 'filename=gnxb14.xml&index=8&type=month');
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (10606, 106, '����С��ר�����', '����С��ר�����', '����С��ר�����', 'gjxb', null, null, null, 5, null, null, null, null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1060601, 10606, '����С��ר�����1', '����С��ר�����1', '����С��ר�����1', 'analysis1', null, null, null, 1, null, null, 'templates/result/internalProject/results.html', 'filename=gjxb1.xml&index=8&type=month');
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1060603, 10606, '����С��ר�����3', '����С��ר�����3', '����С��ר�����3', 'analysis3', null, null, null, 3, null, null, 'templates/result/internalProject/results.html', 'filename=gjxb3.xml&index=8&type=month');
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1060604, 10606, '����С��ר�����4', '����С��ר�����4', '����С��ר�����4', 'analysis4', null, null, null, 4, null, null, 'templates/result/internalProject/results.html', 'filename=gjxb4.xml&index=8&type=month');
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1060605, 10606, '����С��ר�����5', '����С��ר�����5', '����С��ר�����5', 'analysis5', null, null, null, 5, null, null, 'templates/result/internalProject/results.html', 'filename=gjxb5.xml&index=8&type=month');
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1060606, 10606, '����С��ר�����6', '����С��ר�����6', '����С��ר�����6', 'analysis6', null, null, null, 6, null, null, 'templates/result/internalProject/results.html', 'filename=gjxb6.xml&index=8&type=month');
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1060607, 10606, '����С��ר�����7', '����С��ר�����7', '����С��ר�����7', 'analysis7', null, null, null, 7, null, null, 'templates/result/internalProject/results.html', 'filename=gjxb7.xml&index=8&type=month');
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1060608, 10606, '����С��ר�����8', '����С��ר�����8', '����С��ר�����8', 'analysis8', null, null, null, 8, null, null, 'templates/result/internalProject/results.html', 'filename=gjxb8.xml&index=8&type=month');
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1060609, 10606, '����С��ר�����9', '����С��ר�����9', '����С��ר�����9', 'analysis9', null, null, null, 9, null, null, 'templates/result/internalProject/results.html', 'filename=gjxb9.xml&index=8&type=month');
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1060610, 10606, '����С��ר�����10', '����С��ר�����10', '����С��ר�����10', 'analysis10', null, null, null, 10, null, null, 'templates/result/internalProject/results.html', 'filename=gjxb10.xml&index=8&type=month');
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1060611, 10606, '����С��ר�����11', '����С��ר�����11', '����С��ר�����11', 'analysis11', null, null, null, 11, null, null, 'templates/result/internalProject/results.html', 'filename=gjxb11.xml&index=8&type=pdate');
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1060612, 10606, '����С��ר�����12', '����С��ר�����12', '����С��ר�����12', 'analysis12', null, null, null, 12, null, null, 'templates/result/internalProject/results.html', 'filename=gjxb12.xml&index=8&type=pdate');
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1060613, 10606, '����С��ר�����13', '����С��ר�����13', '����С��ר�����13', 'analysis13', null, null, null, 13, null, null, 'templates/result/internalProject/results.html', 'filename=gjxb13.xml&index=8&type=pdate');
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1060614, 10606, '����С��ר�����14', '����С��ר�����14', '����С��ר�����14', 'analysis14', null, null, null, 14, null, null, 'templates/result/internalProject/results.html', 'filename=gjxb14.xml&index=8&type=pdate');
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1060602, 10606, '����С��ר�����2', '����С��ר�����2', '����С��ר�����2', 'analysis2', null, null, null, 2, null, null, 'templates/result/internalProject/results.html', 'filename=gjxb2.xml&index=8&type=month');
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1060701, 10607, '���Ĺ���', '���Ĺ���', '���Ĺ���', 'manage', null, null, null, 1, null, null, 'templates/result/subscriptions/manage.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1060702, 10607, '�û�����', '�û�����', '�û�����', 'user', null, null, null, 2, null, null, 'templates/result/subscriptions/user.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1060703, 10607, '������־', '������־', '������־', 'log', null, null, null, 3, null, null, 'templates/result/subscriptions/log.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1030302, 10302, 'ʵ����������', 'ʵ����������', 'ʵ����������', 'download', null, null, null, 2, null, 'download', 'templates/dataIntegration/download/download.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1030201, 10302, 'ʵ�������ϴ�', 'ʵ�������ϴ�', 'ʵ�������ϴ�', 'upload', null, null, null, 1, null, null, 'templates/dataIntegration/upload.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (10301, 103, '���ݶ���', '���ݶ���', '���ݶ���', 'defined', null, null, null, 1, null, null, 'templates/dataIntegration/defined.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (103, 99999, '��������', '��������', '��������', 'dataIntegration', null, null, null, 3, null, 'defined', null, null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1030304, 10302, 'ʵ�����ݹ���', 'ʵ�����ݹ���', 'ʵ�����ݹ���', 'ds', null, null, null, 4, null, null, 'templates/dataIntegration/ds.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (10305, 103, '��������', '��������', '��������', 'assessment', null, null, null, 3, null, null, 'templates/dataIntegration/assessment.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (10306, 103, '������ϴ', '������ϴ', '������ϴ', 'dataClean', null, null, null, 4, null, 'cleanManage', null, null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (10307, 103, '��ַ��ϴƥ��', '��ַ��ϴƥ��', '��ַ��ϴƥ��', 'addressClean', null, null, null, 5, null, null, 'templates/dataIntegration/addressClean.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (10308, 103, '�����ع�', '�����ع�', '�����ع�', 'restructure', null, null, null, 6, null, null, 'templates/dataIntegration/rebuild.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (10309, 103, '��������', '��������', '��������', 'integration', null, null, null, 7, null, null, 'templates/dataIntegration/integration.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1030302, 10303, '�������ؼ�¼', '�������ؼ�¼', '�������ؼ�¼', 'log', null, null, null, 2, null, null, 'templates/dataIntegration/download/log.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1030301, 10303, 'ʵ����������', 'ʵ����������', 'ʵ����������', 'download', null, null, null, 1, null, null, 'templates/dataIntegration/download/download.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (104, 99999, 'ר�����̹���', 'ר�����̹���', 'ר�����̹���', 'technologicalProcess', null, null, null, 4, null, 'demandManage', null, null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (10401, 104, '�������', '�������', '�������', 'demandManage', null, null, null, 1, null, 'list', null, null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1040101, 10401, '��������', '��������', '��������', 'list', null, null, null, 1, null, null, 'templates/technologicalProcess/demandManage/req.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1040102, 10401, '��������', '��������', '��������', 'eap', null, null, null, 2, null, null, 'templates/technologicalProcess/demandManage/eap.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1040103, 10401, '�������', '�������', '�������', 'distribution', null, null, null, 3, null, null, 'templates/technologicalProcess/demandManage/list.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1040104, 10401, '����ȷ��', '����ȷ��', '����ȷ��', 'affirm', null, null, null, 4, null, null, 'templates/technologicalProcess/demandManage/affirm.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (10402, 104, '���̹���', '���̹���', '���̹���', 'process', null, null, null, 2, null, 'list', null, null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1040201, 10402, '��Ŀ�б�', '��Ŀ�б�', '��Ŀ�б�', 'list', null, null, null, 1, null, null, 'templates/technologicalProcess/process/list.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1040202, 10402, '��������', '��������', '��������', 'quantization', null, null, null, 6, null, null, 'templates/technologicalProcess/process/quantization.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1040203, 10402, '������������', '������������', '������������', 'quality', null, null, null, 2, null, null, 'templates/technologicalProcess/process/quality.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1040204, 10402, '����ģ�͹���', '����ģ�͹���', '����ģ�͹���', 'model', null, null, null, 3, null, null, 'templates/technologicalProcess/process/model.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1040205, 10402, '��Ŀ����', '��Ŀ����', '��Ŀ����', 'end', null, null, null, 4, null, null, 'templates/technologicalProcess/process/end.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (10403, 104, '�ɹ�����', '�ɹ�����', '�ɹ�����', 'achievement', null, null, null, 3, null, 'list', null, null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1040301, 10403, '�鿴�ɹ�', '�鿴�ɹ�', '�鿴�ɹ�', 'list', null, null, null, 2, null, null, 'templates/technologicalProcess/achievement/list.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1040302, 10403, '�����ɹ�', '�����ɹ�', '�����ɹ�', 'publish', null, null, null, 1, null, null, 'templates/technologicalProcess/achievement/publish.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (105, 99999, '���ݷ���', '���ݷ���', '���ݷ���', 'dataAnalyse', null, null, null, 5, null, 'quota', null, null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (10501, 105, 'ָ��ά��', 'ָ��ά��', 'ָ��ά��', 'quota', null, null, null, 1, null, null, 'templates/dataAnalyse/quota.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (10502, 105, '����ͬ��', '����ͬ��', '����ͬ��', 'synchronization', null, null, null, 2, null, 'list', null, null);
commit;
prompt 100 records committed...
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1050201, 10502, '����ͬ���б�', '����ͬ���б�', '����ͬ���б�', 'list', null, null, null, 1, null, null, 'templates/dataAnalyse/synchronization/list.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1050202, 10502, '����ͬ�����', '����ͬ�����', '����ͬ�����', 'examine', null, null, null, 2, null, null, 'templates/dataAnalyse/synchronization/examine.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (10503, 105, '�������̲�����', '�������̲�����', '�������̲�����', 'processParametric', null, null, null, 3, null, null, 'templates/dataAnalyse/processParametric.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (10504, 105, '�������̹̻��ͽ������', '�������̹̻��ͽ������', '�������̹̻��ͽ������', 'resultsPublishing', null, null, null, 4, null, 'list', null, null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1050401, 10504, '�������̹̻��б�', '�������̹̻��б�', '�������̹̻��б�', 'list', null, null, null, 1, null, null, 'templates/dataAnalyse/resultsPublishing/list.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1050402, 10504, '�������̹̻����', '�������̹̻����', '�������̹̻����', 'eap', null, null, null, 2, null, null, 'templates/dataAnalyse/resultsPublishing/eap.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1050403, 10504, '�������', '�������', '�������', 'results', null, null, null, 3, null, null, 'templates/dataAnalyse/resultsPublishing/results.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (106, 99999, '�ɹ�չʾ', '�ɹ�չʾ', '�ɹ�չʾ', 'result', null, null, null, 6, null, 'groupProject', null, null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (10601, 106, '��������ר�����', '��������ר�����', '��������ר�����', 'groupProject', null, null, null, 3, null, 'overall', null, null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1060101, 10601, '�������������������', '�������������������', '�������������������', 'overall', null, null, null, 1, null, null, 'templates/result/groupProject/overall.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (10602, 106, '�����˴�ר�����', '�����˴�ר�����', '�����˴�ר�����', 'eightProjects', null, null, null, 1, null, 'trend', null, null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1060201, 10602, '����ҵ��չ���Ʒ���', '����ҵ��չ���Ʒ���', '����ҵ��չ���Ʒ���', 'trend', null, null, null, 1, null, null, 'templates/result/eightProjects/trend.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (10603, 106, '��һ����ר�����', '��һ����ר�����', '��һ����ר�����', 'singleProjects', null, null, null, 2, null, 'overall', null, null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1060301, 10603, '�����������', '�����������', '�����������', 'overall', null, null, null, 1, null, null, 'templates/result/singleProjects/overall.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (10604, 106, 'ԼͶ�Һ�ר�����', 'ԼͶ�Һ�ר�����', 'ԼͶ�Һ�ר�����', 'registeredProject', null, null, null, 4, null, 'timeLimit', null, null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1060401, 10604, 'ԼͶ�Һ�ʱ�޷���', 'ԼͶ�Һ�ʱ�޷���', 'ԼͶ�Һ�ʱ�޷���', 'timeLimit', null, null, null, 1, null, null, 'templates/result/registeredProject/timeLimit.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (10605, 106, '����С��ר�����', '����С��ר�����', '����С��ר�����', 'smallProject', null, null, null, 8, null, 'completion', null, null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1060502, 10605, '����С��ר�����2', '����С��ר�����2', '����С��ר�����2', 'analysis2', null, null, null, 2, null, null, 'templates/result/internalProject/results.html', 'filename=gnxb2.xml&index=8&type=month');
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (10607, 106, '����֪ͨ', '����֪ͨ', '����֪ͨ', 'subscriptions', null, null, null, 7, null, 'manage', null, null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (107, 99999, '���ڶ������', '���ڶ������', '���ڶ������', 'innerService', null, null, null, 7, null, 'defined', null, null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (10701, 107, '���ݶ���', '���ݶ���', '���ݶ���', 'defined', null, null, null, 1, null, null, 'templates/dataIntegration/defined.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (10702, 107, '�����ϴ�', '�����ϴ�', '�����ϴ�', 'upload', null, null, null, 2, null, null, 'templates/dataIntegration/upload.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (10703, 107, '��������', '��������', '��������', 'download', null, null, null, 3, null, 'download', null, null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1070301, 10703, '��������', '��������', '��������', 'download', null, null, null, 1, null, null, 'templates/dataIntegration/download/download.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1070302, 10703, '���ؼ�¼', '���ؼ�¼', '���ؼ�¼', 'log', null, null, null, 2, null, null, 'templates/dataIntegration/download/log.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (10704, 107, '���ݹ���', '���ݹ���', '���ݹ���', 'ds', null, null, null, 4, null, null, 'templates/dataIntegration/ds.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (10705, 107, '��������', '��������', '��������', 'assessment', null, null, null, 5, null, null, 'templates/dataIntegration/assessment.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (10706, 107, '������ϴ', '������ϴ', '������ϴ', 'dataClean', null, null, null, 6, null, 'cleanManage', null, null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1070601, 10706, '��ϴ����', '��ϴ����', '��ϴ����', 'cleanManage', null, null, null, 1, null, null, 'templates/dataIntegration/dataClean/manage.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1070602, 10706, '���ع���', '���ع���', '���ع���', 'filterManage', null, null, null, 2, null, null, 'templates/dataIntegration/dataClean/filterManage.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (10707, 107, '��ַ��ϴƥ��', '��ַ��ϴƥ��', '��ַ��ϴƥ��', 'addressClean', null, null, null, 7, null, null, 'templates/dataIntegration/addressClean.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (10708, 107, '�ʱ�ƥ��', '�ʱ�ƥ��', '�ʱ�ƥ��', 'postMatch', null, null, null, 8, null, null, 'templates/innerService/postMatch.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (10709, 107, '�ͻ�����', '�ͻ�����', '�ͻ�����', 'client', null, null, null, 9, null, null, 'templates/innerService/client.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (10710, 107, '���ݷ�������', '���ݷ�������', '���ݷ�������', 'dataServiceProcess', null, null, null, 10, null, 'manage', null, null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1071001, 10710, '���ݷ������̹���', '���ݷ������̹���', '���ݷ������̹���', 'manage', null, null, null, 1, null, null, 'templates/innerService/dataServiceProcess/manage.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1071002, 10710, '���̹�����', '���̹�����', '���̹�����', 'roleDefine', null, null, null, 2, null, null, 'templates/innerService/dataServiceProcess/roleDefine.html', null);
insert into T02_SYS_MENU (menu_id, par_menu_id, menu_title, menu_name, menu_desc, action, state, role_check_id, menu_type, indexno, deal_id, redirectto, template, params)
values (1071003, 10710, '��������', '��������', '��������', 'run', null, null, null, 3, null, null, 'templates/innerService/dataServiceProcess/run.html', null);
commit;
prompt 137 records loaded
prompt Enabling triggers for T02_SYS_MENU...
alter table T02_SYS_MENU enable all triggers;
set feedback on
set define on
prompt Done.
