/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package Controller;

import BizLogic.SQLAssembly;
import BizLogic.chartAssembly;
import DataAccess.DataBase;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ctDataComparison extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static HttpSession session = null;
	private static String ckbTraffic;
	private static String ckbHW;
	private static String ckbVersion;

	public static String printHeader(String title) {
		String docType = "<!-- Author: jun CAO Mike -->\n<!-- LSV CT/ST DATA Tool  -->\n<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en\" lang=\"en\">\n";

		return docType
				+ "   <head>\n"
				+ "       <link rel=\"stylesheet\" type=\"text/css\" href=\"css/format.css\" />\n"
				+ "       <script type=\"text/javascript\" src=\"/assignment1/assignment1.js\"></script>\n"
				+ "       <title>" + title + "</title>\n" + "   </head>\n"
				+ "   <body>\n" + "       <h1 align=\"center\">" + title
				+ "</h1>";
	}

	public static String printFooter() {
		return "<!-- Start Footer -->\n   </body>\n</html>\n<!-- End Footer -->";
	}

	public static String printSub1Tbl(ResultSet r, String title, int mode)
			throws SQLException {
		String Pass = null;
		String tblHead = null;
		String color = null;
		String operation = null;

		int rowNum = -1;
		try {
			rowNum = r.getRow();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		tblHead = "<table width=\"100%\" border=\"0\" class=\"STYLE1\">\n\t<tr>\n\t\t<td colspan=\"21\" align=\"center\"><strong>"
				+ title
				+ "</strong></td>\n"
				+ "\t</tr>\n"
				+ "  <tr bgcolor=\"#DCE8F3\">\n"
				+ "    <td>Operation</td>\n"
				+ "    <td>Sessions</td>\n"
				+ "    <td>Hightest CSO</td>\n"
				+ "    <td>Traffic Model</td>\n"
				+ "    <td>Interface</td>\n"
				+ "    <td>HW Environment</td>\n"
				+ "    <td>Product</td>\n"
				+ "    <td>Version</td>\n"
				+ "    <td>Team</td>\n"
				+ "    <td>Date</td>\n" + "    <td>Owner</td>\n" + "  </tr>\n";

		if (rowNum < 0) {
			tblHead = tblHead
					+ "  <tr>  <td colspan=\"21\">There is no data retrieved</td> </tr>\n";
		} else {
			do {
				if (mode == 1) {
					operation = "    <td><a href=\"ctDataDetails?ctId="
							+ r.getString("ctId") + "\">Details</a></td>\n";
				} else if (mode == 2) {
					String img = "<img src=\"img/compare.jpg\" width=\"20\" height=\"20\" border=\"0\" title=\"Compare Details\"/>";
					operation = "\t<td bgcolor=\"#FFFFFF\"><a href=\"ctDataComparison?mode=3&ctId="
							+ r.getString("ctId")
							+ "\" target=\"_blank\">"
							+ img + "</a></td>\n";
				}

				tblHead = tblHead + "  <tr bgcolor=\"#F0F0F0\">\n";
				tblHead = tblHead + operation + "    <td>"
						+ r.getString("sessions") + "</td>\n" + "    <td>"
						+ r.getString("cso_sec") + "</td>\n" + "    <td>"
						+ r.getString("traffic_model") + "</td>\n" + "    <td>"
						+ r.getString("interface") + "</td>\n" + "    <td>"
						+ r.getString("tst_machine") + "</td>\n" + "    <td>"
						+ r.getString("product") + "</td>\n" + "    <td>"
						+ r.getString("version") + "</td>\n" + "    <td>"
						+ r.getString("software") + "</td>\n" + "    <td>"
						+ r.getString("run_date") + "</td>\n" + "    <td>"
						+ r.getString("tester") + "</td>\n";
				tblHead = tblHead + "  </tr>\n";
			} while (r.next());
		}

		return tblHead;
	}

	public static String printAnalysisTbl(ResultSet r, int mode)
			throws SQLException {
		String htmltbl = null;
		String ctId = null;
		String traffic = null;
		String hw = null;
		String version = null;

		SQLAssembly SQLAssem = new SQLAssembly();
		ResultSet ansGrid = null;

		int rowNum = -1;
		try {
			rowNum = r.getRow();
			if (rowNum >= 0) {
				r.first();
				ctId = r.getString("ctId");
				traffic = r.getString("traffic_model");
				hw = r.getString("tst_machine");
				version = r.getString("version");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		htmltbl = "<form id=\"Analysis\" name=\"Analysis\" method=\"post\" action=\"ctDataComparison?mode=2&ctId="
				+ ctId
				+ "\">"
				+ "<table width=\"100%\" border=\"0\" align=\"center\" class=\"STYLE1\">"
				+ "<tr bgcolor=\"#DCE8F3\">"
				+ "<td><input type=\"submit\" name=\"Submit\" value=\"Show Same Traffic\" /></td>"
				+ "<td><label><input type=\"checkbox\" name=\"ckbTraffic\" value=\"ckbTraffic\" />All Traffics</label></td>"
				+ "<td><label><input type=\"checkbox\" name=\"ckbHW\" value=\"ckbHW\" />Same HW Scenario</label></td>"
				+ "<td><label><input type=\"checkbox\" name=\"ckbVersion\" value=\"ckbVersion\" />Same Version</label></td>"
				+ "</tr>" + "</table>";

		if (mode == 1) {
			htmltbl = htmltbl
					+ "<table width=\"100%\" border=\"0\" class=\"STYLE1\">\n"
					+ "\t<tr bgcolor=\"#F0F0F0\">\n"
					+ "\t\t<td colspan=\"21\" align=\"center\"><strong>Please pick up your analysis conditions</strong></td>\n"
					+ "\t</tr>\n"
					+ "  <tr bgcolor=\"#DCE8F3\">\n"
					+ "    <td>Operation</td>\n"
					+ "    <td>Sessions</td>\n"
					+ "    <td>Hightest CSO</td>\n"
					+ "    <td>Traffic Model</td>\n"
					+ "    <td>Interface</td>\n"
					+ "    <td>HW Environment</td>\n"
					+ "    <td>Product</td>\n"
					+ "    <td>Version</td>\n"
					+ "    <td>Team</td>\n"
					+ "    <td>Date</td>\n"
					+ "    <td>Owner</td>\n"
					+ "  </tr>\n"
					+ "\t<tr bgcolor=\"#F0F0F0\">\n"
					+ "\t\t<td colspan=\"21\" align=\"center\"><strong>Post your analysis requests to show data up</strong></td>\n"
					+ "\t</tr>\n" + "<table>\n";
		} else if (mode == 2) {
			String condition = null;

			if (ckbTraffic != null) {
				condition = "a.cso_sec in (select MAX(b.cso_sec) from main_cso as b group by b.ctId) and ctId!="
						+ ctId;
			} else {
				condition = "a.cso_sec in (select MAX(b.cso_sec) from main_cso as b group by b.ctId) and traffic_model='"
						+ traffic + "' and ctId!=" + ctId;
			}

			if (ckbHW != null) {
				condition = condition + " and tst_machine='" + hw + "'";
			}

			if (ckbVersion != null) {
				condition = condition + " and version='" + version + "'";
			}

			ansGrid = SQLAssem.getRecordSet("*", "main_cso as a", condition);

			htmltbl = htmltbl + printSub1Tbl(ansGrid, "Data Analysis", mode);
		}

		htmltbl = htmltbl + "</form>";

		return htmltbl;
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		SQLAssembly SQLAssem = new SQLAssembly();
		DataBase db = new DataBase();
		String SQL = null;

		PrintWriter out = response.getWriter();

		String ctId = request.getParameter("ctId");
		int mode = Integer.parseInt(request.getParameter("mode"));

		response.setContentType("text/html");
		String pageTitle = "Test Data Analysis";

		ResultSet sub1 = null;
		ResultSet sub2 = null;
		ResultSet sub3 = null;

		String table1 = null;
		String table2 = null;

		if (mode == 1) {
			if ((Integer.parseInt(ctId) < 0) || (ctId == null))
				return;
			try {
				session = request.getSession(true);
				session.setAttribute("AnalysisId", ctId);

				sub1 = SQLAssem
						.getRecordSet(
								"*",
								"main_cso as a",
								"a.cso_sec in (select MAX(b.cso_sec) from main_cso as b group by b.ctId) and ctId="
										+ ctId);

				table1 = printSub1Tbl(sub1, "CT", 1);

				table2 = printAnalysisTbl(sub1, mode);

				out.println(printHeader(pageTitle));
				out.println(table1);
				out.println(table2);
				out.println(printFooter());
			} catch (SQLException e) {
				e.printStackTrace();
			}

		} else if (mode == 2) {
			if ((Integer.parseInt(ctId) < 0) || (ctId == null))
				return;
			ckbTraffic = request.getParameter("ckbTraffic");
			ckbHW = request.getParameter("ckbHW");
			ckbVersion = request.getParameter("ckbVersion");
			try {
				sub1 = SQLAssem
						.getRecordSet(
								"*",
								"main_cso as a",
								"a.cso_sec in (select MAX(b.cso_sec) from main_cso as b group by b.ctId) and ctId="
										+ ctId);

				table1 = printSub1Tbl(sub1, "", 1);

				table2 = printAnalysisTbl(sub1, mode);

				out.println(printHeader(pageTitle));
				out.println(table1);
				out.println(table2);
				out.println(printFooter());
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			if (mode != 3)
				return;
			String selectedId = (String) session.getAttribute("AnalysisId");
			try {
				sub1 = SQLAssem.getRecordSet("*", "ctmastertbl", "ctId="
						+ selectedId);

				table1 = printMainTbl(sub1, "Main Table");

				sub1 = SQLAssem
						.getRecordSet("*", "ctmastertbl", "ctId=" + ctId);

				table2 = printMainTbl(sub1, "Reference Table");

				String headTbl = print2mainTbl(table1, table2, "", "");

				sub2 = SQLAssem.getRecordSet("*", "cso_session_local_all",
						"ctId=" + selectedId);

				table1 = printSub1Tbl(sub2);

				sub3 = SQLAssem.getRecordSet("*", "cso_session_local_all",
						"ctId=" + ctId);

				table2 = printSub1Tbl(sub3);

				chartAssembly linechart = new chartAssembly();

				String imgMainCso = chartAssembly.GenerateCsoChart(sub2,
						"CSO per second diagram", "Performance Trend",
						"Session", "CSO/Second", "/main.jpg");
				String imgReferCso = chartAssembly.GenerateCsoChart(sub3,
						"CSO per second diagram", "Performance Trend",
						"Session", "CSO/Second", "/refer.jpg");

				String subTbl1 = print2mainTbl(table1, table2, imgMainCso,
						imgReferCso);

				sub2 = SQLAssem.getRecordSet("*", "cpu_session_all", "ctId="
						+ selectedId);

				table1 = printSub2Tbl(sub2);

				sub3 = SQLAssem.getRecordSet("*", "cpu_session_all", "ctId="
						+ ctId);

				table2 = printSub2Tbl(sub3);

				String imgMainCpu = chartAssembly.GenerateCpuChart(sub2,
						"CPU Load diagram", "Session", "CPU Load",
						"/maincpu.jpg");
				String imgReferCpu = chartAssembly.GenerateCpuChart(sub3,
						"CPU Load diagram", "Session", "CPU Load",
						"/refercpu.jpg");

				String subTbl2 = print2mainTbl(table1, table2, imgMainCpu,
						imgReferCpu);

				out.println(printHeader(pageTitle));
				out.println(headTbl);
				out.println(subTbl1);
				out.println(subTbl2);
				out.println(printFooter());
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static String print2mainTbl(String tbl1, String tbl2, String img1,
			String img2) {
		String headTbl = null;
		String pic1 = "";
		String pic2 = "";

		if (img1 != "") {
			pic1 = "<div id=\"Layer2\" align=\"center\"><img src=\"DisplayChart"
					+ img1
					+ "\" width=\"400\" height=\"240\" border=\"0\" title=\"Performance Chart\" align=\"top\" alt=\"Main Chart\" /></div>";
		}
		if (img2 != "") {
			pic2 = "<div id=\"Layer3\" align=\"center\"><img src=\"DisplayChart"
					+ img2
					+ "\" width=\"400\" height=\"240\" border=\"0\" title=\"Performance Chart\" align=\"top\" alt=\"Reference Chart\" /></div>";
		}

		headTbl = "<table width=\"100%\" border=\"0\"><tr><td width=\"50%\" valign=\"top\">"
				+ pic1
				+ tbl1
				+ "</td>"
				+ "<td width=\"50%\" valign=\"top\">"
				+ pic2 + tbl2 + "</td></tr>";

		return headTbl;
	}

	public static String printMainTbl(ResultSet r, String subtitle)
			throws SQLException {
		String htmltbl = null;
		String testType = null;
		String history = null;
		String result = null;
		String noData = "No Data Stored";
		String Loca1 = null;
		String Loca2 = null;
		String Loca3 = null;
		String Loca4 = null;

		while (r.next()) {
			if (Integer.parseInt(r.getString("test_type")) == 0) {
				testType = "Dimension";
			} else {
				testType = "Stability";
			}

			if (Integer.parseInt(r.getString("historical")) == 0) {
				history = "New Data";
			} else {
				history = "Historical Data";
			}

			if (Integer.parseInt(r.getString("result_pass")) == 0) {
				result = "Private CT Data";
			} else if (Integer.parseInt(r.getString("result_pass")) == 1) {
				result = "Publicated CT Data";
			} else if (Integer.parseInt(r.getString("result_pass")) == 2) {
				result = "Benchmark CT Data";
			}

			String name = r.getString("tester");

			htmltbl = "<div id=\"Layer1\" align=\"center\">\n<table width=\"80%\" border=\"0\" cellspacing=\"0\" class=\"STYLE1\">\n\t<tr align=\"center\"><td><p><strong>"
					+ subtitle
					+ "</strong></p></td></tr>\n"
					+ "\t<tr><td>\n"
					+ "\t\t<table width=\"100%\" border=\"0\">\n"
					+ "\t\t\t<tr bgcolor=\"#DCE8F3\">\n"
					+ "\t\t\t\t<td>Hardware Platform </td>\n"
					+ "\t\t\t\t<td>Storage</td>\n"
					+ "\t\t\t\t<td>Resource Nodes </td>\n"
					+ "\t\t\t\t<td>Processing Nodes </td>\n"
					+ "\t\t\t</tr>\n"
					+ "\t\t\t<tr bgcolor=\"#F0F0F0\">\n"
					+ "\t\t\t\t<td>"
					+ r.getString("tst_machine")
					+ "</td>\n"
					+ "\t\t\t\t<td>"
					+ r.getString("tst_storage")
					+ "</td>\n"
					+ "\t\t\t\t<td>"
					+ r.getString("RN")
					+ "</td>\n"
					+ "\t\t\t\t<td>"
					+ r.getString("PN")
					+ "</td>\n"
					+ "\t\t\t</tr>\n"
					+ "\t\t</table>\n"
					+ "\t</td></tr>\n"
					+ "\t<tr><td>\n"
					+ "\t\t<table width=\"100%\" border=\"0\">\n"
					+ "\t\t\t<tr bgcolor=\"#DCE8F3\">\n"
					+ "\t\t\t\t<td>Product</td>\n"
					+ "\t\t\t\t<td>Version</td>\n"
					+ "\t\t\t\t<td>Traffic Model</td>\n"
					+ "\t\t\t\t<td>Interface</td>\n"
					+ "\t\t\t\t<td>NE Link</td>\n"
					+ "\t\t\t\t<td>Proclog</td>\n"
					+ "\t\t\t\t<td>Simulator</td>\n"
					+ "\t\t\t\t<td>Init Capacity </td>\n"
					+ "\t\t\t\t<td>License</td>\n"
					+ "\t\t\t\t<td>Deviation</td>\n"
					+ "\t\t\t</tr>\n"
					+ "\t\t\t<tr bgcolor=\"#F0F0F0\">"
					+ "\t\t\t\t<td>"
					+ r.getString("product")
					+ "</td>\n"
					+ "\t\t\t\t<td>"
					+ r.getString("version")
					+ "</td>\n"
					+ "\t\t\t\t<td>"
					+ r.getString("traffic_model")
					+ "</td>\n"
					+ "\t\t\t\t<td>"
					+ r.getString("interface")
					+ "</td>\n"
					+ "\t\t\t\t<td>"
					+ r.getString("ne_link")
					+ "</td>\n"
					+ "\t\t\t\t<td>"
					+ r.getString("proclog_lv")
					+ "</td>\n"
					+ "\t\t\t\t<td>"
					+ r.getString("simulator_info")
					+ "</td>\n"
					+ "\t\t\t\t<td>"
					+ r.getString("init_capacity")
					+ "</td>\n"
					+ "\t\t\t\t<td>"
					+ r.getString("license")
					+ "</td>\n"
					+ "\t\t\t\t<td>"
					+ r.getString("deviation")
					+ "</td>\n"
					+ "\t\t\t</tr>\n"
					+ "\t\t</table>\n"
					+ "\t</td></tr>\n"
					+ "\t<tr><td>\n"
					+ "\t\t<table width=\"100%\" border=\"0\">\n"
					+ "\t\t\t<tr bgcolor=\"#DCE8F3\">\n"
					+ "\t\t\t\t<td>Team</td>\n"
					+ "\t\t\t\t<td>Tester</td>\n"
					+ "\t\t\t\t<td>Status</td>\n"
					+ "\t\t\t\t<td>Historical data </td>\n"
					+ "\t\t\t\t<td>Date of Execution </td>\n"
					+ "\t\t\t</tr>\n"
					+ "\t\t\t<tr bgcolor=\"#F0F0F0\">\n"
					+ "\t\t\t\t<td>"
					+ r.getString("software")
					+ "</td>\n"
					+ "\t\t\t\t<td>"
					+ r.getString("tester").trim()
					+ "</td>\n"
					+ "\t\t\t\t<td>"
					+ result
					+ "</td>\n"
					+ "\t\t\t\t<td>"
					+ history
					+ "</td>\n"
					+ "\t\t\t\t<td>"
					+ r.getString("run_date")
					+ "</td>\n"
					+ "\t\t\t</tr>\n"
					+ "\t\t</table>\n"
					+ "\t</td></tr>\n"
					+ "</table>\n" + "</div>\n";
		}

		return htmltbl;
	}

	public static String printSub1Tbl(ResultSet r) throws SQLException {
		String htmltbl = null;

		htmltbl = "<table width=\"70%\" border=\"0\" align=\"center\" class=\"STYLE1\">\n\t<tr>\n\t\t<td colspan=\"5\" align=\"center\"><strong>CSO SESSION LOCAL ALL</strong></td>\n\t</tr>\n  <tr bgcolor=\"#DCE8F3\">\n    <td>sessions</td>\n    <td>CSO per Second</td>\n    <td>Failure Ratio</td>\n    <td>NSO/CSO</td>\n  </tr>\n";

		while (r.next()) {
			htmltbl = htmltbl + "  <tr bgcolor=\"#F0F0F0\">\n";

			htmltbl = htmltbl + "    <td>" + r.getString("sessions")
					+ "</td>\n" + "    <td>" + r.getString("cso_sec")
					+ "</td>\n" + "    <td>" + r.getString("failure_ratio")
					+ "</td>\n" + "    <td>" + r.getString("nso_cso")
					+ "</td>\n";

			htmltbl = htmltbl + "  </tr>\n";
		}

		htmltbl = htmltbl + "</table>\n";
		return htmltbl;
	}

	public static String printSub2Tbl(ResultSet r) throws SQLException {
		String htmltbl = null;

		htmltbl = "<table width=\"70%\" border=\"0\" align=\"center\" class=\"STYLE1\">\n\t<tr>\n\t\t<td colspan=\"9\" align=\"center\"><strong>CPU SESSION ALL</strong></td>\n\t</tr>\n  <tr bgcolor=\"#DCE8F3\">\n    <td>sessions</td>\n    <td>Session Port1</td>\n    <td>Session Port2</td>\n    <td>CPU LOAD</td>\n    <td>Free Memory</td>\n    <td>Swap</td>\n    <td>CPU Wait</td>\n    <td>CPU Saturation</td>\n    <td>IP</td>\n  </tr>\n";

		while (r.next()) {
			htmltbl = htmltbl + "  <tr bgcolor=\"#F0F0F0\">\n";

			String tmpcpu_load = r.getString("cpu_load");
			if (tmpcpu_load.contains("%")) {
				tmpcpu_load = tmpcpu_load.replace('%', '\0');
			}

			String tmpip = r.getString("ip");
			if (tmpip.contains("]")) {
				tmpip = tmpip.replace(']', '\0');
			}

			htmltbl = htmltbl + "    <td>" + r.getString("sessions")
					+ "</td>\n" + "    <td>" + r.getString("session_port1")
					+ "</td>\n" + "    <td>" + r.getString("session_port2")
					+ "</td>\n" + "    <td>" + tmpcpu_load + "%" + "</td>\n"
					+ "    <td>" + r.getString("free_mem") + "</td>\n"
					+ "    <td>" + r.getString("swap") + "</td>\n" + "    <td>"
					+ r.getString("cpu_wait") + "</td>\n" + "    <td>"
					+ r.getString("cpu_saturation") + "</td>\n" + "    <td>"
					+ tmpip + "</td>\n";

			htmltbl = htmltbl + "  </tr>\n";
		}
		htmltbl = htmltbl + "</table>\n";
		return htmltbl;
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}