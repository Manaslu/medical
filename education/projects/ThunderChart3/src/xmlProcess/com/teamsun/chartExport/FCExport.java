package com.teamsun.chartExport;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.imageio.ImageWriter;
import javax.imageio.ImageIO;
import javax.imageio.*;
import javax.imageio.ImageWriteParam;
import java.awt.image.BufferedImage;
import javax.imageio.stream.ImageOutputStream; 




public class FCExport extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public FCExport() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out
				.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the GET method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
	    // Extract parameters
	    int width = Integer.parseInt(req.getParameter("width")) - 1;
	    int height = Integer.parseInt(req.getParameter("height"));
	    String bgColorStr = req.getParameter("bgcolor");
	    int bgColor = (bgColorStr == null || bgColorStr.length() == 0) ? 0xffffff : Integer.parseInt(bgColorStr, 16);
	    String data = req.getParameter("data");
	   
	    // Build the bitmap image
	    BufferedImage image = buildBitmap(width, height, bgColor, data);
	   
	    // Compress the image as a JPEG
	    ImageWriter writer = (ImageWriter) ImageIO.getImageWritersByFormatName("jpg").next();
	    ImageWriteParam writerParam = writer.getDefaultWriteParam();
	    writerParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
	    writerParam.setCompressionQuality(0.95f);

	    // Stream the image to the user agent
	    resp.addHeader("Content-Disposition", "attachment; filename=\"FusionCharts.jpg\"");
	    resp.setContentType("image/jpeg");
	    ImageOutputStream imageOut = ImageIO.createImageOutputStream(resp.getOutputStream());
	    writer.setOutput(imageOut);
	    writer.write(null, new IIOImage(image, null, null), writerParam);
	    imageOut.flush();
	    imageOut.close();
	}

	
	private BufferedImage buildBitmap(int width, int height, int bgColor, String data)
	{
	    BufferedImage chart = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

	    String[] rows = data.split(";");
	    int colIdx = 0;
	    for (int rowIdx = 0; rowIdx < rows.length; rowIdx++)
	    {
	        // Split individual pixels
	        String[] pixels = rows[rowIdx].split(",");
	        colIdx = 0;
	        for (int pixelIdx = 0; pixelIdx < pixels.length; pixelIdx++)
	        {               
	            // Split the color and repeat factor
	            String[] clrs = pixels[pixelIdx].split("_");  
	            int color = ("".equals(clrs[0])) ? bgColor : Integer.parseInt(clrs[0], 16);
	            int repeatFactor = Integer.parseInt(clrs[1]);
	           
	            // Set the color the specified number of times
	            for (int repeatCount = 0; repeatCount < repeatFactor; repeatCount++, colIdx++)
	            {                      
	                chart.setRGB(colIdx, rowIdx, color);
	            }
	        }
	    }
	   
	    return chart;
	} 
	
	
	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occure
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
