/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package BizLogic;

public class cso_session_all_list {
	private int length;
	private cso_session_local_all[] CSLA;
	private int csoNo;
	private int columNo;

	public cso_session_all_list(int length) {
		this.columNo = -1;
		this.csoNo = 0;
		this.length = length;
		this.CSLA = new cso_session_local_all[length];
	}

	public int getColumNo() {
		return this.columNo;
	}

	public void setColumNo(int columNo) {
		this.columNo = columNo;
	}

	public void setCSLA(int index, cso_session_local_all tmpCSLA) {
		this.CSLA[index] = new cso_session_local_all();
		this.CSLA[index].setSessions(tmpCSLA.getSessions());
		this.CSLA[index].setCso_sec(tmpCSLA.getCso_sec());
		this.CSLA[index].setFailure_ratio(tmpCSLA.getFailure_ratio());
		this.CSLA[index].setNso_cso(tmpCSLA.getNso_cso());
		this.csoNo += 1;
	}

	public int getCsoNo() {
		return this.csoNo;
	}

	public void setCsoNo(int csoNo) {
		this.csoNo = csoNo;
	}

	public cso_session_local_all getCSLA(int index) {
		return this.CSLA[index];
	}

	public int getLength() {
		return this.length;
	}

	public void setLength(int length) {
		this.length = length;
	}
}