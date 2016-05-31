/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package Controller;

import BizLogic.SQLAssembly;
import BizLogic.fileAssembly;
import BizLogic.masterData;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class writeData extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private String writeMasterData(masterData maData) {
		String maId = null;
		String value = null;

		String loc3 = null;
		String loc4 = null;

		SQLAssembly database = new SQLAssembly();

		if (maData.getLocation3() == null) {
			loc3 = "NULL,";
		} else {
			loc3 = "'" + maData.getLocation3() + "',";
		}

		if (maData.getLocation4() == null) {
			loc4 = "NULL,";
		} else {
			loc4 = "'" + maData.getLocation4() + "',";
		}

		value = maData.getRn() + "," + maData.getPn() + "," + "'"
				+ maData.getStoragetype() + "'," + "'" + maData.getPlatform()
				+ "'," + "'" + maData.getTraffic() + "'," + "'"
				+ maData.getNeLink() + "'," + maData.getProclogLv() + "," + "'"
				+ maData.getSimulatorInfo() + "'," + "'"
				+ maData.getInterFace() + "'," + "'" + maData.getInitCap()
				+ "'," + "'" + maData.getTeamInfo() + "'," + "'"
				+ maData.getDeviation() + "'," + "'" + maData.getTester()
				+ "'," + "'" + maData.getLic() + "'," + "'"
				+ maData.getLocation1() + "'," + "'" + maData.getLocation2()
				+ "'," + loc3 + loc4 + "'" + maData.getSubDir() + "'," + "'"
				+ maData.getAbsUploadDir() + "'," + "0," + "'"
				+ maData.getProduct() + "'," + "'" + maData.getVersion() + "',"
				+ "false," + "false";

		maId = database.callProcMaster("create_new_master", value);

		return maId;
	}

	public void writeCpuSession(masterData maData) throws SQLException,
			IOException {
		fileAssembly fileAss1 = new fileAssembly();

		String Location = maData.getAbsUploadDir() + "/" + maData.getSubDir()
				+ "/cpu_session_all.tbl";

		fileAss1.wrtCpuSession2db(Location, maData.getId());
	}

	public void writeCsoSession(masterData maData) throws IOException,
			SQLException {
		fileAssembly fileAss1 = new fileAssembly();

		String Location = maData.getAbsUploadDir() + "/" + maData.getSubDir()
				+ "/cso_session_local_all.tbl";

		fileAss1.wrtCosSession2db(Location, maData.getId());
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		masterData maData = new masterData();
		String insertId = null;

		String test1 = request.getParameter("loc2");
		String test2 = request.getParameter("subDir");
		String test3 = request.getParameter("trafficModel");

		int loc1 = Integer.parseInt(request.getParameter("loc1"));
		int loc2 = Integer.parseInt(request.getParameter("loc2"));
		int loc3 = Integer.parseInt(request.getParameter("loc3"));
		int loc4 = Integer.parseInt(request.getParameter("loc4"));

		maData.setRn(request.getParameter("RN"));
		maData.setPn(request.getParameter("PN"));
		maData.setStoragetype(request.getParameter("storageType"));
		maData.setPlatform(request.getParameter("platform"));
		maData.setTraffic(request.getParameter("trafficModel"));
		maData.setInterFace(request.getParameter("interface"));
		maData.setNeLink(request.getParameter("neLink"));
		maData.setProclogLv(request.getParameter("proclogLv"));
		maData.setSimulatorInfo(request.getParameter("simInfo"));
		maData.setLic(request.getParameter("license"));
		maData.setProduct(request.getParameter("product"));
		maData.setVersion(request.getParameter("version"));
		maData.setInitCap(request.getParameter("init_cap"));
		maData.setDeviation(request.getParameter("deviation"));
		maData.setTeamInfo(request.getParameter("team"));
		maData.setTester(request.getParameter("tester"));
		maData.setAbsUploadDir(request.getParameter("AbsDir"));
		maData.setSubDir(request.getParameter("subDir"));

		if (loc1 == 1) {
			maData.setLocation1(request.getParameter("location1"));
		}
		if (loc2 == 1) {
			maData.setLocation2(request.getParameter("location2"));
		}
		if (loc3 == 1) {
			maData.setLocation3(request.getParameter("location3"));
		}
		if (loc4 == 1) {
			maData.setLocation4(request.getParameter("location4"));
		}

		if (((loc1 == 1) && (loc2 == 1) && (loc3 == 1) && (loc4 == 1))
				|| ((loc1 == 1) && (loc2 == 1) && (loc3 == 0) && (loc4 == 0))) {
			insertId = writeMasterData(maData);
			maData.setId(insertId);
			try {
				writeCpuSession(maData);
				writeCsoSession(maData);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println(insertId);
		}

		response.sendRedirect("ctDataCtr?mode=2&ctId=" + insertId);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}