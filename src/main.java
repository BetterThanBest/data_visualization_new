import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Calendar;

import DataAccess.DataBase;
import DataAccess.Fileio;
import BizLogic.dataConvert2Json;
import BizLogic.fileAssembly;
import BizLogic.chartAssembly;

/*import java.io.*;  
import java.awt.Dimension;  
import javax.swing.JPanel;  
import org.jfree.chart.*;  
import org.jfree.chart.axis.CategoryAxis;  
import org.jfree.chart.axis.CategoryLabelPositions;  
import org.jfree.chart.plot.CategoryPlot;  
import org.jfree.chart.plot.PlotOrientation;  
import org.jfree.chart.renderer.category.BarRenderer3D;  
import org.jfree.data.category.CategoryDataset;  
import org.jfree.data.category.DefaultCategoryDataset;  
import org.jfree.data.general.DatasetUtilities;  
import org.jfree.ui.ApplicationFrame;  
import org.jfree.ui.RefineryUtilities;*/

public class main 
{

	/**
	 * @param args
	 * @throws SQLException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws SQLException, IOException 
	{
		// TODO Auto-generated method stub

        try {  
            	Class.forName("com.mysql.jdbc.Driver");  
            	Connection ct = DriverManager.getConnection("jdbc:mysql://localhost:3306/lsv_ct_st","root", "1234");  
            	Statement st = ct.createStatement();  
            	ResultSet rs = st.executeQuery("select * from lsv_ct_st.main_cso");  
            	while(rs.next()){  
            		System.out.println("Id: "+ rs.getString(1)+" Name: "+ rs.getString(2));  
            	}  
        	} catch (Exception e) {  
        		// TODO: handle exception  
        		System.out.println(e);  
        	}
		
		
		
/*		String filename=null;
		
		DataAccess.Fileio fileAcc = new DataAccess.Fileio();
		BizLogic.fileAssembly fileAss1 = new BizLogic.fileAssembly();
		
		String cupSessionAllPath = null;
		String csoSessionLocalAllPath = null;
		String emapluginCpuSessionPath = null;
		String emapluginMemSessionPath = null;*/
		
/*		//get the directory from build dir
		cupSessionAllPath= main.class.getResource("cpu_session_all.tbl").getPath().substring(1);
		System.out.println(cupSessionAllPath);
		
		csoSessionLocalAllPath= main.class.getResource("cso_session_local_all.tbl").getPath().substring(1);
		System.out.println(csoSessionLocalAllPath);
		
		emapluginCpuSessionPath= main.class.getResource("emaplugin_cpu_session_all.tbl").getPath().substring(1);
		System.out.println(emapluginCpuSessionPath);
		
		emapluginMemSessionPath= main.class.getResource("emaplugin_mem_session_all.tbl").getPath().substring(1);
		System.out.println(emapluginMemSessionPath);*/
		
		//fileAcc.readFileByLines("C://Users//ecaojun//workspace//LSV_CTST_Proj//src//cpu_session_all.tbl");
		
		
		//fileAcc.readFileByLines(path);
		//fileAss1.convertCpuSessionAll(cupSessionAllPath);
		
/*		fileAss1.convertCsoSessionAll(csoSessionLocalAllPath);*/
		
		//String config = main.class.getResource("Server.conf").getPath().substring(1);
		
//		String temp1 = System.getenv("JAVA_HOME");
//		temp1.replace("\\\\", "/");
//		
//		System.out.println("JAVA_HOME = " + temp1 );
//		
//		String location = fileAcc.getConfServer(0);
//		System.out.println(location);
//		
//		String temp = getCurrentTime();
//		
//		System.out.println(temp);
//		
//		//creatDiagram();
//		
//		BizLogic.chartAssembly linechart = new BizLogic.chartAssembly();
//		BizLogic.SQLAssembly SQLAssem = new BizLogic.SQLAssembly();
//    	ResultSet sub1 = null;
//    	ResultSet sub2 = null;
//    	
//    	sub1 = SQLAssem.getRecordSet("*", "cso_session_local_all", "ctId=32");
//    	sub2 = SQLAssem.getRecordSet("ctId, tst_machine, traffic_model, software, product, version", 
//    			"lsv_ct_st.ctmastertbl", "");
//    	
//    	dataConvert2Json dtConvert = new dataConvert2Json();
//    	
//    	String temp2 = dtConvert.ResultSetToJsonString(sub2);
//    	System.out.println(temp2);
    	
    	
		//linechart.GenerateChart(sub1,sub2);
		
/*		fileAss1.convertPluginSession(emapluginCpuSessionPath,0);
		fileAss1.printList(0);
		
		fileAss1.convertPluginSession(emapluginMemSessionPath,1);
		fileAss1.printList(1);*/
		

		
/*		String sql="SELECT * FROM ctmastertbl";
		DataAccess.DataBase db =null;
		db=new DataAccess.DataBase();
		ResultSet rs = null;
		rs = db.getSelect(sql);*/

