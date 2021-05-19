package com.atex.financeeducation

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.atex.financeeducation.authentication.SignInFragmentDirections
import com.atex.financeeducation.authentication.SignUpFragmentDirections
import com.atex.financeeducation.interfaces.AutorizationInterface
import com.atex.financeeducation.interfaces.ChangeBottomNavView
import com.atex.financeeducation.transactions.ChangeAmountFragment
import com.atex.financeeducation.viewmodel.CommonViewModel
import com.example.androidkeyboardstatechecker.KeyboardEventListener
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity(), AutorizationInterface, ChangeBottomNavView {

    lateinit var mAuth: FirebaseAuth
    lateinit var bottomNavigationView: BottomNavigationView
    lateinit var viewModel: CommonViewModel

    private lateinit var dataStore: DataStore<Preferences>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dataStore = createDataStore(name = "settings")

        bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.itemIconTintList = null
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)

        NavigationUI.setupWithNavController(bottomNavigationView, navController)

        mAuth = FirebaseAuth.getInstance()

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val inflater = navHostFragment.navController.navInflater
        val graph = inflater.inflate(R.navigation.nav_graph)

        viewModel = ViewModelProvider(this).get(CommonViewModel::class.java)
        viewModel.getDreams()

        lifecycleScope.launch {
            val value = read("email")
            viewModel.email =  value ?: "none"

            Toast.makeText(applicationContext, "!" + value + "!", Toast.LENGTH_SHORT).show()
            Toast.makeText(applicationContext, "?" + viewModel.email + "?", Toast.LENGTH_SHORT).show()

            if (value==null || value.equals("none")) {
                graph.startDestination = R.id.signInFragment
            } else {
                graph.startDestination = R.id.budgetFragment
                bottomNavigationView.visibility = View.VISIBLE
            }
            val navController = navHostFragment.navController
            navController.setGraph(graph, intent.extras)
        }


        /*  mAuth.createUserWithEmailAndPassword(email, password)
              .addOnCompleteListener(this) { task ->
                  if (task.isSuccessful) {
                      // Sign in success, update UI with the signed-in user's information
                      Log.d(TAG, "createUserWithEmail:success")
                      val user = auth.currentUser
                      updateUI(user)
                  } else {
                      // If sign in fails, display a message to the user.
                      Log.w(TAG, "createUserWithEmail:failure", task.exception)
                      Toast.makeText(baseContext, "Authentication failed.",
                          Toast.LENGTH_SHORT).show()
                      updateUI(null)
                  }
              }*/

        /*mAuth.signInWithCustomToken(mCustomToken)
            .addOnCompleteListener(
                this
            ) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("TAG", "signInWithCustomToken:success")
                    val user = mAuth.currentUser
//                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("TAG", "signInWithCustomToken:failure", task.exception)
                    Toast.makeText(
                        this, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
//                    updateUI(null)
                }
            }*/
    }

//    override fun onSupportNavigateUp(): Boolean {
//        onBackPressed()
//        return super.onSupportNavigateUp()
//    }

    override fun onStart() {
        super.onStart()
        val currentUser = mAuth.currentUser
    }

    override fun signUp(email: String, password: String, nickname: String) {
        if (email.trim().length >= 0 && password.trim().length >= 6) {
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    viewModel.addUser(email, nickname, applicationContext)
                    Toast.makeText(this, "createUserWithEmail:success", Toast.LENGTH_SHORT)
                        .show()
                    val action = SignUpFragmentDirections.actionSignUpFragmentToSignInFragment()
                    findNavController(R.id.nav_host_fragment).navigate(action)
                } else {
                    Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun signIn(email: String, password: String) {
        if (email.trim().length >= 0 && password.trim().length >= 6) {
            mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
//                            val user = auth.currentUser
                        Toast.makeText(this, "signInWithEmail:success", Toast.LENGTH_SHORT)
                            .show()
                        bottomNavigationView.visibility = View.VISIBLE
                        lifecycleScope.launch {
                            save("email", email)
                            viewModel.email = email
                        }
                        val action = SignInFragmentDirections.actionSignInFragmentToBudgetFragment()
                        findNavController(R.id.nav_host_fragment).navigate(action)
                    } else {
                        Toast.makeText(this, "signInWithEmail:failure", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
        }
    }

    override fun signOut(){
        lifecycleScope.launch {
            save("email", "none")
        }
    }

    override fun hideBottomNavView() {
        bottomNavigationView.visibility = View.GONE
    }

    override fun showBottomNavView() {
        bottomNavigationView.visibility = View.VISIBLE
    }

    override fun onResume() {
        super.onResume()
        KeyboardEventListener(this) {
            if (it) {
                Toast.makeText(this, "Keyboard Open", Toast.LENGTH_SHORT).show()
            } else {
            }
        }
    }

    private suspend fun save(key: String, value: String) {
        val dataStoreKey = preferencesKey<String>(key)
        dataStore.edit { settings ->
            settings[dataStoreKey] = value
        }
    }

    private suspend fun read(key: String): String? {
        val dataStoreKey = preferencesKey<String>(key)
        val preferences = dataStore.data.first()
        return preferences[dataStoreKey]
    }

}