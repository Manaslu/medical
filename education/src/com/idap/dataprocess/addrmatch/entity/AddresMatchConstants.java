package com.idap.dataprocess.addrmatch.entity;

import java.io.Serializable;

public final class AddresMatchConstants implements Serializable {

	private static final long serialVersionUID = 3565520655726718821L;

	public final static boolean USE_ADDRES_FLAG = true;

	public final static String MATCH_END = "1";// 地址匹配结束标志

	public final static int MATCH_SLEEP_TIMES = 3;// 10秒

	public final static int BATCH_SIZE = 2000;

	public final static String APPEND_FLAG_YES = "已追加";// 追加标志

	public final static String APPEND_FLAG_NO = "无追加";// 追加标志

	public final static String ADDRES_NAME_COLUMN = "0";// 详细地址在表中的字段列的序号

	public final static int UNEXISTS_ADDR_NAME_COLUMN = -1;// 详细地址在表中不存在

	public final static int MATCHED_DEGRE = 90;// 置信度

	public final static int MATCH_ADDR_COLUMNS = 15;// 地址匹配所產生的字段总数

	public final static String ADDRESS_SCRIPT_CODE = "address";// 地址匹配所產生的字段总数

	public final static String POST_SCRIPT_CODE = "post";// 地址匹配所產生的字段总数
}
