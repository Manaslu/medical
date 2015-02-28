window.kpiorder = {}
year='2014'
window.kpiorder.v_begin_time = '200812';
window.kpiorder.v_time = '200911';
window.kpiorder.v_org = '0010';
window.kpiorder.v_org12 = '0000'; // 用于预测指标的orgid
window.kpiorder.v_metric = '50111100000'; // 单个kpi选择指标
window.kpiorder.v_type = '1'; // 云南 分析概览

var curDate = new Date();
var c_time = curDate.format('Ymd');
var c_year = curDate.format('Y');
var c_month = curDate.format('m');
var l_year =c_year - 1;

// {v_cycle:'4',v_org:'0010',v_time:'200912'}
window.purchase = {}
window.purchase.v_oil = '0';

window.purchase.v_begin_time = l_year + c_month;
window.purchase.v_time = c_year + c_month;

window.sale = {}
window.sale.v_oil = '0';
window.sale.v_begin_time = l_year + c_month;
window.sale.v_time = c_year + c_month;
window.sale.v_city = '0';

window.dasp = {}
window.dasp.year='2014';
window.dasp.year1='2014';
