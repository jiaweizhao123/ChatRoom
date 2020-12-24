package code;

public class account {
	private String name;
	private String password;
	private String sex;
	private String phone;
	private String email;
	private String age;
	private String Create_time;
	private int id;
	private String brithday;
	private String address;
	private String image_src;
	//用户登录构造方法
	public account(String name,String password) {
		this.name=name;
		this.password=password;
	}
	public account(String name) {
		this.name=name;
	}
	public account() {
		
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getCreate_time() {
		return Create_time;
	}
	public void setCreate_time(String create_time) {
		Create_time = create_time;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getBrithday() {
		return brithday;
	}
	public void setBrithday(String brithday) {
		this.brithday = brithday;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getImage_src() {
		return image_src;
	}
	public void setImage_src(String image_src) {
		this.image_src = image_src;
	}
	public String toString() {
		return this.name+"!!"+this.age+"!!"+this.brithday+"!!"+this.email+"!!"+this.id+"!!"+this.phone+"!!"+this.sex;
	}
	
	
}
