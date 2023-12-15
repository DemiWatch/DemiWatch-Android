package com.project.demiwatch.core.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.demiwatch.core.domain.model.ExpandableHistoryListItem
import com.project.demiwatch.core.utils.DateTimeUtils
import com.project.demiwatch.core.utils.constants.PatientStatus
import com.project.demiwatch.databinding.ItemListHistoryBinding

class PatientHistoryAdapter(
    private var listHistory: List<ExpandableHistoryListItem>,
    private var patientName: String,
    private var patientSymptom: String
) :
    RecyclerView.Adapter<PatientHistoryAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemListHistoryBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemListHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listHistory.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = listHistory[position]

        holder.binding.apply {
            setupData(data, holder.binding, patientName, patientSymptom)

            layoutHistoryDetail.visibility =
                if (data.isExpandable) View.VISIBLE else View.GONE
            ivDropdown.rotation = if (data.isExpandable) 180f else 0f

            constraintLayout2.setOnClickListener {
                toggleItemVisibility(data, holder.binding, position)
            }
        }
    }

    private fun setupData(
        data: ExpandableHistoryListItem,
        binding: ItemListHistoryBinding,
        patientName: String,
        patientSymptom: String,
    ) {
        binding.cardPatientName.text = patientName
        binding.cardPatientSymptomps.text = patientSymptom

        binding.tvHistoryDate.text = DateTimeUtils.formatDateTimeRange(
            data.historyListItem.start,
            data.historyListItem.end
        )

        binding.tvHistoryDuration.text = DateTimeUtils.formatDuration(data.historyListItem.duration)
        binding.tvHomeAddress.text = data.historyListItem.homeAddress
        binding.tvDestinationAddress.text = data.historyListItem.destinationAddress

        when (data.historyListItem.condition) {
            "aman" -> {
                binding.cardPatientStatus.setStatus(PatientStatus.SAFE.status)
            }
            "kendala" -> {
                binding.cardPatientStatus.setStatus(PatientStatus.TROUBLE.status)
            }
        }
    }

    private fun toggleItemVisibility(
        data: ExpandableHistoryListItem,
        binding: ItemListHistoryBinding,
        position: Int
    ) {
        data.isExpandable = !data.isExpandable

        binding.layoutHistoryDetail.visibility = if (data.isExpandable) View.VISIBLE else View.GONE
        binding.ivDropdown.rotation = if (data.isExpandable) 180f else 0f

        binding.layoutHistoryDetail.post {
            notifyItemChanged(position)
        }
    }
}