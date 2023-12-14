package com.project.demiwatch.features.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.demiwatch.core.domain.model.ExpandableHistoryListItem
import com.project.demiwatch.core.ui.adapter.PatientHistoryAdapter
import com.project.demiwatch.core.utils.Resource
import com.project.demiwatch.core.utils.data_mapper.JsonMapper
import com.project.demiwatch.core.utils.showLongToast
import com.project.demiwatch.databinding.FragmentHistoryBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class HistoryFragment : Fragment() {
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private val historyViewModel: HistoryViewModel by viewModels()

    private lateinit var savedToken: String
    private lateinit var savedWatchId: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHistoryBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        historyViewModel.getTokenUser().observe(this) {
            savedToken = it

            getCachedProfile()
        }
    }

    private fun getCachedProfile() {
        historyViewModel.getCachePatientProfile().observe(this) { patient ->
            val data = JsonMapper.convertToPatientProfile(patient)

            savedWatchId = data.watchCode
            initRecyclerView()
        }
    }

    private fun initRecyclerView() {
        historyViewModel.getHistoryPatient(savedToken, savedWatchId).observe(this) { history ->
            when (history) {
                is Resource.Error -> {
                    showLoading(false)
                    activity?.showLongToast("Terjadi kesalahan, pastikan koneksi internet anda baik")
                }
                is Resource.Loading -> {
                    showLoading(true)
                }
                is Resource.Message -> {
                    showLoading(false)
                    Timber.tag("HistoryFragment").d(history.message)
                }
                is Resource.Success -> {
                    showLoading(false)

                    val recyclerViewItems = history.data?.historyList!!.map {
                        ExpandableHistoryListItem(it, false)
                    }
                    initRecyclerViewLayout(
                        recyclerViewItems,
                        history.data.name,
                        history.data.symptom
                    )
                }
            }
        }
    }

    private fun initRecyclerViewLayout(
        listHistory: List<ExpandableHistoryListItem>,
        patientName: String,
        patientSymptom: String
    ) {
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvHistory.layoutManager = layoutManager

        val adapter = PatientHistoryAdapter(listHistory, patientName, patientSymptom)
        binding.rvHistory.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}