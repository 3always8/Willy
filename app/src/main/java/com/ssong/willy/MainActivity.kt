package com.ssong.willy

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

class MainActivity : AppCompatActivity() {

    private var pageDb: PageDB? = null

    var viewPager: ViewPager? = null
    var adapter: ViewPageAdapter? = null
    var currentFragment: Fragment? = null
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

        val threadDbFirst = Thread{
            if (pageDb?.pageDao()?.getPage("people") == null ){
                pageDb?.pageDao()?.insert(Page(0,"people",""))
            }
            if (pageDb?.pageDao()?.getPage("money") == null ){
                pageDb?.pageDao()?.insert(Page(0,"money",""))
            }
            if (pageDb?.pageDao()?.getPage("funeral") == null ){
                pageDb?.pageDao()?.insert(Page(0,"funeral",""))
            }

            contentArray = arrayOf(
                pageDb?.pageDao()?.getPage("people")?.content,
                pageDb?.pageDao()?.getPage("money")?.content,
                pageDb?.pageDao()?.getPage("funeral")?.content
            )


        }
        threadDbFirst.start()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when(item?.itemId){
            R.id.action_finish -> currentFocus?.saveAndFinish()

        }
        return super.onOptionsItemSelected(item)
    }

    fun View.saveAndFinish() {
        //Save
        var editTextView = currentFocus.findViewById<EditText>(R.id.edit_text)
        val threadDbSave = Thread{pageDb?.pageDao()?.getPage("people")?.content = editTextView.text.toString() }
        threadDbSave.start()

        //Finish Writing: turn off the keyboard and clear focus.
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
        clearFocus()
    }

}
