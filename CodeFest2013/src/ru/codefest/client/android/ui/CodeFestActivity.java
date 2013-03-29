package ru.codefest.client.android.ui;

import java.util.HashMap;
import java.util.Map;

import ru.codefest.client.android.R;
import ru.codefest.client.android.service.ServiceHelper;
import ru.codefest.client.android.ui.program.ProgramFragment;
import ru.codefest.client.android.ui.twitter.TwitterFeedFragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.ViewGroup;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.viewpagerindicator.TabPageIndicator;

public class CodeFestActivity extends CodeFestBaseActivity {

    class CodeFestPagerAdapter extends FragmentStatePagerAdapter {

        private Map<Integer, ICodeFestFragment> mPageReferenceMap = new HashMap<Integer, ICodeFestFragment>();

        private String[] content;

        public CodeFestPagerAdapter(FragmentManager fm) {
            super(fm);
            content = new String[] { getString(R.string.programTabText),
                    getString(R.string.favoritesTabText),
                    getString(R.string.chatTabText) };
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

            super.destroyItem(container, position, object);

            mPageReferenceMap.remove(Integer.valueOf(position));
        }

        @Override
        public int getCount() {
            return content.length;
        }

        public ICodeFestFragment getFragment(int key) {

            return mPageReferenceMap.get(key);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                ProgramFragment programFragment = ProgramFragment
                        .newInstance(false);
                mPageReferenceMap.put(Integer.valueOf(position),
                        programFragment);
                return programFragment;
            } else if (position == 1) {
                ProgramFragment favoritesFragment = ProgramFragment
                        .newInstance(true);
                mPageReferenceMap.put(Integer.valueOf(position),
                        favoritesFragment);
                return favoritesFragment;
            } else if (position == 2) {
                TwitterFeedFragment twitterFragment = new TwitterFeedFragment();
                mPageReferenceMap.put(Integer.valueOf(position),
                        twitterFragment);
                return twitterFragment;
            } else {
                return null;
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return content[position % content.length].toUpperCase();
        }
    }

    private CodeFestPagerAdapter adapter;

    static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Log.i("CodeFestParser!!!", "Program parsed successful!");
            super.handleMessage(msg);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getSupportMenuInflater().inflate(R.menu.codefest_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.aboutMenuItem) {
        } else if (item.getItemId() == R.id.refreshMenuItem) {
            ServiceHelper.refreshProgram(this, handler);
        }
        return super.onOptionsItemSelected(item);
    }

    public void sendUpdateListCommand(int position) {
        adapter.getFragment(position).updateList();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_codefest);
        adapter = new CodeFestPagerAdapter(getSupportFragmentManager());

        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);

        TabPageIndicator indicator = (TabPageIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(pager);
    }
}
