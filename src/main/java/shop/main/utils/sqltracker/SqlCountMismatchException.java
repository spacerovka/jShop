package shop.main.utils.sqltracker;

/**
 * Created by Igor Dmitriev on 12/13/15
 */
public class SqlCountMismatchException extends RuntimeException {
	public SqlCountMismatchException(String statement, int expectedCount, int actualCount) {
		super("Expected " + statement + " query count : " + expectedCount + ", but was : " + actualCount);
	}
}
