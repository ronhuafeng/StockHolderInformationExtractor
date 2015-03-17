package org.apache.pdfbox.examples.wind;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: flatlined
 * Date: 14-5-19
 * To change this template use File | Settings | File Templates.
 */
public class ValidateResult {

//
//	final static String templateWordsFile = "G:\\temp\\template2.txt";
//	static List<String> templateWords;
//	public static void main( String[] args ) throws Exception
//	{
//		//String dirPath = "G:\\temp\\沪市主板2007年年度报告\\validResult";
//		String dirPath = "G:\\temp\\沪市主板2007年年度报告\\validResult - 股东性质";
//
//		List<File> files = CommonUtils.getFilesWithSuffix(dirPath, "txt");
//		templateWords = CollectNames.getLinesFromFile(new File(templateWordsFile));
//
//		List<String> words = new ArrayList<String>();
//
//		File validResultPath = new File(dirPath + File.separator + "valid");
//
//		for (File file: files)
//		{
//			List<String> result = CollectNames.getLinesFromFile(file);
//
//			if (validResult(result) == true)
//			{
//				boolean isSucceed = file.renameTo(new File(validResultPath.getAbsolutePath() + File.separator + file.getName()));
//				System.out.println("rename " + file.getName() + " : " + isSucceed);
//			}
//		}
//	}
//
//	//  Validate result with a template file with legal words
//	public static boolean validResult(List<String> result)
//	{
//		boolean isValid  = true;
//		for (String item: result)
//		{
//			boolean find = false;
//			for (String word: templateWords)
//			{
//				if(word.equals(item))
//				{
//					find = true;
//					break;
//				}
//			}
//
//			if (find == false)
//			{
//				isValid = false;
//				break;
//			}
//		}
//		return  isValid;
//	}
}
