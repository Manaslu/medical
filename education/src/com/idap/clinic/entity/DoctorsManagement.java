package com.idap.clinic.entity;

import java.util.Date;

import com.idp.pub.entity.annotation.MetaTable;

@MetaTable
public class DoctorsManagement implements java.io.Serializable {
	private static final long serialVersionUID = 5736511926578194639L;
	
	private String     doctorId;//DOCTOR_ID
	private String     doctorName;//DOCTOR_NAME 
	private String     doctorDesc;//DOCTOR_DESC
	private String     doctorPic;//DOCTOR_PIC
	private String     doctorDegree;//DOCTOR_DEGREE
	private String     doctorTel;//DOCTOR_TEL 
	private String     doctorSpeciality;//DOCTOR_SPECIALITY
	private String     doctorDepartment;//DOCTOR_DEPARTMENT 
	private Date       doctorDate;//DOCTOR_DATE
	private String     clinicId;//CLINIC_ID
	private String     doctorGender;//DOCTOR_GENDER
	private String     doctorIdcard;//DOCTOR_IDCARD
	private String     doctorCertificate;//DOCTOR_CERTIFICATE
	private String clinicName;
	private String clinicTel;



	
	public String getDoctorGender() {
		return doctorGender;
	}
	public void setDoctorGender(String doctorGender) {
		this.doctorGender = doctorGender;
	}
	public String getDoctorIdcard() {
		return doctorIdcard;
	}
	public void setDoctorIdcard(String doctorIdcard) {
		this.doctorIdcard = doctorIdcard;
	}
 
 
	public String getDoctorCertificate() {
		return doctorCertificate;
	}
	public void setDoctorCertificate(String doctorCertificate) {
		this.doctorCertificate = doctorCertificate;
	}
	public String getDoctorId() {
		return doctorId;
	}
	public void setDoctorId(String doctorId) {
		this.doctorId = doctorId;
	}
	public String getDoctorName() {
		return doctorName;
	}
	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}
	public String getDoctorDesc() {
		return doctorDesc;
	}
	public void setDoctorDesc(String doctorDesc) {
		this.doctorDesc = doctorDesc;
	}
	public String getDoctorPic() {
		return doctorPic;
	}
	public void setDoctorPic(String doctorPic) {
		this.doctorPic = doctorPic;
	}
	public String getDoctorDegree() {
		return doctorDegree;
	}
	public void setDoctorDegree(String doctorDegree) {
		this.doctorDegree = doctorDegree;
	}
	public String getDoctorTel() {
		return doctorTel;
	}
	public void setDoctorTel(String doctorTel) {
		this.doctorTel = doctorTel;
	}
	public String getDoctorSpeciality() {
		return doctorSpeciality;
	}
	public void setDoctorSpeciality(String doctorSpeciality) {
		this.doctorSpeciality = doctorSpeciality;
	}
	public String getDoctorDepartment() {
		return doctorDepartment;
	}
	public void setDoctorDepartment(String doctorDepartment) {
		this.doctorDepartment = doctorDepartment;
	}
	public Date getDoctorDate() {
		return doctorDate;
	}
	public void setDoctorDate(Date doctorDate) {
		this.doctorDate = doctorDate;
	}
	public String getClinicId() {
		return clinicId;
	}
	public void setClinicId(String clinicId) {
		this.clinicId = clinicId;
	}
	public String getClinicName() {
		return clinicName;
	}
	public void setClinicName(String clinicName) {
		this.clinicName = clinicName;
	}
	public String getClinicTel() {
		return clinicTel;
	}
	public void setClinicTel(String clinicTel) {
		this.clinicTel = clinicTel;
	}

 
	
 
 

}
