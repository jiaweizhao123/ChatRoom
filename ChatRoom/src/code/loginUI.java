package code;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
public class loginUI extends JFrame implements ActionListener, KeyListener{
    Image im;
    private static final long serialVersionUID = 1L;
    JLabel title,jl1,jl2,jl3,jl4;
    JTextField accounts;
    JPasswordField psw;
    JButton jb1,jb2;
    backgroundpanel jp1;
    public static void main(String[] args) {
        new loginUI();
    }
    public loginUI(){
    	int rand_num=(int)(Math.random()*8)+1; //简单的实现图片轮播效果
    	while(rand_num<1||rand_num>8)
    		rand_num=(int)(Math.random()*8)+1;
    	String src="src/image/背景"+rand_num+".png";
        jp1=new backgroundpanel(new ImageIcon(src).getImage());
        accounts=new JTextField();
        jb1=new JButton("登录");
        jb2=new JButton("没有账号？点击注册用户");
        jl1=new JLabel("用户名:");
        jl2=new JLabel(" 密码:");
        jl3=new JLabel();
        jl4=new JLabel();
        psw=new JPasswordField();
        ImageIcon ic=new ImageIcon("src/image/聊天室.png");
        this.setIconImage(ic.getImage());
        jl1.setBounds(130, 230, 60, 30);
        jl2.setBounds(130, 310, 60, 30);
        jb1.setBounds(300, 400, 100, 45);
        jl3.setBounds(530, 230, 120, 30);
        jl4.setBounds(530, 310, 120, 30);
        jb2.setBounds(225, 460, 250, 30);
        accounts.setBounds(200, 230, 320, 30);
        psw.setBounds(200, 310, 320, 30);
        jb1.setBackground(Color.white);
        accounts.setBackground(Color.white);
        psw.setBackground(Color.white);
        jl1.setFont(new Font("宋体",Font.BOLD,15));
        jl2.setFont(new Font("宋体",1,15));
        jb2.setFont(new Font("宋体",1,13));
        jb2.setForeground(new Color(255,128,64));
        jl1.setForeground(Color.white);
        jl2.setForeground(Color.white);
        jb2.setMargin(new Insets(0,0,0,0));//将边框外的上下左右空间设置为0  
        jb2.setIconTextGap(0);//将标签中显示的文本和图标之间的间隔量设置为0  
        jb2.setBorderPainted(false);//不打印边框  
        jb2.setBorder(null);//除去边框  
//        button.setText(null);//除去按钮的默认名称  
        jb2.setFocusPainted(false);//除去焦点的框  
        jb2.setContentAreaFilled(false);//除去默认的背景填充 

        jp1.setLayout(null);
        jp1.setBounds(0, 0, 700, 600);
        title=new JLabel("用户登录系统");
        title.setBounds(250, 90, 200, 50);
        title.setFont(new Font("仿宋",Font.BOLD,30));
        this.setBackground(Color.orange);
        this.setSize(700,600);
        this.setTitle("登陆界面");
        this.add(jb2);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(3);
        this.add(jp1);
        this.addKeyListener(this);
        accounts.addKeyListener(this);
        psw.addKeyListener(this);
        jp1.add(title);
        jp1.add(jl1);
        jp1.add(jl2);
        jp1.add(jl3);
        jp1.add(jl4);
        jp1.add(jb1);
        jp1.add(accounts);
        jp1.add(psw);
        title.setForeground(Color.white);
        this.setLayout(null);
        this.setVisible(true);
        
        
        //事件处理
        
        //用户名输入框不能为空提示
        accounts.addFocusListener(new FocusListener() { // 在jt1处布置焦点事件监听

            @Override
            public void focusGained(FocusEvent e) { // 焦点获取
                // TODO Auto-generated method stub

            }

            @Override
            public void focusLost(FocusEvent e) { // 焦点失去
                // TODO Auto-generated method stub
                if (accounts.getText().equals("")) {
                    jl3.setText("用户名不能为空");
                    accounts.requestFocus();
                    jl3.setForeground(Color.RED);
//                    account.setSelectionStart(0);
                    jl3.setVisible(true);
                } else if (accounts.getText()!=null) {
                    jl3.setVisible(false);
                }
            }

        });
        
        //密码输入框不能为空提示
        psw.addFocusListener(new FocusListener() { // 在jt1处布置焦点事件监听

            @Override
            public void focusGained(FocusEvent e) { // 焦点获取
                // TODO Auto-generated method stub

            }

            @Override
            public void focusLost(FocusEvent e) { // 焦点失去
                // TODO Auto-generated method stub
                if (psw.getText().equals("")&&!accounts.getText().equals("")) {
                    jl4.setText("密码不能为空");
                    psw.requestFocus();
                    jl4.setForeground(Color.RED);
//                    account.setSelectionStart(0);
                    jl4.setVisible(true);
                } else if (psw.getText()!=null) {
                    jl4.setVisible(false);
                }
            }

        });
        
        jb1.setActionCommand("login");
        jb1.addActionListener(this); 
        
        
        jb2.setActionCommand("sign_in");
        jb2.addActionListener(this);
    }
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
			if(e.getActionCommand().equals("login")) {
				String password=new String(psw.getPassword());
				db DB=new db();
				account user=new account(accounts.getText(),password);
				if(accounts.getText().equals("")||password.equals("")) {
					JOptionPane.showMessageDialog(null,  "账号或密码为空，请重新检查","错误提示", JOptionPane.CLOSED_OPTION); //出现弹窗
				}else{
				if(DB.login_search(user)) {
					 JOptionPane.showMessageDialog(null, "登陆成功，欢迎用户"+accounts.getText(), "登陆提示", JOptionPane.CLOSED_OPTION); //出现弹窗
					 account userself=new account(accounts.getText(),password);
					 new client(userself);
					 this.dispose();
				}else {
					 JOptionPane.showMessageDialog(null,  "用户信息错误,请重新输入","错误提示", JOptionPane.CLOSED_OPTION); //出现弹窗
		                psw.setSelectionStart(0); //将密码框内容全选中              
		                psw.requestFocus(); //将焦点回归文本框
				}}
			}else if(e.getActionCommand().equals("sign_in")) {
				sign_out sign=new sign_out();
				 this.dispose();
			}
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode()==KeyEvent.VK_ENTER) {
			jb1.doClick();
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
