package shop.main.utils;

public final class Constants {
	public static String LEFT = "LEFT";
	public static String RIGHT = "RIGHT";
	public static final String[] menuTypes = { "NONE", LEFT, RIGHT };
	public static final String[] blockTypes = { "FOOTER", "HEADER", "LEFTCOLUMN" };
	public static final String SITE_NAME = "siteName";

	public static enum BlockType {
		TOP, LEFT_TOP, LEFT_BOTTOM, BOTTOM, FOOTER_COL_LEFT, FOOTER_COL_CENTER, FOOTER_COL_RIGHT
	}

	public static enum RoleType {
		USER, ADMIN, MANAGER
	}

	public static String[] getCountryList() {

		return new String[] { "Select...", "Spain", "USA", "Australia" };
	}

}
