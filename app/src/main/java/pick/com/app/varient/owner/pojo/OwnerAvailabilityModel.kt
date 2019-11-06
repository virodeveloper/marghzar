package pick.com.app.varient.owner.pojo

import java.io.Serializable

class OwnerAvailabilityModel : Serializable{

    var status = 0
     var message: String? = null
     var description: String? = null
    var waqas: String? = null
     var data: List<Data>? = null

    class Data : Serializable{

        var avail_id : String? = null
        var user_id : String? = null
        var dayofweek : String? = null
        var start_time : String? = null
        var end_time : String? = null
        var is_available : Int? = null
        var start_date : String? = null
        var end_date : String? = null
        var is_user_available : Int? = null
    }
}