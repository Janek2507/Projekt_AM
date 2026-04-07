package com.example.projekt_am

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.example.projekt_am.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var totalWaterMl: Int = 0

    private fun getResId(resName: String, resType: String): Int {
        return resources.getIdentifier(resName, resType, requireContext().packageName)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateWaterDisplay()

        binding.buttonAddWater.setOnClickListener {
            val inputStr = binding.edittextWaterInput.text.toString()
            if (inputStr.isNotEmpty()) {
                val amount = inputStr.toIntOrNull()
                if (amount != null && amount > 0) {
                    addWater(amount)
                    binding.edittextWaterInput.text?.clear()
                } else {
                    val errorId = getResId("invalid_amount_error", "string")
                    if (errorId != 0) {
                        Toast.makeText(context, getString(errorId), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        binding.buttonAdd250.setOnClickListener {
            addWater(250)
        }

        binding.buttonAdd500.setOnClickListener {
            addWater(500)
        }

        binding.buttonReset.setOnClickListener {
            showResetConfirmationDialog()
        }
        
        // Top bar icon listeners
        binding.iconClose.setOnClickListener {
            showQuitConfirmationDialog()
        }
        
        binding.iconLanguage.setOnClickListener {
            Toast.makeText(context, "Language selection coming soon!", Toast.LENGTH_SHORT).show()
        }

        binding.buttonFirst.setOnClickListener {
            val actionId = getResId("action_FirstFragment_to_SecondFragment", "id")
            if (actionId != 0) {
                findNavController().navigate(actionId)
            }
        }
    }

    private fun showResetConfirmationDialog() {
        val builder = AlertDialog.Builder(requireContext())
        val titleId = getResId("reset_dialog_title", "string")
        val messageId = getResId("reset_dialog_message", "string")
        val yesId = getResId("yes", "string")
        val noId = getResId("no", "string")

        if (titleId != 0) builder.setTitle(titleId)
        if (messageId != 0) builder.setMessage(messageId)

        if (yesId != 0) {
            builder.setPositiveButton(yesId) { _: DialogInterface, _: Int ->
                handleResetChoice(true)
            }
        }

        if (noId != 0) {
            builder.setNegativeButton(noId) { dialog: DialogInterface, _: Int ->
                handleResetChoice(false)
                dialog.dismiss()
            }
        }

        builder.create().show()
    }

    private fun handleResetChoice(choice: Boolean) {
        if (choice) {
            resetWaterIntake()
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

    private fun addWater(amount: Int) {
        totalWaterMl += amount
        updateWaterDisplay()
    }

    private fun resetWaterIntake() {
        totalWaterMl = 0
        updateWaterDisplay()
    }

    private fun updateWaterDisplay() {
        val formatId = getResId("water_amount_format", "string")
        if (formatId != 0) {
            binding.textviewWaterAmount.text = getString(formatId, totalWaterMl)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("total_water", totalWaterMl)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null) {
            totalWaterMl = savedInstanceState.getInt("total_water", 0)
            updateWaterDisplay()
        }
    }
}
