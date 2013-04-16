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

    private static final int CATEGORIES_A_DAY_COUNT = 3;

    private static final String LECTURE_DESCRIPTION_CLASS = "b-doklad__text";

    private static final String DAY_PROGRAM_CLASS = "b-programm-day";

    private static final String ORATOR_DESCRIPTION = "b-orator__text";

    private static final String PEOPLE_INFO_NAME_CLASS = "b-people-info__name";

    private static final String PEOPLE_INFO_COMPANY_CLASS = "b-people-info__company";

    private static final String SECTION_PREVIEW = "b-section-preview";

    private static final String LECTURE_CLASS = "b-people-prev__doclad";

    private static final String START_LECTURE_CLASS = "b-section__title-text";

    private static final String END_LECTURE_CLASS = "b-section__title-date";

    public static final String CODEFEST_URL = "http://2013.codefest.ru";

    /**
     * parse codefest.ru 2013 lecture categories
     *
     * @param programUrl
     * @return
     * @throws IOException
     */
    public List<Category> parseCodeFestCategories(String url)
            throws IOException {
        Document doc = Jsoup.connect(url + "/program").get();
        List<Category> categoryList = new ArrayList<Category>();
        try {
            Elements categoryItems = doc.getElementsByClass("i-web");
            Iterator<Element> categoryIterator = categoryItems.iterator();
            Element currentCategoryElement;
            Category currentCategory = null;
            while (categoryIterator.hasNext()) {
                currentCategoryElement = categoryIterator.next();
                currentCategory = new Category(
                        currentCategoryElement.ownText(),
                        currentCategoryElement.attr("style").substring(6));
                categoryList.add(currentCategory);
            }
        } catch (NullPointerException e) {
            throw new RuntimeException(e);
        }
        return categoryList;
    }

    /**
     * parse codefest.ru program 2013
     *
     * @param codefestUrl
     * @return
     * @throws IOException
     */
    public List<Lecture> parseCodeFestProgram(String codefestUrl,
                                              List<Category> categories, List<LecturePeriod> lecturePeriods)
            throws IOException {
        Document doc = Jsoup.connect(codefestUrl + "/program").get();
        List<Lecture> lecturesList = new ArrayList<Lecture>();
        try {
            Elements daysElements = doc.getElementsByClass(DAY_PROGRAM_CLASS);
            Iterator<Element> dayIterator = daysElements.iterator();
            int dayCounter = 0;
            List<Category> subCategories = null;
            List<LecturePeriod> subPeriods = new ArrayList<LecturePeriod>();
            while (dayIterator.hasNext()) {
                subCategories = categories.subList(dayCounter
                        * CATEGORIES_A_DAY_COUNT, (dayCounter + 1)
                        * CATEGORIES_A_DAY_COUNT);
                subPeriods.clear();
                for (LecturePeriod period : lecturePeriods) {
                    if (period.dayNumber == dayCounter + 1) {
                        subPeriods.add(period);
                    }
                }
                parseDayProgram(codefestUrl, dayIterator.next(), lecturesList,
                        subCategories, subPeriods);
                dayCounter++;
            }
        } catch (NullPointerException e) {
            throw new RuntimeException(e);
        }
        return lecturesList;
    }

    public List<LecturePeriod> parseLecturePeriods(String url)
            throws IOException {
        Document doc = Jsoup.connect(url + "/program").get();
        List<LecturePeriod> lecturerPeriodList = new ArrayList<LecturePeriod>();
        try {
            Elements daysElements = doc.getElementsByClass(DAY_PROGRAM_CLASS);
            Iterator<Element> dayIterator = daysElements.iterator();
            int dayCounter = 1;
            while (dayIterator.hasNext()) {
                Elements lectureBlocks = dayIterator.next().getElementsByClass(
                        SECTION_PREVIEW);
                Iterator<Element> lecturerIterator = lectureBlocks.iterator();
                Element currentLectureElement = null;
                LecturePeriod currentLecturePeriod = null;
                String startLecture = null;
                String endLecture = null;
                while (lecturerIterator.hasNext()) {
                    currentLectureElement = lecturerIterator.next();
                    Elements articles = currentLectureElement
                            .getElementsByTag("article");
                    if (!articles.isEmpty()) {
                        startLecture = currentLectureElement
                                .getElementsByClass(START_LECTURE_CLASS).get(0)
                                .child(0).ownText();
                        endLecture = currentLectureElement
                                .getElementsByClass(END_LECTURE_CLASS).get(0)
                                .ownText();
                        currentLecturePeriod = new LecturePeriod(startLecture
                                + "-" + endLecture, dayCounter);
                        lecturerPeriodList.add(currentLecturePeriod);
                    }
                }
                dayCounter++;
            }
        } catch (NullPointerException e) {
            throw new RuntimeException(e);
        }
        return lecturerPeriodList;
    }

    private void parseDayProgram(String programUrl, Element dayElement,
                                 List<Lecture> lectures, List<Category> categories,
                                 List<LecturePeriod> lecturePeriods) throws IOException {
        Lecture currentLecture = null;
        String lecturerName = null;
        String lecturerCompany = null;
        Category category = null;
        LecturePeriod period = null;
        Element currentLectureElement = null;
        Elements lectureBlocks = dayElement.getElementsByTag("article");
        Iterator<Element> lectureIterator = lectureBlocks.iterator();
        int counter = 0;
        while (lectureIterator.hasNext()) {
            currentLectureElement = lectureIterator.next();
            currentLecture = new Lecture();
            category = categories.get(counter % CATEGORIES_A_DAY_COUNT);
            currentLecture.categoryName = category.name;
            currentLecture.categoryColor = category.color;
            period = lecturePeriods.get(counter / CATEGORIES_A_DAY_COUNT);
            currentLecture.categoryId = category.id;
            currentLecture.periodId = period.id;
            Element aElement = currentLectureElement.child(0);
            if (aElement.attr("href").isEmpty()) {
                continue;
            }
            currentLecture.reporterDescription = parseLectrurerDescription(
                    programUrl, currentLecture,
                    programUrl + aElement.attr("href"));
            lecturerName = aElement.getElementsByClass(PEOPLE_INFO_NAME_CLASS)
                    .get(0).child(0).ownText();
            lecturerCompany = aElement
                    .getElementsByClass(PEOPLE_INFO_COMPANY_CLASS).get(0)
                    .ownText();
            currentLecture.reporterName = lecturerName;
            currentLecture.reporterCompany = lecturerCompany;
            Element a2Element = currentLectureElement
                    .getElementsByClass(LECTURE_CLASS).get(0).child(0);
            currentLecture.lectureDescription = parseLectureDescription(programUrl
                    + a2Element.attr("href"));
            currentLecture.name = a2Element.ownText();
            lectures.add(currentLecture);
            counter++;
        }
    }

    private String parseLectrurerDescription(String programUrl,
                                             Lecture currentLecture, String lecturerUrl) throws IOException {
        Document doc = Jsoup.connect(lecturerUrl).get();
        Element oratorInfo = doc.getElementsByClass(ORATOR_DESCRIPTION).get(0);
        Elements iFrames = oratorInfo.getElementsByTag("iframe");
        Iterator<Element> lectureIterator = iFrames.iterator();
        while (lectureIterator.hasNext()) {
            lectureIterator.next().remove();
        }
        Element imgElement = oratorInfo.getElementsByTag("img").get(0);
        currentLecture.reporterPhotoUrl = programUrl
                + imgElement.getElementsByTag("img").attr("src");
        imgElement.remove();
        return oratorInfo.html();
    }

    private String parseLectureDescription(String lectureUrl)
            throws IOException {
        Document doc = Jsoup.connect(lectureUrl).get();
        Element lectureInfo = doc.getElementsByClass(LECTURE_DESCRIPTION_CLASS)
                .get(0);
        return lectureInfo.html();
    }

}
