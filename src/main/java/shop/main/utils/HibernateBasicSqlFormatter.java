package shop.main.utils;

import org.hibernate.engine.jdbc.internal.BasicFormatterImpl;
import org.hibernate.engine.jdbc.internal.Formatter;

import com.p6spy.engine.spy.appender.MessageFormattingStrategy;

/**
 * Created by Igor Dmitriev on 1/3/16
 */
public class HibernateBasicSqlFormatter implements MessageFormattingStrategy {

	private final Formatter formatter = new BasicFormatterImpl();

	@Override
	public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared,
			String sql) {
		String res = !sql.isEmpty() ? sql : prepared;
		if (res.isEmpty()) {
			return "";
		}
		String template = "Hibernate: %s %s {elapsed: %sms}";
		String batch = "batch".equals(category) ? "batch operation" : "";
		return String.format(template, batch, formatter.format(res), elapsed);
	}
}
