package code;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
public class client extends JFrame implements ActionListener, KeyListener{
    private Image im,icon_myself;
    private static account user,temp;
    private JPanel jp4,jp2,jp3;
    private JButton jb1;
    private backgroundpanel jp1;
	private static String ip="127.0.0.1";
	private static int port=8080;
	private JTextField tfdUserName=new JTextField(10);	//用户标识
	private JTextArea allMsg=new JTextArea();	//聊天信息显示
	private JTextArea tfdMsg=new JTextArea();	//发送消息消息框
	//在线用户列表
	private DefaultListModel<String> dataModel=new DefaultListModel<String>();
	private JList<String> list=new JList<String>(dataModel);
	
	private static Socket client;
	private static PrintWriter pw;
	
    public client(account user) {
    	
    	this.user=user;
    	temp=new account();
    	int rand_num=(int)(Math.random()*6)+1; //简单的实现图片轮播效果
    	while(rand_num<1||rand_num>6)
    		rand_num=(int)(Math.random()*6)+1;
    	String src="src/image/背景5.png";
        jp1=new backgroundpanel(new ImageIcon(src).getImage());
        jp2=new JPanel();
        jp3=new JPanel();
        jp4=new JPanel();
        jb1=new JButton("发送");
        JScrollPane scrollpane1=new JScrollPane();
        allMsg.setBounds(10, 15, 635, 370);
        allMsg.setEditable(false);
        allMsg.setForeground(Color.blue);
        allMsg.setBackground(null);
        allMsg.setOpaque(false);
		allMsg.setFont(new Font("幼圆", Font.BOLD, 14));
		allMsg.setLineWrap(true);
		scrollpane1.setBounds(10, 15, 635, 370);
		scrollpane1.setViewportView(allMsg);
		scrollpane1.setBorder(null);
		scrollpane1.setBackground(null);
		scrollpane1.setOpaque(false);
		scrollpane1.getViewport().setOpaque(false);  
		allMsg.setOpaque(false);
		scrollpane1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER); 
		
		dataModel.addElement("全部");
		list.setBounds(0, 0, 200, 650);
		list.setFont(new Font("幼圆", Font.CENTER_BASELINE, 14));
		list.setSelectedIndex(0);
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);	//设置只能单选
		list.setForeground(Color.blue);
	    list.setBackground(null);
	    list.setOpaque(false);
		list.setBorder(BorderFactory.createLoweredBevelBorder());
		
		JScrollPane scrollpane=new JScrollPane();
		scrollpane.setBounds(10, 7, 630, 130);//自定义该面板位置并设置大小为100*50
		tfdMsg.setBounds(10, 7, 630, 130);
		tfdMsg.setForeground(Color.black);
		tfdMsg.setLineWrap(true);
		tfdMsg.setBackground(new Color(202,234,233));
		jp3.setBackground(new Color(202,234,233));
		scrollpane.setBackground(new Color(202,234,233));
		scrollpane.setBounds(10, 7, 630, 130);
		scrollpane.setForeground(Color.black);
		scrollpane.setViewportView(tfdMsg);
		scrollpane.setBorder(null);
		
		scrollpane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER); 
		

		tfdMsg.setFont(new Font("仿宋", Font.BOLD, 19));
        jp2.setBounds(0, 0, 650, 400);
        jp2.setLayout(null);
        jp2.setBackground(null);
        jp2.setOpaque(false);
        jp3.setBounds(0, 400, 650, 213);
        jp3.setLayout(null);
