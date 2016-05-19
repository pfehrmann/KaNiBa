package de.kaniba.utils;

import static org.junit.Assert.*;

import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

public class UtilsTest {

	@Test
	public void testCopyList() {
		System.out.println("Copy list");
		List<String> original = Arrays.asList("Hallo", "Bla", "Blub");
		List<String> copy = Utils.copyList(original);

		assertFalse("Lists are equal", original == copy);
		assertEquals("Lists differ in size", original.size(), copy.size());
		for (int i = 0; i < original.size(); i++) {
			assertEquals("List elements are not equal", original.get(i), copy.get(i));
		}
	}

	@Test
	@Ignore
	public void testDownloadURL() throws MalformedURLException {
		System.out.println("testDownloadURL");
		String url = "http://kaniba.de/ROOT_DEFAULT/";
		String downloaded = Utils.downloadURL(url);
		assertEquals("Strings not equal. The test might be broken...",
				"<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n"
						+ "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\"\n"
						+ "   \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n"
						+ "<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en\" lang=\"en\">\n" + "<head>\n"
						+ "    <title>Apache Tomcat</title>\n" + "</head>\n" + "\n" + "<body>\n"
						+ "<h1>It works !</h1>\n" + "\n"
						+ "<p>If you're seeing this page via a web browser, it means you've setup Tomcat successfully. Congratulations!</p>\n"
						+ " \n"
						+ "<p>This is the default Tomcat home page. It can be found on the local filesystem at: <code>/var/lib/tomcat7/webapps/ROOT/index.html</code></p>\n"
						+ "\n"
						+ "<p>Tomcat7 veterans might be pleased to learn that this system instance of Tomcat is installed with <code>CATALINA_HOME</code> in <code>/usr/share/tomcat7</code> and <code>CATALINA_BASE</code> in <code>/var/lib/tomcat7</code>, following the rules from <code>/usr/share/doc/tomcat7-common/RUNNING.txt.gz</code>.</p>\n"
						+ "\n"
						+ "<p>You might consider installing the following packages, if you haven't already done so:</p>\n"
						+ "\n"
						+ "<p><b>tomcat7-docs</b>: This package installs a web application that allows to browse the Tomcat 7 documentation locally. Once installed, you can access it by clicking <a href=\"docs/\">here</a>.</p>\n"
						+ "\n"
						+ "<p><b>tomcat7-examples</b>: This package installs a web application that allows to access the Tomcat 7 Servlet and JSP examples. Once installed, you can access it by clicking <a href=\"examples/\">here</a>.</p>\n"
						+ "\n"
						+ "<p><b>tomcat7-admin</b>: This package installs two web applications that can help managing this Tomcat instance. Once installed, you can access the <a href=\"manager/html\">manager webapp</a> and the <a href=\"host-manager/html\">host-manager webapp</a>.<p>\n"
						+ "\n"
						+ "<p>NOTE: For security reasons, using the manager webapp is restricted to users with role \"manager-gui\". The host-manager webapp is restricted to users with role \"admin-gui\". Users are defined in <code>/etc/tomcat7/tomcat-users.xml</code>.</p>\n"
						+ "\n" + "</body>\n" + "</html>\n",
				downloaded);
	}
}
