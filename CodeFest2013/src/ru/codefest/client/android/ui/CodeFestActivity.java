package ru.codefest.client.android.ui;

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

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;
import com.viewpagerindicator.TabPageIndicator;

public class CodeFestActivity extends CodeFestBaseActivity {

    class CodeFestPagerAdapter extends FragmentStatePagerAdapter {

        private String[] content;

        public CodeFestPagerAdapter(FragmentManager fm) {
            super(fm);
            content = new String[] { getString(R.string.programTabText),
                    // getString(R.string.favoritesTabText),
                    getString(R.string.chatTabText) };
        }

        @Override
        public int getCount() {
            return content.length;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
            case 0:
                return new ProgramFragment();
            case 1:
                return new TwitterFeedFragment();
                // case 2:
                // return new ProgramFragment();
            default:
                return new ProgramFragment();
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return content[position % content.length].toUpperCase();
        }
    }

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
        if (item.getItemId() == R.id.refreshMenuItem) {
            ServiceHelper.refreshProgram(this, handler);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.act_codefest);
        FragmentStatePagerAdapter adapter = new CodeFestPagerAdapter(
                getSupportFragmentManager());

        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);

        TabPageIndicator indicator = (TabPageIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(pager);
    }
}
