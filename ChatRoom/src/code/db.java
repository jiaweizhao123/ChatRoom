package code;

import java.util.Date;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class db {
	private String URL="jdbc:mysql://localhost:3306/chatroom?&useSSL=false&serverTimezone=UTC"; 
	private String USER="root";
	private String PASSWORD="a77820101";
	private Connection connection=null;
	private PreparedStatement pstmt=null;
	private ResultSet rs=null;
	private String SQL;
	//自带数据库连接的无参构造方法
	public  db(){
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection= DriverManager.getConnection(URL,USER,PASSWORD);
		}catch(SQLException e) {
			e.printStackTrace();
		}catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void getClose() {

	}
	
	//获取当前时间
	public String getnowtime() {
		Date date=new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String now=sdf.format(date).toString();
		return now;
	}
	//根据用户名查找用户
	public boolean search_account(String name) {
		try {
			SQL="select count(*) from accounts_information where name=?";
			pstmt=connection.prepareStatement(SQL);
			pstmt.setString(1, name);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				return rs.getInt(1)==0?false:true;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			getClose();
		}
		
		return true;
	}
	//在数据库中注册用户
    public boolean create_account(account user) {
    	try {
    		if(search_account(user.getName())) {
    			return false;
    		}else {
    			String now=getnowtime();
    			SQL="insert into accounts_information(name,password,create_time) values(?,?,?)";
    			pstmt=connection.prepareStatement(SQL);
    			pstmt.setString(1, user.getName());
    			pstmt.setString(2, user.getPassword());
    			pstmt.setString(3, now);
    			if(pstmt.executeUpdate()!=0)
    			return true;
    			return false;
    		}
    	}catch(SQLException e) {
    		e.printStackTrace();
    	}catch(Exception e) {
    		e.printStackTrace();
    	}finally {
			getClose();
		}
    	return false;
    }
    //登录数据库校验用户信息
    public boolean login_search(account user) {
    	try {
    		String SQL="select count(*) from accounts_information where name=? and password=?";
    		pstmt=connection.prepareStatement(SQL);
    		pstmt.setString(1, user.getName());
    		pstmt.setString(2, user.getPassword());
    		rs=pstmt.executeQuery();
    		if(rs.next()) {
    			return rs.getInt(1)==0?false:true;
    		}
    	}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(Exception e) {
    		e.printStackTrace();
    	}finally {
			getClose();
		}
    	return false;
    }
    //按照用户账号查找用户并返回该用户对象
    public account username_search(String name) {
    	account user=new account();
    	try {
    		String SQL="select * from accounts_information where name=?";
    		pstmt=connection.prepareStatement(SQL);
    		pstmt.setString(1, "'"+name+"'");
    		rs=pstmt.executeQuery();
    		if(rs.next()) {
    			if(rs.getInt(1)==0) {
    				return null;
    			}else {
    				user.setName(rs.getString("name"));
    				user.setAge(rs.getInt("age")+"");
    				user.setPhone(rs.getString("phone"));
    				user.setId(rs.getInt("id"));
    				user.setEmail(rs.getString("email"));
    				user.setSex(rs.getString("sex"));
    				user.setBrithday(rs.getString("brithday"));
    				return user;
    			}
    		}
    	}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(Exception e) {
    		e.printStackTrace();
    	}
    	return user;
    }
    //将用户聊天记录存入数据库中
    public boolean save_chatrecord(String chat,account user) {
    	try {
    			if(search_account(user.getName())) {
    				String SQL="insert into chatrecord(name,chats,send_time) values(?,?,?)";
    				String now=getnowtime();
    				pstmt=connection.prepareStatement(SQL);
    				pstmt.setString(1, user.getName());
    				pstmt.setString(2, chat);
    				pstmt.setString(3, now);
    				if(pstmt.executeUpdate()!=0)
    	    			return true;
    	    			return false;
    			}else {
    				return false;
    			}
    	}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(Exception e) {
    		e.printStackTrace();
    	}finally {
			getClose();
		}
    	return false;
    }
    
    //通过用户名返回用户信息
    public account getmessage(String username) {
    	account user=new account(username);
    	String SQL="select * from accounts_information where name=?";
    	try {
			pstmt=connection.prepareStatement(SQL);
			pstmt.setString(1, username);
			user.setName(username);
			rs=pstmt.executeQuery();
			if(rs.next()){
				user.setName(rs.getString("name"));
				user.setSex(rs.getString("sex"));
				user.setAge(rs.getString("age"));
				user.setBrithday(rs.getString("brithday"));
				user.setEmail(rs.getString("email"));
				user.setId(Integer.parseInt(rs.getString("id")));
				user.setPhone(rs.getString("phone"));
			}else {
				return null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			getClose();
		}
    	return user;
    	
    }
    
    //通过id返回用户信息
    public account getmessage(int id) {
    	account user=new account();
    	String SQL="select * from accounts_information where id=?";
    	try {
			pstmt=connection.prepareStatement(SQL);
			pstmt.setInt(1, id);
			rs=pstmt.executeQuery();
			if(rs.next()){
				user.setName(rs.getString("name"));
				user.setSex(rs.getString("sex"));
				user.setAge(rs.getString("age"));
				user.setBrithday(rs.getString("brithday"));
				user.setEmail(rs.getString("email"));
				user.setId(Integer.parseInt(rs.getString("id")));
				user.setPhone(rs.getString("phone"));
			}else {
				return null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			getClose();
		}
    	return user;
    	
    }
    
    public account userupdate(account user) {
    	String SQL="update accounts_information set age=?,brithday=?,phone=?,email=?,sex=? where name=?";
    	try {
			pstmt=connection.prepareStatement(SQL);
			pstmt.setInt(1, Integer.parseInt(user.getAge()));
			pstmt.setString(2, user.getBrithday());
			pstmt.setString(3, user.getPhone());
			pstmt.setString(4, user.getEmail());
			pstmt.setString(5, user.getSex());
			pstmt.setString(6, user.getName());
			if(pstmt.executeUpdate()!=0) {
				return getmessage(user.getName());
			}else {
				return null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return user;
    }
    
}
