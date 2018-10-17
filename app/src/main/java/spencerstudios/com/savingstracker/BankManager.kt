package spencerstudios.com.savingstracker

import android.content.Context
import android.preference.PreferenceManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class BankManager(context: Context) {

    private val bank = PreferenceManager.getDefaultSharedPreferences(context)
    private val bankKey = "bank_key"
    private val currencyKey = "currency_key"

    fun getCurrencySym(): String {
        return bank.getString(currencyKey, "£")
    }

    fun setCurrencySym(s: String) {
        bank.edit().putString(currencyKey, s).apply()
    }

    fun getFunds(): String {
        return bank.getString(bankKey, "00.00")
    }

    fun addFunds(cash: String) {
        val v = getValues(cash)
        var pound = v[0] + v[2]
        var pennies = v[1] + v[3]

        if (pennies > 99) {
            pound += 1
            pennies -= 100
        }
        formatAndCommitToBank(pound, pennies)
    }

    fun deductFunds(cash: String) {
        val v = getValues(cash)
        var pound = v[2] - v[0]
        var pennies = v[3] - v[1]
        if (pennies < 0) {
            pound -= 1
            pennies += 100
        }
        formatAndCommitToBank(pound, pennies)
    }

    fun commitSingleTransaction(date: String, amount: String, balance: String) {
        val transactions: ArrayList<Transaction> = getAllTransactions()
        transactions.add(0, Transaction(date, amount, balance))
        val gson = Gson()
        val foo = gson.toJson(transactions)
        bank.edit().putString("all_transactions", foo).apply()
    }

    private fun getValues(cash: String): IntArray {
        val ints = IntArray(4)
        //cash to add/deduct from balance
        ints[0] = cash.split(".")[0].toInt()
        ints[1] = cash.split(".")[1].toInt()
        //current balance
        ints[2] = getFunds().split(".")[0].toInt()
        ints[3] = getFunds().split(".")[1].toInt()
        return ints
    }

    private fun formatAndCommitToBank(pound: Int, pennies: Int) {
        var stringPenny = pennies.toString()
        val stringPound = pound.toString()
        if (stringPenny.length < 2) stringPenny = "0" + stringPenny
        bank.edit().putString(bankKey, stringPound + "." + stringPenny).apply()
    }

    fun getAllTransactions(): ArrayList<Transaction> {
        val temp: ArrayList<Transaction>
        val gson = Gson()
        val content = bank.getString("all_transactions", "")

        temp = if (content!!.isEmpty()) {
            ArrayList()
        } else {
            val type = object : TypeToken<List<Transaction>>() {}.type
            gson.fromJson(content, type)
        }
        return temp
    }

    fun resetBank() {
        bank.edit().putString("all_transactions", "").apply()
        bank.edit().putString(bankKey, "00.00").apply()
    }
}