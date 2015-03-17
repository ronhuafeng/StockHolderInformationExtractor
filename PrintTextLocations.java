/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.pdfbox.examples.wind;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDStream;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import org.apache.pdfbox.pdmodel.encryption.StandardDecryptionMaterial;

/**
 * This is an example on how to get some x/y coordinates of text.
 *
 * Usage: java org.apache.pdfbox.examples.util.PrintTextLocations &lt;input-pdf&gt;
 *
 * @author <a href="mailto:ben@benlitchfield.com">Ben Litchfield</a>
 * @version $Revision: 1.7 $
 */
public class PrintTextLocations extends PDFTextStripper
{
	/**
	 * Default constructor.
	 *
	 * @throws IOException If there is an error loading text stripper properties.
	 */
	public PrintTextLocations() throws IOException
	{
		super.setSortByPosition( true );
	}

	final static int HEADER_HEIGHT = 60;
	final static int TARGET_WORD_LENGTH = 10;
	// 2.25
	final static float MARGIN_INDEX = 1.0f / 4f;
	static String FEATURE_KEY = "股东性质";
	static String SHARE_KEY = "持股比例";
	final static int MAX_FEATURE_LEN = 20;



	public List<TextPosition> textBuffer = new ArrayList<TextPosition>();
	public List<List<TextPosition>> infoBlock = new ArrayList<List<TextPosition>>();
	public String code = "";
	public List<String> features = new ArrayList<String>();
	public List<String> shares = new ArrayList<String>();
	private String firstPageContent = "";
//	private String stockCode = "";


	/**
	 * This will print the documents data.
	 *
	 * @param args The command line arguments.
	 *
	 * @throws Exception If there is an error parsing the document.
	 */
	public static void main( String[] args ) throws Exception
	{
		if( args.length != 1 )
		{
			usage();
		}
		else
		{

		}
	}


	/***
	 *
	 * @param validPDFPath
	 * @param validResult
	 * @param item The PDF file to extract information from.
	 * @throws IOException
	 */
	public void extractInfoFromFile(File validPDFPath, File validResult, File item) throws IOException {
		System.out.println(item.getName());
		PrintTextLocations printer = workflow(item.getAbsolutePath());

		// If extracted information (top-10 stakeholders) is valid then write the source code to
		if (printer.code.isEmpty() == false)
		{
			item.renameTo(new File(validPDFPath.getAbsolutePath() + File.separator + item.getName()));

			/* 写入Txt文件 */
			File writename = new File(validResult.getAbsolutePath() + File.separator + "code" + File.separator + item.getName() + ".txt");
			// 相对路径，如果没有则要建立一个新的output.txt文件
			writename.createNewFile(); // 创建新文件
			BufferedWriter out = new BufferedWriter(new FileWriter(writename));

			out.write(printer.code);
			out.flush(); // 把缓存区内容压入文件
			out.close(); // 最后记得关闭文件
		}

		if (printer.code.isEmpty() == false && printer.features.size() == 10)
		{
			//item.renameTo(new File(validPDFPath.getAbsolutePath() + File.separator + item.getName()));

			/* 写入Txt文件 */
			File writename = new File(validResult.getAbsolutePath() + File.separator + "feature" + File.separator + item.getName() + ".txt");
			// 相对路径，如果没有则要建立一个新的output.txt文件
			writename.createNewFile(); // 创建新文件
			BufferedWriter out = new BufferedWriter(new FileWriter(writename));
			for (String feature: printer.features)
			{
				out.write(feature);
				out.newLine();
			}
			out.flush(); // 把缓存区内容压入文件
			out.close(); // 最后记得关闭文件  ％  %
		}

		if (printer.code.isEmpty() == false && printer.shares.size() == 10)
		{
			//item.renameTo(new File(validPDFPath.getAbsolutePath() + File.separator + item.getName()));

			/* 写入Txt文件 */
			File writename = new File(validResult.getAbsolutePath() + File.separator + "share" + File.separator + item.getName() + ".txt");
			// 相对路径，如果没有则要建立一个新的output.txt文件
			writename.createNewFile(); // 创建新文件
			BufferedWriter out = new BufferedWriter(new FileWriter(writename));
			for (String feature: printer.shares)
			{
				out.write(feature);
				out.newLine();
			}
			out.flush(); // 把缓存区内容压入文件
			out.close(); // 最后记得关闭文件
		}
		System.gc();
	}

