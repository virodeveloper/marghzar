package pick.com.app

import androidx.databinding.BaseObservable
import java.io.Serializable

class Model {
    var id: String = ""
    var firstname: String? = null
    var lastname: String? = null
    val bank_name = Array<String?>(10) { null }
    var status=""
    var data: List<Data>? = null
    class Data : Serializable, BaseObservable() {

        var wallet_id: String? = null
        var descriptions: String? = null
        var updated_on: String? = null


    }
}
