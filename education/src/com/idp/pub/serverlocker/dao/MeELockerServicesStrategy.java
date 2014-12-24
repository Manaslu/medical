package com.idp.pub.serverlocker.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.idp.pub.serverlocker.entity.LockInfoEntity;
import com.idp.pub.serverlocker.entity.Paramters;
import com.idp.pub.serverlocker.utils.LockInfoEntityUtil;

/**
 * 排它锁的内存实现<br>
 * 
 * @author panei
 * 
 */
public class MeELockerServicesStrategy implements ILockerDaoStrategy {

	/**
	 * 查询结果
	 * 
	 * @author panfei
	 * 
	 */
	private static final class QueryResult {
		private boolean isExist;
		private Node lockNode;

		public boolean isExist() {
			return isExist;
		}

		public Node getLockNode() {
			return lockNode;
		}

		public QueryResult(Node locknode, boolean isexist) {
			this.isExist = isexist;
			this.lockNode = locknode;
		}
	}

	/**
	 * 一级缓存节点、离散存储
	 * 
	 * @author panfei
	 * 
	 */
	private static final class Node {
		public String key = null;
		public Map<String, LockInfoEntity> lockmap = new HashMap<String, LockInfoEntity>(
				50);
		public Node next = null;
	}

	private static int MaxSize = 300;

	private static Node[] nodes = new Node[MaxSize];

	private static ReentrantReadWriteLock[] wrlockers = new ReentrantReadWriteLock[MaxSize];

	static {
		for (int i = 0; i < wrlockers.length; i++) {
			wrlockers[i] = new ReentrantReadWriteLock();
		}
	}

	/**
	 * 生成离散索引
	 * 
	 * @param key
	 *            锁值
	 * @return
	 */
	private static int hashCode(String key) {
		int hash = key.hashCode() % MaxSize;
		if (hash < 0)
			hash *= -1;
		return hash;
	}

	/**
	 * 生成离散索引
	 * 
	 * @param key
	 *            锁值
	 * @return
	 */
	@SuppressWarnings("unused")
	private static int hashCode2(String key) {
		int hash = key.hashCode();
		hash ^= (hash >>> 20) ^ (hash >>> 12);
		hash = hash ^ (hash >>> 7) ^ (hash >>> 4);
		return hash & (MaxSize - 1);
	}

	/**
	 * 加读锁
	 * 
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unused")
	private int readLock(Paramters params) {
		return readLock(params.toString());
	}

	/**
	 * 加读锁
	 * 
	 * @param nodeky
	 * @return
	 */
	private int readLock(String nodeky) {
		int nodeindex = hashCode(nodeky);
		wrlockers[nodeindex].readLock().lock();
		return nodeindex;
	}

	/**
	 * 解读锁
	 * 
	 * @param index
	 */
	private void readUnLock(int index) {
		wrlockers[index].readLock().unlock();
	}

	/**
	 * 加写锁
	 * 
	 * @param nodeky
	 * @return
	 */
	private int writeLock(String nodeky) {
		int nodeindex = hashCode(nodeky);
		wrlockers[nodeindex].writeLock().lock();
		return nodeindex;
	}

	/**
	 * 加写锁
	 * 
	 * @param index
	 */
	private void writeLock(int index) {
		wrlockers[index].writeLock().lock();
	}

	/**
	 * 解写锁
	 * 
	 * @param index
	 */
	private void writeUnLock(int index) {
		wrlockers[index].writeLock().unlock();
	}

