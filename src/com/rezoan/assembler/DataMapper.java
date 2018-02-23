package com.rezoan.assembler;



import java.util.Collection;


import java.util.TreeMap;

public class DataMapper {

	TreeMap<String, String> tm=new TreeMap<String, String> ();
	
	private String address,binCode;
	public DataMapper() {
		this.address="0";
		this.binCode="0000000000000000";

	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		if(address.length()>=4)address=address.substring(1);
		this.address = address.toUpperCase();
		tm.put(this.address, this.binCode);

		}
	
	public void setBinCode(String binCode) {
		this.binCode = binCode;
		tm.put(this.address, this.binCode);
	}
	
	public String getBinCode(String address) {
		if(address.length()>=4)address=address.substring(1);
		this.address=address.toUpperCase();
		this.binCode= tm.get(this.address);
		return this.binCode;
	}
	public String getBinCode() {
		return this.binCode;
	}


	
	public void makeEntry(String address,String binCode){
		if(address.length()>=4)address=address.substring(1);
		this.address = address.toUpperCase();
		this.binCode = binCode;
		tm.put(this.address, this.binCode);
		
	}
	public Collection<String> getNextBinCode() {
		Collection<String> binCode=tm.values();
		return binCode;
	}
	public String getAll(){
		
		return tm.toString();
	}
}
