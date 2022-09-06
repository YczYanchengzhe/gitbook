package code.common;

import java.util.Objects;

/**
 * @author chengzhe yan
 * @description
 * @date 2022/9/5 20:36
 */
public class Pair<A, B> {

	public final A key;
	public final B value;

	public Pair(A key, B value) {
		this.key = key;
		this.value = value;
	}

	@Override
	public String toString() {
		return "Pair[" + key + "," + value + "]";
	}

	@Override
	public boolean equals(Object other) {
		return other instanceof Pair<?, ?> &&
						Objects.equals(key, ((Pair<?, ?>) other).key) &&
						Objects.equals(value, ((Pair<?, ?>) other).value);
	}

	@Override
	public int hashCode() {
		if (key == null) return (value == null) ? 0 : value.hashCode() + 1;
		else if (value == null) return key.hashCode() + 2;
		else return key.hashCode() * 17 + value.hashCode();
	}

	public static <A, B> Pair<A, B> of(A a, B b) {
		return new Pair<>(a, b);
	}

	public A getKey() {
		return key;
	}

	public B getValue() {
		return value;
	}
}
