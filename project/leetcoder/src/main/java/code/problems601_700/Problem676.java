package code.problems601_700;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author chengzhe yan
 * @description
 * @date 2022/7/11 14:58
 */
public class Problem676 {

	/**
	 * ["MagicDictionary", "buildDict", "search", "search", "search", "search"]
	 * [[], [["hello","leetcode"]], ["hello"], ["hhllo"], ["hell"], ["leetcoded"]]
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		MagicDictionary magicDictionary = new MagicDictionary();
		magicDictionary.buildDict(new String[]{"hello", "leetcode", "hallo"});
		System.out.println(magicDictionary.search("hello"));
		System.out.println(magicDictionary.search("hallo"));
		System.out.println(magicDictionary.search("hell"));
		System.out.println(magicDictionary.search("leetcoded"));
	}

}

class MagicDictionary2 {
	Trie root;

	public MagicDictionary2() {
		root = new Trie();
	}

	public void buildDict(String[] dictionary) {
		for (String word : dictionary) {
			Trie cur = root;
			for (int i = 0; i < word.length(); i++) {
				char ch = word.charAt(i);
				int idx = ch - 'a';
				if (cur.child[idx] == null) {
					cur.child[idx] = new Trie();
				}
				cur = cur.child[idx];
			}
			cur.isFinished = true;
		}
	}

	public boolean search(String searchWord) {
		return dfs(searchWord, root, 0, false);
	}

	private boolean dfs(String searchWord, Trie node, int pos, boolean modified) {
		if (pos == searchWord.length()) {
			return modified && node.isFinished;
		}
		int idx = searchWord.charAt(pos) - 'a';
		if (node.child[idx] != null) {
			if (dfs(searchWord, node.child[idx], pos + 1, modified)) {
				return true;
			}
		}
		if (!modified) {
			for (int i = 0; i < 26; i++) {
				if (i != idx && node.child[i] != null) {
					if (dfs(searchWord, node.child[i], pos + 1, true)) {
						return true;
					}
				}
			}
		}
		return false;
	}
}

class Trie {
	boolean isFinished;
	Trie[] child;

	public Trie() {
		this.isFinished = false;
		this.child = new Trie[26];
	}
}

class MagicDictionary {

	Map<Integer, Set<String>> data = new HashMap<>();

	public MagicDictionary() {
	}

	public void buildDict(String[] dictionary) {

		for (String s : dictionary) {
			if (data.containsKey(s.length())) {
				data.get(s.length()).add(s);
			} else {
				Set<String> dataSet = new HashSet<>();
				dataSet.add(s);
				data.put(s.length(), dataSet);
			}
		}
	}

	public boolean search(String searchWord) {
		if (data.containsKey(searchWord.length())) {
			Set<String> set = data.get(searchWord.length());
			for (String data : set) {
				int diffCount = 0;
				for (int i = 0; i < data.toCharArray().length; i++) {
					if (data.charAt(i) != searchWord.charAt(i)) {
						diffCount++;
					}
					if (diffCount > 1) {
						break;
					}
				}
				if (diffCount == 1) {
					return true;
				}
			}
		}
		return false;
	}

}
