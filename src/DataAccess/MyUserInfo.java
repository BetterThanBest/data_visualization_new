/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package DataAccess;

import com.jcraft.jsch.UserInfo;
import java.io.PrintStream;

public class MyUserInfo implements UserInfo {
	private String password;
	private String passphrase;

	public String getPassphrase() {
		System.out.println("MyUserInfo.getPassphrase()");
		return null;
	}

	public String getPassword() {
		System.out.println("MyUserInfo.getPassword()");
		return null;
	}

	public boolean promptPassphrase(String arg0) {
		System.out.println("MyUserInfo.promptPassphrase()");
		System.out.println(arg0);
		return false;
	}

	public boolean promptPassword(String arg0) {
		System.out.println("MyUserInfo.promptPassword()");
		System.out.println(arg0);
		return false;
	}

	public boolean promptYesNo(String arg0) {
		System.out.println("MyUserInfo.promptYesNo()");
		System.out.println(arg0);

		return (arg0.contains("The authenticity of host"));
	}

	public void showMessage(String arg0) {
		System.out.println("MyUserInfo.showMessage()");
	}
}