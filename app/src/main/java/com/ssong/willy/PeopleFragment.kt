package com.ssong.willy

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText


class PeopleFragment : Fragment() {

    private var pageDb: PageDB? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true

        pageDb = PageDB.getInstance(activity as MainActivity)

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_people, null)
        var editTextView = view.findViewById<EditText>(R.id.edit_text)

        editTextView.text = Editable.Factory.getInstance().newEditable((activity as MainActivity).contentArray?.get(0))


/*
        val thread = Thread{editTextView.text = Editable.Factory.getInstance().newEditable(pageDb?.pageDao()?.getPage("people")?.content)}
        thread.start()
*/
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }


}
