package com.example.projekt_am

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var totalWaterMl: Int = 0

    private fun getResId(resName: String, resType: String): Int {
        return resources.getIdentifier(resName, resType, requireContext().packageName)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layoutId = getResId("fragment_first", "layout")
        return if (layoutId != 0) {
            inflater.inflate(layoutId, container, false)
        } else {
            null
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateWaterDisplay(view)

        val buttonAddWater = view.findViewById<Button>(getResId("button_add_water", "id"))
        val edittextWaterInput = view.findViewById<EditText>(getResId("edittext_water_input", "id"))
        val buttonAdd250 = view.findViewById<Button>(getResId("button_add_250", "id"))
        val buttonAdd500 = view.findViewById<Button>(getResId("button_add_500", "id"))
        val buttonReset = view.findViewById<Button>(getResId("button_reset", "id"))
        val iconClose = view.findViewById<ImageView>(getResId("icon_close", "id"))
        val iconLanguage = view.findViewById<ImageView>(getResId("icon_language", "id"))
        val buttonFirst = view.findViewById<Button>(getResId("button_first", "id"))

        buttonAddWater?.setOnClickListener {
            val inputStr = edittextWaterInput?.text?.toString() ?: ""
            if (inputStr.isNotEmpty()) {
                val amount = inputStr.toIntOrNull()
                if (amount != null && amount > 0) {
                    addWater(amount, view)
                    edittextWaterInput?.text?.clear()
                } else {
                    val errorId = getResId("invalid_amount_error", "string")
                    if (errorId != 0) {
                        Toast.makeText(context, getString(errorId), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        buttonAdd250?.setOnClickListener {
            addWater(250, view)
        }

        buttonAdd500?.setOnClickListener {
            addWater(500, view)
        }

        buttonReset?.setOnClickListener {
            showResetConfirmationDialog(view)
        }
        
        iconClose?.setOnClickListener {
            showQuitConfirmationDialog()
        }
        
        iconLanguage?.setOnClickListener {
            Toast.makeText(context, "Language selection coming soon!", Toast.LENGTH_SHORT).show()
        }

        buttonFirst?.setOnClickListener {
            val actionId = getResId("action_FirstFragment_to_SecondFragment", "id")
            if (actionId != 0) {
                findNavController().navigate(actionId)
            }
        }
    }

    private fun showResetConfirmationDialog(view: View) {
        val builder = AlertDialog.Builder(requireContext())
        val titleId = getResId("reset_dialog_title", "string")
        val messageId = getResId("reset_dialog_message", "string")
        val yesId = getResId("yes", "string")
        val noId = getResId("no", "string")

        if (titleId != 0) builder.setTitle(titleId)
        if (messageId != 0) builder.setMessage(messageId)

        if (yesId != 0) {
            builder.setPositiveButton(yesId) { _: DialogInterface, _: Int ->
                handleResetChoice(true, view)
            }
        }

        if (noId != 0) {
            builder.setNegativeButton(noId) { dialog: DialogInterface, _: Int ->
                handleResetChoice(false, view)
                dialog.dismiss()
            }
        }

        builder.create().show()
    }

    private fun handleResetChoice(choice: Boolean, view: View) {
        if (choice) {
            resetWaterIntake(view)
        }
    }

    private fun showQuitConfirmationDialog() {
        val builder = AlertDialog.Builder(requireContext())
        val titleId = getResId("quit_dialog_title", "string")
        val messageId = getResId("quit_dialog_message", "string")
        val yesId = getResId("yes", "string")
        val noId = getResId("no", "string")

        if (titleId != 0) builder.setTitle(titleId)
        if (messageId != 0) builder.setMessage(messageId)

        if (yesId != 0) {
            builder.setPositiveButton(yesId) { _: DialogInterface, _: Int ->
                handleQuitChoice(true)
            }
        }

        if (noId != 0) {
            builder.setNegativeButton(noId) { dialog: DialogInterface, _: Int ->
                handleQuitChoice(false)
                dialog.dismiss()
            }
        }

        builder.create().show()
    }

    private fun handleQuitChoice(choice: Boolean) {
        if (choice) {
            activity?.finish()
        }
    }

    private fun addWater(amount: Int, view: View) {
        totalWaterMl += amount
        updateWaterDisplay(view)
    }

    private fun resetWaterIntake(view: View) {
        totalWaterMl = 0
        updateWaterDisplay(view)
    }

    private fun updateWaterDisplay(view: View) {
        val formatId = getResId("water_amount_format", "string")
        val textViewId = getResId("textview_water_amount", "id")
        if (formatId != 0 && textViewId != 0) {
            view.findViewById<TextView>(textViewId)?.text = getString(formatId, totalWaterMl)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("total_water", totalWaterMl)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null) {
            totalWaterMl = savedInstanceState.getInt("total_water", 0)
            view?.let { updateWaterDisplay(it) }
        }
    }
}
