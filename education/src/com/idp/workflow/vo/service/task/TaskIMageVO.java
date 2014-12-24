package com.idp.workflow.vo.service.task;

/**
 * 任务信息vo
 * 
 * @author panfei
 * 
 */
public class TaskIMageVO extends TaskVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1124609119630290001L;

	private int x = -1;
	private int y = -1;
	private int width = -1;
	private int height = -1;

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
}
