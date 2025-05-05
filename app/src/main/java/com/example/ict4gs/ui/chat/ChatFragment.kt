package com.example.ict4gs.ui.chat

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.ict4gs.BuildConfig
import com.example.ict4gs.R
import com.example.ict4gs.databinding.FragmentChatBinding
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import okhttp3.MediaType.Companion.toMediaTypeOrNull


class ChatFragment : Fragment() {
    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!
    private val client = OkHttpClient()
    private val apiKey = BuildConfig.OPENAI_API_KEY
    private var globalLayoutListener: ViewTreeObserver.OnGlobalLayoutListener? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentChatBinding.inflate(inflater, container, false)

        binding.sendButton.setOnClickListener {
            sendUserMessage()
        }

        binding.userInput.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEND ||
                (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN)) {
                sendUserMessage()
                true
            } else {
                false
            }
        }

        val bottomNav = requireActivity().findViewById<com.google.android.material.bottomnavigation.BottomNavigationView>(R.id.bottom_nav)

        if (bottomNav != null) {
            globalLayoutListener = ViewTreeObserver.OnGlobalLayoutListener {
                // ✅ Add a null check for binding to avoid crash
                _binding?.let { bindingNonNull ->
                    val heightDiff = bindingNonNull.root.rootView.height - bindingNonNull.root.height
                    if (heightDiff > 200) {
                        bottomNav.visibility = View.GONE
                    } else {
                        bottomNav.visibility = View.VISIBLE
                    }
                }
            }
            binding.root.viewTreeObserver.addOnGlobalLayoutListener(globalLayoutListener)
        }


        return binding.root
    }



    private fun sendUserMessage() {
        val userMessage = binding.userInput.text.toString().trim()
        if (userMessage.isNotEmpty()) {
            appendMessage("You: $userMessage")
            binding.userInput.setText("")
            hideKeyboard()
            sendToLLM(userMessage)
        }
    }

    private fun hideKeyboard() {
        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.userInput.windowToken, 0)
    }

    private fun appendMessage(message: String) {
        binding.chatHistory.append("$message\n\n")
        binding.scrollView.post { binding.scrollView.fullScroll(View.FOCUS_DOWN) }
    }

    private fun sendToLLM(userMessage: String) {
        val json = JSONObject()
        json.put("model", "gpt-3.5-turbo")
        json.put("messages", JSONArray().apply {
            put(JSONObject().put("role", "system").put("content", "You are an autism advisor. You help parents with autistic children with their problems and give them advice. Only focus on autism."))
            put(JSONObject().put("role", "user").put("content", userMessage))
        })

        val requestBody = RequestBody.create("application/json".toMediaTypeOrNull(), json.toString())
        val request = Request.Builder()
            .url("https://api.openai.com/v1/chat/completions")
            .addHeader("Authorization", "Bearer $apiKey")
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                requireActivity().runOnUiThread {
                    appendMessage("Error: ${e.localizedMessage}")
                }
            }

            override fun onResponse(call: Call, response: Response) {
                if (!response.isSuccessful) {
                    requireActivity().runOnUiThread {
                        appendMessage("Error: API call failed with code ${response.code}")
                    }
                    return
                }

                val responseBody = response.body?.string()
                if (responseBody != null) {
                    try {
                        val content = JSONObject(responseBody)
                            .getJSONArray("choices")
                            .getJSONObject(0)
                            .getJSONObject("message")
                            .getString("content")

                        requireActivity().runOnUiThread {
                            appendMessage("Advisor: $content")
                        }
                    } catch (e: Exception) {
                        requireActivity().runOnUiThread {
                            appendMessage("Error: Could not parse response")
                        }
                    }
                } else {
                    requireActivity().runOnUiThread {
                        appendMessage("Error: Empty response from API")
                    }
                }
            }
        })
    }



    override fun onDestroyView() {
        globalLayoutListener?.let {
            binding.root.viewTreeObserver.removeOnGlobalLayoutListener(it)
        }
        _binding = null
        super.onDestroyView()
    }

}
