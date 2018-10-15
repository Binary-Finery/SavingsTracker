package spencerstudios.com.savingstracker

import android.content.Context
import android.preference.PreferenceManager

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
        val v = getVals(cash)
        var pound = v[0] + v[2]
        var pennies = v[1] + v[3]

        if (pennies > 99) {
            pound += 1
            pennies -= 100
        }
        formatAndCommitToBank(pound, pennies)
    }

    fun deductFunds(cash: String) {
        val v = getVals(cash)
        var pound = v[2] - v[0]
        var pennies = v[3] - v[1]
        if (pennies < 0) {
            pound -= 1
            pennies += 100
        }
        formatAndCommitToBank(pound, pennies)
    }

    fun getVals(cash: String): IntArray {
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
}