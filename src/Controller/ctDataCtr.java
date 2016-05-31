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
import javax.servlet.http.HttpSession;

public class ctDataCtr extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String dir = null;
	private static HttpSession session = null;

	public static String printHeader(String title) {
		String docType = "<!-- Author: jun CAO Mike -->\n<!-- LSV CT/ST DATA Tool  -->\n<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en\" lang=\"en\">\n";

		return docType
				+ "   <head>\n"
				+ "       <link rel=\"stylesheet\" type=\"text/css\" href=\"css/format.css\" />\n"
				+ "       <script type=\"text/javascript\" src=\"js/select.js\">\n"
				+ "       </script>\n" + "       <title>" + title
				+ "</title>\n" + "\t\t<style type=\"text/css\">"
				+ "\t\t.STYLE3" + "\t\t{" + "\t\t\tcolor: #FFFFFF;"
				+ "\t\t\tfont-family: \"Arial Black\";"
				+ "\t\t\tfont-weight: bold;" + "\t\t\tfont-size: 12px;"
				+ "\t\t}" + "\t\t</style>" + "\t\t<style type=\"text/css\">"
				+ "\t\t.STYLE2" + "\t\t{" + "\t\t\tcolor: #FF0000;"
				+ "\t\t\tfont-family: \"Arial Black\";" + "\t\t}"
				+ "\t\t</style>" + "   </head>\n" + "   <body>\n"
				+ "       <h1 align=\"center\">" + title + "</h1>";
	}

	public static String printFooter() {
		return "<!-- Start Footer -->\n   </body>\n</html>\n<!-- End Footer -->";
	}

	public static String printMasterTbl(ResultSet r, String title)
			throws SQLException {
		String result = null;
		String Pass = null;
		String tblHead = null;
		String color = null;
		String maxCso = null;
		String operation = null;

		String imgAnalysis = "<img src=\"img/analyse.jpg\" width=\"20\" height=\"20\" border=\"0\" title=\"Analysis\"/>";
		String imgDetails = "<img src=\"img/details.jpg\" width=\"20\" height=\"20\" border=\"0\" title=\"Detailed Information\"/>";
		String benchmark = "<img src=\"img/benchmark.jpg\" width=\"20\" height=\"20\" border=\"0\" title=\"Release CT as Benchmark\"/>";
		String publish = "<img src=\"img/publish.jpg\" width=\"20\" height=\"20\" border=\"0\" title=\"Release CT as Validated\"/>";
		String delete = "<img src=\"img/del.jpg\" width=\"20\" height=\"20\" border=\"0\" title=\"Remove CT from Database\"/>";
		String downgrade = "<img src=\"img/downgrade.jpg\" width=\"20\" height=\"20\" border=\"0\" title=\"Downgrade it to private data\"/>";
		String edit = "<img src=\"img/edit.jpg\" width=\"20\" height=\"20\" border=\"0\" title=\"Switch to edit mode to update data\"/>";
		String cancel = "<img src=\"img/cancel.jpg\" width=\"20\" height=\"20\" border=\"0\" title=\"Return to browse mode\"/>";
		String save = "<img src=\"img/save.jpg\" width=\"20\" height=\"20\" border=\"0\" title=\"Save the data\"/>";

		int rowNum = -1;
		try {
			rowNum = r.getRow();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		tblHead = "<table width=\"100%\" border=\"1\" cellpadding=\"1\" cellspacing=\"1\" bordercolor=\"#FFFFFF\">\n\t<tr class=\"STYLE2\">\t\t<td colspan=\"13\"><label>Hint: Column 'Highest CSO' indicates the highest CSO for each CT and 'Historical Peak CSO' indicates highest CSO record in the data repository with same traffic model and same hardware environment</label></td>\t</tr>  <tr bgcolor=\"#003399\" class=\"STYLE3\">\n    <td>Operation</td>\n    <td>Sessions</td>\n    <td>Highest CSO</td>\n    <td>Historical Peak CSO</td>\n    <td>Traffic Model</td>\n    <td>Interface</td>\n    <td>HW Environment</td>\n    <td>Product</td>\n    <td>Version</td>\n    <td>Team</td>\n    <td>Date</td>\n    <td>Owner</td>\n    <td>Result</td>\n  </tr>\n";

		if (rowNum < 0) {
			tblHead = tblHead
					+ "  <tr class=\"STYLE2\">  <td colspan=\"21\">There is no data retrieved</td> </tr>\n";
		} else {
			do {
				String deleteLink = " <a href=\"javascript:if(confirm('Are you sure to delete the CT data?'))location='ctDataCtr?mode=5&ctId="
						+ r.getString("ctId") + "'\" >" + delete + "</a>";
				String editLink = " <a href=\"javascript:if(confirm('Do you want to switch to edit mode?'))location='ctDataCtr?mode=7&ctId="
						+ r.getString("ctId") + "'\" >" + edit + "</a>";

				if (Integer.parseInt(r.getString("result_pass")) == 1) {
					Pass = "Published Data";
					color = "#009900";
					operation = "    <td bgcolor=\"#FFFFFF\"><a href=\"ctDataDetails?ctId="
							+ r.getString("ctId")
							+ "\">"
							+ imgDetails
							+ "</a>  <a href=\"ctDataComparison?mode=1&ctId="
							+ r.getString("ctId")
							+ "\">"
							+ imgAnalysis
							+ "</a>  <a href=\"javascript:if(confirm('Do you want to release it as benchmark?'))location='ctDataCtr?mode=4&ctId="
							+ r.getString("ctId")
							+ "'\" >"
							+ benchmark
							+ "</a>" + deleteLink + editLink + "</td>\n";
				} else if (Integer.parseInt(r.getString("result_pass")) == 0) {
					Pass = "Private Data";
					color = "#CC0000";
					operation = "    <td bgcolor=\"#FFFFFF\"><a href=\"ctDataDetails?ctId="
							+ r.getString("ctId")
							+ "\">"
							+ imgDetails
							+ "</a>  <a href=\"ctDataComparison?mode=1&ctId="
							+ r.getString("ctId")
							+ "\">"
							+ imgAnalysis
							+ "</a>  <a href=\"javascript:if(confirm('Do you want to publish this CT?'))location='ctDataCtr?mode=3&ctId="
							+ r.getString("ctId")
							+ "'\" >"
							+ publish
							+ "</a>"
							+ deleteLink + editLink + "</td>\n";
				}
				if (Integer.parseInt(r.getString("result_pass")) == 2) {
					Pass = "Benchmark Data";
					color = "#0099FF";
					operation = "    <td bgcolor=\"#FFFFFF\"><a href=\"ctDataDetails?ctId="
							+ r.getString("ctId")
							+ "\">"
							+ imgDetails
							+ "</a>  <a href=\"ctDataComparison?mode=1&ctId="
							+ r.getString("ctId")
							+ "\">"
							+ imgAnalysis
							+ "</a> <a href=\"javascript:if(confirm('Do you want to down grade benchmark to private?'))location='ctDataCtr?mode=6&ctId="
							+ r.getString("ctId")
							+ "'\" >"
							+ downgrade
							+ "</a></td>\n";
				}

				maxCso = getMaxCso(r.getString("traffic_model"),
						r.getString("tst_machine"), r.getString("ctId"));

				int mode = ((Integer) session.getAttribute("mode")).intValue();

				if (mode == 7) {
					int modifyId = ((Integer) session.getAttribute("ctId"))
							.intValue();
					int ctId = Integer.parseInt(r.getString("ctId"));

					if (ctId == modifyId) {
						tblHead = tblHead
								+ "  <tr bgcolor=\"#990000\" class=\"STYLE1\">\n";

						operation = "    <td bgcolor=\"#FFFFFF\"><input type=\"image\" src=\"img/save.jpg\" width=\"20\" height=\"20\"/> <a href=\"javascript:if(confirm('Return to Browse mode?'))location='ctDataCtr?mode=8'\" >"
								+ cancel + "</a></td>\n";
						tblHead = tblHead
								+ "<form id=\"newCTform\" name=\"newCTform\" method=\"post\" action=\"ctDataCtr?mode=9&ctId="
								+ r.getString("ctId")
								+ "\" onsubmit=\"return submitChkEdit()\">"
								+ operation
								+ "    <td>"
								+ r.getString("sessions")
								+ "</td>\n"
								+ "    <td>"
								+ r.getString("cso_sec")
								+ "</td>\n"
								+ "    <td>"
								+ maxCso
								+ "</td>\n"
								+ "    <td>"
								+ crtTrafficList(r.getString("traffic_model"))
								+ "</td>\n"
								+ "    <td>"
								+ crtInterface(r.getString("interface"))
								+ "</td>\n"
								+ "    <td>"
								+ crtHdwareList()
								+ "</td>\n"
								+ "    <td>"
								+ crtProduct(r.getString("product"))
								+ "</td>\n"
								+ "    <td>"
								+ crtVersion(r.getString("version"))
								+ "</td>\n"
								+ "    <td><input type=\"text\" name=\"team\" value=\""
								+ r.getString("software")
								+ "\" size=\"10\"/></td>\n" + "    <td>"
								+ r.getString("run_date") + "</td>\n"
								+ "    <td>" + r.getString("tester")
								+ "</td>\n" + "    <td bgcolor=\"" + color
								+ "\">" + Pass + "</td>\n" + "</form>";
					} else {
						tblHead = tblHead + "  <tr class=\"STYLE1\">\n";
						tblHead = tblHead
								+ operation
								+ "    <td>"
								+ r.getString("sessions")
								+ "</td>\n"
								+ "    <td>"
								+ r.getString("cso_sec")
								+ getPerformIcon(Double.parseDouble(maxCso),
										Double.parseDouble(r
												.getString("cso_sec")))
								+ "</td>\n" + "    <td>" + maxCso + "</td>\n"
								+ "    <td>" + r.getString("traffic_model")
								+ "</td>\n" + "    <td>"
								+ r.getString("interface") + "</td>\n"
								+ "    <td>" + r.getString("tst_machine")
								+ "</td>\n" + "    <td>"
								+ r.getString("product") + "</td>\n"
								+ "    <td>" + r.getString("version")
								+ "</td>\n" + "    <td>"
								+ r.getString("software") + "</td>\n"
								+ "    <td>" + r.getString("run_date")
								+ "</td>\n" + "    <td>"
								+ r.getString("tester") + "</td>\n"
								+ "    <td bgcolor=\"" + color + "\">" + Pass
								+ "</td>\n";
					}
				} else if (mode == 9) {
					int modifyId = ((Integer) session.getAttribute("ctId"))
							.intValue();
					int ctId = Integer.parseInt(r.getString("ctId"));

					if (ctId == modifyId) {
						tblHead = tblHead
								+ "  <tr bgcolor=\"#6699CC\" class=\"STYLE1\">\n";
					} else {
						tblHead = tblHead + "  <tr class=\"STYLE1\">\n";
					}

					tblHead = tblHead
							+ operation
							+ "    <td>"
							+ r.getString("sessions")
							+ "</td>\n"
							+ "    <td>"
							+ r.getString("cso_sec")
							+ getPerformIcon(Double.parseDouble(maxCso),
									Double.parseDouble(r.getString("cso_sec")))
							+ "</td>\n" + "    <td>" + maxCso + "</td>\n"
							+ "    <td>" + r.getString("traffic_model")
							+ "</td>\n" + "    <td>" + r.getString("interface")
							+ "</td>\n" + "    <td>"
							+ r.getString("tst_machine") + "</td>\n"
							+ "    <td>" + r.getString("product") + "</td>\n"
							+ "    <td>" + r.getString("version") + "</td>\n"
							+ "    <td>" + r.getString("software") + "</td>\n"
							+ "    <td>" + r.getString("run_date") + "</td>\n"
							+ "    <td>" + r.getString("tester") + "</td>\n"
							+ "    <td bgcolor=\"" + color + "\">" + Pass
							+ "</td>\n";
				} else {
					tblHead = tblHead + "  <tr class=\"STYLE1\">\n";
					tblHead = tblHead
							+ operation
							+ "    <td>"
							+ r.getString("sessions")
							+ "</td>\n"
							+ "    <td>"
							+ r.getString("cso_sec")
							+ getPerformIcon(Double.parseDouble(maxCso),
									Double.parseDouble(r.getString("cso_sec")))
							+ "</td>\n" + "    <td>" + maxCso + "</td>\n"
							+ "    <td>" + r.getString("traffic_model")
							+ "</td>\n" + "    <td>" + r.getString("interface")
							+ "</td>\n" + "    <td>"
							+ r.getString("tst_machine") + "</td>\n"
							+ "    <td>" + r.getString("product") + "</td>\n"
							+ "    <td>" + r.getString("version") + "</td>\n"
							+ "    <td>" + r.getString("software") + "</td>\n"
							+ "    <td>" + r.getString("run_date") + "</td>\n"
							+ "    <td>" + r.getString("tester") + "</td>\n"
							+ "    <td bgcolor=\"" + color + "\">" + Pass
							+ "</td>\n";
				}

				tblHead = tblHead + "  </tr>\n";
			} while (r.next());
		}

		return tblHead;
	}

	private static String getPerformIcon(double HistMax, double currentMax) {
		String img = null;

		if (currentMax > HistMax) {
			if ((currentMax - HistMax) / HistMax > 0.5D) {
				img = "<img src=\"img/increaseRed.JPG\" width=\"20\" height=\"20\" border=\"0\" title=\"Increased Dramatically\"/>";
			} else {
				img = "<img src=\"img/increaseOrg.JPG\" width=\"20\" height=\"20\" border=\"0\" title=\"Increased Sharply\"/>";
			}
		} else if (currentMax == HistMax) {
			img = "<img src=\"img/draw.jpg\" width=\"20\" height=\"20\" border=\"0\" title=\"Same performance\"/>";
		} else if ((HistMax - currentMax) / HistMax > 0.3D) {
			img = "<img src=\"img/decreaseGreen.JPG\" width=\"20\" height=\"20\" border=\"0\" title=\"Decreased Dramatically\"/>";
		} else {
			img = "<img src=\"img/decreaseBlue.JPG\" width=\"20\" height=\"20\" border=\"0\" title=\"Decreased Sharply\"/>";
		}

		return img;
	}

	private static String crtVersion(String version) {
		String list = null;
		String[] lstArray = new String[7];
		lstArray[0] = "MA60";
		lstArray[1] = "MA61";
		lstArray[2] = "MA62";
		lstArray[3] = "MA63";
		lstArray[4] = "MA70";
		lstArray[5] = "MA71";
		lstArray[6] = "MA72";

		String[] lstArray1 = new String[8];
		lstArray1[0] = "GA";
		lstArray1[1] = "CP1";
		lstArray1[2] = "CP2";
		lstArray1[3] = "CP3";
		lstArray1[4] = "CP4";
		lstArray1[5] = "CP5";
		lstArray1[6] = "CP6";
		lstArray1[7] = "CP7";

		String tmp1 = version.substring(0, 4);
		String tmp2 = version.substring(4);

		list = "<select name=\"version_1\">\n";
		for (String string : lstArray) {
			if (string.equals(tmp1)) {
				list = list + "<option value=\"" + string
						+ "\" selected=\"selected\">" + string + "</option>\n";
			} else {
				list = list + "<option value=\"" + string + "\">" + string
						+ "</option>\n";
			}
		}
		list = list + "</select>\n";

		list = list + "<select name=\"version_2\">\n";
		for (String string : lstArray1) {
			if (string.equals(tmp2)) {
				list = list + "<option value=\"" + string
						+ "\" selected=\"selected\">" + string + "</option>\n";
			} else {
				list = list + "<option value=\"" + string + "\">" + string
						+ "</option>\n";
			}
		}
		list = list + "</select>\n";

		return list;
	}

	private static String crtProduct(String Product) {
		String list = null;
		String[] lstArray = new String[3];
		lstArray[0] = "PGClassic";
		lstArray[1] = "PGNGN";
		lstArray[2] = "PM";

		list = "<select name=\"product\">\n";
		for (String string : lstArray) {
			if (string.equals(Product)) {
				list = list + "<option value=\"" + string
						+ "\" selected=\"selected\">" + string + "</option>\n";
			} else {
				list = list + "<option value=\"" + string + "\">" + string
						+ "</option>\n";
			}
		}

		list = list + "</select>\n";
		return list;
	}

	private static String crtInterface(String inter) {
		String list = null;
		String[] lstArray = new String[3];
		lstArray[0] = "CAI";
		lstArray[1] = "CAI3G";
		lstArray[2] = "XCAI3G";

		list = "<select name=\"interface\">\n";
		for (String string : lstArray) {
			if (string.equals(inter)) {
				list = list + "<option value=\"" + string
						+ "\" selected=\"selected\">" + string + "</option>\n";
			} else {
				list = list + "<option value=\"" + string + "\">" + string
						+ "</option>\n";
			}
		}

		list = list + "</select>\n";
		return list;
	}

	private static String crtTrafficList(String traffic) {
		String list = null;
		String[] lstArray = new String[29];
		lstArray[0] = "CoreNetwork141";
		lstArray[1] = "HLR141";
		lstArray[2] = "HLR141_CAI3G";
		lstArray[3] = "FAI_FAM";
		lstArray[4] = "Edifact_MA61_CDP21";
		lstArray[5] = "ICS50_FD1";
		lstArray[6] = "ICS50_Layered";
		lstArray[7] = "IMT4_UP_BWR15";
		lstArray[8] = "IMT4_UP_BWR16";
		lstArray[9] = "IMT4_UP_BWR17";
		lstArray[10] = "IMT4_UP_BWR17SP4";
		lstArray[11] = "MiO20";
		lstArray[12] = "MiO30";
		lstArray[13] = "MTAS31";
		lstArray[14] = "PAS";
		lstArray[15] = "RR_BWR15";
		lstArray[16] = "BCS";
		lstArray[17] = "CoreNetwork12B";
		lstArray[18] = "CS";
		lstArray[19] = "CS50_2";
		lstArray[20] = "CS50_3";
		lstArray[21] = "HLR_NPlusONE";
		lstArray[22] = "HLR135_CAI3G";
		lstArray[23] = "ICS50_FD1";
		lstArray[24] = "MMTel30";
		lstArray[25] = "SAPC";
		lstArray[26] = "UDR_Redundancy_CAI";
		lstArray[27] = "UDR_Redundancy_CAI3G";
		lstArray[28] = "XCAI_traffic";

		list = "<select name=\"trafficModel\">\n";
		for (String string : lstArray) {
			if (string.equals(traffic)) {
				list = list + "<option value=\"" + string
						+ "\" selected=\"selected\">" + string + "</option>\n";
			} else {
				list = list + "<option value=\"" + string + "\">" + string
						+ "</option>\n";
			}
		}

		list = list + "</select>\n";
		return list;
	}

	private static String crtHdwareList() {
		String listSet = null;

		listSet = "<select name=\"selMachine\" id=\"select\" onchange=\"selectScenario()\">";
		listSet = listSet
				+ "\t<option value=\"\" selected=\"selected\">--Select HDware--</option>";
		listSet = listSet + "\t<option value=\"SPARC\">SPARC</option>";
		listSet = listSet + "\t<option value=\"X86\">X86</option>";
		listSet = listSet + "\t<option value=\"EBS\">EBS</option>";
		listSet = listSet + "</select>";
		listSet = listSet
				+ "<select name=\"selScenario\" id=\"select2\" onchange=\"selectType()\">";
		listSet = listSet
				+ "\t<option value=\"\" selected=\"selected\">--Select Scenario--</option>";
		listSet = listSet + "</select>";
		listSet = listSet + "<select name=\"selType\" id=\"select3\">";
		listSet = listSet
				+ "\t<option value=\"\" selected=\"selected\">--Select Type--</option>";
		listSet = listSet + "</select>";
		listSet = listSet + "<select name=\"RN\">";
		listSet = listSet
				+ "\t<option value=\"\" selected=\"selected\">--Select RN--</option>";
		listSet = listSet + "</select>";
		listSet = listSet + "<select name=\"PN\">";
		listSet = listSet
				+ "\t<option value=\"\" selected=\"selected\">--Select PN--</option>";
		listSet = listSet + "</select>";
		return listSet;
	}

	public static String getMaxCso(String traffic, String hw, String ctId)
			throws SQLException {
		String result = null;

		SQLAssembly SQLAssem = new SQLAssembly();
		ResultSet rs = null;

		rs = SQLAssem.getRecordSet("Max(cso_sec) as mx", "main_cso",
				"traffic_model = '" + traffic + "' and tst_machine='" + hw
						+ "' and ctId!=" + ctId);
		rs.next();
		result = rs.getString(1);
		if (result != null) {
			return result;
		}

		return "0";
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		DataBase db = new DataBase();
		SQLAssembly SQLAssem = new SQLAssembly();
		String SQL = null;

		String searchType = null;
		String CT_ST = null;

		String RadioGroup1 = null;
		String Type = null;
		String chkType = null;
		ResultSet rs = null;
		String table = null;
		String ctId = null;
		String Mode = null;

		session = request.getSession(true);

		dir = request.getRealPath("/");

		response.setContentType("text/html");
		String pageTitle = "Search Content";
		PrintWriter out = response.getWriter();

		Mode = request.getParameter("mode");

		if (Integer.parseInt(Mode) == 2) {
			ctId = request.getParameter("ctId");
			pageTitle = "New CT Data Updated!";

			String condition = "a.cso_sec in (select MAX(b.cso_sec) from main_cso as b group by b.ctId) and ctId="
					+ ctId;
			session.setAttribute("condition", condition);

			session.setAttribute("mode", Integer.valueOf(2));
			try {
				rs = SQLAssem.getRecordSet("*", "main_cso as a",
						(String) session.getAttribute("condition"));

				table = printMasterTbl(rs, pageTitle);
			} catch (SQLException e) {
				e.printStackTrace();
			}

		} else if (Integer.parseInt(Mode) == 1) {
			RadioGroup1 = request.getParameter("RadioGroup1");
			chkType = request.getParameter("chkType");
			String file = request.getParameter("file");
			Type = request.getParameter("Type");

			session.setAttribute("mode", Integer.valueOf(1));

			if (Integer.parseInt(RadioGroup1) == 1) {
				String ckbTraffic = request.getParameter("chbtraffic");
				String ckbHd = request.getParameter("chbhdware");
				String ckbTeam = request.getParameter("chbTeam");
				String ckbTester = request.getParameter("chbTester");
				String ckbProd = request.getParameter("chbProduct");
				String ckbVersion = request.getParameter("chbVersion");
				String ckbInterface = request.getParameter("chbInterface");

				String condition = "a.cso_sec in (select MAX(b.cso_sec) from main_cso as b group by b.ctId)";

				if ((ckbTraffic != null)
						&& (request.getParameter("TrafficModule") != null)) {
					String traffic = request.getParameter("TrafficModule")
							.trim();
					condition = condition + " and traffic_model='" + traffic
							+ "'";
				}

				if ((ckbHd != null)
						&& (request.getParameter("Platform") != null)) {
					String HDware = request.getParameter("Platform").trim();
					condition = condition + " and tst_machine like '%" + HDware
							+ "%'";
				}

				if ((ckbTeam != null) && (request.getParameter("team") != null)) {
					String team = request.getParameter("team").trim();
					condition = condition + " and software='" + team + "'";
				}

				if ((ckbTester != null)
						&& (request.getParameter("chbTester") != null)) {
					String tester = request.getParameter("Tester").trim();
					condition = condition + " and tester='" + tester + "'";
				}

				if ((ckbProd != null)
						&& (request.getParameter("chbProduct") != null)) {
					String product = request.getParameter("chbProduct").trim();
					condition = condition + " and product='" + product + "'";
				}

				if ((ckbVersion != null)
						&& (request.getParameter("chbVersion") != null)) {
					String version = request.getParameter("chbVersion").trim();
					condition = condition + " and version='" + version + "'";
				}

				if ((ckbInterface != null)
						&& (request.getParameter("Interface") != null)) {
					String inter = request.getParameter("Interface").trim();
					condition = condition + " and interface='" + inter + "'";
				}

				session.setAttribute("condition", condition);
				try {
					rs = SQLAssem.getRecordSet("*", "main_cso as a",
							(String) session.getAttribute("condition"));

					table = printMasterTbl(rs, pageTitle);
				} catch (SQLException e) {
					e.printStackTrace();
				}

			} else if (Integer.parseInt(RadioGroup1) == 2) {
				session.setAttribute("condition",
						"a.cso_sec in (select MAX(b.cso_sec) from main_cso as b group by b.ctId)");
				try {
					rs = SQLAssem.getRecordSet("*", "main_cso as a",
							(String) session.getAttribute("condition"));

					table = printMasterTbl(rs, pageTitle);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} else if (Integer.parseInt(Mode) == 3) {
			ctId = request.getParameter("ctId");
			pageTitle = "CT Data Status changed to Released";

			session.setAttribute("mode", Integer.valueOf(3));
			try {
				int update = 0;

				update = SQLAssem.setUpdate("ctmastertbl", "result_pass=1",
						"ctId=" + ctId);

				rs = SQLAssem.getRecordSet("*", "main_cso as a",
						(String) session.getAttribute("condition"));

				table = printMasterTbl(rs, pageTitle);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if (Integer.parseInt(Mode) == 4) {
			ctId = request.getParameter("ctId");
			pageTitle = "CT Data Status changed as benchmark";

			session.setAttribute("mode", Integer.valueOf(4));
			try {
				int update = 0;

				update = SQLAssem.setUpdate("ctmastertbl", "result_pass=2",
						"ctId=" + ctId);

				rs = SQLAssem.getRecordSet("*", "main_cso as a",
						(String) session.getAttribute("condition"));

				table = printMasterTbl(rs, pageTitle);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if (Integer.parseInt(Mode) == 5) {
			ctId = request.getParameter("ctId");
			pageTitle = "CT Data Deleted!";

			session.setAttribute("mode", Integer.valueOf(5));
			try {
				int update = 0;

				update = SQLAssem.setUpdate("ctmastertbl", "result_pass=-1",
						"ctId=" + ctId);

				rs = SQLAssem.getRecordSet("*", "main_cso as a",
						(String) session.getAttribute("condition"));

				table = printMasterTbl(rs, pageTitle);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if (Integer.parseInt(Mode) == 6) {
			ctId = request.getParameter("ctId");
			pageTitle = "CT Data down grade to private!";

			session.setAttribute("mode", Integer.valueOf(5));
			try {
				int update = 0;

				update = SQLAssem.setUpdate("ctmastertbl", "result_pass=0",
						"ctId=" + ctId);

				rs = SQLAssem.getRecordSet("*", "main_cso as a",
						(String) session.getAttribute("condition"));

				table = printMasterTbl(rs, pageTitle);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if (Integer.parseInt(Mode) == 7) {
			ctId = request.getParameter("ctId");
			pageTitle = "Using edit mode to update CT data";

			session.setAttribute("mode", Integer.valueOf(7));
			session.setAttribute("ctId",
					Integer.valueOf(Integer.parseInt(ctId)));
			try {
				rs = SQLAssem.getRecordSet("*", "main_cso as a",
						(String) session.getAttribute("condition"));

				table = printMasterTbl(rs, pageTitle);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if (Integer.parseInt(Mode) == 8) {
			pageTitle = "Cancelled Operations from Edit Mode";
			session.setAttribute("mode", Integer.valueOf(8));
			try {
				rs = SQLAssem.getRecordSet("*", "main_cso as a",
						(String) session.getAttribute("condition"));

				table = printMasterTbl(rs, pageTitle);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if (Integer.parseInt(Mode) == 9) {
			pageTitle = "Updated Data into Repository";
			session.setAttribute("mode", Integer.valueOf(9));

			ctId = request.getParameter("ctId");
			String hdware = request.getParameter("selMachine");
			String scenario = request.getParameter("selScenario");
			String type = request.getParameter("selType");
			String platform = hdware + " " + scenario + " " + type;
			String rn = request.getParameter("RN");
			String pn = request.getParameter("PN");
			String traffic = request.getParameter("trafficModel");
			String inter = request.getParameter("interface");
			String product = request.getParameter("product");
			String version1 = request.getParameter("version_1");
			String version2 = request.getParameter("version_2");
			String team = request.getParameter("team");
			version1 = version1 + version2;
			try {
				int update = 0;

				String updateStr = "tst_machine='" + platform + "',RN=" + rn
						+ ",PN=" + pn + ",traffic_model='" + traffic
						+ "',interface='" + inter + "',product='" + product
						+ "',version='" + version1 + "',software='" + team
						+ "'";

				update = SQLAssem.setUpdate("ctmastertbl", updateStr, "ctId="
						+ ctId);

				rs = SQLAssem.getRecordSet("*", "main_cso as a",
						(String) session.getAttribute("condition"));

				table = printMasterTbl(rs, pageTitle);
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		out.println(printHeader(pageTitle));
		out.println(table);
		out.println(printFooter());
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}