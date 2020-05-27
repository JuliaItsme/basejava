package com.urise.webapp.util;

import com.urise.webapp.model.Resume;
import com.urise.webapp.model.Section;
import com.urise.webapp.model.SectionType;
import org.junit.Assert;
import org.junit.Test;

import static com.urise.webapp.ResumeTestData.getResume1;
import static com.urise.webapp.ResumeTestData.getSection;


public class JsonParserTest {

    @Test
    public void testResume() throws Exception {
        String json = JsonParser.write(getResume1());
        System.out.println(json);
        Resume resume = JsonParser.read(json, Resume.class);
        Assert.assertEquals(getResume1(), resume);
    }

    @Test
    public void testSections() throws Exception {
        Section section1 = getSection(getResume1(), SectionType.PERSONAL);
        String json = JsonParser.write(section1, Section.class);
        System.out.println(json);
        Section section2 = JsonParser.read(json, Section.class);
        Assert.assertEquals(section1, section2);
    }
}