//        jp3.setOpaque(false);
        jp4.setBounds(650, 0, 200, 650);
        jp4.setLayout(null);
        jp4.setBackground(null);
        jp4.setOpaque(false);
        jp1.setLayout(null);
        jp1.setBounds(0, 0, 850, 650);
        jb1.setBounds(575, 165, 60, 30);
        jb1.setBackground(new Color(41,162,214));
        jb1.setForeground(Color.white);
        jb1.setMargin(new Insets(0,0,0,0));//将边框外的上下左右空间设置为0  
        jb1.setIconTextGap(0);//将标签中显示的文本和图标之间的间隔量设置为0  
        jb1.setBorderPainted(false);//不打印边框  
        jb1.setBorder(null);//除去边框  
        jb1.setFocusPainted(false);//除去焦点的框  
        jb1.setActionCommand("send");
        jb1.addActionListener(this); 
        this.setSize(850,650);
        this.setTitle("聊天室界面");
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(3);
        this.add(jp1);
        ImageIcon ic=new ImageIcon("src/image/聊天室.png");
        this.setIconImage(ic.getImage());
        jp1.add(jp2);
        jp1.add(jp3);
        jp1.add(jp4);
        jp2.add(scrollpane1);
        jp3.add(jb1);
        jp3.add(scrollpane);
        jp4.add(list);
        tfdMsg.addKeyListener(this);
        list.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // 鼠标点击（按下并抬起）
            }
            @Override
            public void mousePressed(MouseEvent e) {
                // 鼠标按下
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                // 鼠标释放

                // 如果是鼠标右键，则显示弹出菜单
                if (e.isMetaDown()) {
                    showPopupMenu(e.getComponent(), e.getX(), e.getY());
                }
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                // 鼠标进入组件区域
            }
            @Override
            public void mouseExited(MouseEvent e) {
                // 鼠标离开组件区域
            }
        });
        allMsg.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // 鼠标点击（按下并抬起）
            }
            @Override
            public void mousePressed(MouseEvent e) {
                // 鼠标按下
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                // 鼠标释放

                // 如果是鼠标右键，则显示弹出菜单
                if (e.isMetaDown()) {
                    showPopupMenu(e.getComponent(), e.getX(), e.getY());
                }
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                // 鼠标进入组件区域
            }
            @Override
            public void mouseExited(MouseEvent e) {
                // 鼠标离开组件区域
            }
        });
        JPopupMenu.setDefaultLightWeightPopupEnabled(false);     
        KeyStroke enter = KeyStroke.getKeyStroke("ENTER");
        tfdMsg.getInputMap().put(enter, "none");
        this.addKeyListener(this);
        this.setLayout(null);
        connecting();
        this.setVisible(true);
        this.addWindowListener(new WindowAdapter() {
        	
        	
        	public void windowClosing(WindowEvent e) {
        		super.windowClosing(e);
        		String msg="exit@#全部@#null@#"+user.getName();
    			pw.println(msg);
    			System.out.println("closes");
        	}
        	
        }); 
        
        
    }
    
    public static void showPopupMenu(Component invoker, int x, int y) {
    	JPopupMenu popupMenu;
    	JMenuItem showmyself;
    	JMenu search;
    	JMenuItem exit;
    	JMenuItem searchbyname;
    	JMenuItem searchbyid;
    	
    	popupMenu=new JPopupMenu();
		showmyself=new JMenuItem("查看自身资料");
		search=new JMenu("查找用户");
		exit=new JMenuItem("退出聊天室");
		searchbyname=new JMenuItem("通过昵称查找");
		searchbyid=new JMenuItem("通过id查找");
		popupMenu.add(showmyself);
		popupMenu.add(search);
		popupMenu.add(exit);
		search.add(searchbyid);
		search.add(searchbyname);
		popupMenu.show(invoker, x, y);
		exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
        		String msg="exit@#全部@#null@#"+user.getName();
    			pw.println(msg);
    			System.exit(1);
            }
        });
		
		showmyself.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
        		new showself(user,client);
            }
        });
		
		searchbyname.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	String searchname=JOptionPane.showInputDialog(null," 请输入用户名","用户搜索",JOptionPane.PLAIN_MESSAGE);
            	String msg="on@#查找用户@#searchname@#"+searchname;
            	pw.println(msg);
            }
        });
		
		searchbyid.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	String searchid=JOptionPane.showInputDialog(null," 请输入id","用户搜索",JOptionPane.PLAIN_MESSAGE);
            	String msg="on@#查找用户@#searchid@#"+searchid;
            	pw.println(msg);
            }
        });
		
		
    }
    
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
			if(e.getActionCommand().equals("send")) {
				System.out.println("test1");
				if(tfdMsg.getText().equals("")){
					JOptionPane.showMessageDialog(this, "发送消息不能为空");
					return;
				}
			else {
				System.out.println(list.getSelectedValue());
				System.out.println(tfdMsg.getText());
				System.out.println(user.getName());
				String msg="on@#"+list.getSelectedValue()+"@#"+tfdMsg.getText()+"@#"+user.getName();
				pw.println(msg);
				System.out.println("transimit!!");
				tfdMsg.setText("");
				}
			}
	}
	public void setUser(String age,String brithday,String email,String id,String phone,String sex) {
		user.setAge(age);
		user.setBrithday(brithday);
		user.setEmail(email);
		user.setPhone(phone);
		user.setSex(sex);
		user.setId(Integer.parseInt(id));
	}
	
	public void setOthers(String name,String age,String brithday,String email,String id,String phone,String sex) {
		temp.setAge(age);
		temp.setBrithday(brithday);
		temp.setEmail(email);
		temp.setPhone(phone);
		temp.setSex(sex);
		temp.setName(name);
		temp.setId(Integer.parseInt(id));
	}
	
	private void connecting() {
		try {
			client=new Socket("127.0.0.1",port); 
			pw=new PrintWriter(client.getOutputStream(),true);
			pw.println(user.getName());
			new ClientThread(client).start();
			System.out.println("test3");
		}catch(UnknownHostException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	class ClientThread extends Thread{
		private Socket client;
		public ClientThread(Socket client) {
			this.client=client;
		}
		@Override
		public void run() {
			try {
				Scanner sc=new Scanner(client.getInputStream());
				while(sc.hasNext()) {
					System.out.println("init!!");
					String msg=sc.nextLine();
					System.out.println("test111");
					System.out.println(msg);
					String msgs[]=msg.split("@#");
					
					if(msgs==null||msgs.length!=3) {
						System.out.println(msgs);
						System.out.println(user.getName()+"通讯异常1");
						return ;
					}
					System.out.println(msgs[0]);
					if("msg".equals(msgs[0])) {
						if("server".equals(msgs[1])) {
							if(msgs[2].equals(user.getName()+"上线了")) {
								msg="登陆成功,进入聊天室";
								allMsg.append(msg+"\r\n");
							}else {
							msg="系统消息："+msgs[2];
							allMsg.append(msg+"\r\n");
							System.out.println("test7");
							}
						}else{
							//表示该信息聊天信息
							
							msg=msgs[1]+msgs[2];
							allMsg.append(msg+"\r\n");
							System.out.println("用户："+msg);
						}
					}else if("cmdAdd".equals(msgs[0])){
						//表示该消息是用来更新用户在线列表的,添加用户
						dataModel.addElement(msgs[2]);
						System.out.println(user.getName()+msgs[2]);
						System.out.println("add!");
					}else if("cmdRed".equals(msgs[0])){
						//表示该消息是用来更新用户在线列表的,移除用户
						dataModel.removeElement(msgs[2]);
					}else if("cmdget".equals(msgs[0])) {
						String message[]=msgs[2].split("!!");
						setUser(message[1],message[2],message[3],message[4],message[5],message[6]);
						System.out.println(user.getId());
					}else if("cmdgetothers".equals(msgs[0])) {
						System.out.println("searchinto");
						if(msgs[2].equals("NOTFOUND")) {
							JOptionPane.showMessageDialog(null,  "该用户不存在","错误提示", JOptionPane.CLOSED_OPTION); 
						}else {
							String message[]=msgs[2].split("!!");
							setOthers(message[0],message[1],message[2],message[3],message[4],message[5],message[6]);
							new showothers(temp);
						}
					}
					list.validate();	//需要刷新list，不然可能出现list更新失败的bug
					System.out.println("test5");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
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