package org.apache.pdfbox.examples.wind;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: flatlined
 * Date: 14-5-18
 * To change this template use File | Settings | File Templates.
 */
public class AddCSV {

	public static Map<String, List<String>> fileNameToFeature;
	public static Map<String, String> codeToFileName;
	public static Map<String, List<String>> fileNameToPart;


	public static void main( String[] args ) throws Exception
	{
		//String dirPath = "G:\\temp\\沪市主板2007年年度报告\\validResult";
		String csvPath = "G:\\temp\\全部A股_截止20120101_前十名及持股比例all.csv";
		String featurePath =  "G:\\temp\\深市主板2012年年度报告（新名）\\result\\valid";
		String partPath =  "G:\\temp\\深市主板2012年年度报告（新名）\\validResult 4 股份比例";
		String codePath = "G:\\temp\\深市主板2012年年度报告（新名）\\validResult code";
		String resultPath = "G:\\temp" + File.separator + "2012.csv";

		//buildCSV(csvPath, featurePath, partPath, codePath,resultPath);
		csvPath = "G:\\temp\\全部A股_截止20070101_前十名及持股比例all.csv";
		featurePath =  "G:\\temp\\沪市主板2007年年度报告\\validResult - 股东性质\\valid";
		partPath =  "G:\\temp\\沪市主板2007年年度报告\\validResult 持股比例";
		codePath = "G:\\temp\\沪市主板2007年年度报告\\validResult 2 code";
		resultPath = "G:\\temp" + File.separator + "2007.csv";

		Map<String, String> directoryToCSV = new HashMap<String, String>();

		directoryToCSV.put("F:\\郝阳\\Done\\深市主板2003年年度报告",   "2003.csv");
		directoryToCSV.put("F:\\郝阳\\Done\\深市主板2004年年度报告",   "2004.csv");
		directoryToCSV.put("F:\\郝阳\\Done\\深市主板2005年年度报告",   "2005.csv");
		directoryToCSV.put("F:\\郝阳\\Done\\深市主板2006年年度报告",   "2006.csv");
		directoryToCSV.put("F:\\郝阳\\Done\\深市主板2007年年度报告",   "2007.csv");
		directoryToCSV.put("F:\\郝阳\\Done\\深市主板2008年年度报告",   "2008.csv");
		directoryToCSV.put("F:\\郝阳\\Done\\深市主板2009年年度报告",   "2009.csv");
		directoryToCSV.put("F:\\郝阳\\Done\\深市主板2010年年度报告",   "2010.csv");
		directoryToCSV.put("F:\\郝阳\\Done\\深市主板2011年年度报告",   "2011.csv");
		directoryToCSV.put("F:\\郝阳\\Done\\深市主板2012年年度报告",   "2012.csv");
		directoryToCSV.put("F:\\郝阳\\Done\\创业板2009年年度财务报告", "2009.csv");
		directoryToCSV.put("F:\\郝阳\\Done\\创业板2010年年度财务报告", "2010.csv");
		directoryToCSV.put("F:\\郝阳\\Done\\创业板2011年年度财务报告", "2011.csv");
		directoryToCSV.put("F:\\郝阳\\Done\\创业板2012年年度财务报告", "2012.csv");
		directoryToCSV.put("F:\\郝阳\\Done\\沪市主板2004年年度报告",   "2004.csv");
		directoryToCSV.put("F:\\郝阳\\Done\\沪市主板2005年年度报告",   "2005.csv");
		directoryToCSV.put("F:\\郝阳\\Done\\沪市主板2006年年度报告",   "2006.csv");
		directoryToCSV.put("F:\\郝阳\\Done\\沪市主板2007年年度报告",   "2007.csv");
		directoryToCSV.put("F:\\郝阳\\Done\\沪市主板2008年年度报告",   "2008.csv");
		directoryToCSV.put("F:\\郝阳\\Done\\沪市主板2009年年度报告",   "2009.csv");
		directoryToCSV.put("F:\\郝阳\\Done\\沪市主板2010年年度报告",   "2010.csv");
		directoryToCSV.put("F:\\郝阳\\Done\\沪市主板2011年年度报告",   "2011.csv");
		directoryToCSV.put("F:\\郝阳\\Done\\沪市主板2012年年度报告",   "2012.csv");
		directoryToCSV.put("F:\\郝阳\\Done\\中小板2004年年度财务报告", "2004.csv");
		directoryToCSV.put("F:\\郝阳\\Done\\中小板2005年年度财务报告", "2005.csv");
		directoryToCSV.put("F:\\郝阳\\Done\\中小板2006年年度财务报告", "2006.csv");
		directoryToCSV.put("F:\\郝阳\\Done\\中小板2007年年度财务报告", "2007.csv");
		directoryToCSV.put("F:\\郝阳\\Done\\中小板2008年年度财务报告", "2008.csv");
		directoryToCSV.put("F:\\郝阳\\Done\\中小板2009年年度财务报告", "2009.csv");
		directoryToCSV.put("F:\\郝阳\\Done\\中小板2010年年度财务报告", "2010.csv");
		directoryToCSV.put("F:\\郝阳\\Done\\中小板2011年年度财务报告", "2011.csv");
		directoryToCSV.put("F:\\郝阳\\Done\\中小板2012年年度财务报告", "2012.csv");

		for (String dirPath: directoryToCSV.keySet())
		{
			System.out.println(dirPath);
			csvPath = "F:\\郝阳\\CSV Template\\" + directoryToCSV.get(dirPath);
			featurePath = dirPath + File.separator + "validResult" + File.separator + "feature";
			partPath = dirPath + File.separator + "validResult" + File.separator + "share";
			codePath = dirPath + File.separator + "validResult" + File.separator + "code";

			// Seems csv file is close after being read. So use the same path is OK.
			//resultPath = dirPath + File.separator + "validResult" + File.separator + "out.csv";
			resultPath = csvPath;

			buildCSV(csvPath, featurePath, partPath, codePath,resultPath);
		}



	}

