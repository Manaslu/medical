package com.idap.clinic.entity;
import java.util.Date;

import com.idp.pub.entity.annotation.MetaTable;

@MetaTable
public class UserAppointment implements java.io.Serializable {
	private static final long serialVersionUID = 5736511926578194639L;
	
	 
	private String     orderId;//  ORDER_ID order_id
	private String     orderUserId;//ORDER_USER_ID order_user_id 
	private String     orderDoctorId;// ORDER_DOCTOR_ID order_doctor_id
	private Date       orderDate;//ORDER_DATE order_date
	private String     orderClinic;//ORDER_CLINIC order_clinic
	private String     mUserName;//M_USER_NAME m_user_name
	private String     orderStatus;//ORDER_STATUS ORDER_STATUS
	
	
	
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOrderUserId() {
		return orderUserId;
	}
	public void setOrderUserId(String orderUserId) {
		this.orderUserId = orderUserId;
	}
	public String getOrderDoctorId() {
		return orderDoctorId;
	}
	public void setOrderDoctorId(String orderDoctorId) {
		this.orderDoctorId = orderDoctorId;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public String getOrderClinic() {
		return orderClinic;
	}
	public void setOrderClinic(String orderClinic) {
		this.orderClinic = orderClinic;
	}
	public String getmUserName() {
		return mUserName;
	}
	public void setmUserName(String mUserName) {
		this.mUserName = mUserName;
	}
	
 
 
	 
	 
	
	
	 
	

}
