package pick.com.app.varient.owner.pojo

import android.view.View
import android.widget.ImageView
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.adapter.UniverSalBindAdapter
import com.androidnetworking.AndroidNetworking
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.livinglifetechway.k4kotlin.core.onClick
import pick.com.app.BR
import pick.com.app.base.model.PreviewModel
import pick.com.app.interfaces.OnRemoveImage
import pick.com.app.interfaces.onItemSelect


class ImageModel():BaseObservable(){

    var position=0
    var image_id=""
    var image_view_type=""

    @Bindable
    var percantage: String = ""
        set(value) {
            if (field!=value){

                field=value
                notifyPropertyChanged(BR.percantage)

            }
        }


    @Bindable
    var isvisible: Boolean = false
        set(value) {
            if (field!=value){

                field=value
                notifyPropertyChanged(BR.isvisible)

            }
        }

    var url=""

     var adapter:UniverSalBindAdapter?=null
     var onitemSelect: onItemSelect?=null
     var onRemoveImage: OnRemoveImage?=null



    constructor(adapter: UniverSalBindAdapter,onitemSelect: onItemSelect,onRemoveImage : OnRemoveImage) : this(){
        this.adapter = adapter
        this.onitemSelect = onitemSelect
        this.onRemoveImage = onRemoveImage


    }
    constructor(adapter: UniverSalBindAdapter,onitemSelect: onItemSelect,onRemoveImage : OnRemoveImage,image_view_type:String) : this(){
        this.adapter = adapter
        this.onitemSelect = onitemSelect
        this.onRemoveImage = onRemoveImage
        this.image_view_type = image_view_type


    }

    constructor(onitemSelect: onItemSelect,image_view_type:String) : this(){

        this.onitemSelect = onitemSelect

        this.image_view_type = image_view_type


    }



    fun isAddIcon(model: ImageModel):Boolean{

        return if (model.position==9&&model.url!="")
            true

        else if (model.url!="")
            true

        else
            model.position != model.adapter!!.arrraylist.size-1
    }




    fun isImageUploadingLoad(imageModel: ImageModel):Boolean{

        if (imageModel.percantage=="0"&&imageModel.percantage=="100") return true

        return false


    }


    companion object {


        @JvmStatic
        @BindingAdapter("setRecyclerView")
        fun setRecyclerView(recyclerView: RecyclerView, model: ImageModel) {


            recyclerView.adapter= UniverSalBindAdapter(pick.com.app.R.layout.custom_image_booking_details).apply {

                add(model)
                add(model)
                add(model)
                add(model)
                add(model)
                add(model)
                add(model)
            }

        }

        @BindingAdapter("loadImage")
        @JvmStatic
        fun loadImage(view: ImageView, model: ImageModel) {

            view.onClick {

var previewModel=PreviewModel()
                previewModel.currentPosition=model.position
                previewModel.view_type=model.image_view_type



                model.onitemSelect!!.getSelectedItem(previewModel)

            }


            Glide.with(view)
                .asBitmap()
                .apply(
                    RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(pick.com.app.R.drawable.placeholder).error(
                        pick.com.app.R.drawable.placeholder
                    )
                )
                .load(model.url)
                .into(view);
        }


            @BindingAdapter("addImage")
            @JvmStatic
        fun addImage(view: View, model: ImageModel) {



            view.visibility= if (model.isAddIcon(model))  View.GONE else View.VISIBLE
            view.onClick {  model.onitemSelect!!.getSelectedItem(model)}

        }




            @BindingAdapter("crossIcon")
            @JvmStatic
        fun crossIcon(view: View, model: ImageModel) {



            view.visibility= if (model.isAddIcon(model)&& !model.url.contains("http"))  View.VISIBLE else View.GONE
            view.onClick {

                if (model.image_id!=null)
                model.onRemoveImage!!.onImageRemoved(model.image_id)
                model.adapter!!.removeItemWithnotify(model)
                AndroidNetworking.forceCancel(model.url)
                if (model.adapter!!.arrraylist.size==9&&(model.adapter!!.arrraylist.get(8) as ImageModel).url!="")
                 model.adapter!!. add(ImageModel(model.adapter!!,model.onitemSelect!!,model.onRemoveImage!!))
            }

        }
    }

}
