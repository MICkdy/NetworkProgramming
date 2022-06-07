package com.socket.chat;
import java.io.*;
import java.net.*;
import java.util.*;

public class Client_Back extends Thread {
	private String NickName, IPAddress;
	private int Port;
	private Socket socket;
	private String Message;
	private DataInputStream in;
	private DataOutputStream out;
	private Client_ChatGUI chatgui;
	ArrayList<String> NickNameList = new ArrayList<String>(); // ��������� �����մϴ�.

	public void getUserInfo(String NickName, String IPAddress, int Port) {
		// Client_GUI�κ��� �г���, ������, ��Ʈ ���� �޾ƿɴϴ�.
		this.NickName = NickName;
		this.IPAddress = IPAddress;
		this.Port = Port;
	}

	public void setGui(Client_ChatGUI chatgui) {
		// �����ߴ� Client_GUI �� ��ü�� ������ ���ɴϴ�.
		this.chatgui = chatgui;
	}

	public void run() {
		// ���� ������ �����մϴ�.
		try {
			socket = new Socket(IPAddress, Port);
			out = new DataOutputStream(socket.getOutputStream());
			in = new DataInputStream(socket.getInputStream());
			out.writeUTF(NickName);
			while (in != null) { 
				// ������ �ĺ��ڸ� �޾� �г��� Ȥ�� �Ϲ� �޼������� ���� ���н�ŵ�ϴ�.
				Message = in.readUTF();
				if (Message.contains("+++�г����ǽ���+++")) { 
					// +++�г����ǽ���+++�̶�� ���ľ �پ����� ��쿣 �г������� �����մϴ�.
					chatgui.UserList.setText(null);
					NickNameList.add(Message.substring(12));
					chatgui.AppendUserList(NickNameList);
				} else if (Message.contains("���� �����ϼ̽��ϴ�.")) {
					// ~~ ���� �����ϼ̽��ϴ�. ��� �ĺ��ڸ� ������ ������ �г��� ����Ʈ �ʱ�ȭ �� ���� �Է½�ŵ�ϴ�.
					NickNameList.clear();
					chatgui.UserList.setText(null);
					chatgui.AppendMessage(Message);
				} else if (Message.contains("���� �����ϼ̽��ϴ�.")) {
					// ~~ ���� �����ϼ̽��ϴ�. ��� �ĺ��ڸ� ������ ������ �г��� ����Ʈ �ʱ�ȭ �� ���� �Է½�ŵ�ϴ�.
					NickNameList.clear();
					chatgui.UserList.setText(null);
					chatgui.AppendMessage(Message);
				} else {
					// �� ��� ���� �ƴ� �ÿ� �Ϲ� �޼����� �����մϴ�.
					chatgui.AppendMessage(Message);
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void Transmit(String Message) {
		// �Է¹��� ���� ������ ����(out) ���ݴϴ�.
		try {
			out.writeUTF(Message);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}