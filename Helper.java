package org.apache.pdfbox.examples.wind;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: flatlined
 * Date: 14-10-28
 * To change this template use File | Settings | File Templates.
 */
public class Helper {
	public static void main( String[] args ) throws Exception
	{
		args = new String[]{"F:\\郝阳\\Done\\深市2011主板"};
		if( args.length != 1 )
		{

		}
		else
		{
			File file = new File(args[0]);
			if (file.exists())
			{
				for (File f: file.listFiles())
				{
					if (f.isFile())
					{
						System.out.println(f.getName());
						String [] names = f.getName().split("_");

						String newPath = f.getAbsolutePath();
						if (names.length > 0)
						{
							newPath = f.getParent() + File.separator + "s" + names[names.length-1];
						}

						File newFile = new File(newPath);


						f.renameTo(newFile);
					}
				}
			}
		}
	}


}
