/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package BizLogic;

public class cpu_session_all_list {
	private int length;
	private cpu_session_all[] CSA;
	private int elementNo;
	private int columLv;

	public int getColumLv() {
		return this.columLv;
	}

	public void setColumLv(int columLv) {
		this.columLv = columLv;
	}

	public int getElementNo() {
		return this.elementNo;
	}

	public void setElementNo(int elementNo) {
		this.elementNo = elementNo;
	}

	public cpu_session_all_list(int length) {
		this.length = length;
		this.CSA = new cpu_session_all[length];
	}

	public void setCSA(int index, cpu_session_all tmpCSA) {
		this.CSA[index] = new cpu_session_all();
		this.CSA[index].setSessions(tmpCSA.getSessions());
		this.CSA[index].setSession_port1(tmpCSA.getSession_port1());
		this.CSA[index].setSession_port2(tmpCSA.getSession_port2());
		this.CSA[index].setCpu_load(tmpCSA.getCpu_load());
		this.CSA[index].setFree_mem(tmpCSA.getFree_mem());
		this.CSA[index].setSwap(tmpCSA.getSwap());
		this.CSA[index].setCpu_wait(tmpCSA.getCpu_wait());
		this.CSA[index].setCpu_saturation(tmpCSA.getCpu_saturation());
		this.CSA[index].setIp(tmpCSA.getIp());
		this.CSA[index].setId(tmpCSA.getId());
		this.elementNo += 1;
	}

	public cpu_session_all getCSA(int index) {
		return this.CSA[index];
	}

	public int getLength() {
		return this.length;
	}

	public void setLength(int length) {
		this.length = length;
	}
}