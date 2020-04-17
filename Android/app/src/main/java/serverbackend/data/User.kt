package serverbackend.data

import com.google.firebase.auth.FirebaseUser

data class User(
        var fireBaseUser: FirebaseUser,
        var username: String = "",
        var email: String = "",
        var imageUrl: String = "",
        var uuid: String = ""


) {

    init {
        uuid = fireBaseUser.uid
        email = fireBaseUser.email.toString()
    }

    fun setPassword(password: String) {
        fireBaseUser.updatePassword(password)
    }


}
