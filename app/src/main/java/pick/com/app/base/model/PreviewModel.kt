package pick.com.app.base.model

import java.io.Serializable

class PreviewModel :Serializable{

    var currentPosition=0
    var view_type=""

    var arrlist=ArrayList<Data>()

     class Data :Serializable{
         constructor(imageurl: String) {
             this.imageurl = imageurl
         }

         var imageurl=""
    }
}
