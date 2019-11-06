package pick.com.app.base.sociallogin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.iid.FirebaseInstanceId
import com.livinglifetechway.k4kotlin.core.onClick
import pick.com.app.R
import pick.com.app.base.model.CustomFirebaseUser
import pick.com.app.uitility.session.SessionManager
import java.util.*

open class SocialLogin : GoogleLogin(), getFirebaseUser {


    var mCallbackManager: CallbackManager?=null




    fun faceBookLogout() {


        LoginManager.getInstance().logOut();
        FirebaseAuth.getInstance().signOut();

    }

    internal var TAG = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w(TAG, "getInstanceId failed", task.exception)
                    return@OnCompleteListener
                }

                // Get new Instance ID token
                val token = task.result?.token

                // Log and toast
                //  val msg = getString(R.string.msg_token_fmt, token)
                //Log.d(TAG, msg)
                //Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
                SessionManager().setDeviceID(token,this)
            })

    }

    fun facebookonCreat(getFirebaseUSer: getFirebaseUser, loginButton: View) {
        mCallbackManager = CallbackManager.Factory.create()
        this.getFirebaseUSer = getFirebaseUSer

        loginButton.onClick {
            LoginManager.getInstance().logInWithReadPermissions(this@SocialLogin, Arrays.asList("public_profile"));

        }
        LoginManager.getInstance().registerCallback(mCallbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult) {
                    // App code


                    Log.d("", "facebook:onSuccess:$loginResult")
                    handleFacebookAccessToken(loginResult.accessToken)
                }

                override fun onCancel() {
                    // App code
                    Log.e("","")
                }

                override fun onError(exception: FacebookException) {
                    // App code
                    Log.e("","")
                }
            })







    }


    private fun handleFacebookAccessToken(token: AccessToken) {
        Log.d(TAG, "handleFacebookAccessToken:$token")

        val credential = FacebookAuthProvider.getCredential(token.token)
        mAuth!!.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = mAuth!!.currentUser


                    var custom= CustomFirebaseUser()

                    try {
                        custom.displayName=user!!.displayName
                        custom.email=user!!.email
                    } catch (e: Exception) {
                        custom.email=""
                    }
                    custom.photoUrl=user!!.photoUrl.toString()

                    custom.uid=token.userId

                    custom.providerstype = user!!.providers!![0].split(".")[0]



                    getFirebaseUSer.getCurretnUser(custom)
                    // updateUI(user);
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    Toast.makeText(applicationContext, getString(R.string.pleaserebootyourdevice), Toast.LENGTH_SHORT).show()

                    // updateUI(null);
                }

                // ...
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (mCallbackManager!=null)
        mCallbackManager!!.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        setDatainStartACtivityResult(requestCode, data)
    }


}
