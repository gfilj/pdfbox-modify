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

package org.apache.pdfbox.util;

import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.text.CustomPDFTextStripperByArea;
import org.apache.pdfbox.text.LegacyPDFStreamEngine;

/**
 * Examples of various different ways to print PDFs using PDFBox.
 */
public final class PdfUtil {

	public static final int IMG_TEXT_OFFSET = 40;
	public static final int TEXT_LINE = 20;

	/**
	 * This will print the usage for this document.
	 */
	private static void usage() {
		System.err.println("Usage: java " + PdfUtil.class.getName() + " <input-pdf>");
	}

	/**
	 * This will print the documents data.
	 *
	 * @param args
	 *            The command line arguments.
	 * @throws IOException
	 *             If there is an error parsing the document.
	 */
	public static void main(String[] args) {

		args = new String[] { "d:\\pdf\\10.pdf" };
		// args = new
		// String[]{"/Users/apple/Downloads/LGX及9LGIA、B接触器类电动机中压柜保护预防性维修.pdf"};
		// args = new String[]{"/Users/apple/Downloads/13--带有下标[第8页].pdf"};

		for (int argsI = 0; argsI < args.length; argsI++) {
			System.out.println(TextStripperFromPdf(args[argsI], 0, 0));
		}

	}

	public static String TextStripperFromPdf(String filename) {
		return TextStripperFromPdf(filename, 0, 0);
	}
	
	public static String TextStripperFromPdf(String filename,int FlagPicTitle) {
		return TextStripperFromPdf(filename, FlagPicTitle, 0);
	}
	
	public static String TextStripperFromPdf(String filename, int FlagPicTitle, int FlagTable) {

		try (PDDocument document = PDDocument.load(new File(filename))) {

			System.out.println("文件名：" + filename);
			new LegacyPDFStreamEngine();
			CustomPDFTextStripperByArea stripper = new CustomPDFTextStripperByArea(FlagPicTitle, FlagTable);
			stripper.addRegion("content", new Rectangle(50, 110, 500, 800));
			String content = "";
			
			for (int i = 5; i < document.getNumberOfPages(); i++) {
				PDPage page = document.getPage(i);
				stripper.extractRegions(page);
				String[] contents = stripper.getTextForRegion("content").split("\n");
				content += stripper.getTextForRegion("content");
			}
			return content;
		} catch (IOException e) {
			return e.getMessage();
		}

	}
}
