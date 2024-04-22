package com.buzztech.calenderapp.ui.main.knowledge

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.buzztech.calenderapp.R
import com.buzztech.calenderapp.databinding.FragmentKnowledgeBinding
import com.buzztech.calenderapp.model.Knowledge
import com.buzztech.calenderapp.utils.gone
import kotlinx.coroutines.launch


class KnowledgeFragment : Fragment() {

    companion object {
        fun newInstance() = KnowledgeFragment()
    }

    private var _binding: FragmentKnowledgeBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: KnowledgeViewModel
    private lateinit var contentAdapter: KnowledgeContentAdapter
    private lateinit var knowLedgeDetailAdapter: KnowledgeDetailAdapter

    private var contentList: ArrayList<String> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this).get(KnowledgeViewModel::class.java)
        _binding = FragmentKnowledgeBinding.inflate(layoutInflater)
        binding.apply {
            icBack.setOnClickListener {
                findNavController().popBackStack()
            }

            requireActivity().findViewById<ImageView>(R.id.drawer).gone()
        }

        observer()
        return binding.root
    }

    private fun observer() {
        lifecycleScope.launch {
            viewModel.knowledgeEventChannel.collect { event ->
                when (event) {
                    is KnowledgeViewModel.KnowledgeEvent.Error -> {
                        Toast.makeText(requireContext(), event.error, Toast.LENGTH_SHORT).show()
                    }

                    is KnowledgeViewModel.KnowledgeEvent.KnowledgeDataList -> {
                        event.knowledgeList.forEach {
                            if (requireArguments().getString("knowledge").equals(it.KnowledgeFor)) {
                                loadContentAdapter(it.content)
                                loadKnowledgeDetailAdapter(it)
                            }
                        }
                    }

                    else -> {}
                }
            }
        }
    }

    private fun loadContentAdapter(content: List<String>) {
        binding.apply {
            contentAdapter = KnowledgeContentAdapter(content) { position ->

                Handler(Looper.myLooper()!!).postDelayed({
                    val layoutManager = detailsRv.layoutManager as? LinearLayoutManager
                    if (layoutManager != null) {
                        layoutManager.smoothScrollToPosition(
                            detailsRv,
                            RecyclerView.State(),
                            position
                        )
                    }
                }, 500)

            }
            contentRv.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            contentRv.adapter = contentAdapter
        }
    }

    private fun loadKnowledgeDetailAdapter(content: Knowledge) {
        binding.apply {
            knowLedgeDetailAdapter = KnowledgeDetailAdapter(content)
            detailsRv.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            detailsRv.adapter = knowLedgeDetailAdapter
        }
    }
}