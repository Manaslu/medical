package com.teamsun.thunderreport.core;

import java.util.List;

import com.teamsun.thunderreport.core.bean.Page;

public interface DataSourceService {

	public List getData(String sql, Page page);

}
