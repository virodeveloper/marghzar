package pick.com.app.base.sociallogin

import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.UserProfileChangeRequest
import com.livinglifetechway.k4kotlin.core.onClick
import pick.com.app.R
import pick.com.app.base.CommonActivity
import com.google.firebase.internal.FirebaseAppHelper.getUid
import com.google.firebase.auth.UserInfo
import kotlinx.android.synthetic.main.registration_header.*
import pick.com.app.base.BaseActivity
import pick.com.app.base.model.CustomFirebaseUser


open class GoogleLogin: CommonActivity(), getFirebaseUser {
    override fun getCurretnUser(firebaseUser: CustomFirebaseUser?) {

    }


    val mAuth: FirebaseAuth by lazy {
       FirebaseAuth.getInstance()
    }

    private val RC_SIGN_IN = 9001
    lateinit var getFirebaseUSer: getFirebaseUser

    fun googleOnCreat(getFirebaseUSer: getFirebaseUser,view: View):GoogleSignInClient{

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(pick.com.app.R.string.default_web_client_id))
            .requestEmail()
            .build()




       this.getFirebaseUSer=getFirebaseUSer

        view.onClick {
            googleLogin(this)
        }
        this.view=view
        return GoogleSignIn.getClient(this, gso)


    }


    fun googleLogin(view:View) {

        val signInIntent = googleOnCreat(getFirebaseUSer,view)!!.getSignInIntent()
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }



    lateinit var view:View


     fun googleLogout() {
        // Firebase sign out

        mAuth!!.signOut()

        // Google revoke access
         val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
             .requestIdToken(getString(pick.com.app.R.string.default_web_client_id))
             .requestEmail()
             .build()
         GoogleSignIn.getClient(this, gso)!!.revokeAccess().addOnCompleteListener(
            this
        ) {
            //updateUI(null);

        }
    }


    fun setDatainStartACtivityResult(requestCode: Int,data: Intent?){

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)


                firebaseAuthWithGoogle(account!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w("", "Google sign in failed", e)
                // [START_EXCLUDE]
                //updateUI(null);
                // [END_EXCLUDE]
            }

        }

    }



    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        Log.d("", "firebaseAuthWithGoogle:" + acct.id!!)

        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        mAuth!!.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("", "signInWithCredential:success")
                    val user = mAuth!!.getCurrentUser()
                  //  103434835194655097844

                    user!!.getIdToken(false)

                    var custom= CustomFirebaseUser()

                    try {
                        custom.displayName=user!!.displayName
                        custom.email=user!!.email
                    } catch (e: Exception) {
                        custom.email=""
                    }
                    custom.photoUrl=user!!.photoUrl.toString()

                    custom.uid=acct.id!!

                    custom.providerstype = user!!.providers!![0].split(".")[0]



                    getFirebaseUSer.getCurretnUser(custom)
                    //  updateUI(user);
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("", "signInWithCredential:failure", task.exception)
                    Toast.makeText(applicationContext, getString(pick.com.app.R.string.pleaserebootyourdevice), Toast.LENGTH_SHORT).show()


                }

                // ...
            }
    }





}
