package com.idp.workflow.vo.service.prodef;

import java.io.InputStream;
import java.util.Date;

import com.idp.workflow.vo.pub.SuperVO;

/**
 * 部署信息VO
 * 
 * @author panfei
 * 
 */
public class DeploymentVO extends SuperVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3837629245759619415L;

	private String id;
	private String name;
	private Date deploymentTime;

	private InputStream imageResource;

	private InputStream ConfigXmlResource;

	public DeploymentVO() {
		super();
	}

	/**
	 * 传入参数初始化
	 * 
	 * @param deployName
	 *            工作流定义部署名称
	 * @param imageResource
	 *            图片资源
	 * @param ConfigXmlResource
	 *            定义配置文件资源
	 */
	public DeploymentVO(String deployName, InputStream imageResource,
			InputStream ConfigXmlResource) {
		this.name = deployName;
		this.imageResource = imageResource;
		this.ConfigXmlResource = ConfigXmlResource;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getDeploymentTime() {
		return deploymentTime;
	}

	public void setDeploymentTime(Date deploymentTime) {
		this.deploymentTime = deploymentTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public InputStream getImageResource() {
		return imageResource;
	}

	public void setImageResource(InputStream imageResource) {
		this.imageResource = imageResource;
	}

	public InputStream getConfigXmlResource() {
		return ConfigXmlResource;
	}

	public void setConfigXmlResource(InputStream configXmlResource) {
		ConfigXmlResource = configXmlResource;
	}
}
