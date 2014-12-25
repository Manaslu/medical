package com.teamsun.framework.util;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Cell;
import com.lowagie.text.Chunk;
import com.lowagie.text.Font;

public class PdfCell extends Cell
{

	public PdfCell(String content, int rowspan, int colspan) throws BadElementException
	{
		super(new Chunk(content, PDFChineseFont.createChineseFont(10,
				Font.NORMAL)));
		setRowspan(rowspan);
		setColspan(colspan);
		setHeader(false);
	}
}
