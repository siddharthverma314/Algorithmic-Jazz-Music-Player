package framework.utilities;

import java.util.List;

public class ListUtils {

	public static int[] convertToIntArray(List<Integer> integers) {
		int[] ret = new int[integers.size()];
		for (int i = 0; i < ret.length; i++) {
			ret[i] = integers.get(i).intValue();
		}
		return ret;
	}

}
