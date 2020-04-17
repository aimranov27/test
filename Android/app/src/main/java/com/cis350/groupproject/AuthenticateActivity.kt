package com.cis350.groupproject

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_authenticate.*
import kotlinx.android.synthetic.main.activity_authenticate_create.*
import kotlinx.android.synthetic.main.activity_authenticate_login.*
import kotlinx.android.synthetic.main.activity_authenticate_welcome.*
import serverbackend.data.User
import java.util.*


class AuthenticateActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    // Holds layout ids of each view we have navigated through
    private var mNavigationStack: Stack<Int>? = null
    private var mCurrentView: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authenticate)
        initViewAnimator()

        // Initialize Fire base Auth
        auth = FirebaseAuth.getInstance()

        // Initialize the navigation stack
        mNavigationStack = Stack()
        btn_finish.setOnClickListener { switchToHomeActivity() }
    }

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        // Bypass authentication activity if user is already logged in
        if (currentUser != null) {
            Toast.makeText(this, "Logged in user successfully", Toast.LENGTH_SHORT).show()
            switchToHomeActivity()
        }
    }

    fun onLoginClick(view: View) {
        val email = et_email_login.text.toString()
        val password = et_password_login.text.toString()
        // Log into fire base
        logIn(email, password)
    }

    private fun logIn(email: String, password: String) {
        Log.d(TAG, "logIn:$email")
        //showProgressDialog()
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    //TODO get user object  from database
                    //val user = auth.currentUser
                    Toast.makeText(baseContext, "Welcome",
                        Toast.LENGTH_SHORT).show()
                    switchToHomeActivity()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, task.exception!!.message,
                        Toast.LENGTH_SHORT).show()
                }
                //hideProgressDialog()
            }
    }


    fun onCreateClick(view: View) {
        val email = field_email_create.text.toString()
        val password = et_password_create.text.toString()

        // Create user in FireBase
        createAccount(email, password)
    }


    private fun createAccount(
        email: String,
        password: String
    ): Boolean {
        Log.d(TAG, "createAccount:$email")
        if (!validateForm()) {
            return false
        }
        //showProgressDialog()
        var result: Task<AuthResult> = auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    // Create the local user and set some of its properties
                    val username = et_username_create.text.toString()
                    val user = User(auth.currentUser!!)
                    user.username = username
                    user.email = email
                    user.uuid = user.fireBaseUser.uid

                    // Write new user
                    writeNewUser(user)
                    auth.currentUser!!.sendEmailVerification()
                            .addOnCompleteListener(OnCompleteListener<Void?> { task ->
                                if (task.isSuccessful) {
                                    Log.d("TAG", "Email sent.")
                                }
                            })
                    switchToWelcomeView(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, task.exception!!.message,
                        Toast.LENGTH_SHORT).show()
                }
                //hideProgressDialog()
            }
        return result.isSuccessful
    }


    private fun writeNewUser(newUser: User) {
        //TODO write new user to database
    }

    fun switchToLoginView(view: View) {
        mNavigationStack!!.push(mCurrentView)
        mCurrentView = LOGIN
        view_animator.displayedChild = mCurrentView
    }

    fun switchToCreateView(view: View) {
        mNavigationStack!!.push(mCurrentView)
        mCurrentView = CREATE
        view_animator.displayedChild = (mCurrentView)
        et_username_create.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                // Set the edit text color back to normal
                et_username_create.background = ResourcesCompat.getDrawable(resources,
                    R.drawable.et_default, null)
                tv_username_unavailable.visibility = View.GONE
                btn_create_account.isEnabled = true

            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })

        et_password_create.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                // Check if the username and password are valid and enable button accordingly
                btn_create_account.isEnabled = s.toString().isNotEmpty() &&
                        tv_username_unavailable.visibility == View.GONE
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })
    }

    private fun switchToWelcomeView(user: User) {
        mNavigationStack!!.push(mCurrentView)
        mCurrentView = WELCOME
        view_animator.displayedChild = mCurrentView

        // Setup the profile image and border

        Glide.with(this)
            .load(R.drawable.background)
            .transform(CircleCrop())
                //TODO fix this
            .into(iv_background)
        Glide.with(this)
            .load(R.drawable.avatar)
            .transform(CircleCrop())
            .error(R.drawable.avatar)
            .placeholder(R.drawable.avatar)
            .into(iv_outline_welcome)

        tv_username.text = user.username
    }

    private fun switchToHomeActivity() {
        Toast.makeText(this, "Logged in user successfully", Toast.LENGTH_SHORT).show()
        val i = Intent(this, MainActivity::class.java)
        i.putExtra("currentUserId", auth.currentUser!!.uid)
        startActivity(i)
        finish()
    }

    private fun validateForm(): Boolean {
        var valid = true

        val email = field_email_create.text.toString()
        if (TextUtils.isEmpty(email)) {
            field_email_create.error = "Required."
            valid = false
        } else {
            field_email_create.error = null
        }

        val password = et_password_create.text.toString()
        if (TextUtils.isEmpty(password)) {
            et_password_create.error = "Required."
            valid = false
        } else {
            et_password_create.error = null
        }

        return valid
    }

    /**
     * Adds all views to the view animator and sets up the animations
     */
    private fun initViewAnimator() {
        // Add views to the view animator
        view_animator.addView(
            layoutInflater
                .inflate(R.layout.activity_authenticate_home, view_animator, false), HOME
        )
        view_animator.addView(
            layoutInflater
                .inflate(R.layout.activity_authenticate_login, view_animator, false), LOGIN
        )
        view_animator.addView(
            layoutInflater
                .inflate(R.layout.activity_authenticate_create, view_animator, false), CREATE
        )
        view_animator.addView(
            layoutInflater
                .inflate(R.layout.activity_authenticate_welcome, view_animator, false), WELCOME
        )

        // Set up view animator animations
        view_animator.animateFirstView = false
        view_animator.setInAnimation(this, R.anim.fade_in)
        view_animator.setOutAnimation(this, R.anim.fade_out)
    }

    /**
     * Override the back button to use the navigation stack to decide which page to return to
     */
    override fun onBackPressed() {
        if (mNavigationStack!!.empty()) {
            // If the navigation stack is empty then do the default behavior
            super.onBackPressed()
        } else {
            // Otherwise get the previous view from the navigation stack
            mCurrentView = mNavigationStack!!.pop()
            view_animator.displayedChild = mCurrentView
        }
    }

    companion object {
        // Indices for each view inside the view animator
        private const val HOME = 0
        private const val LOGIN = 1
        private const val CREATE = 2
        private const val WELCOME = 3
        private const val TAG = "EmailPassword"
    }


    override fun onDestroy() {
        super.onDestroy()

    }



}
