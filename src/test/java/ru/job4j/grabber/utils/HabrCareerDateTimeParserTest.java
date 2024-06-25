package ru.job4j.grabber.utils;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.*;

class HabrCareerDateTimeParserTest {
    @Test
    void whenParse() {
        LocalDateTime date0 = LocalDateTime.now();
        String text = date0.format(DateTimeFormatter.ISO_DATE_TIME);
        assertThat(new HabrCareerDateTimeParser().parse(text)).isEqualTo(date0);
    }
}
