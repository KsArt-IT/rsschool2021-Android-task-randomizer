package com.rsschool.android2021

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

class FirstFragment : Fragment() {

    interface OnFirstCallback {
        fun onFirstCallback(min: Int, max: Int)
    }
    private var generateButton: Button? = null
    private var previousResult: TextView? = null
    private var minValue: TextView? = null
    private var maxValue: TextView? = null

    private val sendFromGenerate
        get() = activity?.let { it as? OnFirstCallback }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        previousResult = view.findViewById(R.id.previous_result)
        generateButton = view.findViewById(R.id.generate)
        minValue = view.findViewById(R.id.min_value)
        maxValue = view.findViewById(R.id.max_value)

        val result = arguments?.getInt(PREVIOUS_RESULT_KEY) ?: 0
        previousResult?.text = "Previous result: ${result}"

        generateButton?.setOnClickListener {
            val minString = minValue?.text.toString()
            val maxString = maxValue?.text.toString()
            if (minString.isBlank() || maxString.isBlank()) {
                Toast.makeText(this.context, "Fill in all the fields (Min, Max)", Toast.LENGTH_SHORT).show()
            } else {
                val min = minString.toLongOrNull() ?: -1
                val max = maxString.toLongOrNull() ?: -1
                if (min < 0 || min > Int.MAX_VALUE || max < 0 || max > Int.MAX_VALUE) {
                    Toast.makeText(this.context, "The numbers are too big", Toast.LENGTH_SHORT).show()
                } else {
                    if (min > max) {
                        Toast.makeText(this.context, "Min must be less than Max", Toast.LENGTH_SHORT).show()
                    } else {
                        sendFromGenerate?.onFirstCallback(min.toInt(), max.toInt())
                    }
                }
            }
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(previousResult: Int): FirstFragment {
            val fragment = FirstFragment()
            val args = Bundle()
            args.putInt(PREVIOUS_RESULT_KEY, previousResult)
            fragment.arguments = args
            return fragment
        }

        private const val PREVIOUS_RESULT_KEY = "PREVIOUS_RESULT"
    }
}