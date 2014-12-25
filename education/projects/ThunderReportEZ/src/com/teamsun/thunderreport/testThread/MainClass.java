package com.teamsun.thunderreport.testThread;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.teamsun.thunderreport.util.ApplicationPro;

public class MainClass {
	public Object lockObject=new Object();
	public void Test(){
		final Thread thread1=new Thread(
			new Runnable(){	
				public void run(){
					while(true){
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
					 	}
						System.out.println("execute thread1");
						
					}
				}
			}
		);
		thread1.start();
		Thread thread2=new Thread(
				new Runnable(){	
					public void run(){
						for(int i=0;i<1;i++){
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}							
						}
						if(thread1.isAlive()){
							thread1.stop();
						}
						System.out.println("thread1 stop");
						synchronized(lockObject){
							lockObject.notifyAll();
						}
					}
				}
			);		
		thread2.start();
		while(thread2.isAlive()||thread1.isAlive()){
			synchronized(lockObject){
			try {
				lockObject.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		}
		System.out.println("thread2 stop !");
	}
	public void Test2(){
		int a=10/6;
		System.out.println(a);
	}
	
	public void Test3(final Page page){
		page.setName("lkt");
		System.out.println(page.getName());
	}
	
	public void  Test4(){
		final Page p=new Page();
		p.setName("nihao");
		System.out.println(p.getName());
		Test3(p);
	}
	public void Test5(){
		System.out.println("beging time="+new Date());
		String time="13:08:00";
		Date curDate=new Date();
		String dateStr=(new SimpleDateFormat("yyyyMMdd")).format(curDate);
		time=dateStr+" "+time;		

		try {
			Date d=new SimpleDateFormat("yyyyMMdd HH:mm:ss").parse(time);
			
			System.out.println((curDate.getTime()-d.getTime())/1000);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("end time="+new Date());
	}
	public void Test6(){
		FileReader fr=null;
		try {
		    fr=new FileReader("c:/lkt1.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(fr!=null){
			try {
				fr.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void Test7(){
		final Thread thread1=new Thread(
				new Runnable(){	
					public void run(){
						while(true){
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
						 	}
							System.out.println("execute thread1");
							
						}
					}
				}
			);
		Thread thread2=new Thread(
				new Runnable(){	
					public void run(){
						for(int i=0;i<10;i++){
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}		
							System.out.println("execute Thread2");
						}
						if(thread1.isAlive()){
							thread1.stop();
						}
						System.out.println("thread1 stop");
						
					}
				}
			);	
		thread1.run();
		thread2.run();
	}
	public void Test8(){
		String html = "<table border=\"1\" width=\"100%\">";
		html += "<p align=\"center\"> 各产品活跃用户趋势</p><tr class=\"tr_head\"> <td colspan=\"13\" align=\"center\">#Prd_Name#</td> </tr> <tr class=\"tr_head\"> <td rowspan=\"2\" align=\"center\">日期 </td> <td rowspan=\"2\" align=\"center\">详单收入 </td> <td rowspan=\"2\" align=\"center\">用户到达数 </td> <td colspan=\"5\" align=\"center\">能拨叫用户 </td> <td colspan=\"5\" align=\"center\">不能拨叫用户 </td> </tr> <tr class=\"tr_head\"> <td align=\"center\">活跃用户数</td> <td align=\"center\">沉默用户数</td> <td align=\"center\">新增活跃用户</td> <td align=\"center\">当月累计活跃用户到达数</td> <td align=\"center\">当日过网活跃用户数</td> <td align=\"center\">欠费单停数</td> <td align=\"center\">欠费双停数</td> <td align=\"center\">停机保号数</td> <td align=\"center\">欠费三个月有来话用户数</td> <td align=\"center\">总计</td> </tr><tr><td>2008-01-01</td><td>19676.91</td><td>10029.00</td><td>999.00</td><td>8427.00</td><td>-78.00</td><td>999.00</td><td>0.00</td><td>0.00</td><td>0.00</td><td>603.00</td><td>0.00</td></tr>";
		html += "<tr><td>2008-01-02</td><td>50399.58</td><td>10035.00</td><td>1356.00</td><td>8076.00</td><td>357.00</td><td>1374.00</td><td>0.00</td><td>0.00</td><td>0.00</td><td>603.00</td><td>0.00</td></tr>";
		html += "<tr><td>2008-01-03</td><td>52968.69</td><td>10026.00</td><td>1317.00</td><td>8106.00</td><td>-39.00</td><td>1401.00</td><td>0.00</td><td>0.00</td><td>0.00</td><td>603.00</td><td>0.00</td></tr>";
		html += "<tr><td>2008-01-04</td><td>764.28</td><td>10026.00</td><td>276.00</td><td>9147.00</td><td>-1041.00</td><td>1401.00</td><td>0.00</td><td>0.00</td><td>0.00</td><td>603.00</td><td>0.00</td></tr>";
		html += "<tr><td>2008-01-05</td><td>14670.51</td><td>10026.00</td><td>681.00</td><td>8742.00</td><td>405.00</td><td>1401.00</td><td>0.00</td><td>0.00</td><td>0.00</td><td>603.00</td><td>0.00</td></tr>";
		html += "</table>";
		try {
			//String newHtml=new String(html.getBytes("gb2312"));
			FileOutputStream out = new FileOutputStream(new File("c:\\lkt.xls"));			
		    out.write(html.getBytes("utf-8"));
		    out.flush();	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void Test9() {		
		String[][] date=new String[2][7];
		for (int i = 0; i < 7; i++) {	
			Calendar c = Calendar.getInstance();
			c.add(Calendar.DATE, i-7);
			Date d = c.getTime();
			String dateStr = (new SimpleDateFormat("yyyy-MM-dd")).format(d);
			date[0][i]=dateStr;
			date[1][i]=dateStr;
		}
		String[][] date2=(String[][])date.clone();
		System.out.println(date2.length);
	}
	
	public void Test10(){
		int i=6000000;
		String[] arr=new String[i];
		System.out.println("nihao");
	}
	public void Test11(){
		String funExp="DIVIDE(U1,U0)";
		if(funExp.indexOf("DIVIDE")>-1){
			int i=funExp.indexOf("DIVIDE");
			int j=funExp.indexOf(",");
			String colo=funExp.substring(i+7, j);
			String col1=funExp.substring(j+1, funExp.length()-1);
			System.out.println("nihao");
		}
	}
	
	class Page{
		String name="";

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}		
	}
public static void main(String[] args){	
	new MainClass().Test11();	
	System.out.println("Thread shutdown");
}
}
