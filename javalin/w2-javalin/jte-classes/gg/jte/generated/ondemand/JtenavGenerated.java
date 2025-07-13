package gg.jte.generated.ondemand;
import model.User;
public final class JtenavGenerated {
	public static final String JTE_NAME = "nav.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,1,1,4,4,4,4,4,4,4,8};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, User user) {
		jteOutput.writeContent("\r\n<div>\r\n    <span>Welcome ");
		jteOutput.setContext("span", null);
		jteOutput.writeUserContent(user.name);
		jteOutput.writeContent(" <a href=\"#\">");
		jteOutput.setContext("a", null);
		jteOutput.writeUserContent(user.id);
		jteOutput.writeContent("</a></span>\r\n    <hr>\r\n\r\n</div>\r\n");
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		User user = (User)params.get("user");
		render(jteOutput, jteHtmlInterceptor, user);
	}
}
