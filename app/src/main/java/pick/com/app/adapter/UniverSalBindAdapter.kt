package com.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.livinglifetechway.k4kotlin.core.onClick
import pick.com.app.BR
import pick.com.app.base.model.CountryCodeModel
import pick.com.app.base.model.DrawerModel
import pick.com.app.interfaces.onItemClick
import pick.com.app.varient.owner.pojo.BookingModel
import pick.com.app.varient.owner.pojo.BookingModel.Data.BeforePickupDetail.AsseriesData
import pick.com.app.varient.owner.pojo.ImageModel
import pick.com.app.varient.owner.pojo.VehicleModel
import pick.com.app.varient.user.pojo.RegistrationModel
import java.io.Serializable


/**
 * Created by naseem on 28/11/17.
 */

class UniverSalBindAdapter(): RecyclerView.Adapter< RecyclerView.ViewHolder>() ,Filterable {
    override fun getFilter(): Filter {

        return object : Filter() {
           override  fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                if (charString.isEmpty()) {
                    arrraylist = contactListFiltered
                } else {
                    val filteredList = java.util.ArrayList<Any>()
                    for (row in contactListFiltered) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row is CountryCodeModel.Data){
                            var row=row as CountryCodeModel.Data
                            if (row.sortname!!.toLowerCase().contains(charString.toLowerCase()) || row.phonecode.contains(
                                    charSequence
                                )||row.name!!.toLowerCase().contains(charString.toLowerCase())
                            ) {
                                filteredList.add(row)
                            }
                        }

                    }

                    arrraylist = filteredList
                }

