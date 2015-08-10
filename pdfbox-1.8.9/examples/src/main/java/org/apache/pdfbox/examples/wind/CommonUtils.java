package org.apache.pdfbox.examples.wind;

import org.apache.pdfbox.util.TextPosition;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: flatlined
 * Date: 14-10-23
 * To change this template use File | Settings | File Templates.
 */
public class CommonUtils {
	// list pdf paths of a specified directory
	public static List<File> getFilesWithSuffix(String path, String suffix)
	{
		List<File> result = new ArrayList<File>();

		File file = new File(path);

		if (file.exists())
		{
			for (File f: file.listFiles())
			{
				if (f.isFile() && f.getName().toLowerCase().endsWith(suffix.toLowerCase()))
				{
					// found a pdf file
					result.add(f);
				}
			}
		}

		return result;
	}

	// get the left boarder of a text buffer
	static public float getBufferLeft(List<TextPosition> buffer)
	{
		if (buffer.isEmpty())
			return 0.0f;

		// find the leftmost character in this buffer
		float leftBoarder = buffer.get(0).getX();

		for (TextPosition text: buffer)
		{
			if (text.getX() < leftBoarder)
				leftBoarder = text.getX();
		}

		return leftBoarder;
	}

	// get the right boarder of a text buffer
	static public float getBufferRight(List<TextPosition> buffer)
	{
		if (buffer.isEmpty())
			return 0.0f;

		TextPosition lastText = buffer.get(buffer.size() - 1);

		float rightBoarder = lastText.getX() + lastText.getWidth();

		//System.out.print(" last: " + lastText.getCharacter() + "*"  + rightBoarder);

		for (TextPosition text: buffer)
		{
			if (text.getX() + text.getWidthDirAdj() > rightBoarder)
			{
				rightBoarder = text.getX() + text.getWidthDirAdj();

				//System.out.print(" update: " + text.getCharacter() + "*" + rightBoarder);
			}
		}

		return rightBoarder;
	}

	// collect characters in a text buffer and form a string
	static public String collectTextBuffer(List<TextPosition> textBuffer)
	{
		String result = "";
		for (TextPosition text: textBuffer)
		{
			result += text.getCharacter();
		}

		return  result;
	}

	// test if two text buffers are overlapped.
	static public Boolean isBufferHorizontalOverlapped(List<TextPosition> preBuffer, List<TextPosition> postBuffer)
	{
		float leftPre = getBufferLeft(preBuffer);
		float rightPre = getBufferRight(preBuffer);
		float leftPost = getBufferLeft(postBuffer);
		float rightPost = getBufferRight(postBuffer);

		return (leftPost >= leftPre && leftPost <= rightPre)
				|| (leftPre >= leftPost && leftPre <= rightPost);
	}
	static public Boolean isBufferOverlapWithSegment(float leftPre, float rightPre, List<TextPosition> postBuffer)
	{
		float leftPost = getBufferLeft(postBuffer);
		float rightPost = getBufferRight(postBuffer);

		return (leftPost >= leftPre && leftPost <= rightPre)
				|| (leftPre >= leftPost && leftPre <= rightPost);
	}
}
