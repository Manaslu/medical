package com.idap.clinic.entity;
import com.idp.pub.entity.annotation.MetaTable;

@MetaTable
public class UserEvaluate implements java.io.Serializable {
	private static final long serialVersionUID = 5736511926578194639L;
	 
	private String     evaluateId;//evaluate_id EVALUATE_ID
	private String     mUserId;//M_USER_ID m_user_id 
	private String     evaluateContent;//EVALUATE_CONTENT evaluate_content  
	private String     evaluateTime;//EVALUATE_TIME evaluate_time 
	private String     doctorId;//DOCTOR_ID doctor_id
	private String     clinicId;//CLINIC_ID clinic_id
	private int        evaluateDoctScore;//EVALUATE_DOCT_SCORE evaluate_doct_score
	private int        evaluateClinicScore;//EVALUATE_CLINIC_SCORE evaluate_clinic_score
	private String     mUserName;
	private String     doctorName;
	private String     clinicName;
	private String     evaluateFeedback;
	
	
	public String getEvaluateFeedback() {
		return evaluateFeedback;
	}
	public void setEvaluateFeedback(String evaluateFeedback) {
		this.evaluateFeedback = evaluateFeedback;
	}
	public String getmUserName() {
		return mUserName;
	}
	public void setmUserName(String mUserName) {
		this.mUserName = mUserName;
	}
	public String getDoctorName() {
		return doctorName;
	}
	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}
	public String getClinicName() {
		return clinicName;
	}
	public void setClinicName(String clinicName) {
		this.clinicName = clinicName;
	}
	public String getEvaluateId() {
		return evaluateId;
	}
	public void setEvaluateId(String evaluateId) {
		this.evaluateId = evaluateId;
	}
	public String getmUserId() {
		return mUserId;
	}
	public void setmUserId(String mUserId) {
		this.mUserId = mUserId;
	}
	public String getEvaluateContent() {
		return evaluateContent;
	}
	public void setEvaluateContent(String evaluateContent) {
		this.evaluateContent = evaluateContent;
	}
	public String getEvaluateTime() {
		return evaluateTime;
	}
	public void setEvaluateTime(String evaluateTime) {
		this.evaluateTime = evaluateTime;
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
	public int getEvaluateDoctScore() {
		return evaluateDoctScore;
	}
	public void setEvaluateDoctScore(int evaluateDoctScore) {
		this.evaluateDoctScore = evaluateDoctScore;
	}
	public int getEvaluateClinicScore() {
		return evaluateClinicScore;
	}
	public void setEvaluateClinicScore(int evaluateClinicScore) {
		this.evaluateClinicScore = evaluateClinicScore;
	}
 
 
	
	 
	

}
