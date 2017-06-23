package com.agraeta.user.btl;

public class Bean_cartlist {
	
	int img_pro;
	String pro_name;
	String pro_oprice;
	String pro_cprice;
	public Bean_cartlist(int img_pro, String pro_name, String pro_oprice,
			String pro_cprice) {
		super();
		this.img_pro = img_pro;
		this.pro_name = pro_name;
		this.pro_oprice = pro_oprice;
		this.pro_cprice = pro_cprice;
	}
	public Bean_cartlist() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getImg_pro() {
		return img_pro;
	}
	public void setImg_pro(int img_pro) {
		this.img_pro = img_pro;
	}
	public String getPro_name() {
		return pro_name;
	}
	public void setPro_name(String pro_name) {
		this.pro_name = pro_name;
	}
	public String getPro_oprice() {
		return pro_oprice;
	}
	public void setPro_oprice(String pro_oprice) {
		this.pro_oprice = pro_oprice;
	}
	public String getPro_cprice() {
		return pro_cprice;
	}
	public void setPro_cprice(String pro_cprice) {
		this.pro_cprice = pro_cprice;
	}
	
	

}
