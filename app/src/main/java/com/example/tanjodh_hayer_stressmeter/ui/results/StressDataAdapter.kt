package com.example.tanjodh_hayer_stressmeter.ui.results
import com.example.tanjodh_hayer_stressmeter.ui.results.StressData
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tanjodh_hayer_stressmeter.R

class StressDataAdapter(private val stressDataList: List<StressData>) :
    RecyclerView.Adapter<StressDataAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewTime: TextView = itemView.findViewById(R.id.textViewTitleTimestamp)
        val textViewStress: TextView = itemView.findViewById(R.id.textViewTitleStress)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.stress_data, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val stressData = stressDataList[position]
        holder.textViewTime.text = stressData.timestamp.toString()
        holder.textViewStress.text = stressData.stressLevel.toString()
    }

    override fun getItemCount(): Int {
        return stressDataList.size
    }
}

