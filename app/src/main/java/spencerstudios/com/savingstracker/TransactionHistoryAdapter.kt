package spencerstudios.com.savingstracker

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.list_item.view.*

class TransactionHistoryAdapter constructor(private val ctx: Context, private val items: ArrayList<Transaction>) : BaseAdapter() {

    @SuppressLint("ViewHolder", "InflateParams")
    override fun getView(pos: Int, p1: View?, p2: ViewGroup?): View {
        val v = LayoutInflater.from(ctx).inflate(R.layout.list_item, null)
        v.tv_datetime.text = items[pos].datetime
        v.tv_amount.text = items[pos].amount
        v.tv_balance.text = items[pos].balance
        return v
    }

    override fun getItem(p0: Int): Any {
        return p0
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getCount(): Int {
        return items.size
    }
}