package com.example.ict4gs.ui.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.ict4gs.databinding.FragmentChatBinding
import okhttp3.*
import java.io.IOException
import com.google.gson.Gson
import com.example.ict4gs.BuildConfig
import com.google.gson.annotations.SerializedName
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

class ChatFragment : Fragment() {
    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!
    private val client = OkHttpClient()
    private val gson = Gson()

    private val apiKey = BuildConfig.OPENAI_API_KEY
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentChatBinding.inflate(inflater, container, false)
        binding.sendButton.setOnClickListener {
            val userMessage = binding.userInput.text.toString()
            if (userMessage.isNotBlank()) {
                appendMessage("You: $userMessage")
                binding.userInput.setText("")
                sendToLLM(userMessage)
            }
        }
        return binding.root
    }

    private fun appendMessage(message: String) {
        binding.chatHistory.append("$message\n\n")
        binding.scrollView.post {
            binding.scrollView.fullScroll(View.FOCUS_DOWN)
        }
    }

    private fun sendToLLM(message: String) {
        val requestBody = gson.toJson(
            mapOf(
                "model" to "gpt-3.5-turbo",
                "messages" to listOf(
                    mapOf("role" to "user", "content" to message)
                )
            )
        )

        val request = Request.Builder()
            .url("https://api.openai.com/v1/chat/completions")
            .addHeader("Authorization", "Bearer $apiKey")
            .addHeader("Content-Type", "application/json")
            .post(requestBody.toRequestBody("application/json".toMediaTypeOrNull()))
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                requireActivity().runOnUiThread {
                    appendMessage("Error: ${e.message}")
                }
            }

            override fun onResponse(call: Call, response: Response) {
                response.body?.string()?.let { bodyString ->
                    val completion = gson.fromJson(bodyString, CompletionResponse::class.java)
                    val reply = completion.choices.firstOrNull()?.message?.content ?: "No reply"
                    requireActivity().runOnUiThread {
                        appendMessage("LLM: $reply")
                    }
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

data class CompletionResponse(
    val choices: List<Choice>
)

data class Choice(
    val message: Message
)

data class Message(
    val role: String,
    val content: String
)
