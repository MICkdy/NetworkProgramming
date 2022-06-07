package com.socket.chat;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Client_GUI {
	public static void main(String[] args) {
		LoginGUI LG = new LoginGUI();
	}
}

class LoginGUI extends JFrame implements ActionListener {
	// ������ �α��� â
	private JPanel Login_GUIPanel = new JPanel();
	private JTextField NickName_Text = new JTextField(20);
	private JTextField Port_Text = new JTextField("####", 20);
	private JTextField IPAddress_Text = new JTextField("###.###.###.###", 20);
	private JLabel NickName_Label = new JLabel("���� �Է�");
	private JLabel Port_Label = new JLabel("��Ʈ �Է�");
	private JLabel IPAddress_Label = new JLabel("�ּ� �Է�");
	private JButton Login_GUI_Button = new JButton("����!");

	public LoginGUI() {
		setTitle("�α��� ȭ��");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setSize(300, 170);
		setResizable(false);
		setVisible(true);
		Login_GUI_Button.setPreferredSize(new Dimension(260, 40));
		Login_GUI_Button.addActionListener(this);
		Login_GUIPanel.add(NickName_Label);
		Login_GUIPanel.add(NickName_Text);
		Login_GUIPanel.add(Port_Label);
		Login_GUIPanel.add(Port_Text);
		Login_GUIPanel.add(IPAddress_Label);
		Login_GUIPanel.add(IPAddress_Text);
		Login_GUIPanel.add(Login_GUI_Button);
		add(Login_GUIPanel);
	}

	public void actionPerformed(ActionEvent e) {
		// �г���, �ּ�, ��Ʈ���� ��ư�� ���� �Է¹޽��ϴ�.
		try {
			if (e.getSource() == Login_GUI_Button) {
				String NickName = NickName_Text.getText().trim();
				String IPAddress = IPAddress_Text.getText().trim();
				int Port = Integer.parseInt(Port_Text.getText().trim());
				new Client_ChatGUI(NickName, IPAddress, Port);
				setVisible(false);
			}
		} catch (Exception a) {
			// ���� �ùٸ��� �ʴ� ���� �ԷµǸ� �˾�â�� ����ݴϴ�.
			JOptionPane.showMessageDialog(null, "�ùٸ��� ���� �Է��Դϴ�!");
		}
	}
}

class Client_ChatGUI extends JFrame implements ActionListener, KeyListener {
	//Ŭ���̾�Ʈ�� ä��â
	String NickName;
	Client_Back CB = new Client_Back();
	JPanel ClientGUIPanel = new JPanel();
	JLabel UserLabel = new JLabel("���� ���");
	JLabel User = new JLabel(NickName);
	JTextField Chat = new JTextField(45);
	JButton Enter = new JButton("����");
	TextArea ChatList = new TextArea(30, 50);
	TextArea UserList = new TextArea(30, 15);

	public Client_ChatGUI(String NickName, String IPAddress, int Port) {
		this.NickName = NickName;

		setTitle("�� â");
		setVisible(true);
		setLocationRelativeTo(null);
		setSize(750, 530);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		ChatList.setEditable(false);
		UserList.setEditable(false);
		Chat.addKeyListener(this);
		Enter.addActionListener(this);

		ClientGUIPanel.add(User);
		ClientGUIPanel.add(ChatList);
		ClientGUIPanel.add(UserLabel);
		ClientGUIPanel.add(UserList);
		ClientGUIPanel.add(Chat);
		ClientGUIPanel.add(Enter);
		add(ClientGUIPanel);
		CB.setGui(this);
		CB.getUserInfo(NickName, IPAddress, Port);
		CB.start(); // ä��â�� ������ ���ÿ� ������ �������ݴϴ�.
	}

	public void actionPerformed(ActionEvent e) { 
		// ���� ��ư�� ������, �Է°��� 1�̻��϶��� ���۵ǵ��� �մϴ�.
		String Message = Chat.getText().trim();
		if (e.getSource() == Enter && Message.length() > 0) {
			CB.Transmit(NickName + " : " + Message + "\n");
			Chat.setText(null);
		}
	}

	public void keyPressed(KeyEvent e) { 
		// Ű���� ����Ű�� ������, �Է°��� 1�̻��϶��� ���۵ǵ��� �մϴ�.
		String Message = Chat.getText().trim();
		if (e.getKeyCode() == KeyEvent.VK_ENTER && Message.length() > 0) {
			CB.Transmit(NickName + " : " + Message + "\n");
			Chat.setText(null);
		}
	}

	public void AppendMessage(String Message) {
		ChatList.append(Message);
	}

	public void AppendUserList(ArrayList NickName) {
		// ��������� ��������Ʈ�� ����ݴϴ�.
		String name;
		UserList.setText(null);
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