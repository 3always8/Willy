package com.ssong.willy

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity(), ViewPageAdapter.SaveInterface {

    var pageDb: PageDB? = null
    var viewPager: ViewPager? = null
    var adapter: ViewPageAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //toolbar
        setSupportActionBar(findViewById(R.id.toolbar))

        //tablayout
        var tabLayout = findViewById<TabLayout>(R.id.tabs_main)
//      line under the current tab
        viewPager = findViewById<ViewPager>(R.id.view_pager)
        adapter = ViewPageAdapter(supportFragmentManager, this)
        viewPager?.adapter = adapter

        val r = Runnable{
            tabLayout.setupWithViewPager(viewPager)
        }
        tabLayout.postDelayed(r, 100)

        pageDb = PageDB.getInstance(this)

        val rDb = Runnable{
            val pageList =pageDb?.pageDao()?.getAll()
            pageDb?.pageDao()?.initialInsert(Page(0,"people","initial data"))
            pageDb?.pageDao()?.initialInsert(Page(1,"money","initial data"))
            pageDb?.pageDao()?.initialInsert(Page(2,"funeral","initial data"))

            Log.d("TAG", pageDb?.pageDao()?.getAll()?.get(0)?.content)

        }
        val threadDb = Thread(rDb)
        threadDb.start()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when(item?.itemId){
            R.id.action_finish -> adapter?.currentFragment?.view?.saveAndFinish()

        }
        return super.onOptionsItemSelected(item)
    }

    fun View.saveAndFinish() {
        //Save
        var editTextView = adapter?.currentFragment?.view?.findViewById<EditText>(R.id.edit_text)

        val r = Runnable{
            var i = when (adapter?.currentFragment) {
                is PeopleFragment -> 0
                is MoneyFragment -> 1
                is FuneralFragment -> 2
                else -> 0
            }
            var name = when (adapter?.currentFragment) {
                is PeopleFragment -> "people"
                is MoneyFragment -> "money"
                is FuneralFragment -> "funeral"
                else -> "people"
            }
            Log.d("TAG", editTextView?.text.toString())
            val page = Page(i, name, editTextView?.text.toString())
            pageDb?.pageDao()?.insert(page)
            Log.d("TAG", pageDb?.pageDao()?.getAll()?.get(i)?.content)

        }
        val thread = Thread(r)
        thread.start()

        //Finish Writing: turn off the keyboard and clear focus.
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)

        clearFocus()

    }

    override fun main() {
        this.adapter?.currentFragment?.view?.saveAndFinish()

    }
}