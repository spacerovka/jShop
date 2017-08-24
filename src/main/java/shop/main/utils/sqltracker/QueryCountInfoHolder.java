package shop.main.utils.sqltracker;

/**
 * Created by Igor Dmitriev on 12/6/15
 */
public class QueryCountInfoHolder {
	private static ThreadLocal<QueryCountInfo> queryInfoHolder = ThreadLocal.withInitial(QueryCountInfo::new);

	public static QueryCountInfo getQueryInfo() {
		return queryInfoHolder.get();
	}

	public static void clear() {
		queryInfoHolder.get().clear();
	}
}
