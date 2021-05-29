package com.atex.financeeducation.mainfragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.atex.financeeducation.R
import com.atex.financeeducation.data.UserInformation
import com.atex.financeeducation.databinding.UserInformFragmentBinding
import com.atex.financeeducation.interfaces.AutorizationInterface
import com.atex.financeeducation.interfaces.ChangeBottomNavView
import com.atex.financeeducation.viewmodel.CommonViewModel
import com.google.firebase.firestore.MetadataChanges
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class UserInformFragment : Fragment(R.layout.user_inform_fragment) {

    private lateinit var viewModel: CommonViewModel

    private val db = Firebase.firestore
    private val users = db.collection("Users")

    private lateinit var authInterface: AutorizationInterface
    private lateinit var changeBotNavViewInterface: ChangeBottomNavView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.let {
            instantiateNavigationInterface(it)
        }

        val userName = view.findViewById<TextView>(R.id.user_name)
        val userEmail = view.findViewById<TextView>(R.id.user_email)
        val countNotesTV = view.findViewById<TextView>(R.id.count_notes)
        val countDreamsTV = view.findViewById<TextView>(R.id.count_dreams)

        viewModel = ViewModelProvider(requireActivity()).get(CommonViewModel::class.java)

        users.document(viewModel.email)
            .addSnapshotListener(MetadataChanges.INCLUDE) { snapshot, e ->
                val userInform: UserInformation? = snapshot?.toObject(UserInformation::class.java)
                userInform?.let {
                    userName.text = "Имя пользователя: ${it.username}"
                    userEmail.text = "Электронный адрес: ${it.email}"
                }
            }

        users.document(viewModel.email).collection("notes")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    var countNotes = 0
                    for (document in task.result!!) {
                        countNotes++
                    }
                    countNotesTV.text = "Количество записей: ${countNotes}"
                }
            }

        users.document(viewModel.email).collection("dreams")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    var countDreams = 0
                    for (document in task.result!!) {
                        countDreams++
                    }
                    countDreamsTV.text = "Желаний: ${countDreams}"
                }
            }

        view.findViewById<Button>(R.id.goOutBtn).setOnClickListener {
            authInterface.signOut()
            val action = UserInformFragmentDirections.actionUserInformFragmentToSignInFragment()
            findNavController().navigate(action)
            changeBotNavViewInterface.hideBottomNavView()
        }


    }

    private fun instantiateNavigationInterface(context: FragmentActivity) {
        authInterface = context as AutorizationInterface
        changeBotNavViewInterface = context as ChangeBottomNavView
    }

}