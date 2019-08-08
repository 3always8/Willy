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

class MainActivity : AppCompatActivity() {

    private var pageDb: PageDB? = null
    var viewPager: ViewPager? = null
    var adapter: ViewPageAdapter? = null
    var contentArray: Array<String?>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //toolbar
        setSupportActionBar(findViewById(R.id.toolbar))

        //tablayout
        var tabLayout = findViewById<TabLayout>(R.id.tabs_main)
//      line under the current tab
        viewPager = findViewById<ViewPager>(R.id.view_pager)
        adapter = ViewPageAdapter(supportFragmentManager)
        viewPager?.adapter = adapter
        val r = Runnable{
            tabLayout.setupWithViewPager(viewPager)
        }
        tabLayout.postDelayed(r, 100)

        pageDb = PageDB.getInstance(this)

        val rDb = Runnable{
            val pageList =pageDb?.pageDao()?.getAll()
            if (pageList?.get(0)?.content == null ){
                pageDb?.pageDao()?.insert(Page(0,"people","initial data"))
            }
            if (pageList?.get(1)?.content == null ){
                pageDb?.pageDao()?.insert(Page(1,"money","initial data"))
            }
            if (pageList?.get(2)?.content == null ){
                pageDb?.pageDao()?.insert(Page(2,"funeral","initial data"))
            }

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
            val page = Page(0,"people",editTextView?.text.toString())
            pageDb?.pageDao()?.insert(page)
            contentArray?.set(0, editTextView?.text.toString())
            if (pageDb?.pageDao()?.getAll()?.get(0) != null ){
                Log.d("TAG", pageDb?.pageDao()?.getAll()?.get(0)?.content)
            }
        }
        val thread = Thread(r)
        thread.start()

        //Finish Writing: turn off the keyboard and clear focus.
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
        clearFocus()
    }

}
