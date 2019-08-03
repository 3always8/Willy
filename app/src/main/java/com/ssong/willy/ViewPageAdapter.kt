package com.ssong.willy

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.View
import android.view.ViewGroup

class ViewPageAdapter internal constructor (fm: FragmentManager) : FragmentPagerAdapter(fm) {
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
        if (currentFragment != `object`) {
            currentFragment = `object` as Fragment

        }
        super.setPrimaryItem(container, position, `object`)
    }


}