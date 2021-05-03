package com.atex.financeeducation.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.atex.financeeducation.data.DreamItem
import com.atex.financeeducation.data.NoteItem
import com.atex.financeeducation.data.UserInformation
import com.atex.financeeducation.enums.ChangeAmountState
import com.example.androidkeyboardstatechecker.showToast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.MetadataChanges
import java.text.DateFormat
import java.text.SimpleDateFormat
import android.net.Uri
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.util.*

class CommonViewModel() : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    private val storage = Firebase.storage
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

    fun updateFunds(value: Int, type: String, state: ChangeAmountState) {
        if (state == ChangeAmountState.SET) {
            users.document(this.email)
                .update(type, value)
        } else {
            users.document(this.email).get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        val prev_value = document.data?.get(type) as Long
                        when (state) {
                            ChangeAmountState.ADD -> {
                                users.document(this.email)
                                    .update(type, prev_value + value)
                            }
                            ChangeAmountState.REDUCE -> {
                                if (prev_value - value < 0) {
                                    users.document(this.email)
                                        .update(type, 0)
                                } else {
                                    users.document(this.email)
                                        .update(type, prev_value - value)
                                }
                            }
                            else -> {

                            }
                        }
                    } else {

                    }
                }
                .addOnFailureListener { exception ->

                }
        }
    }

    fun getInformAboutUser(): MutableLiveData<UserInformation> {
        users.document(this.email).addSnapshotListener(MetadataChanges.INCLUDE) { snapshot, e ->
            val user: UserInformation? = snapshot!!.toObject(UserInformation::class.java)
            userInf.value = user ?: UserInformation()
        }
        return userInf
    }

    fun createDream(dreamName: String, dreamCost: Int, link: String, uri: Uri) {
        val currentDate = Date()
        val dateFormat: DateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        val timeFormat: DateFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        val date = dateFormat.format(currentDate)
        val time = timeFormat.format(currentDate)

        val docId = "${dreamName}-${date}-${time}"
        val storeId = "${this.email}/$docId"

        val imagesRef = storage.reference.child(storeId)
        val uploadTask = imagesRef.putFile(uri)

        val urlTask = uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            imagesRef.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val downloadUri = task.result
                users.document(this.email).collection("dreams").document(docId)
                    .set(DreamItem(dreamName, dreamCost, downloadUri.toString(), link, date, time))
            } else {

            }
        }

    }

}