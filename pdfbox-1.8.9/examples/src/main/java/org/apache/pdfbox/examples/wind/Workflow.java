package org.apache.pdfbox.examples.wind;

import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: flatlined
 * Date: 14-10-23
 * To change this template use File | Settings | File Templates.
 */
public class Workflow
{
	private String dirPath;

	public Workflow(String dirPath)
	{
		this.dirPath = dirPath;
	}

	public static void main( String[] args ) throws Exception
	{
		String [] dirPaths = {
//				"F:\\郝阳\\深市主板2003年年度报告",
//				"F:\\郝阳\\深市主板2004年年度报告",
//				"F:\\郝阳\\深市主板2005年年度报告",
//				"F:\\郝阳\\深市主板2006年年度报告",
//				"F:\\郝阳\\深市主板2007年年度报告",
				"/media/wind/Data/郝阳-上市公司数据/2014"
//				,"F:\\郝阳\\深市主板2009年年度报告",
//				"F:\\郝阳\\深市主板2010年年度报告",
//				"F:\\郝阳\\深市主板2012年年度报告"
		};

//		String [] dirPaths = {
//				"E:\\郝阳\\test"
//		};
		for (int i=0;i<dirPaths.length;i++)
		{
			//new Thread(new Workflow(dirPaths[i])).start();
			new Workflow(dirPaths[i]).extractDir();
		}
	}



	private void extractDir() throws IOException {
		File dirFile = new File(dirPath);

		if (dirFile.exists() && dirFile.isDirectory())
		{
			// To store PDF files which feature results are (possibly) extracted.
			File validPDFPath = new File(dirPath + File.separator + "validPDF");
			if (validPDFPath.exists())
			{
				validPDFPath.delete();
			}
			validPDFPath.mkdir();

			// To store files which contains top-10 stockholders
			File validResult = new File(dirPath + File.separator + "validResult");
			if (validResult.exists())
			{
				validResult.delete();
			}
			validResult.mkdir();

			File validResultCode = new File(dirPath + File.separator + "validResult" + File.separator + "code");
			if (validResultCode.exists())
			{
				validResultCode.delete();
			}
			validResultCode.mkdir();

			File validResultFeature = new File(dirPath + File.separator + "validResult" + File.separator + "feature");
			if (validResultFeature.exists())
			{
				validResultFeature.delete();
			}
			validResultFeature.mkdir();

			File validResultShare = new File(dirPath + File.separator + "validResult" + File.separator + "share");
			if (validResultShare.exists())
			{
				validResultShare.delete();
			}
			validResultShare.mkdir();




			for (File item: CommonUtils.getFilesWithSuffix(dirPath, "pdf"))
			{
				try
				{
					new PrintTextLocations().extractInfoFromFile(validPDFPath, validResult, item);
					System.gc();
				}
				catch (Exception e)
				{

				}
			}
		}
		else
		{
			System.out.println("Not a valid directory name.");
		}
	}

	/**
	 * When an object implementing interface <code>Runnable</code> is used
	 * to create a thread, starting the thread causes the object's
	 * <code>run</code> method to be called in that separately executing
	 * thread.
	 * <p/>
	 * The general contract of the method <code>run</code> is that it may
	 * take any action whatsoever.
	 *
	 * @see Thread#run()
	 */
	public void run() {
		//To change body of implemented methods use File | Settings | File Templates.
		try {
			extractDir();
		} catch (IOException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}
	}
}
