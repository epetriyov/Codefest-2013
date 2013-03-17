package ru.codefest.client.android.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import ru.codefest.client.android.model.Category;
import ru.codefest.client.android.model.Lecture;
import ru.codefest.client.android.model.LecturePeriod;

/**
 * html parser of codefest.ru program 2013
 */
public class CodeFestHtmlParser implements IWebParser {

    private static final String LECTURE_DESCRIPTION_CLASS = "b-doklad__text";

    private static final String DAY_PROGRAM_CLASS = "b-programm-day";

    private static final String ORATOR_DESCRIPTION = "b-orator__text";

    private static final String PEOPLE_INFO_NAME_CLASS = "b-people-info__name";

    private static final String PEOPLE_INFO_COMPANY_CLASS = "b-people-info__company";

    private static final String LECTURE_CLASS = "b-people-prev__doclad";

    private static final String START_LECTURE_CLASS = "b-section__title-text";

    private static final String END_LECTURE_CLASS = "b-section__title-date";

    private static final String LECTURE_PERIOD_CLASS_NAME = "b-section__title";

    public static final String CODEFEST_URL = "http://2013.codefest.ru";

    /**
     * parse codefest.ru program 2013
     * 
     * @param codefestUrl
     * @return
     * @throws IOException
     */
    public List<Lecture> parseCodeFestProgram(String codefestUrl)
            throws IOException {
        Document doc = Jsoup.connect(codefestUrl + "/program").get();
        List<Lecture> lecturesList = new ArrayList<Lecture>();
        try {
            Elements daysElements = doc.getElementsByClass(DAY_PROGRAM_CLASS);
            Element firstDayElement = daysElements.get(0);
            parseDayProgram(codefestUrl, firstDayElement, lecturesList);
            Element secondDayElement = daysElements.get(1);
            parseDayProgram(codefestUrl, secondDayElement, lecturesList);
        } catch (NullPointerException e) {
            throw new RuntimeException(e);
        }
        return lecturesList;
    }

    /**
     * parse codefest.ru 2013 lecture categories
     * 
     * @param programUrl
     * @return
     * @throws IOException
     */
    private List<Category> parseCodeFestCategories(Element rootElement) {
        Elements categoryItems = rootElement.getElementsByClass("i-web");
        Iterator<Element> categoryIterator = categoryItems.iterator();
        List<Category> categoryList = new ArrayList<Category>();
        Element currentCategoryElement;
        Category currentCategory = null;
        while (categoryIterator.hasNext()) {
            currentCategoryElement = categoryIterator.next();
            int categoryColor = Integer.decode(currentCategoryElement.attr(
                    "style").substring(6));
            currentCategory = new Category(currentCategoryElement.ownText(),
                    categoryColor);
            categoryList.add(currentCategory);
        }
        return categoryList;
    }

    private void parseDayProgram(String programUrl, Element dayElement,
            List<Lecture> lectures) throws IOException {
        Lecture currentLecture = null;
        String lecturerName = null;
        String lecturerCompany = null;
        Category category = null;
        LecturePeriod period = null;
        Element currentLectureElement = null;
        Elements lectureBlocks = dayElement.getElementsByTag("article");
        Iterator<Element> lectureIterator = lectureBlocks.iterator();
        List<Category> categories = parseCodeFestCategories(dayElement);
        List<LecturePeriod> lecturePeriods = parseLecturePeriods(dayElement);
        int counter = 0;
        while (lectureIterator.hasNext()) {
            currentLectureElement = lectureIterator.next();
            currentLecture = new Lecture();
            category = categories.get(counter % 3);
            currentLecture.categoryName = category.name;
            currentLecture.categoryColor = category.color;
            period = lecturePeriods.get(counter / 3);
            currentLecture.startDate = period.startDate;
            currentLecture.endDate = period.endDate;
            Element aElement = currentLectureElement.child(0);
            if (aElement.attr("href").isEmpty()) {
                continue;
            }
            currentLecture.reporterDescription = parseLectrurerDescription(programUrl
                    + aElement.attr("href"));
            currentLecture.reporterPhotoUrl = programUrl
                    + aElement.getElementsByTag("img").attr("src");
            lecturerName = aElement.getElementsByClass(PEOPLE_INFO_NAME_CLASS)
                    .get(0).child(0).data();
            lecturerCompany = aElement
                    .getElementsByClass(PEOPLE_INFO_COMPANY_CLASS).get(0)
                    .data();
            currentLecture.reporterInfo = lecturerName + " " + lecturerCompany;
            Element a2Element = currentLectureElement
                    .getElementsByClass(LECTURE_CLASS).get(0).child(0);
            currentLecture.lectureDescription = parseLectureDescription(programUrl
                    + a2Element.attr("href"));
            currentLecture.name = a2Element.data();
            lectures.add(currentLecture);
            counter++;
        }
    }

    private String parseLectrurerDescription(String lecturerUrl)
            throws IOException {
        Document doc = Jsoup.connect(lecturerUrl).get();
        Element oratorInfo = doc.getElementsByClass(ORATOR_DESCRIPTION).get(0);
        oratorInfo.getElementsByTag("img").get(0).remove();
        return oratorInfo.html();
    }

    private String parseLectureDescription(String lectureUrl)
            throws IOException {
        Document doc = Jsoup.connect(lectureUrl).get();
        Element lectureInfo = doc.getElementsByClass(LECTURE_DESCRIPTION_CLASS)
                .get(0);
        return lectureInfo.html();
    }

    private List<LecturePeriod> parseLecturePeriods(Element rootElement) {
        Elements lectureBlocks = rootElement
                .getElementsByClass(LECTURE_PERIOD_CLASS_NAME);
        Iterator<Element> lecturerIterator = lectureBlocks.iterator();
        List<LecturePeriod> lecturerPeriodList = new ArrayList<LecturePeriod>();
        Element currentLectureElement = null;
        LecturePeriod currentLecturePeriod = null;
        String startLecture = null;
        String endLecture = null;
        while (lecturerIterator.hasNext()) {
            currentLectureElement = lecturerIterator.next();
            startLecture = currentLectureElement
                    .getElementsByClass(START_LECTURE_CLASS).get(0).child(0)
                    .data();
            endLecture = currentLectureElement
                    .getElementsByClass(END_LECTURE_CLASS).get(0).data();
            currentLecturePeriod = new LecturePeriod(startLecture, endLecture);
            lecturerPeriodList.add(currentLecturePeriod);
        }
        return lecturerPeriodList;
    }

}
