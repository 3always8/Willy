package com.ssong.willy
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.willy_page_view.view.*

class WillyPageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyle, defStyleRes) {

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.willy_page_view, this, true)

        orientation = VERTICAL
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it,
                R.styleable.WillyPageView, 0, 0)
            val title = resources.getText(typedArray
                .getResourceId(R.styleable
                    .WillyPageView_custom_component_title,
                    R.string.component_one))

            edit_text.hint =
                "${resources.getString(R.string.hint_text)} $title"

            typedArray.recycle()
        }
    }

}