	/**
	 * 
	 * @param nodeindex
	 * @param nodekey
	 * @param key
	 * @param repeatLock
	 * @return
	 */
	private boolean Locking(QueryResult result, int nodeindex, String nodekey,
			String key, boolean repeatLock) {
		readUnLock(nodeindex);
		writeLock(nodeindex);
		try {
			if (nodes[nodeindex] == null) {
				nodes[nodeindex] = new Node();
				nodes[nodeindex].key = nodekey;
				nodes[nodeindex].lockmap.put(key,
						LockInfoEntityUtil.createMeLockInfoEntity());
				return true;
			}

			Node needlock = result.getLockNode();
			if (nodekey.equals(needlock.key)) {
				if (needlock.lockmap.containsKey(key)) {
					if (repeatLock
							&& needlock.lockmap.get(key).getThreadId() == Thread
									.currentThread().getId()) {
						return true;
					}
					return false;
				}
				needlock.lockmap.put(key,
						LockInfoEntityUtil.createMeLockInfoEntity());
				return true;
			}
			Node locknode = new Node();
			locknode.key = nodekey;
			locknode.lockmap.put(key,
					LockInfoEntityUtil.createMeLockInfoEntity());
			needlock.next = locknode;
			return true;
		} finally {
			wrlockers[nodeindex].readLock().lock();
			writeUnLock(nodeindex);
		}
	}

	/**
	 * 
	 * @param result
	 * @param nodeindex
	 * @param nodekey
	 * @param key
	 * @param repeatLock
	 * @return
	 */
	private boolean Locking(QueryResult[] result, int nodeindex,
			String nodekey, String[] keys, boolean repeatLock) {
		readUnLock(nodeindex);
		writeLock(nodeindex);
		try {
			if (nodes[nodeindex] == null) {
				nodes[nodeindex] = new Node();
				nodes[nodeindex].key = nodekey;
				for (String key : keys) {
					nodes[nodeindex].lockmap.put(key,
							LockInfoEntityUtil.createMeLockInfoEntity());
				}
				return true;
			}

			Node needlock = result[0].getLockNode();
			if (nodekey.equals(needlock.key)) {
				for (String key : keys) {
					if (needlock.lockmap.containsKey(key)) {
						if (repeatLock
								&& needlock.lockmap.get(key).getThreadId() == Thread
										.currentThread().getId()) {
							// return true;
						}
						return false;
					}
					needlock.lockmap.put(key,
							LockInfoEntityUtil.createMeLockInfoEntity());
					// return true;
				}
				return true;
			}

			Node locknode = new Node();
			locknode.key = nodekey;
			for (String key : keys) {
				locknode.lockmap.put(key,
						LockInfoEntityUtil.createMeLockInfoEntity());
			}
			needlock.next = locknode;
			return true;
		} finally {
			wrlockers[nodeindex].readLock().lock();
			writeUnLock(nodeindex);
		}
	}

	/**
	 * 检查是否可以锁住
	 * 
	 * @param nodeindex
	 *            节点索引
	 * @param key
	 *            待锁键值
	 * @param repeatLock
	 *            是否可以重复加锁
	 * @return
	 */
	private QueryResult canLock(int nodeindex, String nodekey, String key,
			boolean repeatLock) {
		if (nodes[nodeindex] == null) {
			return new QueryResult(null, false);
		}
		Node tempNode = nodes[nodeindex];
		while (tempNode != null) {
			if (nodekey.equals(tempNode.key)) {
				if (tempNode.lockmap.containsKey(key)) {
					if (repeatLock
							&& tempNode.lockmap.get(key).getThreadId() == Thread
									.currentThread().getId()) {
						return new QueryResult(tempNode, false);
					}
					return new QueryResult(tempNode, true);
				}
				return new QueryResult(tempNode, false);
			}
			tempNode = tempNode.next;
		}
		return new QueryResult(tempNode, false);
	}

