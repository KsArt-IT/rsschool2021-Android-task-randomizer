package com.rsschool.android2021

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.TextView
import androidx.activity.addCallback
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import kotlin.random.Random

class SecondFragment : Fragment() {
    interface OnSecondCallback {
        fun onSecondCallback(result: Int)
    }

    private var backButton: Button? = null
    private var result: TextView? = null

    private val sendResult
        get() = activity?.let { it as? OnSecondCallback }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        result = view.findViewById(R.id.result)
        backButton = view.findViewById(R.id.back)

        val min = arguments?.getInt(MIN_VALUE_KEY) ?: 0
        val max = arguments?.getInt(MAX_VALUE_KEY) ?: 0

        result?.text = generate(min, max).toString()

        backButton?.setOnClickListener {
            backResult()
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            backResult()
        }
        context?.hideKeyboardFrom(view)
    }

    private fun generate(min: Int, max: Int): Int {
        return Random.nextInt(min, max + 1)
    }

    private fun backResult() {
        val res = result?.text.toString().toIntOrNull() ?: 0
        sendResult?.onSecondCallback(res)
    }

    companion object {

        @JvmStatic
        fun newInstance(min: Int, max: Int): SecondFragment {
            val fragment = SecondFragment()
            val args = Bundle()
            args.putInt(MIN_VALUE_KEY, min)
            args.putInt(MAX_VALUE_KEY, max)
            fragment.arguments = args
            return fragment
        }

        private const val MIN_VALUE_KEY = "MIN_VALUE"
        private const val MAX_VALUE_KEY = "MAX_VALUE"
    }
}