	private PrintTextLocations workflow(String fileName) throws  IOException{
		PDDocument document = null;
		try
		{
			document = PDDocument.load( fileName );
			if( document.isEncrypted() )
			{
				try
				{
					StandardDecryptionMaterial sdm = new StandardDecryptionMaterial("");
					document.openProtection(sdm);
				}
				catch( InvalidPasswordException e )
				{
					System.err.println( "Error: Document is encrypted with a password." );
					System.exit( 1 );
				}
			}
			PrintTextLocations printer = this;
			List allPages = document.getDocumentCatalog().getAllPages();
			for( int i=0; i<allPages.size(); i++ )
			{
				PDPage page = (PDPage)allPages.get( i );
				PDStream contents = page.getContents();
				if( contents != null )
				{
					printer.processStream( page.findResources(), page.getContents().getStream(),
							page.findCropBox(), page.findRotation() );
				}

				if (i == 0)
				{
					for (int j=0;j<infoBlock.size();j++)
					{
						firstPageContent += CommonUtils.collectTextBuffer(infoBlock.get(j));
					}
				}
			}

/*			// print the result
			for (List<TextPosition> buffer: printer.infoBlock)
			{
				System.out.print("[ " + printer.getBufferLeft(buffer) + ", "
						+ printer.getBufferRight(buffer) + " || "
						+ buffer.get(0).getY() + ", "
						+ (buffer.get(0).getY() + buffer.get(0).getHeightDir())+ " ]");

				String textSegment = printer.collectTextBuffer(buffer);
				System.out.println(textSegment);

			}*/
			printer.code = printer.extractCode();
			printer.features = printer.extractFeatures();
			printer.shares = printer.extractShares();

			return printer;
		}
		finally
		{
			if( document != null )
			{

				document.close();
			}
		}
	}

	private List<String> extractFeatures() {

		// Store features of stakeholders
		List<String> result = new ArrayList<String>();

		int startPoint = -1;

		List<TextPosition> featureItem;
		for (int i = 0; i < infoBlock.size(); i++)
		{
			// To get index of the text in the information block, if the text does not exist, the index will be -1.
			int index = CommonUtils.collectTextBuffer(infoBlock.get(i)).indexOf(FEATURE_KEY);
			if (index != -1)
			{
				startPoint = i + 1;

				// To locate the PDF object from the text feature to the end of the information block.
				featureItem = infoBlock.get(i).subList(index, index + FEATURE_KEY.length());

				// To extract information vertically. startPoint now is where the information block next to the
				// one that contains text feature.
				if (startPoint != -1)
				{
					result = filterTextBuffersVerical(featureItem, infoBlock, startPoint, 10);
/*						for(String item: result)
						{
							System.out.println(item);
						}*/
				}

				break;
			}
		}

		return result;
	}

	private List<String> extractShares()
	{
		// Store shares of stakeholders
		List<String> result = new ArrayList<String>();

		int startPoint = -1;

		List<TextPosition> shareItem;
		for (int i = 0; i < infoBlock.size(); i++)
		{
			// To get index of the text in the information block, if the text does not exist, the index will be -1.
			int index = CommonUtils.collectTextBuffer(infoBlock.get(i)).indexOf(SHARE_KEY);
			if (index != -1 && CommonUtils.collectTextBuffer(infoBlock.get(i)).length() < 15)
			{
				startPoint = i + 1;

				// To locate the PDF object from the text feature to the end of the information block.
				shareItem = infoBlock.get(i).subList(index, index + SHARE_KEY.length());

				// To extract information vertically. startPoint now is where the information block next to the
				// one that contains text share.
				if (startPoint != -1)
				{
					result = filterTextBuffersVerical(shareItem, infoBlock, startPoint, 10);
/*						for(String item: result)
						{
							System.out.println(item);
						}*/
				}

				break;
			}
		}

		for (int i=0;i<result.size();i++)
		{
			int index = result.get(i).indexOf("%");
			if (index != -1)
			{
				result.set(i, result.get(i).substring(0, index));
			}
			else
			{
				index = result.get(i).indexOf("％");
				if (index != -1)
				{
					result.set(i, result.get(i).substring(0, index));
				}
			}
		}

		return result;
	}

