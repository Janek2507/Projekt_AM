package com.example.projekt_am

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
                    Toast.makeText(context, getString(R.string.invalid_amount_error), Toast.LENGTH_SHORT).show()
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
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }

    private fun showResetConfirmationDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(R.string.reset_dialog_title)
        builder.setMessage(R.string.reset_dialog_message)

        builder.setPositiveButton(R.string.yes) { _, _ ->
            val yes = true // 1
            handleResetChoice(yes)
        }

        builder.setNegativeButton(R.string.no) { dialog, _ ->
            val no = false // 0
            handleResetChoice(no)
            dialog.dismiss()
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
        builder.setTitle(R.string.quit_dialog_title)
        builder.setMessage(R.string.quit_dialog_message)

        builder.setPositiveButton(R.string.yes) { _, _ ->
            val yes = true // 1
            handleQuitChoice(yes)
        }

        builder.setNegativeButton(R.string.no) { dialog, _ ->
            val no = false // 0
            handleQuitChoice(no)
            dialog.dismiss()
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
        binding.textviewWaterAmount.text = getString(R.string.water_amount_format, totalWaterMl)
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