package com.idp.workflow.util.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.idp.workflow.exception.pub.WfBusinessException;
import com.idp.workflow.util.common.StringUtil;

/**
 * 轻量缓存
 * 
 * @author panfei
 * 
 */
public class MiniCache {

	private static int MaxSize = 300;

	private static final class Node {
		public String key = null;
		public Map<String, Object> paramsMap = new HashMap<String, Object>();
		public Node next = null;
	}

	private static Node[] nodes = new Node[MaxSize];

	private static ReentrantReadWriteLock[] wrlockers = new ReentrantReadWriteLock[MaxSize];

	static {
		for (int i = 0; i < wrlockers.length; i++) {
			wrlockers[i] = new ReentrantReadWriteLock();
		}
	}

	private static int hashCode(String key) {
		int hash = key.hashCode() % MaxSize;
		if (hash < 0)
			hash *= -1;
		return hash;
	}

	private int readLock(String key) {
		int nodeindex = hashCode(key);
		wrlockers[nodeindex].readLock().lock();
		return nodeindex;
	}

	private void readUnLock(int index) {
		wrlockers[index].readLock().unlock();
	}

	private int writeLock(String key) {
		int nodeindex = hashCode(key);
		wrlockers[nodeindex].writeLock().lock();
		return nodeindex;
	}

	private void writeUnLock(int index) {
		wrlockers[index].writeLock().unlock();
	}

	public void putVariables(String key, Map<String, Object> paramsMap)
			throws WfBusinessException {
		if (StringUtil.isEmpty(key)) {
			throw new WfBusinessException("key不能为空！");
		}
		int index = this.writeLock(key);
		try {
			Node searchNode = nodes[index];
			if (searchNode == null) {
				Node addone = new Node();
				addone.key = key;
				addone.paramsMap = paramsMap;
				nodes[index] = addone;
			} else {
				Node addend = null;
				while (searchNode != null) {
					addend = searchNode;
					searchNode = searchNode.next;
				}
				Node addone = new Node();
				addone.key = key;
				addone.paramsMap = paramsMap;
				addend.next = addone;
			}
		} finally {
			this.writeUnLock(index);
		}
	}

	public Map<String, Object> getVariables(String key)
			throws WfBusinessException {
		if (StringUtil.isEmpty(key)) {
			throw new WfBusinessException("key不能为空！");
		}
		int index = this.readLock(key);
		try {
			Node searchNode = nodes[index];
			while (searchNode != null) {
				if (key.equals(searchNode.key)) {
					return searchNode.paramsMap;
				}
				searchNode = searchNode.next;
			}
			return null;
		} finally {
			this.readUnLock(index);
		}
	}

	public void clear(String key) throws WfBusinessException {
		if (StringUtil.isEmpty(key)) {
			throw new WfBusinessException("key不能为空！");
		}
		int index = this.writeLock(key);
		try {
			Node searchNode = nodes[index];
			Node prevNode = null;
			int count = 0;
			while (searchNode != null) {
				if (key.equals(searchNode.key)) {
					if (count == 0) {
						nodes[index] = searchNode.next;
					} else {
						prevNode.next = searchNode.next;
					}
					break;
				}
				count++;
				prevNode = searchNode;
				searchNode = searchNode.next;
			}
		} finally {
			this.writeUnLock(index);
		}
	}
}
