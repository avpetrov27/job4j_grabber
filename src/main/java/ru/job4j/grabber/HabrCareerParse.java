package ru.job4j.grabber;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.job4j.grabber.utils.DateTimeParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HabrCareerParse implements Parse {
    public static final String SOURCE_LINK = "https://career.habr.com";
    public static final String PREFIX = "/vacancies?page=";
    public static final String SUFFIX = "&q=Java%20developer&type=all";
    public static final int PAGE_COUNT = 5;
    private final DateTimeParser dateTimeParser;

    public HabrCareerParse(DateTimeParser dateTimeParser) {
        this.dateTimeParser = dateTimeParser;
    }

    @Override
    public List<Post> list(String link) throws IOException {
        List<Post> ret = new ArrayList<>();
        for (int pageNumber = 1; pageNumber <= PAGE_COUNT; pageNumber++) {
            String fullLink = "%s%s%d%s".formatted(link, PREFIX, pageNumber, SUFFIX);
            Connection connection = Jsoup.connect(fullLink);
            Document document = connection.get();
            Elements rows = document.select(".vacancy-card__inner");
            rows.forEach(row -> {
                Element titleElement = row.select(".vacancy-card__title").first();
                Element linkElement = Objects.requireNonNull(titleElement).child(0);
                String vacancyName = titleElement.text();
                String vacancyLink = String.format("%s%s", SOURCE_LINK, linkElement.attr("href"));
                String time = Objects.requireNonNull(row.select(".vacancy-card__date").first()).child(0).attr("datetime");
                String description;
                try {
                    description = retrieveDescription(vacancyLink);
                } catch (IOException e) {
                    description = e.getMessage();
                }
                ret.add(new Post(0, vacancyName, vacancyLink, description, dateTimeParser.parse(time)));
            });
        }
        return ret;
    }

    private String retrieveDescription(String link) throws IOException {
        return Objects.requireNonNull(Jsoup.connect(link).get().selectFirst(".vacancy-description__text")).text();
    }
}
