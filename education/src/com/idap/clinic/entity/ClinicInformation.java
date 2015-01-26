package com.idap.clinic.entity;
import java.util.Date;

import com.idp.pub.entity.annotation.MetaTable;

@MetaTable
public class ClinicInformation implements java.io.Serializable {
	private static final long serialVersionUID = 5736511926578194639L;
	 
	private String		clinicId 	;//	clinic_id 	CLINIC_ID 
	private String		clinicName 	;//	clinic_name 	CLINIC_NAME 
	private String		clinicAddress  	;//	clinic_address  	CLINIC_ADDRESS  
	private String		clinicDesc 	;//	clinic_desc 	CLINIC_DESC 
	private String		clinicPic  	;//	clinic_pic  	CLINIC_PIC  
	private String		clinicLicense  	;//	clinic_license  	CLINIC_LICENSE  
	private String		clinicTel  	;//	clinic_tel  	CLINIC_TEL  
	private String		clinicLongitude  	;//	clinic_longitude  	CLINIC_LONGITUDE  
	private String		clinicLatitude 	;//	clinic_latitude 	CLINIC_LATITUDE 
	private String		clinicSpeciality 	;//	clinic_speciality 	CLINIC_SPECIALITY 
	private Date		clinicDate 	;//	clinic_date 	CLINIC_DATE 
	private String		tenementId 	;//	tenement_id 	TENEMENT_ID 
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
	public String getClinicAddress() {
		return clinicAddress;
	}
	public void setClinicAddress(String clinicAddress) {
		this.clinicAddress = clinicAddress;
	}
	public String getClinicDesc() {
		return clinicDesc;
	}
	public void setClinicDesc(String clinicDesc) {
		this.clinicDesc = clinicDesc;
	}
	public String getClinicPic() {
		return clinicPic;
	}
	public void setClinicPic(String clinicPic) {
		this.clinicPic = clinicPic;
	}
	public String getClinicLicense() {
		return clinicLicense;
	}
	public void setClinicLicense(String clinicLicense) {
		this.clinicLicense = clinicLicense;
	}
	public String getClinicTel() {
		return clinicTel;
	}
	public void setClinicTel(String clinicTel) {
		this.clinicTel = clinicTel;
	}
	public String getClinicLongitude() {
		return clinicLongitude;
	}
	public void setClinicLongitude(String clinicLongitude) {
		this.clinicLongitude = clinicLongitude;
	}
	public String getClinicLatitude() {
		return clinicLatitude;
	}
	public void setClinicLatitude(String clinicLatitude) {
		this.clinicLatitude = clinicLatitude;
	}
	public String getClinicSpeciality() {
		return clinicSpeciality;
	}
	public void setClinicSpeciality(String clinicSpeciality) {
		this.clinicSpeciality = clinicSpeciality;
	}
	public Date getClinicDate() {
		return clinicDate;
	}
	public void setClinicDate(Date clinicDate) {
		this.clinicDate = clinicDate;
	}
	public String getTenementId() {
		return tenementId;
	}
	public void setTenementId(String tenementId) {
		this.tenementId = tenementId;
	}
	
	 
	
	 
	

}
