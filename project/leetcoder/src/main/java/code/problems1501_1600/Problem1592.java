package code.problems1501_1600;


import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author chengzhe yan
 * @description
 * @date 2022/9/7 15:40
 */
public class Problem1592 {

	public static void main(String[] args) throws ExecutionException, InterruptedException {
		String a = new Problem1592().reorderSpaces2(" practice ");
		System.out.println(a);
	}

	public String reorderSpaces2(String text) {
		try {
			CompletableFuture<String> result = new CompletableFuture<>();
			CompletableFuture<String[]> words = new CompletableFuture<>();
			words.completeAsync(() -> text.trim().split("\\s+"))
					.thenApply(wordArr -> getCount(text, wordArr))
					.thenAcceptBoth(words, (countTmp, wordTmp) -> {
						if (wordTmp.length == 1) {
							result.complete(wordTmp[0] + " ".repeat(Math.max(0, countTmp)));
						}
						CompletableFuture<String> nu = getStringBuilder(countTmp / (wordTmp.length - 1));
						CompletableFuture<String> end = getStringBuilder(countTmp % (wordTmp.length - 1));
						nu.thenCombine(end, (nuTmp, endTmp) -> getString(wordTmp, nuTmp, endTmp)).whenComplete((r, e) -> {
							if (e == null) {
								result.complete(r);
							} else {
								result.completeExceptionally(e);
							}
						});
					});
			return result.get();
		} catch (InterruptedException | ExecutionException ignore) {
			return "";
		}
	}

	private int getCount(String text, String[] words) {
		int count = text.length();
		for (String word : words) {
			count -= word.length();
		}
		return count;
	}

	private CompletableFuture<String> getStringBuilder(int i) {
		CompletableFuture<String> string = new CompletableFuture<>();
		string.completeAsync(() -> " ".repeat(Math.max(0, i)));
		return string;
	}

	private String getString(String[] words, String nu, String end) {
		StringBuilder result = new StringBuilder();
		for (int i1 = 0; i1 < words.length; i1++) {
			if (i1 == words.length - 1) {
				result.append(words[i1]).append(end);
				break;
			}
			result.append(words[i1]).append(nu);
		}
		return result.toString();
	}

	public String reorderSpaces(String text) {
		String[] words = text.trim().split("\\s+");
		int count = text.length();
		for (String word : words) {
			count -= word.length();
		}
		if (words.length == 1) {
			return words[0] + " ".repeat(Math.max(0, count));
		}
		int endCount = count % (words.length - 1);
		int every = count / (words.length - 1);
		StringBuilder result = new StringBuilder();
		StringBuilder nu = new StringBuilder();
		nu.append(" ".repeat(Math.max(0, every)));
		StringBuilder end = new StringBuilder();
		end.append(" ".repeat(Math.max(0, endCount)));
		for (int i1 = 0; i1 < words.length; i1++) {
			if (i1 == words.length - 1) {
				result.append(words[i1]).append(end.toString());
				break;
			}
			result.append(words[i1]).append(nu.toString());
		}
		return result.toString();
	}
}
