package com.ssong.willy

import android.app.PendingIntent.getActivity
import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.View
import android.view.ViewGroup
import kotlin.math.acos

class ViewPageAdapter internal constructor (fm: FragmentManager, val context: Context) : FragmentPagerAdapter(fm) {
    private val COUNT = 3
    public var currentFragment : Fragment? = null

    override fun getItem(p0: Int): Fragment {
        var fragment: Fragment? = null
        when (p0){
            0 -> fragment = PeopleFragment()
            1 -> fragment = MoneyFragment()
            2 -> fragment = FuneralFragment()

        }
        return fragment!!
    }

    override fun getCount(): Int {
        return COUNT
    }

    override fun getPageTitle(position: Int): CharSequence? {
        var title :String? = null
        when (position) {
            0 -> title = "내 사람"
            1 -> title = "내 재산"
            2 -> title = "내 장례"

        }

        return title
    }

    override fun setPrimaryItem(container: ViewGroup, position: Int, `object`: Any) {
        //This function is called when displayed fragment is changed.
        if (currentFragment != `object`) {
            currentFragment = `object` as Fragment


/*
            So, this code was right for the whole time -
            - only need to be passed as a factor.
*/
            // Still, clearFocus() is not working.
            var activity = context as MainActivity
            activity.main()

        }

        super.setPrimaryItem(container, position, `object`)

    }


/*
    This interface helps saveAndFinish function to work when fragment changes.
    To to this, ViewPagerAdapter is changed to get one more factor -
    "This" from MainActivity.
    Now ViewPageAdapter can access "the" instance of the MainActivity.

    In MainActivity, there is main() function written.
    It calls View.saveAndFinish().
*/
    interface SaveInterface {
        fun main()

    }

}