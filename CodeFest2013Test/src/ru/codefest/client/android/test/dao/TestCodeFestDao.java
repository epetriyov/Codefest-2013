package ru.codefest.client.android.test.dao;

import java.util.ArrayList;
import java.util.List;

import ru.codefest.client.android.dao.CodeFestDao;
import ru.codefest.client.android.model.Lecture;
import ru.codefest.client.android.model.LecturePeriod;

import com.petriyov.android.libs.bindings.BinderHelper;

public class TestCodeFestDao extends AbstractCodeFestProvider {

    private CodeFestDao dao;

    public void testGetLecturesByPeriodId() {
        List<LecturePeriod> periods = new ArrayList<LecturePeriod>();
        periods.add(new LecturePeriod("10-11", 1));
        dao.bulkInsertItems(periods, LecturePeriod.TABLE_NAME);
        List<LecturePeriod> actualPeriods = dao.getList(LecturePeriod.class,
                LecturePeriod.TABLE_NAME);
        LecturePeriod actualPeriod = actualPeriods.get(0);
        List<Lecture> lectures = new ArrayList<Lecture>();
        Lecture expectedLecture = new Lecture();
        expectedLecture.name = "expected";
        expectedLecture.periodId = actualPeriod.id;
        lectures.add(expectedLecture);
        dao.bulkInsertItems(lectures, Lecture.TABLE_NAME);
        List<Lecture> actualLectures = dao
                .getLecturesByPeriodId(actualPeriod.id);
        assertEquals(actualLectures.size(), lectures.size());
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        dao = new CodeFestDao(mockedContext, new BinderHelper());
        dao.deleteAllEntities();
    }

    @Override
    protected void tearDown() throws Exception {

        super.tearDown();
    }
}
