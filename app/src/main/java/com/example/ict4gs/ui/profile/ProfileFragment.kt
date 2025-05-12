package com.example.ict4gs.ui.profile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.ict4gs.databinding.FragmentProfileBinding
import com.example.ict4gs.utils.LocaleHelper

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val languages = arrayOf("English", "Malay")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        val sharedPref = requireContext().getSharedPreferences("UserCredentials", Context.MODE_PRIVATE)
        val currentUsername = sharedPref.getString("loggedInUser", "(No user)")

        binding.usernameText.text = currentUsername

        // Set up language spinner
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, languages)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.languageSpinner.adapter = adapter

        // Load saved language if any
        val savedLanguage = sharedPref.getString("language", "English")
        val languagePosition = languages.indexOf(savedLanguage)
        if (languagePosition >= 0) {
            binding.languageSpinner.setSelection(languagePosition)
        }

        binding.saveButton.setOnClickListener {
            val selectedLanguage = binding.languageSpinner.selectedItem.toString()
            sharedPref.edit().putString("language", selectedLanguage).apply()
            LocaleHelper.setLocale(requireContext(), selectedLanguage)

            Toast.makeText(
                requireContext(),
                "Language saved: $selectedLanguage. Changing the language...",
                Toast.LENGTH_SHORT
            ).show()
            activity?.recreate()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
