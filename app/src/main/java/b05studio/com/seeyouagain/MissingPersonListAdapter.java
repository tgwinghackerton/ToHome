package b05studio.com.seeyouagain;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by mansu on 2017-10-21.
 */

public class MissingPersonListAdapter extends FragmentStatePagerAdapter {
    public MissingPersonListAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                ListRegisteredFragment fragment1 = new ListRegisteredFragment();
                return fragment1;
            case 1:
                GreenUmFragment fragment2 = new GreenUmFragment();
                return fragment2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
