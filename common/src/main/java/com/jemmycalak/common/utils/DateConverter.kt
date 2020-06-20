package com.jemmycalak.common.utils

import java.text.SimpleDateFormat
import java.util.*

class DateConverter {

    companion object{
        val idLocale = Locale("id","ID")
        val yyyy_MM_dd = "yyyy-MM-dd"
        val EEE_ddMMMyyyy = "EEE, d MMM yyyy"

        fun dateConterver(date: String, fromFormat: String, toFormat: String):String{
            try {
                val dateFormat = SimpleDateFormat(fromFormat, idLocale)
                val mDate = dateFormat.parse(date)

                val dateFormatTo = SimpleDateFormat(toFormat, idLocale)
                return dateFormatTo.format(mDate)
            }catch (e:Exception){
                e.printStackTrace()
                return ""
            }
        }
    }
}