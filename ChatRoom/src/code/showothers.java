package code;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class showothers extends JFrame{
	private account user;
	private Socket client;
	JLabel Lname,Lage,Lbrithday,Lid,Lphone,Lemail,Lsex;
	JTextField name,age,brithday,id,phone,email;
	PrintWriter pw;
	ButtonGroup sex;
	CalendarPanel p;
	JRadioButton c1,c2,c3;
	static boolean flag1,flag2,flag3;
	public showothers(account user) {
		this.user=user;
		flag1=flag3=true;
		flag2=false;
		Lname=new JLabel("账户名:");
		Lage=new JLabel("年龄:");
		Lphone=new JLabel("手机号:");
		Lsex=new JLabel("性别:");
		Lemail=new JLabel("邮箱:");
		Lid=new JLabel("id:");
		Lbrithday=new JLabel("生日:");
		name=new JTextField(user.getName());
		age=new JTextField(user.getAge());
		brithday=new JTextField(user.getBrithday());
		sex=new ButtonGroup();
		p=new CalendarPanel(brithday, "yyyy/MM/dd");
		p.initCalendarPanel();
		
		c1=new JRadioButton("男");
		c2=new JRadioButton("女");
		c3=new JRadioButton("保密");
		sex.add(c1);
		sex.add(c2);
		sex.add(c3);
		if(user.getSex().equals("男")) {
			c1.setSelected(true);
		}else if(user.getSex().equals("女")) {
			c2.setSelected(true);
		}else {
			c3.setSelected(true);
		}
		phone=new JTextField("非公开信息");
		phone.setEditable(false);
		email=new JTextField(user.getEmail());
		email.setEditable(false);
		id=new JTextField(user.getId()+"");
		
		Lid.setBounds(70, 40, 50, 30);
		id.setBounds(120, 40, 200, 30);
		id.setEditable(false);
		Lname.setBounds(70, 80, 50, 30);
		name.setBounds(120, 80, 200, 30);
		name.setEditable(false);
		Lsex.setBounds(70, 120, 50, 30);
		c1.setBounds(120, 120, 65, 30);
		c2.setBounds(187, 120, 65, 30);
		c3.setBounds(253, 120, 65, 30);
		c1.setEnabled(false);
		c2.setEnabled(false);
		c3.setEnabled(false);
		Lage.setBounds(70, 160, 50, 30);
		age.setBounds(120, 160, 200, 30);
		age.setEditable(false);
		Lemail.setBounds(70, 200, 50, 30);
		email.setBounds(120, 200, 200,30);
		Lbrithday.setBounds(70, 240, 50, 30);
		brithday.setBounds(120, 240, 200, 30);
		brithday.setEditable(false);
		Lphone.setBounds(70,280,50,30);
		phone.setBounds(120, 280, 200, 30);
		this.setLayout(null);
		this.add(p);
		this.add(c1);
		this.add(c2);
		this.add(c3);
		this.add(Lname);
		this.add(name);
		this.add(Lage);
		this.add(age);
		this.add(Lid);
		this.add(id);
		this.add(Lemail);
		this.add(email);
		this.add(Lphone);
		this.add(phone);
		this.add(Lbrithday);
		this.add(brithday);
		this.add(Lsex);
		this.setSize(400, 400);
		this.setLocationRelativeTo(null);
	    this.setVisible(true);
	}
}
