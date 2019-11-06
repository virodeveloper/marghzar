package pick.com.app.base.model

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import pick.com.app.R
import java.util.*

class ToolbarCustom {

    var lefticon: Int =if(Locale.getDefault().language=="en") R.drawable.back_arrow else R.drawable.right_arrow
    var title: String = ""
    var righticon: Int = R.drawable.nav_icon
    var notificatoinicon: Int = R.drawable.nav_icon


    constructor(lefticon: Int, title: String, righticon: Int, notificatoinicon: Int) {
        this.lefticon = lefticon
        this.title = title
        this.righticon = righticon
        this.notificatoinicon = notificatoinicon
    }


    companion object {
        var lefticon: Int =if(Locale.getDefault().language=="en") R.drawable.back_arrow else R.drawable.right_arrow

        var NoIcon=1001

        @JvmStatic
        @BindingAdapter("setLeftIcon")
        fun setLeftIcon(view: ImageView,icon: ToolbarCustom) {
            view.setImageResource(icon.lefticon)

        }

        @JvmStatic
        @BindingAdapter("setIcon")
        fun setIcon(view: ImageView,icon: Int) {
            if (icon!=1001)
            view.setImageResource(icon)
            else
                view.setImageDrawable(null)

        }

        @JvmStatic
        @BindingAdapter("setRightIcon")
        fun setRightIcon(view: ImageView,icon: ToolbarCustom) {
            view.setImageResource(icon.righticon)
        }

    }


}
