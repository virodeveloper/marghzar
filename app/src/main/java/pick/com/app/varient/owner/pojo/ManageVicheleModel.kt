package pick.com.app.varient.owner.pojo

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.adapter.UniverSalBindAdapter
import pick.com.app.R
import pick.com.app.uitility.custom.GridDividerDecoration

class ManageVicheleModel{

    companion object {
        @JvmStatic
        @BindingAdapter("setRecyclerView")
        fun setRecyclerView(recyclerView: RecyclerView, model: ManageVicheleModel) {


            recyclerView.addItemDecoration(GridDividerDecoration(recyclerView.context))
            recyclerView.adapter=UniverSalBindAdapter(R.layout.custom_week_layout).apply {

                add(model)
                add(model)
                add(model)
                add(model)
                add(model)
            }

        }
    }



    }
