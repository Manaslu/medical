package com.idp.workflow.vo.service.prodef;

import com.idp.workflow.vo.pub.IWorkFlowTypes;
import com.idp.workflow.vo.pub.SuperVO;

/**
 * 工作流程定义VO
 * 
 * @author panfei
 * 
 */
public class ProcessDefineVO extends SuperVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5408731351294260266L;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getDeploymentId() {
		return deploymentId;
	}

	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDiagramResourceName() {
		return diagramResourceName;
	}

	public void setDiagramResourceName(String diagramResourceName) {
		this.diagramResourceName = diagramResourceName;
	}

	public boolean isHasStartFormKey() {
		return hasStartFormKey;
	}

	public void setHasStartFormKey(boolean hasStartFormKey) {
		this.hasStartFormKey = hasStartFormKey;
	}

	public int getSuspensionState() {
		return suspensionState;
	}

	public void setSuspensionState(int suspensionState) {
		this.suspensionState = suspensionState;
	}

	public boolean isCanUsed() {
		if (suspensionState == IWorkFlowTypes.SuspensionState_Alive) {
			return true;
		}
		return false;
	}

	public String getDeployName() {
		return deployName;
	}

	public void setDeployName(String deployName) {
		this.deployName = deployName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	private String id;

	private int version;

	private String deploymentId;

	private String deployName;

	private String name;

	private String code;

	private String category;

	private String resourceName;

	private String diagramResourceName;

	private boolean hasStartFormKey;

	private int suspensionState = IWorkFlowTypes.SuspensionState_Alive;

	private String description;

	public final static String ID = "id";
	public final static String VERSION = "version";
	public final static String CODE = "code";
	public final static String NAME = "name";
}
