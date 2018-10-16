package spencerstudios.com.savingstracker

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.TextView
import kotlinx.android.synthetic.main.add_dialog.view.*
import java.text.DateFormat

class DialogFactory {

    @SuppressLint("InflateParams")
    fun displayAddDialog(bool: Boolean, context: Context, tv: TextView) {

        val bm = BankManager(context)
        val box = AlertDialog.Builder(context).create()
        val view = LayoutInflater.from(context).inflate(R.layout.add_dialog, null)
        box.setView(view)

        view.tv_symb.text = bm.getCurrencySym()
        view.title.text = if (bool) "Add Funds" else "Deduct Funds"
        view.btn_commit.text = if (bool) "Add amount" else "Deduct amount"

        view.btn_commit.setOnClickListener {
            val a = if (foo(view.et1).isEmpty()) "0" else foo(view.et1)
            val b = if (foo(view.et2).isEmpty()) "0" else foo(view.et2)
            val amt: String = bm.getCurrencySym() + a + "." + b
            if (bool) bm.addFunds(amt) else bm.deductFunds(amt)
            tv.text = bm.getFunds()

            val datetime = DateFormat.getDateTimeInstance().format(System.currentTimeMillis())
            bm.commitSingleTransaction(datetime, if (bool) "+" + amt else "-" + amt, tv.text as String)

            box.dismiss()
        }
        box.show()
    }

    private fun foo(et: EditText): String {
        return et.text.toString()
    }

}