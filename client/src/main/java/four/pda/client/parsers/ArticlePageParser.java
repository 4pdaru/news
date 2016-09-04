package four.pda.client.parsers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import four.pda.client.exceptions.ParseException;
import four.pda.client.model.ArticleContent;

/**
 * Created by asavinova on 09/04/15.
 */
public class ArticlePageParser {

	private static final Logger L = LoggerFactory.getLogger(ArticlePageParser.class);

	private static final String PREVIEW_URL_STRING = "http://i.ytimg.com/vi/%s/hqdefault.jpg";

	public ArticleContent parse(String pageSource) {
		Document document = Jsoup.parse(pageSource);
		Element content = document.select("div.content").first();

		if (content == null) {
			String message = "Article content not found";
			L.error(message);
			throw new ParseException(message);
		}

		try {
			replaceVideoBlocks(content);
		} catch (Exception e) {
			String message = "Can't parse article page";
			L.error(message, e);
			throw new ParseException(message, e);
		}

		replaceScreenshotsSrc(content);

		ArticleContent articleContent = new ArticleContent();
		articleContent.setContent(content.html());
		articleContent.setImages(getImages(content));

		return articleContent;
	}

	private void replaceVideoBlocks(Element element) {

		for (Element videoBlockEl : element.select("iframe[allowfullscreen]")) {

			String src = videoBlockEl.attr("src");

			if (!src.contains("www.youtube.com")) {
				continue;
			}

			String hash = src.substring(src.lastIndexOf("/") + 1);
			String previewImageUrl = String.format(PREVIEW_URL_STRING, hash);
			String link = String.format("<a href=\"%s\"><img src=\"%s\" width=\"100%%\" /></a>", src, previewImageUrl);

			videoBlockEl.parent().append(link);
			videoBlockEl.remove();

		}

	}

	private void replaceScreenshotsSrc(Element content) {
		Elements screenshots = content.select("div.sc-content > a > img");
		for (Element img : screenshots) {
			String src = img.attr("src");
			if (src.startsWith("//")) {
				src = "http:" + src;
				img.attr("src", src);

				Element link = img.parent();
				link.attr("href", src);
			}
		}
	}

	private List<String> getImages(Element content) {
		List<String> images = new ArrayList<>();

		Elements elements = content.select("a > img");
		for (Element element : elements) {
			images.add(element.attr("src"));
		}

		return images;
	}

}