	private String extractCode()
	{
		String code = "";

		int startPoint = -1;
		int index = 0;

//		do{
//			index =  stockCode.indexOf("股票代码");
//			if (index == -1)
//			{
//				index =  stockCode.indexOf("证券代码");
//			}
//
//			if (index != -1)
//			{
//				String temp = stockCode.substring(index + 4, index + 14);
//				code = search6DigitString(temp);
//				if (code.length() == 6)
//					return code;
//				stockCode = stockCode.substring(index + 4);
//			}
//		}while (index != -1);

		for (int i = 0; i < infoBlock.size(); i++)
		{
			int offset = 4;
			// To get index of the text in the information block, if the text does not exist, the index will be -1.
			index = CommonUtils.collectTextBuffer(infoBlock.get(i)).indexOf("股票代码");
			if (index == -1)
			{
				index = CommonUtils.collectTextBuffer(infoBlock.get(i)).indexOf("证券代码");
				if (index == -1)
				{
					index = CommonUtils.collectTextBuffer(infoBlock.get(i)).indexOf("股代码");
					offset = 3;
				}
			}

			if (index != -1)
			{
				String temp =
					  CommonUtils.collectTextBuffer(infoBlock.get(i)).substring(index+offset)
					  + CommonUtils.collectTextBuffer(infoBlock.get(i+1))
					  + CommonUtils.collectTextBuffer(infoBlock.get(i+2));


				code = search6DigitString(temp);
				if (code.length() == 6)
					break;
				else
				{
					List<TextPosition> codeItem = infoBlock.get(i).subList(index, index + 4);
					startPoint = i + 1;
					List<String> result = filterTextBuffersVerical(codeItem, infoBlock, startPoint, 1);

					if (result.size() > 0)
					{
						code = search6DigitString(result.get(0));
						if (code.length() == 6)
						{
							break;
						}
					}
				}
			}
		}

		if (code.isEmpty())
		{
			// Still not found, then go to the first page.
			code = search6DigitString(firstPageContent);
		}




		return code;
	}

	private String search6DigitString(String str)
	{
		String result = "";
		for (int i = 0; i < str.length(); i++)
		{
			if(Character.isDigit(str.charAt(i)))
			{
				boolean found = true;
				for (int j = i;j<i+6;j++)
				{
					if (j >= str.length())
					{
						found = false;
						break;
					}
					else if (Character.isDigit(str.charAt(j)) == false)
					{
						found = false;
						break;
					}
				}

				if (found == true)
				{
					result = str.substring(i, i+6);
					break;
				}
			}
		}

		return result;
	}


