package com.atex.financeeducation.mainfragments

import android.graphics.text.LineBreaker
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.atex.financeeducation.R
import com.atex.financeeducation.enums.Articles


class ArticleFragment : Fragment(R.layout.article_fragment) {

    private val args: ArticleFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val title = view.findViewById<TextView>(R.id.help_title)
        val text = view.findViewById<TextView>(R.id.help_text)

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            text.justificationMode = LineBreaker.JUSTIFICATION_MODE_INTER_WORD
        }

        when (args.articleType) {
            Articles.BUDGET -> {
                title.text = getString(R.string.article_budget_title)
                text.text = getString(R.string.article_budget_text)
            }
            Articles.DAIRY -> {
                title.text = getString(R.string.article_dairy_title)
                text.text = getString(R.string.article_dairy_text)
            }
            Articles.DREAMS -> {
                title.text = getString(R.string.article_dreams_title)
                text.text = getString(R.string.article_dreams_text)
            }
            Articles.GOALS -> {
                title.text = getString(R.string.article_goals_title)
                text.text = getString(R.string.article_goals_text)
            }
        }

    }

}