                val filterResults = FilterResults()
                filterResults.values = arrraylist
                return filterResults
            }

       override      fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {

                arrraylist = filterResults.values as java.util.ArrayList<Any>
                notifyDataSetChanged()
            }
        }
    }



    override fun getItemViewType(position: Int): Int {

        return if (position == arrraylist!!.size - 1 && isLoadingAdded) LOADING else ITEM

    }
    var type=""
    var layout:Int = 0

    constructor(layout:Int) :this() {
        univerSalBindAdapter=this

        this.layout=layout
    }
   var  itemclick: onItemClick?=null
 constructor(layout:Int,itemclick: onItemClick) :this() {
        univerSalBindAdapter=this

        this.layout=layout
        this.itemclick=itemclick
    }


    constructor(layout:Int,listener:ItemAdapterListener) :this() {
        univerSalBindAdapter=this
        this.listener=listener
        this.layout=layout
    }
     constructor(layout:Int,type:String) :this() {
         this.layout=layout
        this.type=type
    }

    override fun getItemCount(): Int {

        return if (arrraylist == null) 0 else arrraylist!!.size
    }

    fun <T : RecyclerView.ViewHolder> T.onClick(event: (view: View, position: Int, type: Int) -> Unit): T {
        itemView.setOnClickListener {
            event.invoke(it, getAdapterPosition(), getItemViewType())
        }
        return this
    }

    private var isLoadingAdded = false
    private var retryPageLoad = false

    fun addLoadingFooter( any: Any) {
        isLoadingAdded = true
        add(any)
    }

    fun removeItem(item:Any) {

       var position=arrraylist!!.indexOf(item)
            arrraylist!!.removeAt(position)
            notifyItemRemoved(position)

    }

    fun removeItemWithnotify(item:Any) {

        var position=arrraylist!!.indexOf(item)
        arrraylist!!.removeAt(position)
        notifyItemRemoved(position)
notifyDataSetChanged()
    }
    fun removeLoadingFooter() {
        isLoadingAdded = false

        val position = arrraylist!!.size - 1
        val result = getItem(position)

        if (result != null&&position!=-1) {
            arrraylist!!.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    var arrraylist=ArrayList<Any>()



     var contactListFiltered=ArrayList<Any>()

    fun getItem(position: Int): Any? {
        return arrraylist[position]
    }

    fun add(r: Any,position: Int) {
        arrraylist.add(position,r)

        notifyItemInserted(position)
    }

    fun add(r: Any) {
        arrraylist.add(r)
        contactListFiltered.add(r)

        notifyItemInserted(arrraylist.size - 1)
    }

    fun removeAll() {
        arrraylist.clear()
        contactListFiltered.clear()

       notifyDataSetChanged()
    }

    fun addWithNotify(r: Any) {
        arrraylist.add(r)

        notifyItemInserted(arrraylist.size - 1)
        notifyDataSetChanged()
    }



    fun addAll(arrraylist: ArrayList<Any>) {
        for (result in arrraylist) {
            add(result)
        }
    }





    override fun onBindViewHolder(parent:  RecyclerView.ViewHolder, p1: Int) {


        when (getItemViewType(p1)) {



            ITEM -> {


                val vh = parent as MYViewHolder
                when(arrraylist.get(p1)){
                    is RegistrationModel.Data.Locations->{
                        (arrraylist.get(p1) as RegistrationModel.Data.Locations).position=parent.adapterPosition
                    }
                    is DrawerModel->{
                        (arrraylist.get(p1) as DrawerModel).position=parent.adapterPosition
                    }is ImageModel ->{


                        (arrraylist.get(p1) as ImageModel).position=parent.adapterPosition
                    }

                    is VehicleModel.Data.BulkAvailability ->{


                        (arrraylist.get(p1) as VehicleModel.Data.BulkAvailability).position=parent.adapterPosition
                    } is AsseriesData ->{


                        (arrraylist.get(p1) as AsseriesData).position=parent.adapterPosition
                    }is BookingModel.Data ->{

if (itemclick!=null)
                        (arrraylist.get(p1) as BookingModel.Data).itemclick=itemclick
                    }

                }

                parent.databinding.setVariable(BR.user,arrraylist.get(p1))
                parent.databinding.executePendingBindings()


            }


            LOADING -> {
                val loadingVH = parent as LoadingVH

                if (retryPageLoad) {
                    loadingVH.mErrorLayout.visibility = View.VISIBLE
                    loadingVH.mProgressBar.visibility = View.GONE

                    loadingVH.mErrorTxt.text = if (errorMsg != null)
                        errorMsg
                    else
                       "An unexpected error occurred"

                } else {
                    loadingVH.mErrorLayout.visibility = View.GONE
                    loadingVH.mProgressBar.visibility = View.VISIBLE
                }
            }


        }


    }
    fun <T : RecyclerView.ViewHolder> T.listen(event: (position: Int, type: Int) -> Unit): T {
        itemView.setOnClickListener {
            event.invoke(getAdapterPosition(), getItemViewType())
        }
        return this
    }
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int):  RecyclerView.ViewHolder  {


        var viewHolder: RecyclerView.ViewHolder? = null
        val inflater = LayoutInflater.from(parent.context)

        when (p1) {
            ITEM -> {

                var     databinding:ViewDataBinding =  DataBindingUtil.inflate(inflater,layout, parent, false)



                viewHolder= MYViewHolder(databinding.root,databinding)


                viewHolder.itemView.onClick {
                    if (listener!=null)
                    listener!!.onItemSelected(arrraylist.get(viewHolder!!.adapterPosition)) }





            }
            LOADING -> {

                val viewLoading = inflater.inflate(pick.com.app.R.layout.item_progress, parent, false)
                viewHolder = LoadingVH(viewLoading)
            }

        }
        return viewHolder!!









    }


    protected inner class LoadingVH(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val mProgressBar: ProgressBar
        val mRetryBtn: ImageButton
        val mErrorTxt: TextView
        val mErrorLayout: LinearLayout

        init {

            mProgressBar = itemView.findViewById(pick.com.app.R.id.loadmore_progress)
            mRetryBtn = itemView.findViewById(pick.com.app.R.id.loadmore_retry)
            mErrorTxt = itemView.findViewById(pick.com.app.R.id.loadmore_errortxt)
            mErrorLayout = itemView.findViewById(pick.com.app.R.id.loadmore_errorlayout)

            mRetryBtn.setOnClickListener(this)
            mErrorLayout.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            when (view.id) {
                pick.com.app.R.id.loadmore_retry, pick.com.app.R.id.loadmore_errorlayout -> {

                    showRetry(false, null)
                    //mCallback.retryPageLoad()
                }
            }
        }
    }

    class MYViewHolder(view: View,databinding: ViewDataBinding) : RecyclerView.ViewHolder(view) {

       var databinding=databinding


    }
    fun showRetry(show: Boolean, errorMsg: String?) {
        retryPageLoad = show
        notifyItemChanged(arrraylist!!.size - 1)

        if (errorMsg != null) this.errorMsg = errorMsg
    }

    private var errorMsg: String? = null

    companion object {
        lateinit var univerSalBindAdapter:UniverSalBindAdapter
        // View Types
        private val ITEM = 0
        private val LOADING = 1
        private val HERO = 2

        private val BASE_URL_IMG = "https://image.tmdb.org/t/p/w150"
    }

     var listener: ItemAdapterListener? = null

   public interface ItemAdapterListener:Serializable {
        fun onItemSelected(model: Any)
    }
}
