/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package BizLogic;

import DataAccess.Fileio;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.StringTokenizer;

public class fileAssembly {
	private String[] copyedStrFile;
	private String[] tmpStr;
	private cpu_session_all CSA;
	private cpu_session_all_list CSAList;
	private cso_session_local_all CSLA;
	private cso_session_all_list CSLAList;
	private emaplugin_session_all cpuPlugin;
	private emaplugin_session_list cpuPluginList;
	private emaplugin_session_all memPlugin;
	private emaplugin_session_list memPluginList;
	private Fileio fileAcc;

	public fileAssembly() {
		this.tmpStr = new String[7];
		this.CSA = new cpu_session_all();
		this.CSLA = new cso_session_local_all();
		this.cpuPlugin = new emaplugin_session_all();
		this.fileAcc = new Fileio();
	}

	public void convertPluginSession(String fileName, int mode)
			throws IOException {
		this.copyedStrFile = new String[250];
		this.copyedStrFile = ((String[]) Arrays.copyOf(
				Fileio.readFileByLines(fileName), this.copyedStrFile.length));
		int i = 0;
		int j = 0;
		int id = 0;
		String ip = null;

		String[] tmpPluginList = new String[15];

		if (mode == 0) {
			int length = getComponentLength(this.copyedStrFile);
			this.cpuPluginList = new emaplugin_session_list(length);
		} else {
			int length = getComponentLength(this.copyedStrFile);
			this.memPluginList = new emaplugin_session_list(length);
		}

		boolean pluginload = false;

		while (this.copyedStrFile[i] != null) {
			if (this.copyedStrFile[i].startsWith("#[")) {
				ip = this.copyedStrFile[i].substring(2, 15);
			} else if ((this.copyedStrFile[i].startsWith("#S"))
					&& (!(pluginload))) {
				StringTokenizer st = new StringTokenizer(this.copyedStrFile[i]);
				int pluginNo = st.countTokens() - 1;
				tmpPluginList = new String[pluginNo];

				st.nextToken();
				for (int x = 0; x < pluginNo; ++x) {
					tmpPluginList[x] = st.nextToken();
				}
			} else if ((!(this.copyedStrFile[i].startsWith("#")))
					&& (this.copyedStrFile[i].length() > 2)) {
				int rite = 0;

				StringTokenizer st = new StringTokenizer(this.copyedStrFile[i]);
				String session = st.nextToken();
				int listlength = getLength(tmpPluginList);

				while (rite < listlength) {
					if (mode == 0) {
						this.cpuPlugin = new emaplugin_session_all();
						this.cpuPlugin.setSession(session);
						this.cpuPlugin.setComponent(tmpPluginList[rite]);
						this.cpuPlugin.setRatio(st.nextToken());
						this.cpuPlugin.setIp(ip);
						this.cpuPlugin.setiId(String.valueOf(j));

						this.cpuPluginList.setEmapluginSA(j, this.cpuPlugin);
					} else {
						this.memPlugin = new emaplugin_session_all();
						this.memPlugin.setSession(session);
						this.memPlugin.setComponent(tmpPluginList[rite]);
						this.memPlugin.setRatio(st.nextToken());
						this.memPlugin.setIp(ip);
						this.memPlugin.setiId(String.valueOf(j));

						this.memPluginList.setEmapluginSA(j, this.memPlugin);
					}

					++j;
					++rite;
				}
			}
			++i;
		}
	}

	public void printList(int mode) {
		if (mode == 0) {
			this.cpuPluginList.printList();
		} else
			this.memPluginList.printList();
	}

	private int getComponentLength(String[] tmpBuffer) {
		int totalLength = 0;

		int AxisY = 0;
		int AxisX = 0;

		int i = 0;

		if ((tmpBuffer[2] != null) && (tmpBuffer[2].startsWith("#"))) {
			StringTokenizer st = new StringTokenizer(tmpBuffer[2]);
			AxisY = st.countTokens() - 1;
		}

		while (tmpBuffer[i] != null) {
			if ((!(tmpBuffer[i].startsWith("#")))
					&& (tmpBuffer[i].length() > 2)) {
				++AxisX;
			}
			++i;
		}

		totalLength = AxisX * AxisY;

		return totalLength;
	}

