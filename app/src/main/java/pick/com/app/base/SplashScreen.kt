package pick.com.app.base

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.SystemClock
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.widget.VideoView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.BitmapRequestListener
import com.crashlytics.android.Crashlytics
import com.livinglifetechway.k4kotlin.core.toast
import io.fabric.sdk.android.Fabric
import pick.com.app.BuildConfig
import pick.com.app.R
import pick.com.app.service.OnClearFromRecentService
import pick.com.app.uitility.helpper.Redirection
import pick.com.app.uitility.session.SessionManager
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*


class
SplashScreen : BaseActivity() {


    private fun getAppName(context: Context): String {
        val pm = context.applicationContext.packageManager
        var ai: ApplicationInfo?
        try {
            ai = pm.getApplicationInfo(context.packageName, 0)
        } catch (e: PackageManager.NameNotFoundException) {
            ai = null
        }

        return (if (ai != null) pm.getApplicationLabel(ai) else "(unknown)") as String
    }

    private fun getWorkingDirectory(): File {
        val directory = File(Environment.getExternalStorageDirectory(), BuildConfig.APPLICATION_ID)
        if (!directory.exists()) {
            directory.mkdir()
        }
        return directory
    }
    fun getAppFolder(context: Context): File {
        val photoDirectory = File(
            getWorkingDirectory().getAbsolutePath(),
            getAppName(context.applicationContext)
        )
        if (!photoDirectory.exists()) {
            photoDirectory.mkdir()
        }
        return photoDirectory

    }
    fun shareImage(context: Context, bitmap: Bitmap): Uri? {
        val photo = File(
            getAppFolder(context),
            SystemClock.currentThreadTimeMillis().toString() + ".jpg"
        )
        try {

            val fos = FileOutputStream(photo.getPath())
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
            fos.close()
            if (photo.exists()) {
                val values = ContentValues()

                values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis())
                values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                values.put(MediaStore.MediaColumns.DATA, photo.getAbsolutePath())

                return context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
            }
        } catch (e: Exception) {
            Log.e("Picture", "Exception in photoCallback", e)
        }

        return null
    }


    fun getLocalBitmapUri(bmp: Bitmap): Uri? {
        var bmpUri: Uri? = null
        try {
            val file = File(
                getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                "share_image_" + System.currentTimeMillis() + ".png"
            )
            val out = FileOutputStream(file)
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out)
            out.close()
            bmpUri = Uri.fromFile(file)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return bmpUri
    }
    fun shareItem(url: String) {

        AndroidNetworking.get(url)
            .setTag("imageRequestTag")
            .setPriority(Priority.MEDIUM)
            .setBitmapMaxHeight(100)
            .setBitmapMaxWidth(100)
            .setBitmapConfig(Bitmap.Config.ARGB_8888)
            .build()
            .getAsBitmap(object : BitmapRequestListener {
                override fun onResponse(bitmap: Bitmap) {
                    // do anything with bitmap

                    val text = "Look at my awesome picture"
                    val pictureUri = Uri.parse("file://my_picture")
                    val shareIntent = Intent(Intent.ACTION_SEND)
                    shareIntent.putExtra(Intent.EXTRA_TEXT, text)
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, text)
                    shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

                    //  shareIntent.putExtra(Intent.EXTRA_STREAM, shareImage(this@SplashScreen,bitmap))
                    shareIntent.type = "*/*";
                    shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                  //  startActivity(Intent.createChooser(shareIntent, "Share images..."))


                   // shareIntent.setType("image/jpeg");

                    shareIntent.putExtra(Intent.EXTRA_STREAM, shareImage(this@SplashScreen, bitmap))
                startActivity(Intent.createChooser(shareIntent, "Share Image"))


                }

                override fun onError(error: ANError) {
                    // handle error
                }
            })
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Fabric.with(this,  Crashlytics());

        setContentView(pick.com.app.R.layout.splash_layout)
        startService(Intent(baseContext, OnClearFromRecentService::class.java))

        val videoHolder = findViewById<VideoView>(R.id.videoview)


        try {
            // VideoView videoHolder = new VideoView(thisetContentView(videoView);
            //videoHolder.setVideoURI(uri);
            val video = Uri.parse("android.resource://" + packageName + "/" + R.raw.pick)
            videoHolder.setVideoURI(video)
            videoHolder.requestFocus()

            videoHolder.setOnCompletionListener { jump() }
            videoHolder.start()
        } catch (ex: Exception) {
            jump()
        }
     //  shareItem("http://efdreams.com/data_images/dreams/face/face-03.jpg")

        var language =   Locale.getDefault().language

if (intent.getStringExtra("lang")!=null)
    SessionManager().setLaunguage(intent.getStringExtra("lang"),this)

        if(SessionManager().getLaunguage(this) == "launguage")
            SessionManager().setLaunguage(language, this)


        try {
            val info = packageManager.getPackageInfo(
                "pick.com.app.owner",
                PackageManager.GET_SIGNATURES
            )
            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT))
            }
        } catch (e: PackageManager.NameNotFoundException) {

        } catch (e: NoSuchAlgorithmException) {

        }


        val handler = Handler()
        handler.postDelayed({

            if (SessionManager.getLoginModel(this).data!=null&&SessionManager.getLoginModel(this).data.user_id!=""&&SessionManager.getLoginModel(this).data.is_active!=0){
                Redirection().goToHome(true, this, null)
            }


            else if (SessionManager.getLoginModel(this).message == ""){
                Redirection().goToLogin(true, this, null)


            }
            else if (SessionManager.getLoginModel(this).data!=null&&SessionManager.getLoginModel(this).data.is_active==0){
                Redirection().goToLogin(true, this, null)

            }

            else  if (SessionManager.getLoginModel(this).is_active==-1){
                Redirection().goToLogin(true, this, null)

            }

            else  if (SessionManager.getLoginModel(this).is_active==0||SessionManager.getLoginModel(this).is_active==-1){
                Redirection().goToLogin(true, this, null)

            }

            else
                Redirection().goToHome(true, this, null)

            finish()
        }, 3000)


    }

    private fun jump() {
        activity.toast("WELCOME")
    }
}
