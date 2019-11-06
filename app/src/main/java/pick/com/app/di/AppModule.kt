package pick.com.app.di

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import androidx.appcompat.app.AlertDialog
import android.widget.ProgressBar
import dagger.Module
import dagger.Provides
import pick.com.app.MyApplication
import pick.com.app.base.CommonActivity
import javax.inject.Singleton


@Module
 class AppModule(private val application: MyApplication) {



    @Provides
    @PerApplication
    @Singleton
    fun provideApplication(): MyApplication =application


    @Provides
    @Singleton
    fun getstring(): String ="Hello"

    @Provides
    @Singleton
    fun getPrefs(context: Context): SharedPreferences {

        return context.getSharedPreferences(
            "UserNameAcrossApplication",
            Context.MODE_PRIVATE
        )

    }

    @Provides
    @Singleton
    fun getAlertDialog(context: Context):AlertDialog{

        val builder = AlertDialog.Builder(context)
        //View view = getLayoutInflater().inflate(R.layout.progress);

       return builder.create()
    }



    @Provides
    @Singleton
    internal fun providesAppContext(): Context {


        return application

    }




    @Provides
    @Singleton
    internal fun getCommonACtivity(): CommonActivity {


        return CommonActivity()

    }





    @Provides
    @Singleton
    internal fun getprogressBar(context: Context): ProgressBar {


        return ProgressBar(context)

    }




    @Provides
    @Singleton
    internal fun getResources(context: Context): Resources {


        return context.resources

    }

}
