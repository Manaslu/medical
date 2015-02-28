package com.hjtp.incas.chart;

import java.awt.Label;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class FileExp {

	 /**
	  * ȡ��ͼ�������Ϣ
	  * @param chart:BufferedImage����
	  * @return ByteArrayOutputStream����
	  * @author qingbao-gao
	  * <p>Date:2010-03-03 PM 15:16</p> 
	  */
	 public ByteArrayOutputStream savePicture(BufferedImage chart)
	 {
		  ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
		  try 
		  {
		       ImageIO.write(chart, "jpeg", byteArrayOut);
		  } 
		  catch (IOException e) 
		  {
		       e.printStackTrace();
		  }
		  return byteArrayOut;
	 }
	 /**   
	  *<p>ת��Image���Ϊbyte����</p>   
	  *@param bImage:BufferedImage����   
	  *@param format:image��ʽ�ַ�.��"jpeg","png"   
	  *@return   byte���� 
	  *@author qingbao-gao
	  *<p>Date:2010-03-03 PM 15:16</p>  
	  */   
	  public   static   byte[]  imageToBytes(BufferedImage   bImage, String   format)   
	  {   
			ByteArrayOutputStream   out   =   new   ByteArrayOutputStream();   
			try   
			{   
			    ImageIO.write(bImage,   format,   out);   
			}   
			catch   (IOException   e)   
			{   
				e.printStackTrace();
			}   
			return   out.toByteArray();   
	   }
	   /**   
		*<p>ת��byte����ΪImage</p>   
		*@param   bytes:Image��bytes�������   
		*@return  Image:��׼ͼ�����
		*@author qingbao-gao
		*<p>Date:2010-03-03 PM 15:19</p>
		*/   
		public   static   java.awt.Image   bytesToImage(byte[]   bytes)   
		{   
		    java.awt.Image   image=null;   
			try   
			{   
				image   =   Toolkit.getDefaultToolkit().createImage(bytes);
				MediaTracker   mt   =   new   MediaTracker(new   Label());   
				mt.addImage(image,   0);   
				mt.waitForAll();   
			}   
			catch(InterruptedException   e)   
			{   
				e.printStackTrace()  ;
			}   
			return   image;   
		}
}
