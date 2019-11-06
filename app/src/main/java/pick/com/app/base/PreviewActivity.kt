package pick.com.app.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_preview.*
import pick.com.app.R
import pick.com.app.adapter.ArrayViewPagerAdapter
import pick.com.app.adapter.MapListingAdpter
import pick.com.app.base.model.PreviewModel

class PreviewActivity : BaseActivity() {

    fun onBackPress(view :View){
        onBackPressed()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preview)
        var model=intent.extras.get("model") as PreviewModel
        var pagerdapter= MapListingAdpter<PreviewModel.Data>(model.arrlist,R.layout.custom_image_preview)

        var moss=object: ArrayViewPagerAdapter<PreviewModel.Data>(){
            override fun getView(
                inflater: LayoutInflater?,
                container: ViewGroup?,
                item: PreviewModel.Data?,
                position: Int
            ): View {

                var view=inflater!!.inflate(R.layout.custom_image_preview, container, false)

                var imageveiw=view.findViewById<ImageView>(R.id.imageveiw)


                loadImage(imageveiw,model.arrlist[position].imageurl)



               return view
            }

        }
        moss.addAll(model.arrlist)
        veiapger.adapter=moss
        veiapger.currentItem=model.currentPosition


    }


}
