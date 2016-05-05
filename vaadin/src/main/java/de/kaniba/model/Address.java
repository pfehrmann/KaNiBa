package de.kaniba.model;

import java.io.Serializable;

/**
 * Class to represent a Address.
 * @author Philipp
 *
 */
public class Address implements Serializable {
	private static final long serialVersionUID = 1L;
	private String city;
	private String street;
	private String number;
	private String zip;
	
	public Address(String city, String street, String number, String zip) {
		this.city = city;
		this.street = street;
		this.number = number;
		this.zip = zip;
	}
	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
}
