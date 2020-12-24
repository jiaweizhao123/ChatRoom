package code;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.border.LineBorder;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class showself extends JFrame implements ActionListener{
	private account user;
	private Socket client;
	JButton jb1;
	JLabel Lname,Lage,Lbrithday,Lid,Lphone,Lemail,Lsex;
	JTextField name,age,brithday,id,phone,email;
	PrintWriter pw;
	ButtonGroup sex;
	CalendarPanel p;
	JRadioButton c1,c2,c3;
	static boolean flag1,flag2,flag3;
	public showself(account user,Socket s) {
		this.user=user;
		this.client=s;
		flag1=flag3=true;
		flag2=false;
		try {
			pw=new PrintWriter(s.getOutputStream(),true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		phone=new JTextField(user.getPhone());
		email=new JTextField(user.getEmail());
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
		Lage.setBounds(70, 160, 50, 30);
		age.setBounds(120, 160, 200, 30);
		Lemail.setBounds(70, 200, 50, 30);
		email.setBounds(120, 200, 200,30);
		Lbrithday.setBounds(70, 240, 50, 30);
		brithday.setBounds(120, 240, 200, 30);
		Lphone.setBounds(70,280,50,30);
		phone.setBounds(120, 280, 200, 30);
		
		jb1=new JButton("修改");
		jb1.setBounds(150, 330, 100, 40);
		jb1.setForeground(Color.white);
		jb1.setBackground(new Color(41,162,214));
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
		this.add(jb1);
		this.setSize(400, 500);
		this.setLocationRelativeTo(null);
	    this.setVisible(true);
	    
	    jb1.setActionCommand("change");
	    jb1.addActionListener(this);
	    age.addFocusListener(new FocusListener() { // 在jt1处布置焦点事件监听

            @Override
            public void focusGained(FocusEvent e) { // 焦点获取
                // TODO Auto-generated method stub

            }

            @Override
            public void focusLost(FocusEvent e) { // 焦点失去
                // TODO Auto-generated method stub
                if (!check(age.getText())) {
                    flag2=false;
                    age.setBorder(new TextBorderUtlis(Color.red,1,true));
                } else{
                	flag2=true;
                	age.setBorder(new TextBorderUtlis(Color.black,1,true));
                }
            }

        });
	    
	    email.addFocusListener(new FocusListener() { // 在jt1处布置焦点事件监听

            @Override
            public void focusGained(FocusEvent e) { // 焦点获取
                // TODO Auto-generated method stub

            }

            @Override
            public void focusLost(FocusEvent e) { // 焦点失去
                // TODO Auto-generated method stub
                if (!isEmail(email.getText())) {
                    email.setBorder(new TextBorderUtlis(Color.red,1,true));
                    flag1=false;
                } else{
                	email.setBorder(new TextBorderUtlis(Color.black,1,true));
                	flag1=true;
                }
            }

        });
	    
	   c1.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(isNumeric(age.getText())) {
				flag2=true;
			}
		}
	});
	   c2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(isNumeric(age.getText())) {
					flag2=true;
				}
			}
		});
	   
	   c3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(isNumeric(age.getText())) {
					flag2=true;
				}
			}
		});
	    
	    phone.addFocusListener(new FocusListener() { // 在jt1处布置焦点事件监听

            @Override
            public void focusGained(FocusEvent e) { // 焦点获取
                // TODO Auto-generated method stub

            }

            @Override
            public void focusLost(FocusEvent e) { // 焦点失去
                // TODO Auto-generated method stub
                if (!check(phone.getText())||phone.getText().length()!=11) {
                    flag3=false;
                    phone.setBorder(new TextBorderUtlis(Color.red,1,true));
                } else{
                	flag3=true;
                	phone.setBorder(new TextBorderUtlis(Color.black,1,true));
                }
            }

        });
			
		
	}
	
	public static boolean isEmail(String string) {
        if (string == null)
            return false;
        String regEx1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern p;
        Matcher m;
        p = Pattern.compile(regEx1);
        m = p.matcher(string);
        if (m.matches())
            return true;
        else
            return false;
    }
	public boolean check(String num) {
		for(int i=0;i<num.length();i++) {
			if(!Character.isDigit(num.charAt(i))) {
				return false;
			}
		}
		return true;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand().equals("change")) {
			if(flag1&&flag2&&flag3) {
				String sexmessage;
				if(c1.isSelected())
					sexmessage=c1.getText();
				else if(c2.isSelected())
					sexmessage=c2.getText();
				else
					sexmessage=c3.getText();
				String message=age.getText()+"!!"+brithday.getText()+"!!"+email.getText()+"!!"+sexmessage+"!!"+phone.getText();
				String msg="on@#"+"修改信息"+"@#"+message+"@#"+user.getName();
				pw.println(msg);
				JOptionPane.showMessageDialog(null,  "修改成功","提示", JOptionPane.CLOSED_OPTION); //出现弹窗
				this.dispose();
			}else {
				JOptionPane.showMessageDialog(null,  "未作出修改或信息格式错误","错误提示", JOptionPane.CLOSED_OPTION); //出现弹窗
			}
		}
	}
	
	public static boolean isNumeric(String str){
		   for (int i = str.length();--i>=0;){
		       if (!Character.isDigit(str.charAt(i))){
		           return false;
		       }
		   }
		   return true;
		}
}
