/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package BizLogic;

public class cso_session_local_all {
	private String id;
	private String ctId;
	private String sessions;
	private String cso_sec;
	private String failure_ratio;
	private String nso_cso;

	public cso_session_local_all() {
		this.id = null;
		this.ctId = null;
		this.sessions = null;
		this.cso_sec = null;
		this.failure_ratio = null;
		this.nso_cso = null;
	}

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

	public String getCso_sec() {
		return this.cso_sec;
	}

	public void setCso_sec(String cso_sec) {
		this.cso_sec = cso_sec;
	}

	public String getFailure_ratio() {
		return this.failure_ratio;
	}

	public void setFailure_ratio(String failure_ratio) {
		this.failure_ratio = failure_ratio;
	}

	public String getNso_cso() {
		return this.nso_cso;
	}

	public void setNso_cso(String nso_cso) {
		this.nso_cso = nso_cso;
	}
}