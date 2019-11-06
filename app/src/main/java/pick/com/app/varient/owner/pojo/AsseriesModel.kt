package pick.com.app.varient.owner.pojo

import android.view.View
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.BindingAdapter
import com.adapter.UniverSalBindAdapter
import com.livinglifetechway.k4kotlin.core.*
import pick.com.app.BR
import pick.com.app.R
import pick.com.app.base.CommonActivity

class AsseriesModel(var addapter: UniverSalBindAdapter) : BaseObservable() {

    var position=0

    @Bindable
    var name = ""
        set(value) {
            if (field != value) {

                field = value
                notifyPropertyChanged(BR.name)

            }
        }


    @Bindable
    var qty = ""
        set(value) {
            if (field != value) {

                field = value
                notifyPropertyChanged(BR.qty)

            }
        }


    @Bindable
    var price = ""
        set(value) {
            if (field != value) {

                field = value
                notifyPropertyChanged(BR.price)

            }
        }





    companion object {



        @JvmStatic
        @BindingAdapter("addAccessories")
        fun addAccessories(view: View,model: AsseriesModel){



            if (model.addapter.arrraylist.size - 1 == model.position) {
                view.show()
            } else {
                view.invisible()
            }

            view.setOnClickListener {

                CommonActivity.activity.hideKeyboard()
                if (model.addapter.arrraylist.size<20)
                    model.addapter.addWithNotify(AsseriesModel(model.addapter))
                else
                    view.context.toast(it.context.getString(R.string.drop_location_max_twenty))

            }


        }

        @JvmStatic
        @BindingAdapter("removeAccessories")
        fun removeAccessories(view: View,model: AsseriesModel){


            if (model.position == model.addapter.arrraylist.size - 1) {
                view.show()
            } else {
                view.show()
            }

            if (model.addapter.arrraylist.size == 1) {
                view.hide()
            }

            view.onClick {
                CommonActivity.activity.hideKeyboard()
                model.addapter.removeItemWithnotify(model)
            }
        }

    }
}
