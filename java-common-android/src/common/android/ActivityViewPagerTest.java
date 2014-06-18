package common.android;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import common.android.extensions.FragmentActivityBase;
import common.android.utils.ViewUtil;
import common.basic.logs.Logger;
import common.basic.utils.ListUtil;

import java.util.List;

public class ActivityViewPagerTest extends FragmentActivityBase {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_view_pager_test);

        final ViewPager viewPager = ViewUtil.findViewPager(this, R.id.viewPager);
        final List<String> list = ListUtil.create("A");

        makeRotate(viewPager, new ProviderRotate() {
            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public Context getContext() {
                return ActivityViewPagerTest.this;
            }

            @Override
            public FragmentManager getFragmentManager() {
                return getSupportFragmentManager();
            }


            @Override
            public Fragment getFragment(final int i) {
                return new Fragment() {
                    @Override
                    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
                        final Button button = new Button(getContext());
                        button.setText(list.get(getIndexFromVirtualIndex(i)));
                        return button;
                    }
                };
            }
        });

    }

    public static abstract class ProviderRotate {
        public abstract Context getContext();
        public abstract FragmentManager getFragmentManager();
        public abstract Fragment getFragment(int virtualIndex);
        public abstract int getCount();

        public int getVirtualCount() {
            return getCount() + 2;
        }

        public int getVirtualIndexFromIndex(int i) {
            return ListUtil.getIndexByInfiniteIndexWithOffset(getCount(), i, 1);
        }

        public int getIndexFromVirtualIndex(int i) {
            return ListUtil.getIndexByInfiniteIndexWithOffset(getCount(), i, -1);
        }
    }

    private static void makeRotate(final ViewPager viewPager, final ProviderRotate provider) {
        viewPager.setAdapter(new FragmentStatePagerAdapter(provider.getFragmentManager()) {
            @Override
            public Fragment getItem(final int i) {
                return provider.getFragment(i);
            }

            @Override
            public int getCount() {
                return provider.getCount() + 2;
            }
        });
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                Logger.e(position);
                if (viewPager.getCurrentItem() == provider.getVirtualCount() - 1) {
                    viewPager.setCurrentItem(1, false);
                }
                if (viewPager.getCurrentItem() == 0) {
                    viewPager.setCurrentItem(provider.getVirtualCount() - 2, false);
                }
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setCurrentItem(1);
    }


    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}