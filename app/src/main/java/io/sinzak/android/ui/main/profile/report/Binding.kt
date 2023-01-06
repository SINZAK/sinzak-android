package io.sinzak.android.ui.main.profile.report

import android.widget.TextView
import androidx.databinding.BindingAdapter
import io.sinzak.android.R
import io.sinzak.android.enums.ReportType
import io.sinzak.android.enums.ReportType.*

@BindingAdapter("reportType")
fun setReportType(view: TextView, type: ReportType)
{
    view.setText(
        when(type)
        {
            REPORT_SELLER -> R.string.str_report_type_seller
            REPORT_NO_MANNER -> R.string.str_report_type_no_manner
            REPORT_SEXUAL -> R.string.str_report_type_sexual
            REPORT_DISPUTE -> R.string.str_report_type_dispute
            REPORT_CHEAT -> R.string.str_report_type_cheat
            REPORT_OTHER -> R.string.str_report_type_other
        }
    )
}