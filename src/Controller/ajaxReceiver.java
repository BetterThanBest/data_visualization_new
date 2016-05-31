/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package Controller;

import DataAccess.Shell;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ajaxReceiver extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public static String printHeader(String title) {
		String docType = "<!-- Author: jun CAO Mike -->\n<!-- LSV CT/ST DATA Tool  -->\n<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en\" lang=\"en\">\n";

		return docType
				+ "   <head>\n"
				+ "\t\t<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />"
				+ "       <link rel=\"stylesheet\" type=\"text/css\" href=\"css/format.css\" />\n"
				+ "       <title>" + title + "</title>\n"
				+ "\t\t<style type=\"text/css\">" + "\t\t.STYLE3" + "\t\t{"
				+ "\t\t\tfont-family: \"Arial Black\";"
				+ "\t\t\tfont-weight: bold;" + "\t\t\tfont-size: 9px;"
				+ "\t\t}" + "\t\t</style>" + "   </head>\n" + "   <body>\n";
	}

	public static String printTbl(String emaStatus, String dgStatus,
			String crmStatus, String networkStatus) {
		String result = null;

		result = "<h2 style=\"color:#FF0000\">EMA status: " + emaStatus
				+ "  DataGuard : " + dgStatus + "  crm status: " + crmStatus
				+ " </h2>";

		return result;
	}

	public static String printFooter() {
		return "<!-- Start Footer -->\n   </body>\n</html>\n<!-- End Footer -->";
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");

		String emaStatus = request.getParameter("emaStatus");
		String dgStatus = request.getParameter("dgStatus");
		String crmStatus = request.getParameter("crmStatus");
		String networkStatus = request.getParameter("networkStatus");

		Shell sshExecutor = new Shell();
		sshExecutor.openSession("10.170.13.193", "root", "root000");

		sshExecutor.execute("ls -altr |grep ST");

		Vector stdout = sshExecutor.getStandardOutput();
		for (String str : stdout) {
			out.println(str);
		}
		sshExecutor.closeSession();

		out.println(printHeader("ST configuration"));

		out.println(printTbl(emaStatus, dgStatus, crmStatus, networkStatus));

		out.println(printFooter());
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}