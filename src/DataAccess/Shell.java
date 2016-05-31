/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package DataAccess;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Vector;

public class Shell {
	private String ipAddress;
	private String username;
	private String password;
	public static final int DEFAULT_SSH_PORT = 22;
	private Vector<String> stdout;
	private Session session;
	private JSch jsch;
	private MyUserInfo userInfo;

	public Shell() {
		this.ipAddress = null;
		this.username = null;
		this.password = null;
		this.stdout = new Vector();
	}

	public Session openSession(String ipAddress, String username,
			String password) {
		this.ipAddress = ipAddress;
		this.username = username;
		this.password = password;

		this.jsch = new JSch();
		this.userInfo = new MyUserInfo();
		try {
			this.session = this.jsch.getSession(this.username, this.ipAddress,
					22);
			this.session.setPassword(this.password);
			this.session.setUserInfo(this.userInfo);

			this.session.connect();
		} catch (JSchException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return this.session;
	}

	public int execute(String command) {
		int returnCode = 0;
		try {
			Channel channel = this.session.openChannel("exec");
			((ChannelExec) channel).setCommand(command);

			channel.setInputStream(null);
			BufferedReader input = new BufferedReader(new InputStreamReader(
					channel.getInputStream()));

			channel.connect();
			System.out.println("The remote command is: " + command);
			String line;
			while ((line = input.readLine()) != null) {
				String line1 = null;
				this.stdout.add(line1);
			}
			input.close();

			if (channel.isClosed()) {
				returnCode = channel.getExitStatus();
			}

			channel.disconnect();
		} catch (JSchException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnCode;
	}

	public void sshSftpPut(String src, String dst) throws Exception {
		Channel channel = null;

		if (22 <= 0) {
			this.session = this.jsch.getSession(this.username, this.ipAddress);
		} else {
			this.session = this.jsch.getSession(this.username, this.ipAddress,
					22);
		}

		if (this.session == null) {
			throw new Exception("session is null");
		}

		this.session.setPassword(this.password);

		this.session.setConfig("StrictHostKeyChecking", "no");
		this.session.connect(30000);
		try {
			channel = this.session.openChannel("sftp");
			channel.connect(1000);
			ChannelSftp sftp = (ChannelSftp) channel;

			Vector v = sftp.ls("*.txt");

			for (int i = 0; i < v.size(); ++i) {
				System.out.println(v.get(i));
			}

			sftp.put(src, dst, 0);
			sftp.quit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			channel.disconnect();
		}
	}

	public void sshSftpGet(String src, String dst) throws Exception {
		Channel channel = null;

		if (22 <= 0) {
			this.session = this.jsch.getSession(this.username, this.ipAddress);
		} else {
			this.session = this.jsch.getSession(this.username, this.ipAddress,
					22);
		}

		if (this.session == null) {
			throw new Exception("session is null");
		}

		this.session.setPassword(this.password);

		this.session.setConfig("StrictHostKeyChecking", "no");
		this.session.connect(30000);
		try {
			channel = this.session.openChannel("sftp");
			channel.connect(1000);
			ChannelSftp sftp = (ChannelSftp) channel;

			Vector v = sftp.ls("*.txt");

			for (int i = 0; i < v.size(); ++i) {
				System.out.println(v.get(i));
			}

			sftp.get(src, dst);
			sftp.quit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			channel.disconnect();
		}
	}

	public void closeSession() {
		this.session.disconnect();
	}

	public Vector<String> getStandardOutput() {
		return this.stdout;
	}
}