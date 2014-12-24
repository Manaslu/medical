package com.idap.web.result.subscriptions.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.idap.intextservice.result.subscriptions.entity.Report;
import com.idp.pub.constants.Constants;
import com.idp.pub.service.IBaseService;
import com.idp.pub.utils.JsonUtils;
import com.idp.pub.web.controller.BaseController;

@Controller
@RequestMapping(value = "/report")
public class ReportController extends BaseController<Report, String> {
	@Resource(name = "reportService")
	public void setBaseService(IBaseService<Report, String> baseService) {
		super.setBaseService(baseService);
	}

	@Resource(name = "reportService")
	public IBaseService<Report, String> reportService;
	
	@RequestMapping(params = "method=report")
	public String report(ModelMap map,Report report, HttpServletRequest request) {
		Map<String, Object> results = Constants.MAP();
		    String year="2014";
		    String month="04";
		    String time=year+month;
			results.put("dayId", time);
			
			//国内小包-重点城市次日递
			results.put("mywhere", "mywhere");
			results.put("showId", "gnxb12");
			List<Report> reportlist2=this.reportService.findList(results);
			
			//国内小包-时限指标
			results.put("showId", "gnxb13");
			List<Report> reportlist3=this.reportService.findList(results);
			
			//国际小包-流量流向
			results.put("showId", "gjxb7");
			List<Report> reportlist5=this.reportService.findList(results);
			
			//国际小包-运递指标-本月20个重点国家的业务量、信息上网率及时长指标：
			results.put("showId", "gjxb9");
			List<Report> reportlist6=this.reportService.findList(results);
			
			//国际小包-流量流向
			results.put("showId", "gjxb13");
			List<Report> reportlist7=this.reportService.findList(results);
			
			
			//国内小包-客户情况
			results.put("mywhere", null);
			results.put("showId", null);
			results.put("gnkhqk", "gnkhqk");
			List<Report> reportlist1=this.reportService.findList(results);
			
			//国际小包-客户情况
			results.put("gnkhqk", null);
			results.put("showId", null);
			results.put("gjkhqk", "gjkhqk");
			List<Report> reportlist4=this.reportService.findList(results);
			
			//报表1数据
			results.put("gjkhqk", null);
			results.put("bbdata", "bbdata");
			results.put("showId", "gnxb1");
			List<Report> reportlist8=this.reportService.findList(results);
			
			//报表2数据
			results.put("showId", "gjxb1");
			List<Report> reportlist9=this.reportService.findList(results);
			
			int list1i=reportlist1.size();
			int list8i=reportlist8.size();
			
			map.put("reportlist1", reportlist1);
			map.put("reportlist2", reportlist2);
			map.put("reportlist3", reportlist3);
			map.put("reportlist4", reportlist4);
			map.put("reportlist5", reportlist5);
			map.put("reportlist6", reportlist6);
			map.put("reportlist7", reportlist7);
			map.put("reportlist8", JsonUtils.toJson(reportlist8));
			map.put("reportlist9", JsonUtils.toJson(reportlist9));
			map.put("time", year+"-"+month);
			map.put("month", month);
			
			//报表1数据概况
			map.put("bb1ywl", reportlist8.get(list8i-1).getA2());
			map.put("bb1ywhb", reportlist8.get(list8i-1).getB1());
			map.put("bb1srhb", reportlist8.get(list8i-1).getB2());
			
			map.put("gnkhz", reportlist1.get(list1i-1).getA2());
			map.put("gnhbzz", reportlist1.get(list1i-1).getA3());
			map.put("gnkhls", reportlist1.get(list1i-1).getA4());
			
		return "/templates/result/subscriptions/report";
	}
}
