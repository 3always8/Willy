package com.ssong.willy

import android.annotation.SuppressLint
import android.content.Context
import android.os.*
import android.support.v4.app.Fragment
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import kotlin.concurrent.thread
import android.os.Looper
import java.lang.Compiler.command



class MoneyFragment : Fragment() {

    private var pageDb: PageDB? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_people, null)
        pageDb = PageDB.getInstance(activity as MainActivity)
        // I thought this will be needed but no it didn't.
//        val editTextView = view.findViewById<WillyPageView>(R.id.willy_page_view).findViewById<EditText>(R.id.edit_text)
        val editTextView = view.findViewById<EditText>(R.id.edit_text)

        Thread(Runnable{
            Looper.prepare()
            editTextView.setText(pageDb?.pageDao()?.getAll()?.get(1)?.content)
            Log.d("TAG", "MoneyFragment thread : "+pageDb?.pageDao()?.getAll()?.get(1)?.content)
        }).start()

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }


}
