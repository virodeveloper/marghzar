package pick.com.app

import android.app.Application
import android.os.StrictMode
import androidx.appcompat.app.AlertDialog
import androidx.multidex.MultiDex
import com.google.android.gms.maps.MapsInitializer
import pick.com.app.di.AppModule
import pick.com.app.di.ApplicationComponent
import pick.com.app.di.DaggerApplicationComponent
import pick.com.app.uitility.TypefaceUtil


class MyApplication : Application() {





    override fun onCreate() {
        super.onCreate()

        appComponent=   DaggerApplicationComponent.builder().appModule(AppModule(this)).build()

        MultiDex.install(this)
        appComponent.inject(this)
        MapsInitializer.initialize(this);

        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
        builder.detectFileUriExposure()
        TypefaceUtil.overrideFont(applicationContext, "SERIF", "Cairo-Regular.ttf");

    }




    fun getInstance(): ApplicationComponent {
        if (appComponent == null)
            appComponent=   DaggerApplicationComponent.builder().appModule(AppModule(this)).build()


        return appComponent
    }






    companion object {
        lateinit var appComponent: ApplicationComponent
        lateinit var alertDialog: AlertDialog
    }
}
