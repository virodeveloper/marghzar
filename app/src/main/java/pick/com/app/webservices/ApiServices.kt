package pick.com.app.webservices

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.annotation.UiThread
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat.startActivityForResult
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.*
import com.facebook.FacebookSdk.getApplicationContext
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.livinglifetechway.k4kotlin.core.isNetworkAvailable
import com.livinglifetechway.k4kotlin.core.toast
import com.paytabs.paytabs_sdk.payment.ui.activities.PayTabActivity
import com.paytabs.paytabs_sdk.utils.PaymentParams
import org.json.JSONArray
import org.json.JSONObject
import pick.com.app.MyApplication
import pick.com.app.R
import pick.com.app.base.CommonActivity
import pick.com.app.interfaces.onResponse
import pick.com.app.uitility.session.SessionManager
import pick.com.app.varient.owner.pojo.ImageModel
import pick.com.app.varient.user.pojo.RegistrationModel
import java.io.File
import java.io.FileOutputStream
import java.util.*
import javax.inject.Inject


class ApiServices<T> {

    public fun goPayment(activity: Activity) {
        val intent = Intent(activity, PayTabActivity::class.java)
        intent.putExtra(PaymentParams.MERCHANT_EMAIL, "turkiothman@hotmail.com")
        intent.putExtra(
            PaymentParams.SECRET_KEY,
            "8pJ1qNyOZvvxFkaYXpwZJC6gDBmOEeGNEpO5dnTcSoJyIUxFj3F3FLkjnG81VOPzeEFr1yEUXnsFiZlB31FJNZ3aFbcB01afFQHG"
        )//Add your Secret Key Here

        intent.putExtra(PaymentParams.LANGUAGE, PaymentParams.ENGLISH)
        intent.putExtra(PaymentParams.TRANSACTION_TITLE, "Test Paytabs android library")
        intent.putExtra(PaymentParams.AMOUNT, 5.0)

        intent.putExtra(PaymentParams.CURRENCY_CODE, "BHD")
        intent.putExtra(PaymentParams.CUSTOMER_PHONE_NUMBER, "009733")
        intent.putExtra(PaymentParams.CUSTOMER_EMAIL, "customer-email@example.com")
        intent.putExtra(PaymentParams.ORDER_ID, "123456")
        intent.putExtra(PaymentParams.PRODUCT_NAME, "Product 1, Product 2")

        //Billing Address
        intent.putExtra(PaymentParams.ADDRESS_BILLING, "Flat 1,Building 123, Road 2345")
        intent.putExtra(PaymentParams.CITY_BILLING, "Manama")
        intent.putExtra(PaymentParams.STATE_BILLING, "Manama")
        intent.putExtra(PaymentParams.COUNTRY_BILLING, "BHR")
        intent.putExtra(
            PaymentParams.POSTAL_CODE_BILLING,
            "00973"
        ) //Put Country Phone code if Postal code not available '00973'

        //Shipping Address
        intent.putExtra(PaymentParams.ADDRESS_SHIPPING, "Flat 1,Building 123, Road 2345")
        intent.putExtra(PaymentParams.CITY_SHIPPING, "Manama")
        intent.putExtra(PaymentParams.STATE_SHIPPING, "Manama")
        intent.putExtra(PaymentParams.COUNTRY_SHIPPING, "BHR")
        intent.putExtra(
            PaymentParams.POSTAL_CODE_SHIPPING,
            "00973"
        ) //Put Country Phone code if Postal code not available '00973'

        //Payment Page Style
        intent.putExtra(PaymentParams.PAY_BUTTON_COLOR, "#2474bc")

        //Tokenization
        intent.putExtra(PaymentParams.IS_TOKENIZATION, true)
        activity.startActivityForResult(intent, PaymentParams.PAYMENT_REQUEST_CODE)
    }


    @Inject
    lateinit var context: CommonActivity


