/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package Controller;

import BizLogic.SQLAssembly;
import BizLogic.dataConvert2Json;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class statistics extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		SQLAssembly SQLAssem = new SQLAssembly();
		dataConvert2Json dtConvert = new dataConvert2Json();
		ResultSet datarecord = null;

		response.setContentType("text/html");

		PrintWriter out = response.getWriter();

		String action = request.getParameter("action");

		if (Integer.parseInt(action) == 1) {
			datarecord = SQLAssem
					.getRecordSet(
							"ctId, tst_machine, traffic_model, software, product, version",
							"ctmastertbl", "");
			String JSstr = dataConvert2Json.ResultSetToJsonString(datarecord);

			out.write(JSstr);
			out.close();
		} else {
			if (Integer.parseInt(action) != 2)
				return;
			datarecord = SQLAssem.getRecordSet(
					"count(traffic_model) as groupTotal, traffic_model",
					"ctmastertbl", "ctId >0 group by traffic_model");
			String JSstr = dataConvert2Json.ResultSetToJsonString(datarecord);

			out.write(JSstr);
			out.close();
		}
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}