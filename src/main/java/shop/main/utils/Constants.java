package shop.main.utils;

public final class Constants {
	public static String LEFT = "LEFT";
	public static String RIGHT = "RIGHT";
	public static final String[] menuTypes = { "NONE", LEFT, RIGHT };
	public static final String[] blockTypes = { "FOOTER", "HEADER", "LEFTCOLUMN" };
	public static final String SITE_NAME = "SITE_NAME";
	public static final String THEME = "THEME";
	public static final String SUPPORT_EMAIL = "SUPPORT_EMAIL";

	public static enum BlockType {
		TOP, LEFT_TOP, LEFT_BOTTOM, BOTTOM, FOOTER_COL_LEFT, FOOTER_COL_CENTER, FOOTER_COL_RIGHT
	}

	public static enum RoleType {
		USER, ADMIN, MANAGER
	}

	public static String[] getCountryList() {

		return new String[] { "Select...", "Spain", "USA", "Australia" };
	}

	public static enum MainProperties {
		SITE_NAME, THEME, MAINPAGE_TITLE, MAINPAGE_DESCRIPTION
	}

	public static enum GeneralProperties {
		TILTE_PREFIX, TITLE_SUFFIX, DESCRIPTION_PREFIX, DESCRIPTION_SUFFIX

	}

}
