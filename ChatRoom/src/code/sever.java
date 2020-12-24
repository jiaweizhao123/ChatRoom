package code;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

public class sever extends JFrame implements ActionListener {
	private JTextArea area;//在线的用户信息显示 /*文本域*/
	private DefaultListModel<String> dataModel;	//在线的用户列表显示 
	private Map<String, Socket> userMap=new HashMap<String, Socket>();
	JMenu menu1;
	JMenuBar menuBar;
	JMenuItem itemexit;
	JMenuItem itemrun;
	ServerSocket sever;
	account user;
	db DB=new db();
	public sever(){
		Toolkit toolkit=Toolkit.getDefaultToolkit();
		Dimension dim=toolkit.getScreenSize();
		int width=(int) dim.getWidth();
		int height=(int) dim.getHeight();
		int runWidth=600;
		int runHeight=500;
		
        this.setDefaultCloseOperation(3);
		this.setResizable(false);
		this.setBounds(width/2-runWidth/2, height/2-runHeight/2, runWidth, runHeight);
		this.setTitle("服务端");
		ImageIcon ic=new ImageIcon("src/image/IM服务-聊天室.png");
        this.setIconImage(ic.getImage());
		area=new JTextArea();
		dataModel=new DefaultListModel<String>();
		area.setEditable(false);
		this.getContentPane().add(new JScrollPane(area),BorderLayout.CENTER);
		
		dataModel=new DefaultListModel<String>();
		JList<String> list=new JList<String>(dataModel);
		JScrollPane scroll=new JScrollPane(list);
		scroll.setBorder(new TitledBorder("在线"));
		scroll.setPreferredSize(new Dimension(100, this.getHeight()));
		getContentPane().add(scroll,BorderLayout.EAST);
		
		menuBar=new JMenuBar();
		this.setJMenuBar(menuBar);
		
		menu1=new JMenu("端口控制");
		menuBar.add(menu1);
		
		itemrun=new JMenuItem("开启");
		itemrun.setActionCommand("run");
		itemrun.addActionListener(this);
		menu1.add(itemrun);

		itemexit=new JMenuItem("关闭");
		itemexit.setActionCommand("exit");
		itemexit.addActionListener(this);
		menu1.add(itemexit);
		
		
		this.setVisible(true);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new sever();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
			if(e.getActionCommand().equals("run")) {
				startsever();
				itemrun.setEnabled(false);
			}else if(e.getActionCommand().equals("exit")) {
				if(!itemrun.isEnabled()) {
					itemrun.setEnabled(true);
					try {
						sever.close();
						area.append("服务器关闭"+'\n');
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
	}
	
	private void startsever() {
		try {
			sever=new ServerSocket(8080);
			area.append("启动服务器:"+sever+'\n');
			new ServerThread(sever).start();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	class ServerThread extends Thread{
		private ServerSocket client_server;
		
		public ServerThread(ServerSocket server) {
			this.client_server=server;
		}
		@Override
		public void run() {
			try {
				while(true) {
					Socket s=client_server.accept();
					Scanner sc=new Scanner(s.getInputStream());
					if(sc.hasNext()) {
						String userName=sc.next();
						area.append("\r\n"+userName+"上线了"+s+'\n');
						dataModel.addElement(userName);
						
						new ClientThread(s).start();
						System.out.println(userName);
						sendMsgToAll(userName);
						sendMsgToSelf(s,userName);
						sendselfmessage(s,userName);
						user=new account();
						userMap.put(userName, s);
						
					}
				}
				}catch(IOException e) {
					e.printStackTrace();
				}
			}
	}
	//他人获取用户上线通知,并更新用户列表
	public void sendMsgToAll(String userName) throws IOException{
		Iterator<Socket> it=userMap.values().iterator();
		while(it.hasNext()) {
			Socket s=it.next();
			PrintWriter pw=new PrintWriter(s.getOutputStream(), true);
			String msg="msg@#server@#"+userName+"上线了";
			pw.println(msg);
			msg="cmdAdd@#server@#"+userName;
			pw.println(msg);
		}
	}
	//更新自身用户列表
	public void sendMsgToSelf(Socket s,String username) throws IOException{
		PrintWriter pw=new PrintWriter(s.getOutputStream(),true);
		Iterator<String> it=userMap.keySet().iterator();
		while(it.hasNext()) {
			String userName=it.next();
			String msg="cmdAdd@#server@#"+userName;
			pw.println(msg);
		}
		
	}
	public void sendselfmessage(Socket s,String username) throws IOException {
		PrintWriter pw=new PrintWriter(s.getOutputStream(),true);
		user=DB.getmessage(username);
		System.out.println(username+"!!");
		String msg="cmdget@#server@#"+user.toString();
		System.out.println(msg);
		String msgs[]=msg.split("@#");
		String in[]=msgs[2].split("!!");
		System.out.println(in[0]);
		System.out.println(msgs[2]+"server");
		pw.println(msg);
	}
	
	public void sendothersmessage(Socket s) throws IOException {
		PrintWriter pw=new PrintWriter(s.getOutputStream(),true);
		String msg;
		if(user==null) {
			msg="cmdgetothers@#server@#"+"NOTFOUND";
		}else {
			msg="cmdgetothers@#server@#"+user.toString();
		}
		pw.println(msg);
	}
	
	class ClientThread extends Thread{
		private Socket s;
		public ClientThread(Socket s) {
			this.s=s;
		}
		
		@Override
		public void run() {
			
			try {
				Scanner sc = new Scanner(s.getInputStream(),"utf-8");
				while(sc.hasNextLine()) {
					String msg=sc.nextLine();

					String msgs[]=msg.split("@#");
					System.out.println("178"+msgs[0]);
					if(msgs==null||msgs.length!=4) {
						System.out.println("180通讯异常"+msg);
						return;
					}
					if(msgs[0].equals("on")) {
						sendMsgToSb(msgs);
						System.out.println("185"+msg);
						System.out.println("186"+msgs[1]);
					}else if(msgs[0].equals("exit")) {
						area.append(msgs[3]+"下线了"+s+'\n');
						dataModel.removeElement(msgs[3]);
						userMap.remove(msgs[3]);
						sendSbExitMsgToAll(msgs);
					}
					
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//接收处理用户发送信息
		public void sendMsgToSb(String[] msgs) throws IOException {
			//公屏聊天处理
			if(msgs[1].equals("全部")) {
				Iterator<String> it = userMap.keySet().iterator();
				
				while(it.hasNext()){
					String userName=it.next();
					String msg=null;
					if(msgs[3].equals(userName)) {
						msg="msg@#"+"我"+"@#说:"+msgs[2];
					}else {
						msg="msg@#"+msgs[3]+"@#说:"+msgs[2];						
					}
					Socket s=userMap.get(userName);
					PrintWriter pw=new PrintWriter(s.getOutputStream(),true);
					pw.println(msg);
					
				}
			//私聊处理
			}else if(msgs[1].equals("修改信息")) {
				String info[]=msgs[2].split("!!");
				user.setName(msgs[3]);
				user.setAge(info[0]);
				user.setBrithday(info[1]);
				user.setEmail(info[2]);
				user.setSex(info[3]);
				user.setPhone(info[4]);
				user=DB.userupdate(user);
				sendselfmessage(s,user.getName());
			}else if(msgs[1].equals("查找用户")) {
				if(msgs[2].equals("searchname")) {
				user=DB.getmessage(msgs[3]);
				}else {
					int temp_id=Integer.parseInt(msgs[3]);
					user=DB.getmessage(temp_id);
				}
				sendothersmessage(s);
			}else {
				String userName=msgs[1];
				Socket s=userMap.get(userName);
				System.out.println("222siliao:"+userName);
				String msg="msg@#"+msgs[3]+"@#对你说:"+msgs[2];
				System.out.println("224"+msg);
				PrintWriter pw=new PrintWriter(s.getOutputStream(),true);
				pw.println(msg);
				
				Socket s2=userMap.get(msgs[3]);
				PrintWriter pw2=new PrintWriter(s2.getOutputStream(),true);
				String str2 = "msg@#"+"我"+"@#对 "+userName+"说:"+msgs[2];
				pw2.println(str2);
				System.out.println("232"+str2);
			}
		}
		public void sendSbExitMsgToAll(String[] msgs) throws IOException{
			Iterator<String> it=userMap.keySet().iterator();
			while(it.hasNext()){
				String userName=it.next();
				Socket s=userMap.get(userName);
				PrintWriter pw=new PrintWriter(s.getOutputStream(), true);
				String msg="msg@#server@#用户["+msgs[3]+"]退出了";
				pw.println(msg);
				msg="cmdRed@#server@#"+msgs[3];
				pw.println(msg);
			}
		}
	}

}
