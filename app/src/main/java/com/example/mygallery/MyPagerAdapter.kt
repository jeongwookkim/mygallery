package com.example.mygallery

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class MyPagerAdapter(fm: FragmentManager?) : FragmentStatePagerAdapter(fm) {
   private val items = ArrayList<Fragment>()

    override fun getItem(position: Int): Fragment {
        return items[position]
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getCount(): Int {
        return items.size
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    fun updateFragments(items :List<Fragment>) {
        this.items.addAll(items)
    }
}