	private static void buildCSV(String csvPath, String featurePath, String partPath, String codePath, String resultPath) throws IOException {
		fileNameToFeature = buildFileNameMap(featurePath);
		fileNameToPart = buildFileNameMap(partPath);
		codeToFileName = buildCodeMap(codePath);

		File csvFile = new File(csvPath);

		String [] word2file = new String[43];

		// check if csv file is complete
		List<String> firmLines = getLinesFromFile(csvFile);
		Map<String, String> legalCodes = new HashMap<String, String>();
		for (String item: firmLines)
		{
			String sub = item.substring(0, 6);

			if (Character.isDigit(sub.charAt(0)))
			{
				legalCodes.put(sub, csvPath);
			}
		}
		boolean containCode = true;
		List<String []> moreInfo = new ArrayList<String[]>();
		for (String c: codeToFileName.keySet())
		{


			if (legalCodes.containsKey(c) == false)
			{
				String [] wordsTmp = new String[43];
				String fileName = codeToFileName.get(c);

				wordsTmp[0] = c;
				for (int i=1;i<22;i++)
					wordsTmp[i] = "";

				if (fileNameToFeature.containsKey(fileName) == false)
				{
					for (int i=0;i<10;i++)
						wordsTmp[22+2*i] = "";
				}
				else
				{
					for (int i=0;i<10;i++)
						wordsTmp[22+2*i] = fileNameToFeature.get(fileName).get(i);

				}

				if (fileNameToPart.containsKey(fileName) == false)
				{
					System.out.println("part not found: " + c);
					for (int i=0;i<10;i++)
					{
						wordsTmp[23+2*i] = "";
					}
				}
				else
				{
					for (int i=0;i<10;i++)
					{
						wordsTmp[23+2*i] = fileNameToPart.get(fileName).get(i);
					}
				}
				wordsTmp[42] = fileName;

				moreInfo.add(wordsTmp);
			}
		}




		firmLines = getLinesFromFile(csvFile);

		File writeName = new File(resultPath); // 相对路径，如果没有则要建立一个新的output。txt文件
		writeName.createNewFile(); // 创建新文件
		BufferedWriter out = new BufferedWriter(new FileWriter(writeName));
		try
		{
			for (String item: firmLines)
			{
				item.replace(",", "@");
				String [] words = item.split("`");
				if (words.length >= 22)
				{
					int fIndex = 0;
					char [] codeChars = words[0].toCharArray();
					for (;fIndex<codeChars.length;fIndex++)
					{
						if (codeChars[fIndex] <= '9' && codeChars[fIndex] >= '0')
							break;
					}


					String code = words[0].substring(fIndex, fIndex+6);

					if (codeToFileName.containsKey(code) == false)
					{
						System.out.println("code not found: " + code);
						// write information line  back
						out.write(item);
						out.newLine();
						continue;
					}

					String fileName = codeToFileName.get(code);

					List<String> features = new ArrayList<String>();
					if (fileNameToFeature.containsKey(fileName) == false)
					{
						System.out.println("feature not found: " + code);
						for (int i=0;i<10;i++)
							features.add("");
					}
					else
					{
						features = fileNameToFeature.get(fileName);
					}

					List<String> parts = new ArrayList<String>();
					if (fileNameToPart.containsKey(fileName) == false)
					{
						System.out.println("part not found: " + code);
						for (int i=0;i<10;i++)
						{
							parts.add("");
						}
					}
					else
					{
						parts = fileNameToPart.get(fileName);
					}


					writeLine(out, words, fileName, features, parts);

				}
				else
				{
					System.out.println(csvPath);
				}
			}

			for (String [] item: moreInfo)
			{
				writeArrayInfo(out, item);
			}

			out.close(); // 最后记得关闭文件
		}
		catch (Exception e)
		{

		}

	}

