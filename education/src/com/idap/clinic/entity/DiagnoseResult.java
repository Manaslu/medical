package com.idap.clinic.entity;
import java.util.Date;

import com.idp.pub.entity.annotation.MetaTable;

@MetaTable
public class DiagnoseResult implements java.io.Serializable {
	private static final long serialVersionUID = 5736511926578194639L;
	
	private String seeDoctorId;//SEE_DOCTOR_ID see_doctor_id 
 	private Date seeDoctorDate;//SEE_DOCTOR_DATE see_doctor_date
	private String seeDoctorResult;//SEE_DOCTOR_RESULT see_doctor_result
	private String doctorId;//DOCTOR_ID doctor_id
	private String clinicId;//CLINIC_ID clinic_id
	private String mUserId;//M_USER_ID m_user_id
	private String userOrderId;//USER_ORDER_ID user_order_id
	private String userIllnessDesc;//USER_ILLNESS_DESC user_illness_desc
//	private ClinicInformation clinic;
//	private DoctorsManagement doctor;
	private String clinicName;
	private String doctorName;
	private String doctorDegree;
	private String doctorPic;
	
	
	public String getSeeDoctorId() {
		return seeDoctorId;
	}
	public void setSeeDoctorId(String seeDoctorId) {
		this.seeDoctorId = seeDoctorId;
	}
	 
	public Date getSeeDoctorDate() {
		return seeDoctorDate;
	}
	public void setSeeDoctorDate(Date seeDoctorDate) {
		this.seeDoctorDate = seeDoctorDate;
	}
	public String getSeeDoctorResult() {
		return seeDoctorResult;
	}
	public void setSeeDoctorResult(String seeDoctorResult) {
		this.seeDoctorResult = seeDoctorResult;
	}
	public String getDoctorId() {
		return doctorId;
	}
	public void setDoctorId(String doctorId) {
		this.doctorId = doctorId;
	}
	public String getClinicId() {
		return clinicId;
	}
	public void setClinicId(String clinicId) {
		this.clinicId = clinicId;
	}
	public String getmUserId() {
		return mUserId;
	}
	public void setmUserId(String mUserId) {
		this.mUserId = mUserId;
	}
	public String getUserOrderId() {
		return userOrderId;
	}
	public void setUserOrderId(String userOrderId) {
		this.userOrderId = userOrderId;
	}
	public String getUserIllnessDesc() {
		return userIllnessDesc;
	}
	public void setUserIllnessDesc(String userIllnessDesc) {
		this.userIllnessDesc = userIllnessDesc;
	}
	public String getClinicName() {
		return clinicName;
	}
	public void setClinicName(String clinicName) {
		this.clinicName = clinicName;
	}
	public String getDoctorName() {
		return doctorName;
	}
	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}
	public String getDoctorDegree() {
		return doctorDegree;
	}
	public void setDoctorDegree(String doctorDegree) {
		this.doctorDegree = doctorDegree;
	}
	public String getDoctorPic() {
		return doctorPic;
	}
	public void setDoctorPic(String doctorPic) {
		this.doctorPic = doctorPic;
	}


	
	
	 
	 
	

}
