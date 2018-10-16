package spencerstudios.com.savingstracker

import android.content.Context
import android.preference.PreferenceManager

class HistoryLogger constructor(ctx: Context) {

    val transLogger = PreferenceManager.getDefaultSharedPreferences(ctx)

    fun getLog(): ArrayList<String> {
        return arrayListOf("one", "two")
    }
}