    fun getAlertDialog(activity: Activity): AlertDialog {



        val builder = AlertDialog.Builder(activity, pick.com.app.R.style.CustomDialog)
        //View view = getLayoutInflater().inflate(R.layout.progress);
        builder.setView(pick.com.app.R.layout.progressbar_dialog)
        builder.setCancelable(false)
        return builder.create()


    }

    fun getSocileFile(model: RegistrationModel) {
        var filename = ""
        if (model.data.login_type == "f")
            filename = "${System.currentTimeMillis()}.jpg"
        else
            filename = File(model.data.social_pic).name



        AndroidNetworking.get(model.data.social_pic)
                 .setTag("imageRequestTag")
                 .setPriority(Priority.MEDIUM)
                 .setBitmapMaxHeight(100)
                 .setBitmapMaxWidth(100)
                 .setBitmapConfig(Bitmap.Config.ARGB_8888)
                 .build()
                 .getAsBitmap(object :  BitmapRequestListener {

                     override fun onResponse(bitmap:Bitmap) {
                     // do anything with bitmap



                         val root = Environment.getExternalStorageDirectory().toString()
                         val myDir = File("$root/req_images")
                         myDir.mkdirs()
                         val generator = Random()
                         var n = 10000
                         n = generator.nextInt(n)
                         val fname = "Image-$n.jpg"
                         val file = File(myDir, fname)
                         Log.i("", "" + file)
                         if (file.exists())
                             file.delete()
                         try {
                             val out = FileOutputStream(file)
                             bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)
                             out.flush()
                             out.close()

                             model.data.profilepic =
                                 file

                         } catch (e: Exception) {
                             e.printStackTrace()
                         }




                     }
                     public  override    fun onError( error:ANError) {
                      // handle error
                    }
                });







    }


    fun fileUploadWithProgressBar(model_:ImageModel,hashMap: HashMap<String, Any>,activity: Activity){


AndroidNetworking.upload("${Urls.BASE_URL}${Urls.IMAGE_UPLOAD}")
                 .addMultipartFile("car_image",File(model_.url))
                 .addMultipartParameter("user_id",hashMap["user_id"].toString())
                 .addMultipartParameter("lang",SessionManager().getLaunguage(activity))
                 .addMultipartParameter("booking_id",hashMap["booking_id"].toString())
                 .addMultipartParameter("type",hashMap["type"].toString())
                 .setTag(model_.url)
                 .setPriority(Priority.HIGH)
                 .build()
                 .setUploadProgressListener(object: UploadProgressListener{
                     override fun onProgress(bytesUploaded: Long, totalBytes: Long) {
                         val downloaded = bytesUploaded
                         val total = totalBytes
                         val percent = 100 * downloaded / total
                      //   println(String.format("%.0f%%", percent))
                         model_.percantage=percent.toInt().toString()
                         model_.isvisible = model_.percantage != "100"
                     }

                 })
                 .getAsString(object:StringRequestListener{
                     override fun onResponse(response: String?) {
Log.e("","")

                         var model=Gson().fromJson(response!!,ImageModel::class.java)

                         model_.image_id= model.image_id

                     }

                     override fun onError(anError: ANError?) {

                     }

                 });



    }

    fun PdfDownlaod(url: String,activity: Activity,showprogressbar:Boolean=true){



        val file = File(
            Environment.getExternalStorageDirectory(),
            File(url).name
        )
        if (file.exists() && !file.isDirectory()) {

            val path = Uri.fromFile(file)
            val pdfOpenintent = Intent(Intent.ACTION_VIEW)
            pdfOpenintent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            pdfOpenintent.setDataAndType(path, "application/pdf")
            try {
                activity.startActivity(pdfOpenintent)
            } catch (e: ActivityNotFoundException) {

            }
            // do something
        }else{
        if (activity.isNetworkAvailable()) {
            MyApplication.appComponent.inject(this as ApiServices<Any>)
            var alertDialog = getAlertDialog(activity)



            if (showprogressbar) {
                alertDialog.show()
            }
            AndroidNetworking.download(url, Environment.getExternalStorageDirectory().absolutePath, file.name)
                .setTag("downloadTest")
                .setPriority(Priority.MEDIUM)
                .build()
                .setDownloadProgressListener(object : DownloadProgressListener {
                    override fun onProgress(bytesDownloaded: Long, totalBytes: Long) {
                        // do anything with progress
                    }
                })
                .startDownload(object : DownloadListener {
                    override fun onDownloadComplete() {
                        // do anything after completion
                        if (alertDialog.isShowing) {
                            alertDialog.dismiss()
                        }

                        val path = Uri.fromFile(file)
                        val pdfOpenintent = Intent(Intent.ACTION_VIEW)
                        pdfOpenintent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                        pdfOpenintent.setDataAndType(path, "application/pdf")
                        try {
                            activity.startActivity(pdfOpenintent)
                        } catch (e: ActivityNotFoundException) {

                        }

                    }

                    override fun onError(error: ANError) {
                        // handle error
                        if (alertDialog.isShowing) {
                            alertDialog.hide()
                        }
                    }
                })
        }
    }
    }

    @UiThread
    fun callApi(
        url: String,
        onResponse: onResponse,
        hashMap: HashMap<String, Any> =HashMap<String, Any>(),
        class_: Class<*>,
        showprogressbar: Boolean, activity: Activity
    ) {

        hashMap.put("device_token",SessionManager().getDeviceID(activity))
        hashMap.put("lang",SessionManager().getLaunguage(activity))

            if (activity.isNetworkAvailable()) {
                MyApplication.appComponent.inject(this as ApiServices<Any>)
                var alertDialog = getAlertDialog(activity)



                if (showprogressbar==true) {
                    alertDialog.show()
                }

                var natorking = AndroidNetworking.upload("${Urls.BASE_URL}$url")



                for (pair in hashMap!!.entries) {
                    val key = pair.key
                    val tab = pair.value

                    // println(pair.key + " = " + pair.value)

                    if (pair.value is File) {
                        if ((pair.value as File).path != "")
                        natorking.addMultipartFile("${pair.key}", pair.value as File)

                    } else if (pair.value is String) {


                        natorking.addMultipartParameter("${pair.key}", "${pair.value}")


                    } else if (pair.value is Int) {

                        natorking.addMultipartParameter("${pair.key}", "${pair.value}")


                    } else if (pair.value is Double) {

                        natorking.addMultipartParameter("${pair.key}", "${pair.value}")

                    } else {
                        var hashMap = ArrayList<RegistrationModel.Data.Locations>()
                        hashMap = pair.value as ArrayList<RegistrationModel.Data.Locations>
                        //  natorking.addMultipartParameter(hashMap,"${pair.key}")

                        Gson().toJson(hashMap)
                        val jsonParser = JsonParser()
                        jsonParser.parse(Gson().toJson(hashMap))

                        var array = JSONArray(Gson().toJson(hashMap))
                        var jsonobhetc = JSONObject()

                        //   jsonobhetc.put("${pair.key}",array.toString())


                        //  natorking.addMultipartFile("${pair.key}", jsonobhetc)


                        // jsonobhetc.put("",Gson().toJson(hashMap))


                        //    var object_= Gson().fromJson(Gson().toJson(hashMap),MultipleDropLocation::class.java)

                        // natorking.addMultipartFileList( "",Gson().toJson(array))
                        //natorking.addJSONArrayBody(array)
                        // natorking.addStringBody(Gson().toJson(jsonobhetc))
                    }


                }

                Log.d("api request", Gson().toJson(JSONObject(hashMap)));


                natorking.setTag(this)
                    .setPriority(Priority.LOW)
                    .build()

                    .getAsString(object : StringRequestListener {
                        override fun onResponse(response: String?) {

                            Log.d("api Response", response);



                            alertDialog.dismiss()
                            try {

                                onResponse.onSucess(Gson().fromJson(response, class_), url)

                            } catch (e: Exception) {
                                onResponse.onError(response)
                            }
                        }

                        override fun onError(anError: ANError?) {
                            Log.d("", "lastname : " + "")
                            alertDialog.dismiss()
                            onResponse.onError(anError!!.errorDetail)
                        }

                    })


                /*  .getAsObject(class_, object : ParsedRequestListener<T> {
                    override fun onResponse(user: T) {
                        // do anything with response
                        alertDialog.dismiss()
                        onResponse.onSucess(user, url)

                    }

                    override fun onError(anError: ANError) {
                        // handle error
                        Log.d("", "lastname : " + "")
                        alertDialog.dismiss()
                        onResponse.onError(anError.errorDetail)
                    }
                })*/

            } else {

                activity.toast(activity.resources.getString(R.string.internet_is_not_working_properly))

            }

        }




    lateinit var keys: Iterator<String>
    @UiThread

    fun callApiinObject(
        url: String,
        onResponse: onResponse,
        hashMap: JSONObject=JSONObject(),
        class_: Class<*>,
        showprogressbar: Boolean, activity: Activity
    ) {


        hashMap.put("device_token",SessionManager().getDeviceID(activity))
        hashMap.put("lang",SessionManager().getLaunguage(activity))

        if (activity.isNetworkAvailable()) {
                MyApplication.appComponent.inject(this as ApiServices<Any>)
                var alertDialog = getAlertDialog(activity)


                if (showprogressbar) {
                    alertDialog.show()
                }

                var natorking = AndroidNetworking.upload("${Urls.BASE_URL}$url")



                keys = hashMap!!.keys()
                while (keys.hasNext()) {
                    val key = keys.next() as String // First key in your json object

                    if (hashMap!!.get(key) is File) {
                        if ((hashMap!!.get(key) as File).path != "")
                            natorking.addMultipartFile(key, hashMap!!.get(key) as File)
                    }


                    natorking.addMultipartParameter("$key", "${hashMap.get(key)}".replace("[", "").replace("]", ""))
                }





            Log.d("api request", Gson().toJson((hashMap)));

                natorking.setTag(this)
                    .setPriority(Priority.LOW)
                    .build().getAsString(object : StringRequestListener {
                        override fun onResponse(response: String?) {
                            Log.d("", "lastname : " + "")
                            alertDialog.dismiss()
                            try {

                                onResponse.onSucess(Gson().fromJson(response, class_), url)

                            } catch (e: Exception) {
                                onResponse.onError(response)
                            }

                        }

                        override fun onError(anError: ANError?) {
                            Log.d("", "lastname : " + "")
                            alertDialog.dismiss()
                            onResponse.onError(anError!!.response.toString())
                        }

                    })

                /*  natorking.setTag(this)
                  .setPriority(Priority.LOW)
                  .build()
                  .getAsObject(class_, object : ParsedRequestListener<T> {
                  override fun onResponse(user: T) {
                      // do anything with response
                      alertDialog.dismiss()
                      onResponse.onSucess(user,url)

                  }

                  override fun onError(anError: ANError) {
                      // handle error
                      Log.d("", "lastname : " + "")
                        alertDialog.dismiss()
                      onResponse.onError(anError.errorDetail)
                  }
              })*/
            } else {

                activity.toast(activity.resources.getString(R.string.internet_is_not_working_properly))

            }




    }


    fun callApiRegister(
        url: String,
        onResponse: onResponse,
        hashMap: HashMap<String, Any>?,
        class_: Class<*>,
        showprogressbar: Boolean, activity: Activity
    ) {
        MyApplication.appComponent.inject(this as ApiServices<Any>)
        var alertDialog = getAlertDialog(activity)


        if (showprogressbar) {
            alertDialog.show()
        }

        var natorking = AndroidNetworking.post("${Urls.BASE_URL}$url")



        for (pair in hashMap!!.entries) {
            val key = pair.key
            val tab = pair.value

            // println(pair.key + " = " + pair.value)

            if (pair.value is File) {
                //if ((pair.value as File).path != "")
                //natorking.addFileBody("${pair.key}", pair.value as File)

            } else if (pair.value is String) {

                var jsonObject = JSONObject()

                jsonObject.put("${pair.key}", "${pair.value}")
                natorking.addJSONObjectBody(jsonObject)
                natorking.addStringBody(Gson().toJson(jsonObject))


            } else if (pair.value is Int) {
                var jsonObject = JSONObject()
                jsonObject.put("${pair.key}", "${pair.value}")
                natorking.addJSONObjectBody(jsonObject)
                natorking.addStringBody(Gson().toJson(jsonObject))


            } else if (pair.value is Double) {
                var jsonObject = JSONObject()
                jsonObject.put("${pair.key}", "${pair.value}")
                natorking.addJSONObjectBody(jsonObject)
                natorking.addStringBody(Gson().toJson(jsonObject))

            } else {
                var hashMap = ArrayList<RegistrationModel.Data.Locations>()
                hashMap = pair.value as ArrayList<RegistrationModel.Data.Locations>
                //  natorking.addMultipartParameter(hashMap,"${pair.key}")

                Gson().toJson(hashMap)
                val jsonParser = JsonParser()
                jsonParser.parse(Gson().toJson(hashMap))

                var array = JSONArray(Gson().toJson(hashMap))
                var jsonobhetc = JSONObject()

                //   jsonobhetc.put("${pair.key}",array.toString())


                //  natorking.addMultipartFile("${pair.key}", jsonobhetc)


                // jsonobhetc.put("",Gson().toJson(hashMap))


                //    var object_= Gson().fromJson(Gson().toJson(hashMap),MultipleDropLocation::class.java)

                // natorking.addMultipartFileList( "",Gson().toJson(array))
                //natorking.addJSONArrayBody(array)
                // natorking.addStringBody(Gson().toJson(jsonobhetc))
            }


        }


/*
        natorking.setTag(this)
            .setPriority(Priority.LOW)
            .build().getAsString(object : StringRequestListener {
                override fun onResponse(response: String?) {
                    Log.d("", "lastname : " + "")
                    alertDialog.dismiss()
                    onResponse.onError(response)
                }

                override fun onError(anError: ANError?) {
                    Log.d("", "lastname : " + "")
                    alertDialog.dismiss()
                    onResponse.onError(anError!!.errorDetail)
                }

            })*/

        natorking.setTag(this)
            .setPriority(Priority.LOW)
            .build()
            .getAsObject(class_, object : ParsedRequestListener<T> {
                override fun onResponse(user: T) {
                    // do anything with response
                    alertDialog.dismiss()
                    onResponse.onSucess(user, url)

                }

                override fun onError(anError: ANError) {
                    // handle error
                    Log.d("", "lastname : " + "")
                    alertDialog.dismiss()
                    onResponse.onError(anError.errorDetail)
                }
            })


    }


    fun callApiObject(
        url: String,
        onResponse: onResponse,
        hashMap: RegistrationModel,
        class_: Class<*>,
        showprogressbar: Boolean, activity: Activity
    ) {
        MyApplication.appComponent.inject(this as ApiServices<Any>)
        var alertDialog = getAlertDialog(activity)


        if (showprogressbar) {
            alertDialog.show()
        }

        var natorking = AndroidNetworking.upload("${Urls.BASE_URL}$url")





        natorking.setTag(this)
            .setPriority(Priority.LOW)
            .build().getAsString(object : StringRequestListener {
                override fun onResponse(response: String?) {
                    Log.d("", "lastname : " + "")
                    alertDialog.dismiss()
                    onResponse.onError(response)
                }

                override fun onError(anError: ANError?) {
                    Log.d("", "lastname : " + "")
                    alertDialog.dismiss()
                    onResponse.onError(anError!!.errorDetail)
                }

            })

        /* natorking.setTag(this)
             .setPriority(Priority.LOW)
             .build()
             .getAsObject(class_, object : ParsedRequestListener<T> {
             override fun onResponse(user: T) {
                 // do anything with response
                 alertDialog.dismiss()
                 onResponse.onSucess(user,url)

             }

             override fun onError(anError: ANError) {
                 // handle error
                 Log.d("", "lastname : " + "")
                   alertDialog.dismiss()
                 onResponse.onError(anError.errorDetail)
             }
         })*/


    }


}