	public void convertCsoSessionAll(String fileName) throws IOException {
		this.copyedStrFile = new String[150];
		this.copyedStrFile = ((String[]) Arrays.copyOf(
				Fileio.readFileByLines(fileName), this.copyedStrFile.length));
		int i = 0;
		int j = 0;
		int id = 0;
		int length = getLength(this.copyedStrFile);
		this.CSLAList = new cso_session_all_list(length);

		while (this.copyedStrFile[i] != null) {
			if ((!(this.copyedStrFile[i].startsWith("#")))
					&& (this.copyedStrFile[i].length() > 2)) {
				StringTokenizer st = new StringTokenizer(this.copyedStrFile[i]);
				int count = st.countTokens();
				if (count > 0) {
					this.CSLA.setSessions(st.nextToken());
					this.CSLA.setCso_sec(st.nextToken());
					this.CSLAList.setColumNo(0);
					if (count > 2) {
						this.CSLA.setFailure_ratio(st.nextToken());
						this.CSLA.setNso_cso(st.nextToken());
						this.CSLAList.setColumNo(1);
					}
					this.CSLA.setId(String.valueOf(id));
					++id;
				}

				if (this.CSLAList.getLength() > 0) {
					this.CSLAList.setCSLA(j, this.CSLA);
					++j;
				}
			}
			++i;
		}
	}

	public void convertCpuSessionAll(String fileName) throws IOException {
		String ip = null;

		this.copyedStrFile = new String[250];
		this.copyedStrFile = ((String[]) Arrays.copyOf(
				Fileio.readFileByLines(fileName), this.copyedStrFile.length));

		int i = 0;
		int j = 0;
		int id = 0;
		int length = getLength(this.copyedStrFile);
		this.CSAList = new cpu_session_all_list(length);

		while (this.copyedStrFile[i] != null) {
			if (this.copyedStrFile[i].startsWith("#[")) {
				ip = this.copyedStrFile[i].substring(2, 15);
			} else if ((!(this.copyedStrFile[i].startsWith("#")))
					&& (this.copyedStrFile[i].length() > 2)) {
				StringTokenizer st = new StringTokenizer(this.copyedStrFile[i]);
				int count = st.countTokens();
				if (count > 0) {
					this.CSA.setSessions(st.nextToken());
					this.CSA.setSession_port1(st.nextToken());
					this.CSA.setSession_port2(st.nextToken());
					this.CSA.setCpu_load(st.nextToken());
					this.CSA.setFree_mem(st.nextToken());
					this.CSA.setSwap(st.nextToken());
					this.CSAList.setColumLv(0);
					if (count > 6) {
						this.CSA.setCpu_wait(st.nextToken());
						this.CSA.setCpu_saturation(st.nextToken());
						this.CSAList.setColumLv(1);
					}
					this.CSA.setIp(ip);
					this.CSA.setId(String.valueOf(id));
					++id;
				}

				if (this.CSAList.getLength() > 0) {
					this.CSAList.setCSA(j, this.CSA);
					++j;
				}
			}
			++i;
		}
	}

	private int getLength(String[] tmp) {
		int length = 0;

		for (String string : tmp) {
			++length;
		}
		return length;
	}

	public void wrtCpuSession2db(String Location, String maId)
			throws IOException, SQLException {
		SQLAssembly database = new SQLAssembly();

		convertCpuSessionAll(Location);

		if (this.CSAList.getElementNo() <= 0)
			return;
		database.wrtCpuSession2Db(this.CSAList, maId);
	}

	public void wrtCosSession2db(String Location, String maId)
			throws IOException, SQLException {
		SQLAssembly database = new SQLAssembly();

		convertCsoSessionAll(Location);

		if (this.CSLAList.getCsoNo() <= 0)
			return;
		database.wrtCsoSession2Db(this.CSLAList, maId);
	}

	public String getUploadAddr() {
		return this.fileAcc.getConfServer(3);
	}

	public String getChartAddr() {
		return this.fileAcc.getConfServer(4);
	}
}