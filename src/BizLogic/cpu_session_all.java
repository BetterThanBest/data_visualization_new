/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package BizLogic;

public class cpu_session_all {
	private String id;
	private String ctId;
	private String sessions;
	private String session_port1;
	private String session_port2;
	private String cpu_load;
	private String free_mem;
	private String swap;
	private String cpu_wait;
	private String cpu_saturation;
	private String ip;

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCtId() {
		return this.ctId;
	}

	public void setCtId(String ctId) {
		this.ctId = ctId;
	}

	public String getSessions() {
		return this.sessions;
	}

	public void setSessions(String sessions) {
		this.sessions = sessions;
	}

	public String getSession_port1() {
		return this.session_port1;
	}

	public void setSession_port1(String session_port1) {
		this.session_port1 = session_port1;
	}

	public String getSession_port2() {
		return this.session_port2;
	}

	public void setSession_port2(String session_port2) {
		this.session_port2 = session_port2;
	}

	public String getCpu_load() {
		return this.cpu_load;
	}

	public void setCpu_load(String cpu_load) {
		this.cpu_load = cpu_load;
	}

	public String getFree_mem() {
		return this.free_mem;
	}

	public void setFree_mem(String free_mem) {
		this.free_mem = free_mem;
	}

	public String getSwap() {
		return this.swap;
	}

	public void setSwap(String swap) {
		this.swap = swap;
	}

	public String getCpu_wait() {
		return this.cpu_wait;
	}

	public void setCpu_wait(String cpu_wait) {
		this.cpu_wait = cpu_wait;
	}

	public String getCpu_saturation() {
		return this.cpu_saturation;
	}

	public void setCpu_saturation(String cpu_saturation) {
		this.cpu_saturation = cpu_saturation;
	}

	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
}