/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package Controller;

import BizLogic.SQLAssembly;
import DataAccess.DataBase;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ctDataDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;

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

	public static String printMainTbl(ResultSet r) throws SQLException {
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

			if (r.getString("location") != null) {
				Loca1 = "<p><a href=\"" + r.getString("location")
						+ "\" target=\"_blank\">cpu_session_all.tbl</a></p>";
			} else {
				Loca1 = "<p>No cup session uploaded</p>";
			}

			if (r.getString("location2") != null) {
				Loca2 = "<p><a href=\""
						+ r.getString("location2")
						+ "\" target=\"_blank\">cso_session_local_all.tbl</a></p>";
			} else {
				Loca2 = "<p>No CSO session uploaded</p>";
			}

			if (r.getString("location3") != null) {
				Loca3 = "<p><a href=\""
						+ r.getString("location3")
						+ "\" target=\"_blank\">emaplugin_cpu_session_all.tbl</a></p>";
			} else {
				Loca3 = "<p>No plugin cpu uploaded</p>";
			}

			if (r.getString("location4") != null) {
				Loca4 = "<p><a href=\""
						+ r.getString("location4")
						+ "\" target=\"_blank\">emaplugin_mem_session_all.tbl</a></p>";
			} else {
				Loca4 = "<p>No plugin mem uploaded</p>";
			}

			String name = r.getString("tester");

			htmltbl = "<div id=\"Layer1\" align=\"center\">\n<table width=\"80%\" border=\"0\" cellspacing=\"0\" class=\"STYLE2\">\n\t<tr align=\"center\"><td><p><strong>Detailed Data</strong></p></td></tr>\n\t<tr><td>\n\t\t<table width=\"100%\" border=\"0\">\n\t\t\t<tr bgcolor=\"#DCE8F3\">\n\t\t\t\t<td>Hardware Platform </td>\n\t\t\t\t<td>Storage</td>\n\t\t\t\t<td>Resource Nodes </td>\n\t\t\t\t<td>Processing Nodes </td>\n\t\t\t</tr>\n\t\t\t<tr bgcolor=\"#F0F0F0\">\n\t\t\t\t<td>"
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
					+ "\t<tr><td>"
					+ "\t\t<table width=\"100%\" border=\"0\">"
					+ "\t\t\t<tr bgcolor=\"#DCE8F3\">"
					+ "\t\t\t\t<td>Product</td>"
					+ "\t\t\t\t<td>Version</td>"
					+ "\t\t\t\t<td>Traffic Model</td>"
					+ "\t\t\t\t<td>Interface</td>"
					+ "\t\t\t\t<td>NE Link</td>"
					+ "\t\t\t\t<td>Proclog</td>"
					+ "\t\t\t\t<td>Simulator</td>"
					+ "\t\t\t\t<td>Init Capacity </td>"
					+ "\t\t\t\t<td>License</td>"
					+ "\t\t\t\t<td>Deviation</td>"
					+ "\t\t\t</tr>"
					+ "\t\t\t<tr bgcolor=\"#F0F0F0\">"
					+ "\t\t\t\t<td>"
					+ r.getString("product")
					+ "</td>"
					+ "\t\t\t\t<td>"
					+ r.getString("version")
					+ "</td>"
					+ "\t\t\t\t<td>"
					+ r.getString("traffic_model")
					+ "</td>"
					+ "\t\t\t\t<td>"
					+ r.getString("interface")
					+ "</td>"
					+ "\t\t\t\t<td>"
					+ r.getString("ne_link")
					+ "</td>"
					+ "\t\t\t\t<td>"
					+ r.getString("proclog_lv")
					+ "</td>"
					+ "\t\t\t\t<td>"
					+ r.getString("simulator_info")
					+ "</td>"
					+ "\t\t\t\t<td>"
					+ r.getString("init_capacity")
					+ "</td>"
					+ "\t\t\t\t<td>"
					+ r.getString("license")
					+ "</td>"
					+ "\t\t\t\t<td>"
					+ r.getString("deviation")
					+ "</td>"
					+ "\t\t\t</tr>"
					+ "\t\t</table>"
					+ "\t</td></tr>"
					+ "\t<tr><td>"
					+ "\t\t<table width=\"100%\" border=\"0\">"
					+ "\t\t\t<tr bgcolor=\"#DCE8F3\">"
					+ "\t\t\t\t<td>Team</td>"
					+ "\t\t\t\t<td>Tester</td>"
					+ "\t\t\t\t<td>Status</td>"
					+ "\t\t\t\t<td>Historical data </td>"
					+ "\t\t\t\t<td>Date of Execution </td>"
					+ "\t\t\t\t<td>Download</td>"
					+ "\t\t\t</tr>"
					+ "\t\t\t<tr bgcolor=\"#F0F0F0\">"
					+ "\t\t\t\t<td>"
					+ r.getString("software")
					+ "</td>"
					+ "\t\t\t\t<td>"
					+ r.getString("tester").trim()
					+ "</td>"
					+ "\t\t\t\t<td>"
					+ result
					+ "</td>"
					+ "\t\t\t\t<td>"
					+ history
					+ "</td>"
					+ "\t\t\t\t<td>"
					+ r.getString("run_date")
					+ "</td>"
					+ "\t\t\t\t<td>"
					+ Loca1
					+ Loca2
					+ Loca3
					+ Loca4
					+ "</td>"
					+ "\t\t\t</tr>"
					+ "\t\t</table>"
					+ "\t</td></tr>"
					+ "</table>" + "</div>";
		}

		return htmltbl;
	}

	public static String printSub1Tbl(ResultSet r) throws SQLException {
		String htmltbl = null;

		htmltbl = "<table width=\"100%\" border=\"0\" align=\"center\" class=\"STYLE1\">\n\t<tr>\n\t\t<td colspan=\"5\" align=\"center\"><strong>CSO SESSION LOCAL ALL</strong></td>\n\t</tr>\n  <tr bgcolor=\"#DCE8F3\">\n    <td>sessions</td>\n    <td>CSO per Second</td>\n    <td>Failure Ratio</td>\n    <td>NSO/CSO</td>\n  </tr>\n";

		while (r.next()) {
			String tmpfailure = r.getString("failure_ratio");
			if (tmpfailure.contains("%")) {
				tmpfailure = tmpfailure.replace('%', '\0');
			}

			htmltbl = htmltbl + "  <tr bgcolor=\"#F0F0F0\">\n";

			htmltbl = htmltbl + "    <td>" + r.getString("sessions")
					+ "</td>\n" + "    <td>" + r.getString("cso_sec")
					+ "</td>\n" + "    <td>" + tmpfailure + "%" + "</td>\n"
					+ "    <td>" + r.getString("nso_cso") + "</td>\n";

			htmltbl = htmltbl + "  </tr>\n";
		}

		htmltbl = htmltbl + "</table>\n";
		return htmltbl;
	}

	public static String printSub2Tbl(ResultSet r) throws SQLException {
		String htmltbl = null;
		String tempIp = "0.0.0.0";
		int color = 0;
		String tempColor = "#F0F0F0";
		htmltbl = "<table width=\"70%\" border=\"0\" align=\"center\" class=\"STYLE1\">\n\t<tr>\n\t\t<td colspan=\"9\" align=\"center\"><strong>CPU SESSION ALL</strong></td>\n\t</tr>\n  <tr bgcolor=\"#DCE8F3\">\n    <td>sessions</td>\n    <td>Session Port1</td>\n    <td>Session Port2</td>\n    <td>CPU LOAD</td>\n    <td>Free Memory</td>\n    <td>Swap</td>\n    <td>CPU Wait</td>\n    <td>CPU Saturation</td>\n    <td>IP</td>\n  </tr>\n";

		while (r.next()) {
			String tmpcpu_load = r.getString("cpu_load");
			if (tmpcpu_load.contains("%")) {
				tmpcpu_load = tmpcpu_load.replace('%', '\0');
			}

			if (tempIp.compareTo(r.getString("ip")) != 0) {
				tempIp = r.getString("ip");
				++color;
			}

			if (color % 2 == 1) {
				tempColor = "#F0F0F0";
			} else {
				tempColor = "#DCE8F3";
			}

			htmltbl = htmltbl + "  <tr bgcolor=\"" + tempColor + "\">\n";

			htmltbl = htmltbl + "    <td>" + r.getString("sessions")
					+ "</td>\n" + "    <td>" + r.getString("session_port1")
					+ "</td>\n" + "    <td>" + r.getString("session_port2")
					+ "</td>\n" + "    <td>" + tmpcpu_load + "%" + "</td>\n"
					+ "    <td>" + r.getString("free_mem") + "</td>\n"
					+ "    <td>" + r.getString("swap") + "</td>\n" + "    <td>"
					+ r.getString("cpu_wait") + "</td>\n" + "    <td>"
					+ r.getString("cpu_saturation") + "</td>\n" + "    <td>"
					+ r.getString("ip") + "</td>\n";

			htmltbl = htmltbl + "  </tr>\n";
		}
		htmltbl = htmltbl + "</table>\n";
		return htmltbl;
	}

	private static String printTblAll(String tbl1, String tbl2) {
		String htmltbl = null;

		htmltbl = "<table width=\"100%\" border=\"0\" cellspacing=\"0\">\t<tr>\t\t<td valign=\"top\" width=\"40%\" align=\"center\">"
				+ tbl1
				+ "</td>"
				+ "\t\t<td valign=\"top\" width=\"60%\" align=\"center\">"
				+ tbl2 + "</td>" + "\t</tr>" + "</table>";

		return htmltbl;
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		DataBase db = new DataBase();
		SQLAssembly SQLAssem = new SQLAssembly();
		String SQL = null;

		String ctId = request.getParameter("ctId");
		response.setContentType("text/html");
		String pageTitle = "Detailed Test Data";
		PrintWriter out = response.getWriter();

		ResultSet rs = null;
		ResultSet sub1 = null;
		ResultSet sub2 = null;

		String table = null;
		String table1 = null;
		String table2 = null;
		String tblAll = null;

		if ((Integer.parseInt(ctId) >= 0) && (ctId != null)) {
			try {
				rs = SQLAssem.getRecordSet("*", "ctmastertbl", "ctId=" + ctId);

				table = printMainTbl(rs);

				sub1 = SQLAssem.getRecordSet("*", "cso_session_local_all",
						"ctId=" + ctId);

				table1 = printSub1Tbl(sub1);

				sub2 = SQLAssem.getRecordSet("*", "cpu_session_all", "ctId="
						+ ctId);

				table2 = printSub2Tbl(sub2);

				tblAll = printTblAll(table1, table2);
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		out.println(printHeader(pageTitle));

		out.println(table);
		out.println("<p>&nbsp;</p>");
		out.println(tblAll);

		out.println(printFooter());
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}