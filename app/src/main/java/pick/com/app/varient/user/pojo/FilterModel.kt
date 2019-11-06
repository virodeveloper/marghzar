package pick.com.app.varient.user.pojo

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.adapter.UniverSalBindAdapter
import pick.com.app.R

class FilterModel {

companion object {
    var filtermodel=FilterModel()
    var hashmap=HashMap<String, Any>()
}
    private var status: String? = null
    private var message: String? = null
     var data= ArrayList<Data>()

    fun getStatus(): String? {
        return status
    }

    fun setStatus(status: String) {
        this.status = status
    }

    fun getMessage(): String? {
        return message
    }

    fun setMessage(message: String) {
        this.message = message
    }




    class Data {
        /**
         * title : Transmission
         * filterdata : [{"transmission_id":"1","transmission_title":"automatic"},{"transmission_id":"2","transmission_title":"manual"}]
         */

        var transmission_id: String? = null
        var title: String? = null
        var filterdata: List<Filterdata>? = null




        class Filterdata {
            /**
             * transmission_id : 1
             * transmission_title : automatic
             */
            companion object {
                @JvmStatic
                @BindingAdapter("setRecyclerView")
                fun setRecyclerView(recyclerView: RecyclerView, model: Filterdata) {


                    recyclerView.adapter = UniverSalBindAdapter(R.layout.row_layout_subfilter).apply {

                        addAll(model.subtypes as ArrayList<Any>)

                    }

                }
            }
            var transmission_id: String? = null
            var transmission_title: String? = null
            var type_id: String? = null
            var type_title: String? = null
            var subtype_title: String? = null
            var isitemSelected: Boolean? = false
            var subtypes: List<Subtypes>? = null



            class Subtypes{
                var subtype_id: String? = null
                var subtype_title: String? = null
                var isitemSelected: Boolean? = false
            }
        }
    }

}
