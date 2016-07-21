package net.frygo.findmybuddy;

public class userinfo {
	
	//private variables
	int _id;
	String _name;
	String _email;
	String _regid;
	String _imgid;
	
	// Empty constructor
	public userinfo(){
		
	}

	// constructor
			public userinfo(String name, String email, String regid, String imgid){
				
				this._name = name;
				this._email = email;
				this._imgid=imgid;
				this._regid=regid;
			}
	// getting ID
	public int getID(){
		return this._id;
	}
	
	// setting id
	public void setID(int id){
		this._id = id;
	}
	
	// getting name
	public String getName(){
		return this._name;
	}
	
	// setting name
	public void setName(String name){
		this._name = name;
	}
	
	// getting email
	public String getEmail(){
		return this._email;
	}
	
	// setting name
	public void setEmail(String email){
		this._email = email;
	}
	
	// getting name
	public String getRegid(){
		return this._regid;
	}
	
	// setting name
	public void setRegid(String regid){
		this._regid = regid;
	}
	
	// getting name
	public String getImgid(){
		return this._imgid;
	}
	
	// setting name
	public void setImgid(String imgid){
		this._imgid = imgid;
	}
	
	
	
}
