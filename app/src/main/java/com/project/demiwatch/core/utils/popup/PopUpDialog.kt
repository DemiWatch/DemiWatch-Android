package com.project.demiwatch.core.utils.popup

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.DialogFragment
import com.project.demiwatch.R
import com.project.demiwatch.features.navigation.NavigationActivity

class PopUpDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity.let {
            val builder = AlertDialog.Builder(it, R.style.TransparentDialogTheme)
            val inflater = requireActivity().layoutInflater

            val view = inflater.inflate(R.layout.dialog_logout, null)
            builder.setView(view)
            builder.setCancelable(false)

            val btnAccessRoute = view.findViewById<Button>(R.id.btn_access_route_dialog)
            btnAccessRoute.setOnClickListener {
                val intentToNavigation = Intent(requireContext(), NavigationActivity::class.java)
                startActivity(intentToNavigation)
            }

            builder.create()


        } ?: throw IllegalStateException("Activity cannot be null")
    }
}