	/**
	 * 检查是否可以锁住
	 * 
	 * @param nodeindex
	 *            节点索引
	 * @param nodekey
	 *            节点键值
	 * @param keys
	 *            需要加锁的列表
	 * @param repeatLock
	 *            是否可以重复加锁
	 * @return
	 */
	private QueryResult[] canLock(int nodeindex, String nodekey, String[] keys,
			boolean repeatLock) {
		if (nodes[nodeindex] == null) {
			return buildBatchQueryResults(keys.length, null, false);
		}
		Node tempNode = nodes[nodeindex];
		while (tempNode != null) {
			if (nodekey.equals(tempNode.key)) {
				List<QueryResult> list = new ArrayList<QueryResult>();
				for (int i = 0; i < keys.length; i++) {
					if (tempNode.lockmap.containsKey(keys[i])) {
						if (repeatLock
								&& tempNode.lockmap.get(keys[i]).getThreadId() == Thread
										.currentThread().getId()) {
							list.add(new QueryResult(tempNode, false));
						}
						list.add(new QueryResult(tempNode, true));
					}
					list.add(new QueryResult(tempNode, false));
				}
				return list.toArray(new QueryResult[list.size()]);
			}
			tempNode = tempNode.next;
		}
		return buildBatchQueryResults(keys.length, tempNode, false);
	}

	/**
	 * 
	 * @param size
	 * @param node
	 * @param iseixst
	 * @return
	 */
	private static QueryResult[] buildBatchQueryResults(int size, Node node,
			boolean iseixst) {
		QueryResult[] result = new QueryResult[size];
		for (int i = 0; i < result.length; i++) {
			result[i] = new QueryResult(node, iseixst);
		}
		return result;
	}

	@Override
	public boolean lock(String key, Paramters params) {
		String nodekey = params.toString();
		int nodeindex = readLock(nodekey);
		try {
			QueryResult result = canLock(nodeindex, nodekey, key,
					params.isRepeatLock());
			if (result.isExist()) {
				return false;
			}
			return Locking(result, nodeindex, nodekey, key,
					params.isRepeatLock());
		} finally {
			readUnLock(nodeindex);
		}
	}

	@Override
	public boolean lock(String[] keys, Paramters params) {
		String nodekey = params.toString();
		int nodeindex = readLock(nodekey);
		try {
			QueryResult[] result = canLock(nodeindex, nodekey, keys,
					params.isRepeatLock());
			if (result == null) {
				return false;
			}
			for (QueryResult temp : result) {
				if (temp.isExist()) {
					return false;
				}
			}
			return Locking(result, nodeindex, nodekey, keys,
					params.isRepeatLock());
		} finally {
			readUnLock(nodeindex);
		}
	}

	@Override
	public boolean unlock(String key, Paramters params) {
		String nodekey = params.toString();
		int nodeindex = writeLock(nodekey);
		try {
			Node tempNode = nodes[nodeindex];
			while (tempNode != null) {
				if (nodekey.equals(tempNode.key)) {

					if (tempNode.lockmap.get(key) != null
							&& tempNode.lockmap.get(key).getThreadId() == Thread
									.currentThread().getId()) {
						tempNode.lockmap.remove(key);
					}
					break;
				}
				tempNode = tempNode.next;
			}
			return true;
		} finally {
			writeUnLock(nodeindex);
		}
	}

	@Override
	public boolean unlock(String[] keys, Paramters params) {
		String nodekey = params.toString();
		int nodeindex = writeLock(nodekey);
		try {
			Node tempNode = nodes[nodeindex];
			while (tempNode != null) {
				if (nodekey.equals(tempNode.key)) {
					for (String delkey : keys) {
						if (tempNode.lockmap.get(delkey) != null
								&& tempNode.lockmap.get(delkey).getThreadId() == Thread
										.currentThread().getId()) {
							tempNode.lockmap.remove(delkey);
						}
					}
					break;
				}
				tempNode = tempNode.next;
			}
			return true;
		} finally {
			writeUnLock(nodeindex);
		}
	}

	@Override
	public boolean islocked(String key, Paramters params) {
		String nodekey = params.toString();
		int nodeindex = readLock(nodekey);
		try {
			if (nodes[nodeindex] == null) {
				return false;
			}
			Node checknode = nodes[nodeindex];
			while (checknode != null) {
				if (nodekey.equals(checknode.key)) {
					if (checknode.lockmap.containsKey(key)) {
						return true;
					}
				}
				checknode = checknode.next;
			}
			return false;
		} finally {
			readUnLock(nodeindex);
		}
	}
}
