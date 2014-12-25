package com.teamsun.framework.ui;
import java.util.Comparator;
public class MyComparator implements Comparator
{

	public int compare(Object o1, Object o2)
	{
		String attr1=(String)o1;
		String attr2=(String)o2;
		return -(Integer.parseInt(attr1)-Integer.parseInt(attr2));
	}

}
