package spencerstudios.com.savingstracker

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.TextView
import kotlinx.android.synthetic.main.add_dialog.view.*
import kotlinx.android.synthetic.main.reset_bank_dialog.view.*
import kotlinx.android.synthetic.main.symbol_dialog.view.*
import java.text.DateFormat

class DialogFactory {

    @SuppressLint("InflateParams")
    fun displayTransactionDialog(bool: Boolean, context: Context, tv: TextView) {

        val bm = BankManager(context)
        val valBox = AlertDialog.Builder(context).create()
        val view = LayoutInflater.from(context).inflate(R.layout.add_dialog, null)
        valBox.setView(view)

        view.tv_symb.text = bm.getCurrencySym()
        view.title.text = if (bool) "Add Funds" else "Deduct Funds"
        view.btn_commit.text = if (bool) "Add amount" else "Deduct amount"

        view.btn_commit.setOnClickListener {
            val a = if (foo(view.et1).isEmpty()) "0" else foo(view.et1)
            val b = if (foo(view.et2).isEmpty()) "0" else foo(view.et2)
            val amt = a + "." + b
            if (bool) bm.addFunds(amt) else bm.deductFunds(amt)
            tv.text = bm.getFunds()

            val date = DateFormat.getDateInstance().format(System.currentTimeMillis())
            bm.commitSingleTransaction(date, if (bool) "+" + amt else "-" + amt, tv.text as String)

            valBox.dismiss()
        }
        valBox.show()
    }

    @SuppressLint("InflateParams")
    fun displayEditCurrencySymbolDialog(c: Context, tv: TextView) {
        val bm = BankManager(c)
        val symBox = AlertDialog.Builder(c).create()
        val view = LayoutInflater.from(c).inflate(R.layout.symbol_dialog, null)
        symBox.setView(view)
        view.et_dialog_symbol.setText(bm.getCurrencySym())
        view.btn_commit_symbol.setOnClickListener {
            if (view.et_dialog_symbol.text.isNotEmpty()) {
                bm.setCurrencySym(view.et_dialog_symbol.text.toString())
                tv.text = bm.getCurrencySym()
                symBox.dismiss()
            }
        }
        symBox.show()
    }

    @SuppressLint("InflateParams")
    fun resetBankDialog(c: Context, tv: TextView) {
        val resetBox = AlertDialog.Builder(c).create()
        val v = LayoutInflater.from(c).inflate(R.layout.reset_bank_dialog, null)
        resetBox.setView(v)
        v.btn_reset.setOnClickListener {
            val bm = BankManager(c)
            bm.resetBank()
            tv.text = bm.getFunds()
            resetBox.dismiss()
        }
        resetBox.show()
    }

    private fun foo(et: EditText): String {
        return et.text.toString()
    }
}