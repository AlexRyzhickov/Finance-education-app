package com.atex.financeeducation.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.atex.financeeducation.data.NoteItem
import com.atex.financeeducation.data.UserInformation
import com.example.androidkeyboardstatechecker.showToast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.MetadataChanges
import com.google.firebase.firestore.auth.User
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class CommonViewModel() : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    private val users = db.collection("Users")
    private var topics: MutableLiveData<List<NoteItem>> = MutableLiveData<List<NoteItem>>()
    var email: String = "none"
    private var userInf: MutableLiveData<UserInformation> = MutableLiveData<UserInformation>()

    fun addUser(email: String, username: String, context: Context?) {
        val data = hashMapOf(
            "username" to username,
            "email" to email,
            "funds" to 0,
            "untouchable" to 0,
            "daily" to 0
        )
        users.document(email).set(data)
            .addOnSuccessListener {
                context?.showToast("DocumentSnapshot successfully written!")
            }
            .addOnFailureListener {
                context?.showToast("Error writing document")
            }
    }


    fun addNotes(
        note_1: String,
        note_2: String,
        note_3: String,
        context: Context?
    ) {
        val currentDate = Date()
        val dateFormat: DateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        val timeFormat: DateFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        val date = dateFormat.format(currentDate)
        val time = timeFormat.format(currentDate)

        val notes = hashMapOf(
            "note_1" to note_1,
            "note_2" to note_2,
            "note_3" to note_3,
            "date" to date,
            "time" to time
        )
        users.document(this.email).collection("notes").document("$date $time").set(notes)
            .addOnSuccessListener {
                context?.showToast("Notes successfully added!")
            }
            .addOnFailureListener {
                context?.showToast("Error adding notes")
            }
    }

    fun getNotes(): MutableLiveData<List<NoteItem>> {
        users.document(this.email).collection("notes")
            .addSnapshotListener { value, error ->
                val list: MutableList<NoteItem> = ArrayList<NoteItem>()
                for (topic in value!!.documentChanges) {
                    val user: NoteItem = topic.document.toObject(NoteItem::class.java)
                    list.add(user)
                }
                topics.value = list
            }
        return topics
    }

    /* fun updateInformAboutUser(){
         val washingtonRef = db.collection("cities").document("DC")

 // Set the "isCapital" field of the city 'DC'
         washingtonRef
             .update("capital", true)
             .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully updated!") }
             .addOnFailureListener { e -> Log.w(TAG, "Error updating document", e) }
     }*/

    fun getInformAboutUser(): MutableLiveData<UserInformation> {
        users.document(this.email).addSnapshotListener(MetadataChanges.INCLUDE) { snapshot, e ->
            val user: UserInformation? = snapshot!!.toObject(UserInformation::class.java)
            userInf.value = user ?: UserInformation()
        }
        return userInf
    }

}