		//String path=null;
		//path= main.class.getResource("testDataStore.mdb").getPath().substring(1);
		
/*		int rowNum=-1;
		rowNum=rs.getRow();
		while(rs.next())
		{
			System.out.println(rs.getString("traffic_model"));
		}
		 
		DataAccess.DataBase db =null;
		db=new DataAccess.DataBase();
		
		String tmp = db.executeProcedure("");*/
	}
	
/*	private static void creatDiagram() throws IOException
	{
        CategoryDataset dataset = getDataSet2();  
        JFreeChart chart = ChartFactory.createBarChart3D(  
                                                "Fruit production Diagrm", // 图表标题  
                                                "Fruit", // 目录轴的显示标签  
                                                "Prodution", // 数值轴的显示标签  
                                                dataset, // 数据集  
                                                PlotOrientation.VERTICAL, // 图表方向：水平、垂直  
                                                true,         // 是否显示图例(对于简单的柱状图必须是false)  
                                                false,         // 是否生成工具  
                                                false         // 是否生成URL链接  
                                                );  
        FileOutputStream fos_jpg = null;  
        try {  
                fos_jpg = new FileOutputStream("C:\\Users\\ecaojun\\workspace\\LSV_CTST_Proj\\WebContent\\fruit.jpg");  
                
                ChartUtilities.writeChartAsJPEG(fos_jpg,chart,400,300);  
        } finally 
        {  
                try 
                {  
                        fos_jpg.close();  
                } catch (Exception e) 
                {}  
        }		
	}
	
    private static CategoryDataset getDataSet2() 
    {  
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();  
        dataset.addValue(100, "Beijing", "Apple");  
        dataset.addValue(100, "Shanghai", "Apple");  
        dataset.addValue(100, "Guangzhou", "Apple");  
        dataset.addValue(200, "Beijing", "Pear");  
        dataset.addValue(200, "Shanghai", "Pear");  
        dataset.addValue(200, "Guangzhou", "Pear");  
        dataset.addValue(300, "Beijing", "Grape");  
        dataset.addValue(300, "Shanghai", "Grape");  
        dataset.addValue(300, "Guangzhou", "Grape");  
        dataset.addValue(400, "Beijing", "Banana");  
        dataset.addValue(400, "Shanghai", "Banana");  
        dataset.addValue(400, "Guangzhou", "Banana");  
        dataset.addValue(500, "Beijing", "Lychee");  
        dataset.addValue(500, "Shanghai", "Lychee");  
        dataset.addValue(500, "Guangzhou", "Lychee");  
        return dataset;  
    }
	
    private static String getCurrentTime()
    {
        StringBuffer send = new StringBuffer(10);
        Calendar   sendTime=Calendar.getInstance();
        int second=sendTime.get(Calendar.SECOND);
        int minute=sendTime.get(Calendar.MINUTE);
        int hour=sendTime.get(Calendar.HOUR_OF_DAY);
        int day=sendTime.get(Calendar.DAY_OF_MONTH);
        int month=sendTime.get(Calendar.MONTH)+1;
        int year=sendTime.get(Calendar.YEAR);
        if(year>=2000)   
        	year=year-2000;
        else   
        	year=year-1900;
        send.append(getFormatTime(year,2));
        send.append(getFormatTime(month,2));
        send.append(getFormatTime(day,2));
        send.append(getFormatTime(hour,2));
        send.append(getFormatTime(minute,2));
        send.append(getFormatTime(second,2));
        return send.toString(); 
    } 
    
    
    private static String getFormatTime(int time,int format)
    {
        StringBuffer numm = new StringBuffer();
        int length=String.valueOf(time).length();

        if(format<length)   
        	return null;

        for(int i=0;i<format-length;i++)
        {
            numm.append( "0");
        }
        numm.append(time);
        return numm.toString().trim();
    }*/

}
