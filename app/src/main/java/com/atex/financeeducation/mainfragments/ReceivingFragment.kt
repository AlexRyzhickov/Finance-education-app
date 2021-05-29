package com.atex.financeeducation.mainfragments

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.atex.financeeducation.R
import com.atex.financeeducation.interfaces.ChangeBottomNavView
import com.atex.financeeducation.interfaces.IOnBackPressed
import com.bumptech.glide.Glide
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import nl.dionsegijn.konfetti.KonfettiView
import nl.dionsegijn.konfetti.models.Size

class ReceivingFragment : Fragment(R.layout.receiving_fragment), IOnBackPressed {

    private val args: ReceivingFragmentArgs by navArgs()
    private lateinit var changeBotNavViewInterface: ChangeBottomNavView
    private val storage = Firebase.storage

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.let {
            changeBotNavViewInterface = context as ChangeBottomNavView
        }

        view.findViewById<TextView>(R.id.receiving_dream_name)
            .text = args.dreamName

        view.findViewById<TextView>(R.id.receiving_dream_cost)
            .text = "${args.dreamCost}  ₽"

        view.findViewById<Button>(R.id.backToBudgetFragBtn).setOnClickListener {
            backToBudget()
        }

        val dreamImgUrl = view.findViewById<ImageView>(R.id.receiving_dream_img)

        Glide
            .with(requireContext())
            .load(args.dreamImgUrl)
            .into(dreamImgUrl);

        val konfettiView: KonfettiView = view.findViewById<KonfettiView>(R.id.viewKonfetti)

        konfettiView.build().addColors(
            Color.YELLOW,
            Color.GREEN,
            Color.MAGENTA,
            Color.RED,
            Color.CYAN
        )
            .setDirection(0.0, 359.0)
            .setSpeed(1f, 5f).setFadeOutEnabled(true).setTimeToLive(1000L)
            .addSizes(Size(12, 5f))
            .setPosition(-50f, konfettiView.width + 50f, -50f, -50f)
            .streamFor(300, 3500L)
    }

    override fun onBackPressed(): Boolean {
        backToBudget()
        return false
    }

    private fun backToBudget(){
        storage.reference.child(args.storeId).delete()
        changeBotNavViewInterface.showBottomNavView()
        val action = ReceivingFragmentDirections.actionReceivingFragmentToBudgetFragment()
        findNavController().navigate(action)
    }


}