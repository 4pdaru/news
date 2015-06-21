package ru4pda.news.client;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by asavinova on 09/04/15.
 */
public class ArticlePageParser {

	private static final String CONTENT_PATTERN_STRING = "<img src=\"/pages/imp/%s.gif\" width=\"1\" height=\"1\" />(.*?)<br /></div>";

	private static final Pattern VIDEO_PATTERN = Pattern.compile("<iframe (.*?)</iframe>", Pattern.DOTALL);
	private static final Pattern VIDEO_URL_PATTERN = Pattern.compile("src=\"(.*?)\"", Pattern.DOTALL);
	private static final String PREVIEW_URL_STRING = "http://i.ytimg.com/vi/%s/hqdefault.jpg";


	public String parse(long id, String pageSource) {

		String format = String.format(CONTENT_PATTERN_STRING, id);
		Pattern pattern = Pattern.compile(format, Pattern.DOTALL);
		Matcher matcher = pattern.matcher(pageSource);

		if (!matcher.find()) {
			throw new IllegalStateException("Can't parse page");
		}

		String content = matcher.group(1);

		return replaceVideoBlocks(content);
	}

	private String replaceVideoBlocks(String content) {
		Matcher matcher = VIDEO_PATTERN.matcher(content);

		while (matcher.find()) {
			String videoBlock = matcher.group();

			Matcher videoUrlMatcher = VIDEO_URL_PATTERN.matcher(videoBlock);

			if (!videoUrlMatcher.find()) {
				continue;
			}

			String url = videoUrlMatcher.group(1);
			String hash = url.substring(url.lastIndexOf("/") + 1, url.length());
			String previewUrl = String.format(PREVIEW_URL_STRING, hash);

			String link = String.format("<a href=\"%s\"><img src=\"%s\" width=\"100%%\" /></a>", url, previewUrl);

			content = content.replaceFirst(videoBlock, link);
		}

		return content;
	}

}
