package com.cfms.shared.pojo;

/**
 * This is a POJO which contains the information about the cab provider.
 * @author Samta, Priyanka, Chaitra
 *
 */
public class CabProviderBean {
private String company_name;
private String address;
private String email;
private String phone;

/**
 * Its a getter to get the name of this cab provider company.
 * @return name of this cab provider company
 */
public String getCompany_name() {
	return company_name;
}
/**
 * Its a setter to set the name of this cab provider company.
 * @param  name of this cab provider company
 */
public void setCompany_name(String company_name) {
	this.company_name = company_name;
}
/**
 * This a getter to get the address of this cab provider.
 * @return address of this cab provider
 */
public String getAddress() {
	return address;
}
/**
 * Its a setter to set the address of this cab provider.
 * @param address of this cab provider
 */
public void setAddress(String address) {
	this.address = address;
}
/**
 * Its a getter to get the email id of this cab provider.
 * @return email id of this cab provider
 */
public String getEmail() {
	return email;
}
/**
 * This setter is used to set the email id of this cab provider.
 * @param email id
 */
public void setEmail(String email) {
	this.email = email;
}
/**
 * Its a getter to get the phone number of this cab provider.
 * @return phone number of this cab provider
 */
public String getPhone() {
	return phone;
}
/**
 * Its a setter to set the phone number of this cab provider.
 * @param phone number of this cab provider
 */
public void setPhone(String phone) {
	this.phone = phone;
}
}
