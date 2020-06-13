package lt.gmail.mail.sender.gmail.api;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class ReadHTMLFile {
	
	public static final String HTML_EMAIL_0 = "/resources/email-0.html";
	public static final String HTML_EMAIL_1 = "/resources/email-1.html";
	public static final String HTML_EMAIL_2 = "/resources/email-2.html";
	public static final String HTML_EMAIL_3 = "/resources/email-3.html";
	public static final String HTML_EMAIL_4 = "/resources/email-4.html";
	public static final String HTML_EMAIL_5 = "/resources/email-5.html";
	public static final String HTML_EMAIL_6 = "/resources/email-6.html";
	public static final String HTML_EMAIL_7 = "/resources/email-7.html";
	public static final String HTML_EMAIL_8 = "/resources/email-8.html";
	public static final String HTML_EMAIL_9 = "/resources/email-9.html";
	public static final String HTML_EMAIL_10 = "/resources/email-10.html";
	public static final String HTML_EMAIL_11 = "/resources/email-11.html";
	public static final String HTML_EMAIL_12 = "/resources/email-12.html";
	public static final String HTML_EMAIL_13 = "/resources/email-13.html";
	public static final String HTML_EMAIL_14 = "/resources/email-14.html";
	public static final String HTML_EMAIL_15 = "/resources/email-15.html";
	public static final String HTML_EMAIL_16 = "/resources/email-16.html";
	
	
	public static String readHTML(String filePath) throws IOException {
		StringBuilder textBuilder = new StringBuilder();
		InputStream in = ReadHTMLFile.class.getResourceAsStream(filePath);
		if (in == null) {
			throw new FileNotFoundException("Resource not found: " + filePath);
		}
	  
	    try (Reader reader = new BufferedReader(new InputStreamReader
	      (in, Charset.forName(StandardCharsets.UTF_8.name())))) {
	        int c = 0;
	        while ((c = reader.read()) != -1) {
	            textBuilder.append((char) c);
	        }
	    }
		return textBuilder.toString();

	}

	public static String readFile(String filePath) {
		StringBuilder contentBuilder = new StringBuilder();
		try (Stream<String> stream = Files.lines(Paths.get(filePath), StandardCharsets.UTF_8)) {
			stream.forEach(s -> contentBuilder.append(s).append("\n"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return contentBuilder.toString();
	}

}
