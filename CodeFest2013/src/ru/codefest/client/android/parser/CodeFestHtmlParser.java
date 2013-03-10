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

/**
 * html parser of codefest.ru program 2013
 */
public class CodeFestHtmlParser implements IWebParser {

    private static final String LECTURE_DIV_CLASS_NAME = "block";

    private static final String CATEGORY_CLASS_NAME = "inner";

    private static final String LECTURE_INFO_DIV_CLASS = "anounce";

    public static final String CODEFEST_PROGRAM_URL = "http://codefest.ru/program/2013-03/";

    /**
     * returns html of lecture info by url
     * 
     * @param lectureUrl
     * @return
     * @throws IOException
     */
    public String getLectureHtmlByUrl(String lectureUrl) throws IOException {
        Document doc = Jsoup.connect(lectureUrl).get(); // html document
        Elements lectureInfoBlock = doc
                .getElementsByClass(LECTURE_INFO_DIV_CLASS); // div
                                                             // block
                                                             // with
                                                             // lecture
                                                             // info
        if (!lectureInfoBlock.isEmpty()) {
            return lectureInfoBlock.get(0).html();
        }
        return "";
    }

    /**
     * parse codefest.ru 2013 lecture categories
     * 
     * @param programUrl
     * @return
     * @throws IOException
     */
    public List<Category> parseCodeFestCategories(String programUrl)
            throws IOException {
        Document doc = Jsoup.connect(programUrl).get(); // html document
        Elements categoryItems = doc.getElementsByClass(CATEGORY_CLASS_NAME); // inner
                                                                              // class
        // blocks
        // with
        // category
        // names
        Iterator<Element> categoryIterator = categoryItems.iterator();
        List<Category> categoryList = new ArrayList<Category>();
        Element currentCategoryElement;
        Category currentCategory = null;
        while (categoryIterator.hasNext()) {
            currentCategoryElement = categoryIterator.next();
            currentCategory = new Category(currentCategoryElement.ownText());
            categoryList.add(currentCategory);
        }
        return categoryList;
    }

    /**
     * parse codefest.ru program 2013
     * 
     * @param programUrl
     * @return
     * @throws IOException
     */
    public List<Lecture> parseCodeFestProgram(String programUrl)
            throws IOException {
        Document doc = Jsoup.connect(programUrl).get(); // html document
        Elements lectureBlocks = doc.getElementsByClass(LECTURE_DIV_CLASS_NAME); // div
                                                                                 // blocks
                                                                                 // with
                                                                                 // lecture
                                                                                 // links
        Iterator<Element> lecturerIterator = lectureBlocks.iterator();
        List<Lecture> lecturerList = new ArrayList<Lecture>();
        Element currentLectureElement;
        Lecture currentLecture = null;
        while (lecturerIterator.hasNext()) {
            currentLectureElement = lecturerIterator.next();

            Elements aElements = currentLectureElement.select("a[href]");
            if (!aElements.isEmpty()) {
                Element aElement = aElements.get(0);
                currentLecture = new Lecture();
                currentLecture.name = aElement.ownText();
                Elements imgElement = aElement.getElementsByTag("img");
                if (!imgElement.isEmpty()) {
                    currentLecture.reporterPhotoUrl = imgElement.get(0).attr(
                            "src");
                }
                Elements pElement = currentLectureElement.getElementsByTag("p");
                if (!pElement.isEmpty()) {
                    currentLecture.reporterInfo = pElement.get(0).ownText();
                }
                currentLecture.descriptionHtml = getLectureHtmlByUrl(aElement
                        .attr("href"));
                currentLecture.categoryName = getCategoryNameByLectureElement(currentLectureElement);
            }
            lecturerList.add(currentLecture);
        }
        return lecturerList;
    }

    private String getCategoryNameByLectureElement(Element currentLectureElement) {
        Elements parents = currentLectureElement.parents();
        if (!parents.isEmpty()) {
            Elements categoryParent = parents.select("[class="
                    + CATEGORY_CLASS_NAME + "]");
            if (!categoryParent.isEmpty()) {
                return categoryParent.get(0).ownText();
            }
        }
        return "";
    }
}
