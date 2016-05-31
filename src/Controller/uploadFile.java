/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package Controller;

import BizLogic.fileAssembly;
import BizLogic.masterData;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class uploadFile extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String uploadPath = null;
	private String tempPath = null;

	public void init() throws ServletException {
		fileAssembly FileAss = new fileAssembly();

		this.uploadPath = FileAss.getUploadAddr();

		if (!(new File(this.uploadPath).isDirectory()))
			new File(this.uploadPath).mkdirs();
	}

	private String getCurrentTime() {
		StringBuffer send = new StringBuffer(10);
		Calendar sendTime = Calendar.getInstance();
		int second = sendTime.get(13);
		int minute = sendTime.get(12);
		int hour = sendTime.get(11);
		int day = sendTime.get(5);
		int month = sendTime.get(2) + 1;
		int year = sendTime.get(1);
		if (year >= 2000)
			year -= 2000;
		else
			year -= 1900;
		send.append(getFormatTime(year, 2));
		send.append(getFormatTime(month, 2));
		send.append(getFormatTime(day, 2));
		send.append(getFormatTime(hour, 2));
		send.append(getFormatTime(minute, 2));
		send.append(getFormatTime(second, 2));
		return send.toString();
	}

	private String getFormatTime(int time, int format) {
		StringBuffer numm = new StringBuffer();
		int length = String.valueOf(time).length();

		if (format < length) {
			return null;
		}
		for (int i = 0; i < format - length; ++i) {
			numm.append("0");
		}
		numm.append(time);
		return numm.toString().trim();
	}

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

	public static String printMain(masterData tmpMaData) {
		String htmltbl = null;
		String url = "writeData";
		String printLoc1 = null;
		String printLoc2 = null;
		String printLoc3 = null;
		String printLoc4 = null;
		String button = null;
		//boolean loc1Chk;
		boolean loc1Chk;
		if (tmpMaData.getLocation1() != null) {
			printLoc1 = "\t\t\t<td width=\"69%\">Upload Succeed Location:<input type=\"text\" name=\"location1\" value=\""
					+ tmpMaData.getLocation1()
					+ "\" size=\"30\" readOnly=\"true\" /> <a href=\""
					+ tmpMaData.getLocation1()
					+ "\" target=\"_blank\">Download</a></td>\n";
			url = url + "?loc1=1";
			loc1Chk = true;
		} else {
			printLoc1 = "\t\t\t<td><input type=\"text\" name=\"location1\" value=\"There was no file detected\" size=\"30\" readOnly=\"true\" /></td>\n";
			url = url + "?loc1=0";
			loc1Chk = false;
		}
		//boolean loc2Chk;
		boolean loc2Chk;
		if (tmpMaData.getLocation2() != null) {
			printLoc2 = "\t\t\t<td width=\"69%\">Upload Succeed Location:<input type=\"text\" name=\"location2\" value=\""
					+ tmpMaData.getLocation2()
					+ "\" size=\"30\" readOnly=\"true\" /> <a href=\""
					+ tmpMaData.getLocation2()
					+ "\" target=\"_blank\">Download</a></td>\n";
			url = url + "&loc2=1";
			loc2Chk = true;
		} else {
			printLoc2 = "\t\t\t<td><input type=\"text\" name=\"location2\" value=\"There was no file detected\" size=\"30\" readOnly=\"true\" /></td>\n";
			url = url + "&loc2=0";
			loc2Chk = false;
		}
//		boolean loc3Chk;
		boolean loc3Chk;
		if (tmpMaData.getLocation3() != null) {
			printLoc3 = "\t\t\t<td width=\"69%\">Upload Succeed Location:<input type=\"text\" name=\"location3\" value=\""
					+ tmpMaData.getLocation3()
					+ "\" size=\"30\" readOnly=\"true\" /> <a href=\""
					+ tmpMaData.getLocation3()
					+ "\" target=\"_blank\">Download</a></td>\n";
			url = url + "&loc3=1";
			loc3Chk = true;
		} else {
			printLoc3 = "\t\t\t<td><input type=\"text\" name=\"location3\" value=\"There was no file detected\" size=\"30\" readOnly=\"true\" /></td>\n";
			url = url + "&loc3=0";
			loc3Chk = false;
		}
//		boolean loc4Chk;
		boolean loc4Chk;
		if (tmpMaData.getLocation4() != null) {
			printLoc4 = "\t\t\t<td width=\"69%\">Upload Succeed Location:<input type=\"text\" name=\"location4\" value=\""
					+ tmpMaData.getLocation4()
					+ "\" size=\"30\" readOnly=\"true\" /> <a href=\""
					+ tmpMaData.getLocation4()
					+ "\" target=\"_blank\">Download</a></td>\n";
			url = url + "&loc4=1";
			loc4Chk = true;
		} else {
			printLoc4 = "\t\t\t<td><input type=\"text\" name=\"location4\" value=\"There was no file detected\" size=\"30\" readOnly=\"true\" /></td>\n";
			url = url + "&loc4=0";
			loc4Chk = false;
		}

		if (tmpMaData.getSubDir() != null) {
			url = url + "&subDir=" + tmpMaData.getSubDir();
		} else {
			url = url + "&subDir=0";
		}

		if ((loc1Chk) && (loc2Chk) && (loc3Chk) && (loc4Chk)) {
			button = "\t\t\t<td><input type=\"submit\" name=\"Submit\" value=\"Confirm\" />\n";
		} else if ((loc1Chk) && (loc2Chk) && (!(loc3Chk)) && (!(loc4Chk))) {
			button = "\t\t\t<td><input type=\"submit\" name=\"Submit\" value=\"Confirm\" />\n";
		} else {
			button = "\t\t\t<td><input type=\"submit\" name=\"Submit\" value=\"Confirm\" disabled=\"disabled\"/>At least upload 'cpu_session and cso_session' \n";
		}

		htmltbl = "<form id=\"writeData\" name=\"writeData\" method=\"post\" action=\""
				+ url
				+ "\">\n"
				+ "<table width=\"100%\" border=\"0\" class=\"STYLE2\">\n"
				+ "<tr><td>&nbsp;</td></tr>\n"
				+ "<tr>\n"
				+ "\t<td>\n"
				+ "\t\t<table width=\"60%\" border=\"0\" align=\"center\">\n"
				+ "\t\t<tr bgcolor=\"#CCCCCC\"><td colspan=\"4\"><label>Hardware Settings:</label></td></tr>\n"
				+ "\t\t<tr>\n"
				+ "\t\t\t<td><label>Resource Node(RN)</label></td>\n"
				+ "\t\t\t<td><input type=\"text\" name=\"RN\" value=\""
				+ tmpMaData.getRn()
				+ "\" readOnly=\"true\" /></td>\n"
				+ "\t\t\t<td><label>Processing Node(PN)</label></td>\n"
				+ "\t\t\t<td><input type=\"text\" name=\"PN\" value=\""
				+ tmpMaData.getPn()
				+ "\" readOnly=\"true\" /></td>\n"
				+ "\t\t</tr>\n"
				+ "\t\t<tr>\n"
				+ "\t\t\t<td><label>Storage Type</label></td>\n"
				+ "\t\t\t<td><input type=\"text\" name=\"storageType\" value=\""
				+ tmpMaData.getStoragetype()
				+ "\" readOnly=\"true\" /></td>\n"
				+ "\t\t\t<td><label>Platform</label></td>\n"
				+ "\t\t\t<td><input type=\"text\" name=\"platform\" value=\""
				+ tmpMaData.getPlatform()
				+ "\" readOnly=\"true\" /></td>\n"
				+ "\t\t</tr>\n"
				+ "\t\t</table>\n"
				+ "\t</td>\n"
				+ "</tr>\n"
				+ "<tr>\n"
				+ "\t<td>\n"
				+ "\t\t<table width=\"60%\" border=\"0\" align=\"center\">\n"
				+ "\t\t<tr bgcolor=\"#CCCCCC\"><td colspan=\"4\"><label>Software Settings</label></td></tr>\n"
				+ "\t\t<tr>\n"
				+ "\t\t\t<td><label>Traffic Model</label></td>\n"
				+ "\t\t\t<td><input type=\"text\" name=\"trafficModel\" value=\""
				+ tmpMaData.getTraffic()
				+ "\" readOnly=\"true\"/></td>\n"
				+ "\t\t\t<td><label>Interface</label></td>\n"
				+ "\t\t\t<td><input type=\"text\" name=\"interface\" value=\""
				+ tmpMaData.getInterFace()
				+ "\" readOnly=\"true\" /></td>\n"
				+ "\t\t<tr>\n"
				+ "\t\t<tr>\n"
				+ "\t\t\t<td><label>NE Link</label></td>\n"
				+ "\t\t\t<td><input type=\"text\" name=\"neLink\" value=\""
				+ tmpMaData.getNeLink()
				+ "\" readOnly=\"true\" /></td>\n"
				+ "\t\t\t<td><label>Proclog Level</label></td>\n"
				+ "\t\t\t<td><input type=\"text\" name=\"proclogLv\" value=\""
				+ tmpMaData.getProclogLv()
				+ "\" readOnly=\"true\" /></td>\n"
				+ "\t\t</tr>\n"
				+ "\t\t<tr>\n"
				+ "\t\t\t<td><label>Simulator info</label></td>\n"
				+ "\t\t\t<td><input type=\"text\" name=\"simInfo\" value=\""
				+ tmpMaData.getSimulatorInfo()
				+ "\" readOnly=\"true\" /></td>\n"
				+ "\t\t\t<td><label>License</label></td>\n"
				+ "\t\t\t<td><input type=\"text\" name=\"license\" value=\""
				+ tmpMaData.getLic()
				+ "\" readOnly=\"true\" /></td>\n"
				+ "\t\t</tr>\n"
				+ "\t\t<tr>\n"
				+ "\t\t\t<td><label>Product</label></td>\n"
				+ "\t\t\t<td><input type=\"text\" name=\"product\" value=\""
				+ tmpMaData.getProduct()
				+ "\" readOnly=\"true\" /></td>\n"
				+ "\t\t\t<td><label>Version</label></td>\n"
				+ "\t\t\t<td><input type=\"text\" name=\"version\" value=\""
				+ tmpMaData.getVersion()
				+ "\" readOnly=\"true\" /></td>\n"
				+ "\t\t</tr>\n"
				+ "\t\t<tr>\n"
				+ "\t\t\t<td><label>Initial Capacity</label></td>\n"
				+ "\t\t\t<td><input type=\"text\" name=\"init_cap\" value=\""
				+ tmpMaData.getInitCap()
				+ "\" readOnly=\"true\" /></td>\n"
				+ "\t\t\t<td><label>Deviation</label></td>\n"
				+ "\t\t\t<td><input type=\"text\" name=\"deviation\" value=\""
				+ tmpMaData.getDeviation()
				+ "\" readOnly=\"true\" /></td>\n"
				+ "\t\t</tr>\n"
				+ "\t\t</table>\n"
				+ "\t</td>\n"
				+ "</tr>\n"
				+ "<tr>\n"
				+ "\t<td>\n"
				+ "\t\t<table width=\"60%\" border=\"0\" align=\"center\">\n"
				+ "\t\t<tr bgcolor=\"#CCCCCC\"><td colspan=\"4\"><label>Execution Information</label></td></tr>\n"
				+ "\t\t<tr>\n"
				+ "\t\t\t<td><label>Team Information</label></td>\n"
				+ "\t\t\t<td><input type=\"text\" name=\"team\" value=\""
				+ tmpMaData.getTeamInfo()
				+ "\" readOnly=\"true\" /></td>\n"
				+ "\t\t\t<td><label>Executor</label></td>\n"
				+ "\t\t\t<td><input type=\"text\" name=\"tester\" value=\""
				+ tmpMaData.getTester()
				+ "\" readOnly=\"true\" /></td>\n"
				+ "\t\t</tr>\n"
				+ "\t\t</table>\n"
				+ "\t</td>\n"
				+ "</tr>\n"
				+ "<tr>\n"
				+ "\t<td>\n"
				+ "\t\t<table width=\"60%\" border=\"0\" align=\"center\">\n"
				+ "\t\t<tr bgcolor=\"#CCCCCC\"><td colspan=\"2\"><label>File Record Uploads</label> <input type=\"hidden\" name=\"AbsDir\" value=\""
				+ tmpMaData.getAbsUploadDir()
				+ "\" readOnly=\"true\" /> </td></tr>\n"
				+ "\t\t<tr>\n"
				+ "\t\t\t<td width=\"31%\"><label>File location 1</label></td>\n"
				+ printLoc1
				+ "\t\t</tr>\n"
				+ "\t\t<tr>\n"
				+ "\t\t\t<td width=\"31%\"><label>File location 2</label></td>\n"
				+ printLoc2
				+ "\t\t</tr>\n"
				+ "\t\t<tr>\n"
				+ "\t\t\t<td width=\"31%\"><label>File location 3</label></td>\n"
				+ printLoc3
				+ "\t\t</tr>\n"
				+ "\t\t<tr>\n"
				+ "\t\t\t<td width=\"31%\"><label>File location 4</label></td>\n"
				+ printLoc4
				+ "\t\t</tr>\n"
				+ "\t\t</table>\n"
				+ "\t</td>\n"
				+ "</tr>\n"
				+ "<tr>\n"
				+ "\t<td>\n"
				+ "\t\t<table width=\"60%\" border=\"0\" align=\"center\">\n"
				+ "\t\t<tr>\n"
				+ button
				+ "\t\t\t\t<a href = \"newCTdata.jsp\">Cancel</a></td>\n"
				+ "\t\t\t<td>&nbsp;</td>\n"
				+ "\t\t</tr>\n"
				+ "\t\t</table>\n"
				+ "\t</td>\n" + "</tr>\n" + "</table>\n" + "</form>\n";

		return htmltbl;
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String version = null;
		String platform = null;

		masterData maData = new masterData();

		String dir = request.getRealPath("/");

		init();

		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setRepository(new File(this.uploadPath));

		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setSizeMax(4194304L);

		List items = new ArrayList();

		String pageTitle = "New CT Creation";
		try {
			items = upload.parseRequest(request);
		} catch (FileUploadException e1) {
			System.out.println("Error about upload files:" + e1.getMessage());
			pageTitle = "Error about upload files:" + e1.getMessage();
		}

		Iterator it = items.iterator();

		while (it.hasNext()) {
			FileItem fileItem = (FileItem) it.next();

			if (fileItem.isFormField()) {
				System.out.println(fileItem.getFieldName() + "-"
						+ fileItem.getString());
				String fieldName = fileItem.getFieldName();
				String tmpValue = fileItem.getString();

				if (tmpValue.length() == 0) {
					tmpValue = "N/A";
				}

				if (fieldName.equals("RN")) {
					maData.setRn(tmpValue);
				} else if (fieldName.equals("PN")) {
					maData.setPn(tmpValue);
				} else if (fieldName.equals("storageType")) {
					maData.setStoragetype(tmpValue);
				} else if (fieldName.equals("trafficModel")) {
					maData.setTraffic(tmpValue);
				} else if (fieldName.equals("interface")) {
					maData.setInterFace(tmpValue);
				} else if (fieldName.equals("neLink")) {
					maData.setNeLink(tmpValue);
				} else if (fieldName.equals("proclogLv")) {
					maData.setProclogLv(tmpValue);
				} else if (fieldName.equals("simInfo")) {
					maData.setSimulatorInfo(tmpValue);
				} else if (fieldName.equals("license")) {
					maData.setLic(tmpValue);
				} else if (fieldName.equals("product")) {
					maData.setProduct(tmpValue);
				} else if (fieldName.equals("version_1")) {
					version = tmpValue;
				} else if (fieldName.equals("version_2")) {
					version = version + tmpValue;
					maData.setVersion(version);
				} else if (fieldName.equals("init_cap")) {
					maData.setInitCap(tmpValue);
				} else if (fieldName.equals("deviation")) {
					maData.setDeviation(tmpValue);
				} else if (fieldName.equals("team")) {
					maData.setTeamInfo(tmpValue);
				} else if (fieldName.equals("tester")) {
					maData.setTester(tmpValue);
				} else if (fieldName.equals("selMachine")) {
					platform = tmpValue;
				} else if (fieldName.equals("selScenario")) {
					platform = platform + " " + tmpValue;
				} else {
					if (!(fieldName.equals("selType")))
						continue;
					platform = platform + " " + tmpValue;
					maData.setPlatform(platform);
				}

			} else {
				System.out.println(fileItem.getFieldName() + "-"
						+ fileItem.getName() + "-" + fileItem.isInMemory()
						+ "-" + fileItem.getContentType());
				if ((fileItem.getName() != null) && (fileItem.getSize() != 0L)) {
					String tmpDir = null;
					String dirTime = getCurrentTime();
					String location = null;

					tmpDir = this.uploadPath + "/" + dirTime;

					if (!(new File(tmpDir).isDirectory())) {
						new File(tmpDir).mkdirs();
						maData.setAbsUploadDir(this.uploadPath);
						maData.setSubDir(dirTime);
					}
					File fullFile = new File(fileItem.getName());

					File newFile = new File(tmpDir + "/" + fullFile.getName());

					location = "Uploads/" + dirTime + "/" + fullFile.getName();

					if (fullFile.getName().equals("cpu_session_all.tbl")) {
						maData.setLocation1(location);
					} else if (fullFile.getName().equals(
							"cso_session_local_all.tbl")) {
						maData.setLocation2(location);
					} else if (fullFile.getName().equals(
							"emaplugin_cpu_session_all.tbl")) {
						maData.setLocation3(location);
					} else if (fullFile.getName().equals(
							"emaplugin_mem_session_all.tbl")) {
						maData.setLocation4(location);
					} else {
						pageTitle = "There were no matched upload files, make sure if you still wanna add CT data";
					}

					try {
						fileItem.write(newFile);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					System.out.println("No file selected or file is empty");
				}

			}

		}

		String table = printMain(maData);

		out.println(printHeader(pageTitle));

		out.println(table);

		System.out.println(table);
		System.out.println(dir);

		out.println(printFooter());
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}