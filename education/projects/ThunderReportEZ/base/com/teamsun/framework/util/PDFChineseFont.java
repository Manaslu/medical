package com.teamsun.framework.util;

import java.io.IOException;

import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.BaseFont;

public class PDFChineseFont {
	private static Font chineseFont;

	public final static Font createChineseFont(int size, int style) {
		try {
			chineseFont = new Font(BaseFont.createFont("STSong-Light",
					"UniGB-UCS2-H", BaseFont.NOT_EMBEDDED), size, style);
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return chineseFont;
	}
}
