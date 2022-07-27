package com.example.demo.leetcode.bloom;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Objects;

/**
 * BloomFilter
 *
 * @author chengzhe yan
 * @description : 布隆过滤器
 * @link : https://github.com/apache/orc/blob/93af6b076c210b0c3b77e5af3d6fbef1bd1150a1/java/core/src/java/org/apache/orc/util/BloomFilter.java#L84
 * @lonk : https://juejin.cn/post/6937174232157650951
 * @date 2021/10/7 3:55 下午
 */
public class BloomFilter {
	/**
	 * error entries : 判断异常概率
	 */
	public static final double DEFAULT_FPP = 0.05;

	private final BitSet bitSet;
	private final int numBits;
	private final int numHashFunctions;

	/**
	 * 计算最优哈希函数数量
	 *
	 * @param n
	 * @param m
	 * @return
	 */
	static int optimalNumOfHashFunction(long n, long m) {
		return Math.max(1, (int) Math.round((double) m / n * Math.log(2)));
	}

	/**
	 * 计算最优 bit 数量
	 *
	 * @param number
	 * @param p
	 * @return
	 */
	static int optimalNumOfBits(long number, double p) {
		return (int) (-number * Math.log(p) / (Math.log(2) * Math.log(2)));
	}

	static void checkArgument(boolean expression, String message) {
		if (!expression) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * @param expectedEntries 期望保存的数据量
	 */
	public BloomFilter(long expectedEntries) {
		this(expectedEntries, DEFAULT_FPP);
	}

	/**
	 * @param expectedEntries 期望保存的数据量
	 * @param fpp             误差率
	 */
	public BloomFilter(long expectedEntries, double fpp) {
		expectedEntries = Math.max(expectedEntries, 1);
		checkArgument(fpp > 0.0 && fpp < 1.0, "false positive probability should be > 0.0 && < 1.0");
		int nb = optimalNumOfBits(expectedEntries, fpp);
		// 保证 位数 是 64 的倍数
		this.numBits = nb + (Long.SIZE - (nb % Long.SIZE));
		// 计算最优哈希函数数量
		this.numHashFunctions = optimalNumOfHashFunction(expectedEntries, numBits);
		this.bitSet = new BitSet(numBits);
	}

	/**
	 * 根据指定位 和 函数数量进行初始化操作
	 *
	 * @param bits
	 * @param numFunctions
	 */
	public BloomFilter(long[] bits, int numFunctions) {
		super();
		bitSet = new BitSet(bits);
		this.numBits = (int) bitSet.bitSize();
		this.numHashFunctions = numFunctions;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		BloomFilter that = (BloomFilter) o;
		return numBits == that.numBits && numHashFunctions == that.numHashFunctions && Objects.equals(bitSet, that.bitSet);
	}

	@Override
	public int hashCode() {
		return bitSet.hashCode() + numHashFunctions * 5;
	}

	@Override
	public String toString() {
		return "numBits: " + numBits + " numHashFunctions: " + numHashFunctions;
	}

	public void add(byte[] val) {
		addBytes(val, 0, val == null ? 0 : val.length);
	}

	public void addBytes(byte[] val, int offset, int length) {
		long hash64 = val == null ? Murmur3.NULL_HASHCODE : Murmur3.hash64(val, offset, length);
		addHash(hash64);
	}


	public void addString(String val) {
		if (val == null) {
			add(null);
		} else {
			add(val.getBytes(Charset.defaultCharset()));
		}
	}

	public void addLong(long val) {
		addHash(getLongHash(val));
	}

	public void addDouble(double val) {
		addHash(Double.doubleToLongBits(val));
	}

	private void addHash(long hash64) {
		int hash1 = (int) hash64;
		int hash2 = (int) (hash64 >>> 32);
		// 将多个 hash 函数对应的数值置为 true
		for (int i = 0; i < numHashFunctions; i++) {
			int combineHash = hash1 + (i * hash2);
			if (combineHash < 0) {
				combineHash = ~combineHash;
			}
			int pos = combineHash % numBits;
			bitSet.set(pos);
		}
	}


	public boolean test(byte[] val) {
		return testBytes(val, 0, val == null ? 0 : val.length);

	}

	public boolean testBytes(byte[] val, int offset, int length) {
		long hash64 = val == null ? Murmur3.NULL_HASHCODE : Murmur3.hash64(val, offset, length);
		return testHash(hash64);
	}

	public boolean testString(String val) {
		if (val == null) {
			return test(null);
		} else {
			return test(val.getBytes(Charset.defaultCharset()));
		}
	}

	public boolean testLong(long val) {
		return testHash(getLongHash(val));
	}

	public boolean testDouble(double val) {
		return testHash(Double.doubleToLongBits(val));
	}

	private boolean testHash(long hash64) {
		int hash1 = (int) hash64;
		int hash2 = (int) (hash64 >>> 32);
		// 将多个 hash 函数对应的数值置为 true
		for (int i = 0; i < numHashFunctions; i++) {
			int combineHash = hash1 + (i * hash2);
			if (combineHash < 0) {
				combineHash = ~combineHash;
			}
			int pos = combineHash % numBits;
			// 只要有一个不是 true ,那么就可以返回 false
			if (!bitSet.get(pos)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * http://web.archive.org/web/20071223173210/http://www.concentric.net/~Ttwang/tech/inthash.htm
	 *
	 * @param key
	 * @return
	 */
	private long getLongHash(long key) {
		// key = key << 21 - key -1
		key = (~key) + (key << 21);
		key = key ^ (key >> 24);
		// key * 256
		key = (key + (key << 3)) + (key << 8);
		key = key ^ (key >> 14);
		// key * 21
		key = ((key) + (key << 2)) + (key << 4);
		key = key ^ (key >> 28);
		key = key + (key << 31);
		return key;
	}


	public long sizeInBytes() {
		return getBitSize() / 8;
	}

	public int getBitSize() {
		return (bitSet.getData().length * Long.SIZE);
	}

	public int getNumHashFunctions() {
		return numHashFunctions;
	}

	public long[] getBitSet() {
		return bitSet.getData();
	}

	/**
	 * 合并两个布隆过滤器
	 *
	 * @param bloomFilter
	 */
	public void merge(BloomFilter bloomFilter) {
		if (this != bloomFilter && numBits == bloomFilter.numBits && numHashFunctions == bloomFilter.numHashFunctions) {
			this.bitSet.putAll(bloomFilter.bitSet);
		} else {
			throw new IllegalArgumentException("BloomFilter are not compatible for merging. " +
					" this - " + this.toString() + " that - " + bloomFilter.toString());
		}
	}

	public void reset() {
		this.bitSet.clear();
	}

	public static class BitSet {
		private final long[] data;

		public BitSet(long bits) {
			this(new long[(int) Math.ceil((double) bits / (double) Long.SIZE)]);
		}


		public BitSet(long[] data) {
			assert data.length > 0 : "data length is zero!!!";
			this.data = data;
		}

		public void set(int index) {
			data[index >>> 6] |= (1L << index);
		}

		public boolean get(int index) {
			return (data[index >>> 6] & (1L << index)) != 0;
		}

		public long bitSize() {
			return (long) data.length * Long.SIZE;
		}

		public long[] getData() {
			return data;
		}

		public void putAll(BitSet bitSet) {
			assert data.length == bitSet.data.length : "Bit array size must be of equal length (" + data.length + "!=" + bitSet.data.length + ")";
			for (int i = 0; i < data.length; i++) {
				data[i] |= bitSet.data[i];
			}
		}

		public void clear() {
			Arrays.fill(data, 0);
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) {
				return true;
			}
			if (o == null || getClass() != o.getClass()) {
				return false;
			}
			BitSet bitSet = (BitSet) o;
			return Arrays.equals(data, bitSet.data);
		}

		@Override
		public int hashCode() {
			return Arrays.hashCode(data);
		}
	}

}
