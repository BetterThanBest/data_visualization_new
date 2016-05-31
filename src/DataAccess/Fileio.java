/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package DataAccess;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;

public class Fileio {
	private static String[] copyedStrFile = new String[5];

	public static String[] readFileByLines(String fileName) throws IOException {
		String[] fileInStr = new String[250];

		File file = new File(fileName);
		BufferedReader reader = null;
		try {
			System.out.println("");
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			int line = 0;
			while ((tempString = reader.readLine()) != null) {
				fileInStr[line] = tempString;
				System.out.println(tempString);
				++line;
			}
			reader.close();
		} finally {
			if (reader != null) {
				reader.close();
			}
		}

		return fileInStr;
	}

	public String getConfServer(int Type) {
		String result = null;

		String config = "/opt/apache-tomcat-6.0.37/webapps/cso/WEB-INF/classes/DataAccess/Server.conf";

		System.out.println(config);
		try {
			copyedStrFile = (String[]) Arrays.copyOf(readFileByLines(config),
					copyedStrFile.length);
			result = copyedStrFile[Type];
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}
}