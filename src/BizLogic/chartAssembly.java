/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package BizLogic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

public class chartAssembly {
	public static String GenerateCsoChart(ResultSet dataset1, String title,
			String linename, String XAxis, String YAxis, String imgName)
			throws SQLException {
		fileAssembly FileAss = new fileAssembly();

		String ChartPath = FileAss.getChartAddr();

		CategoryDataset dataset = createDatasetCso(dataset1, linename);

		JFreeChart freeChart = createChart(dataset, title, XAxis, YAxis);

		saveAsFile(freeChart, ChartPath + imgName, 800, 400);

		return imgName;
	}

	public static String GenerateCpuChart(ResultSet dataset1, String title,
			String XAxis, String YAxis, String imgName) throws SQLException {
		fileAssembly FileAss = new fileAssembly();

		String ChartPath = FileAss.getChartAddr();

		CategoryDataset dataset = createDatasetCpu(dataset1);

		JFreeChart freeChart = createChart(dataset, title, XAxis, YAxis);

		saveAsFile(freeChart, ChartPath + imgName, 800, 400);

		return imgName;
	}

	public static CategoryDataset createDatasetCpu(ResultSet dataset1)
			throws SQLException {
		String session = null;
		String cpuTmp = null;
		double cpu = 0.0D;
		String linename = null;
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		dataset1.beforeFirst();
		while (dataset1.next()) {
			session = dataset1.getString("sessions");
			cpuTmp = dataset1.getString("cpu_load");
			if (cpuTmp.contains("%")) {
				cpuTmp = cpuTmp.replace('%', '\0');
			}
			cpu = Double.parseDouble(cpuTmp);

			linename = dataset1.getString("ip");
			if (linename.contains("]")) {
				linename = linename.replace(']', '\0');
			}

			dataset.addValue(cpu, linename, session);
		}
		return dataset;
	}

	public static CategoryDataset createDatasetCso(ResultSet dataset1,
			String linename) throws SQLException {
		String session = null;
		double cso_sec = 0.0D;
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		dataset1.beforeFirst();
		while (dataset1.next()) {
			session = dataset1.getString("sessions");
			cso_sec = Double.parseDouble(dataset1.getString("cso_sec"));

			dataset.addValue(cso_sec, linename, session);
		}

		return dataset;
	}

	public static JFreeChart createChart(CategoryDataset categoryDataset,
			String title, String xAxis, String yAxis) {
		JFreeChart jfreechart = ChartFactory.createLineChart(title, xAxis,
				yAxis, categoryDataset, PlotOrientation.VERTICAL, true, false,
				false);

		CategoryPlot plot = (CategoryPlot) jfreechart.getPlot();

		plot.setBackgroundAlpha(0.5F);

		plot.setForegroundAlpha(0.5F);

		return jfreechart;
	}

	public static void saveAsFile(JFreeChart chart, String outputPath,
			int weight, int height) {
		FileOutputStream out = null;
		try {
			File outFile = new File(outputPath);
			if (!(outFile.getParentFile().exists())) {
				outFile.getParentFile().mkdirs();
			}
			out = new FileOutputStream(outputPath);

			ChartUtilities.writeChartAsJPEG(out, chart, weight, height);
			out.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException localIOException3) {
				}
			}
		}
	}
}