	private static void writeArrayInfo(BufferedWriter out, String[] item) throws IOException {
		for (int i=0; i<22; i++)
		{
			out.write(item[i]);
			out.write("`");
		}

		for (int i=0; i<10; i++)
		{
			out.write(item[22+2*i]);
			out.write("`");
			String part = item[23+2*i];

			if(part.endsWith("%"))
				out.write(part);
			else
			{
				out.write(part+"%");
			}
			out.write("`");
		}

		out.write(item[42]);
		out.newLine();
		out.flush(); // 把缓存区内容压入文件
	}

	private static void writeLine(BufferedWriter out, String[] words, String fileName, List<String> features, List<String> parts) throws IOException {
		for (int i=0; i<22; i++)
		{
			out.write(words[i]);
			out.write("`");
		}

		for (int i=0; i<10; i++)
		{
			out.write(features.get(i));
			out.write("`");
			String part = parts.get(i);

			if(part.endsWith("%"))
				out.write(part);
			else
			{
				out.write(part+"%");
			}
			out.write("`");
		}

		out.write(fileName);
		out.newLine();
		out.flush(); // 把缓存区内容压入文件
	}

	public static Map<String, String> buildCodeMap(String dirPath)
	{
		List<File> files = CommonUtils.getFilesWithSuffix(dirPath, "txt");

		Map<String, String> codeToFileName = new HashMap<String, String>();

		for (File f: files)
		{
			String code = CollectNames.getLinesFromFile(f).get(0);
			codeToFileName.put(code, f.getName());
		}

		return codeToFileName;
	}

	public static Map<String, List<String>> buildFileNameMap(String dirPath)
	{
		List<File> files = CommonUtils.getFilesWithSuffix(dirPath, "txt");

		Map<String, List<String>> fileNameMap = new HashMap<String, List<String>>();

		for (File f: files)
		{
			fileNameMap.put(f.getName(), CollectNames.getLinesFromFile(f));
		}

		return fileNameMap;
	}

	public static List<String> getLinesFromFile(File file) {
		InputStream fis;

		String line;
		List<String> lines = new ArrayList<String>();

		try
		{
			fis = new FileInputStream(file.getAbsolutePath());
			BufferedReader br = new BufferedReader(new InputStreamReader(fis, Charset.forName("UTF-8")));
			while ((line = br.readLine()) != null) {
				// Deal with the line
				lines.add(line);
			}

			br.close();

		}
		catch (Exception e)
		{

		}
		return lines;
	}

}
