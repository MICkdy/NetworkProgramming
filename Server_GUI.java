package com.socket.chat;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Server_GUI {

	public static void main(String[] args) {
		new ManagerLogin();
	}
}

class ManagerLogin extends JFrame implements ActionListener, KeyListener { 
	
	Server_ChatGUI Server_chat = null;
	JPanel Port_Log = new JPanel();
	JLabel Port_Label = new JLabel("�Է��� ����� ��Ʈ ��ȣ�� �Է��ϼ���.");
	JLabel Port_Warning = new JLabel("(��, ��Ʈ ��ȣ�� 0 ~ 65535����)");
	JTextField Port_Text = new JTextField(20);
	JButton Port_Enter = new JButton("����!");

	public ManagerLogin() {
		setTitle("���� �޴��� â");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		setSize(300, 120);
		setVisible(true);
		setResizable(false);
		Port_Enter.addActionListener(this);
		Port_Text.addKeyListener(this);
		Port_Log.add(Port_Label);
		Port_Log.add(Port_Warning);
		Port_Log.add(Port_Text);
		Port_Log.add(Port_Enter);
		add(Port_Log);
	}

	public void actionPerformed(ActionEvent e) { 
		
		try {
			int Port = Integer.parseInt(Port_Text.getText().trim());
			if (e.getSource() == Port_Enter) {
				Server_chat = new Server_ChatGUI(Port);
				setVisible(false);
			}
		} catch (Exception a) {
			JOptionPane.showMessageDialog(null, "�ùٸ��� ���� �Է��Դϴ�!");
		}
	}

	public void keyPressed(KeyEvent e) { 
		
		try {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				int Port = Integer.parseInt(Port_Text.getText().trim());
				Server_chat = new Server_ChatGUI(Port);
				setVisible(false);
			}
		} catch (Exception a) {
			JOptionPane.showMessageDialog(null, "�ùٸ��� ���� �Է��Դϴ�!");
		}

	}

	public void keyTyped(KeyEvent e) {
	}

	public void keyReleased(KeyEvent e) { 
	}

}

class Server_ChatGUI extends JFrame implements ActionListener, KeyListener {
	// ������ ä��â
	JPanel ServerGUI_Panel = new JPanel();
	JLabel ServerLabel = new JLabel("Main Server");
	JLabel UserLabel = new JLabel("���� ���");
	JTextField Chat = new JTextField(45);
	JButton Enter = new JButton("����");
	TextArea ServerChatList = new TextArea(30, 50);
	TextArea UserList = new TextArea(30, 15);
	Server_Back SB = new Server_Back();

	public Server_ChatGUI(int Port) {
		setTitle("���� ����");
		setVisible(true);
		setLocationRelativeTo(null);
		setSize(750, 520);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		ServerChatList.setEditable(false);
		UserList.setEditable(false);
		Chat.addKeyListener(this);
		Enter.addActionListener(this);

		ServerGUI_Panel.add(ServerLabel);
		ServerGUI_Panel.add(ServerChatList);
		ServerGUI_Panel.add(UserLabel);
		ServerGUI_Panel.add(UserList);
		ServerGUI_Panel.add(Chat);
		ServerGUI_Panel.add(Enter);
		add(ServerGUI_Panel);

		UserList.append("Admin\n"); 
		SB.setGUI(this);
		SB.Start_Server(Port);
		SB.start(); 
	}

	public void actionPerformed(ActionEvent e) { 
		String Message = Chat.getText().trim();
		if (e.getSource() == Enter && Message.length() > 0) {
			AppendMessage("���� : " + Message + "\n");
			SB.Transmitall("���� : " + Message + "\n");
			Chat.setText(null); 
		}
	}

	public void keyPressed(KeyEvent e) { 
		String Message = Chat.getText().trim();
		if (e.getKeyCode() == KeyEvent.VK_ENTER && Message.length() > 0) {
			AppendMessage("���� : " + Message + "\n");
			SB.Transmitall("���� : " + Message + "\n");
			Chat.setText(null); 
		}
	}

	public void AppendMessage(String Message) {
		ServerChatList.append(Message);
	}

	public void AppendUserList(ArrayList NickName) {
		String name;
		for (int i = 0; i < NickName.size(); i++) {
			name = (String) NickName.get(i);
			UserList.append(name + "\n");
		}
	}

	public void keyTyped(KeyEvent e) {
	}

	public void keyReleased(KeyEvent e) {
	}
}