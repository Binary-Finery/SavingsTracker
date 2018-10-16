package spencerstudios.com.savingstracker

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        init()
    }

    private fun init() {
        supportActionBar?.hide()

        val bm = BankManager(this)
        val df = DialogFactory()

        tv_amt.text = bm.getFunds()
        amt_sym.text = bm.getCurrencySym()

        btn_add.setOnClickListener { df.displayAddDialog(true, this@MainActivity, tv_amt) }
        btn_deduct.setOnClickListener { df.displayAddDialog(false, this@MainActivity, tv_amt) }
        btn_history.setOnClickListener { startActivity(Intent(this@MainActivity, TransactionActivity::class.java)) }
    }
}
