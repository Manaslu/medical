package com.idap.processmgr.special.dao;

import org.springframework.stereotype.Repository;

import com.idap.processmgr.special.entity.Achievement;
import com.idp.pub.dao.impl.DefaultBaseDao;

@Repository("achievementDao")
public class AchievementDao extends DefaultBaseDao<Achievement, String> {

	@Override
	public String getNamespace() {
		return Achievement.class.getName();
	}

}