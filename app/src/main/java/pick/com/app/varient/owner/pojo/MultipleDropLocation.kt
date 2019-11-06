package pick.com.app.varient.owner.pojo

import android.graphics.Color
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.adapter.UniverSalBindAdapter
import com.livinglifetechway.k4kotlin.core.addTextWatcher
import com.livinglifetechway.k4kotlin.core.invisible
import com.livinglifetechway.k4kotlin.core.onClick
import com.livinglifetechway.k4kotlin.core.show
import pick.com.app.uitility.custom.CustomEditText

class MultipleDropLocation {

    var drop_location_address: String = ""
    var drop_pin_code: String = ""
    var position: Int = 0


    var type: String = "registration"

    constructor(type: String) {
        this.type = type
    }


    companion object {

        @JvmStatic
        @BindingAdapter("setValue")
        fun setValue(editText: CustomEditText, model: MultipleDropLocation) {







            if (model.type != "registration"){
                editText.setBorderColor(Color.parseColor("#353535"))
                editText.setHintTextColor(Color.parseColor("#71353535"))
                editText.setTextColor(Color.parseColor("#353535"))}

            var textWatcher =
                editText.addTextWatcher(onTextChanged = { charSequence: CharSequence?, i: Int, i1: Int, i2: Int ->


                    when {
                        editText.hint.toString().toLowerCase().contains("address") -> {
                            model.drop_location_address = charSequence.toString()

                        }
                        editText.hint.toString().toLowerCase().contains("pincode") -> {
                            model.drop_pin_code = charSequence.toString()


                        }

                    }


                })

        }

        @JvmStatic
        @BindingAdapter("addLocation")
        fun addLocation(view: ImageView, model: MultipleDropLocation) {

            if (model.type != "registration") view.setColorFilter(Color.BLACK)

            if (UniverSalBindAdapter.univerSalBindAdapter.arrraylist.size - 1 == model.position) {
                view.show()
            } else {
                view.invisible()
            }
            view.onClick {

                UniverSalBindAdapter.univerSalBindAdapter.addWithNotify(MultipleDropLocation(model.type))
            }

        }

        @JvmStatic
        @BindingAdapter("removeLocation")
        fun removeLocation(view: ImageView, model: MultipleDropLocation) {
            if (model.type != "registration") view.setColorFilter(Color.BLACK)


            if (model.position == UniverSalBindAdapter.univerSalBindAdapter.arrraylist.size - 1) {
                view.show()
            } else {
                view.show()
            }

            view.onClick { UniverSalBindAdapter.univerSalBindAdapter.removeItemWithnotify(model) }

        }
    }

}
