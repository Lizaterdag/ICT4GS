package com.example.ict4gs.ui.training

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.ict4gs.databinding.FragmentTrainingBinding

class TrainingFragment : Fragment() {

    private var _binding: FragmentTrainingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTrainingBinding.inflate(inflater, container, false)

        binding.cardAutismTest.setOnClickListener {
            startActivity(Intent(requireContext(), AutismTestActivity::class.java))
        }

        binding.cardVideos.setOnClickListener {
            startActivity(Intent(requireContext(), AutismVideosActivity::class.java))
        }

        binding.cardInfo.setOnClickListener {
            startActivity(Intent(requireContext(), AutismInfoActivity::class.java))
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
