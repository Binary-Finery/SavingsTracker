package spencerstudios.com.savingstracker

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_transaction.*

class TransactionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction)

        val bm = BankManager(this@TransactionActivity)
        lv.adapter = TransactionHistoryAdapter(this, bm.getAllTransactions())
    }
}
