/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package BizLogic;

import java.io.PrintStream;

public class emaplugin_session_list {
	private emaplugin_session_all[] emapluginSA;
	private int length;

	public emaplugin_session_list(int length) {
		this.length = length;
		this.emapluginSA = new emaplugin_session_all[length];
	}

	public int getLength() {
		return this.length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public void setEmapluginSA(int index, emaplugin_session_all tmpPlugin) {
		this.emapluginSA[index] = new emaplugin_session_all();
		this.emapluginSA[index].setiId(tmpPlugin.getiId());
		this.emapluginSA[index].setSession(tmpPlugin.getSession());
		this.emapluginSA[index].setComponent(tmpPlugin.getComponent());
		this.emapluginSA[index].setRatio(tmpPlugin.getRatio());
		this.emapluginSA[index].setIp(tmpPlugin.getIp());
	}

	public void printList() {
		int i = 0;
		while (i < this.length) {
			System.out.println(this.emapluginSA[i].getiId() + "|"
					+ this.emapluginSA[i].getSession() + "|"
					+ this.emapluginSA[i].getComponent() + "|"
					+ this.emapluginSA[i].getRatio() + "|"
					+ this.emapluginSA[i].getIp());
			++i;
		}
	}
}