	/**
	 * A method provided as an event interface to allow a subclass to perform
	 * some specific functionality when text needs to be processed.
	 *
	 * @param text The text to be processed
	 */
	protected void processTextPosition( TextPosition text )
	{
//
//		if (text.getCharacter().contains("股")
//				|| text.getCharacter().contains("票")
//				|| text.getCharacter().contains("代")
//				|| text.getCharacter().contains("码")
//				|| text.getCharacter().contains("证")
//				|| text.getCharacter().contains("券")
//				|| Character.isDigit(text.getCharacter().charAt(0))
//				|| text.getCharacter().contains("，")
//				|| text.getCharacter().contains("。")
//				|| text.getCharacter().contains(",")
//				|| text.getCharacter().contains(".")
//				|| text.getCharacter().contains(":")
//				|| text.getCharacter().contains("："))
//		{
//			stockCode += text.getCharacter();
//		}

		if (text.getCharacter().endsWith(" "))
			return;

		if (textBuffer.isEmpty())
		{
			textBuffer.add(text);
		}
		else
		{
			TextPosition lastText = textBuffer.get(textBuffer.size() - 1);

			// If current text is near enough to the end of textBuffer (lastText)
			if (isCharacterAdjacent(lastText, text) == true)
			{
				textBuffer.add(text);
			}
			else
			{

				// A new text segment is found which is adjacent to the current textBuffer,
				// but may be vertically overlapped.

				// Put the segment into a block, the block will be combined later.

				// If the text buffer is overlapped with the last buffer in infoBlock
				// then combine these two buffers.


				if (infoBlock.isEmpty() == false)
				{
					List<TextPosition> lastBuffer = infoBlock.get(infoBlock.size() - 1);

					// MAX_FEATURE_LEN is a magic number to filter too long a line that overlaps with all line below
					if (CommonUtils.isBufferHorizontalOverlapped(lastBuffer, textBuffer) && lastBuffer.size() < MAX_FEATURE_LEN)
					{
						lastBuffer.addAll(textBuffer);
					}
					else
					{
						infoBlock.add(textBuffer);
					}
				}
				else
				{
					infoBlock.add(textBuffer);
				}

				// to create a new buffer for the original object is added to the infoBlock
				textBuffer = new ArrayList<TextPosition>();
				textBuffer.add(text);
			}
		}



/*		System.out.println( "String[" + text.getXDirAdj() + "," +
				text.getYDirAdj() + " fs=" + text.getFontSize() + " xscale=" +
				text.getXScale() + " height=" + text.getHeightDir() + " space=" +
				text.getWidthOfSpace() + " width=" +
				text.getWidthDirAdj() + "]" + text.getCharacter() );*/
	}

	public Boolean isCharacterAdjacent( TextPosition preText, TextPosition postText)
	{
		if (Math.abs(preText.getX() + preText.getWidthDirAdj() - postText.getX()) < postText.getWidthDirAdj() * MARGIN_INDEX)
			return true;
		else
			return false;
	}



	// filter the text-buffer items that locate below the a specified text-buffer item
	// and form a string list
	static public List<String> filterTextBuffersVerical(List<TextPosition> item, List<List<TextPosition>> buffers, int start, int count)
	{
		List<String> result =new ArrayList<String>();

		float leftPre = CommonUtils.getBufferLeft(item);
		float rightPre = CommonUtils.getBufferRight(item);

		for (int i = start; (i < buffers.size()) && (count > 0); i++)
		{
			String word = CommonUtils.collectTextBuffer(buffers.get(i));
			if (CommonUtils.isBufferOverlapWithSegment(leftPre, rightPre, buffers.get(i)) == true
					&& buffers.get(i).size() < TARGET_WORD_LENGTH
					&& buffers.get(i).get(0).getY() > HEADER_HEIGHT        // to remove the header
//					&& templateWords.contains(word)
					&& buffers.get(i).get(0).getY() + buffers.get(i).get(0).getHeightDir() > 70     // height 842 width 595
					&& buffers.get(i).get(0).getY() + buffers.get(i).get(0).getHeightDir() < 785
					)
			{
				result.add(CommonUtils.collectTextBuffer(buffers.get(i)));

				float leftPost = CommonUtils.getBufferLeft(buffers.get(i));
				if (leftPost < leftPre)
				{
					leftPre = leftPost;
				}

				float rightPost = CommonUtils.getBufferRight(buffers.get(i));
				if (rightPost > rightPre)
				{
					rightPre = rightPost;
				}

				count--;
			}
		}

		return result;
	}



	/**
	 * This will print the usage for this document.
	 */
	private static void usage()
	{
		System.err.println( "Usage: java org.apache.pdfbox.examples.pdmodel.PrintTextLocations <input-pdf>" );
	}

}
