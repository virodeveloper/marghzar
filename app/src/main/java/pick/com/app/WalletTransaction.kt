package pick.com.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adapter.UniverSalBindAdapter
import kotlinx.android.synthetic.main.map_vehicle_listing_layout.*
import pick.com.app.R
import pick.com.app.interfaces.onResponse
import pick.com.app.varient.user.pojo.SearchBookingModel
import pick.com.app.varient.user.pojo.VehicleSearchData
import pick.com.app.webservices.ApiServices
import pick.com.app.webservices.Urls

class WalletTransaction : AppCompatActivity(),UniverSalBindAdapter.ItemAdapterListener, onResponse {

    lateinit var recyclerView: RecyclerView

    //var adpater: RecyclerView.Adapter<*>? = null

    override fun <T : Any?> onSucess(result: T, methodtype: String?) {
        if (methodtype == Urls.USERWALLET) {
         var  result=result as VehicleSearchData.Data

            Toast.makeText(applicationContext,"API",Toast.LENGTH_SHORT).show()


//
//            recyclerView.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
//
//
//            recyclerView.adapter = WallAdapter(applicationContext,result)


        }
    }

    override fun onError(error: String?) {

    }

    override fun onItemSelected(model: Any) {
        Toast.makeText(applicationContext,"Clicked",Toast.LENGTH_SHORT).show()
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction)
        recyclerView=findViewById(R.id.recyclerView)


        val hashmap = HashMap<String, Any>()
        hashmap["user_id"] ="237"
        ApiServices<VehicleSearchData>().callApi(
            Urls.USERWALLET, this,
            hashmap,
            VehicleSearchData::class.java, true, this
        )


    }
}
