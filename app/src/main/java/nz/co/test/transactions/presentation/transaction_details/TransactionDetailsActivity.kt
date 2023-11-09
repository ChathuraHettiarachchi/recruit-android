package nz.co.test.transactions.presentation.transaction_details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import nz.co.test.transactions.R
import nz.co.test.transactions.domain.model.Transaction
import nz.co.test.transactions.utils.convertToFormattedDateTime
import nz.co.test.transactions.utils.toFixedDecimal
import java.math.BigDecimal

class TransactionDetailsActivity : AppCompatActivity() {

    lateinit var transaction: Transaction

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction_details)

        transaction = intent.getParcelableExtra<Transaction>("transaction")!!
        initUI()
    }

    private fun initUI() {
        (findViewById<TextView>(R.id.txtTransactionId)).text = transaction.id.toString()
        (findViewById<TextView>(R.id.txtSummary)).text = transaction.summary.toString()
        (findViewById<TextView>(R.id.txtDateTime)).text = transaction.transactionDate.convertToFormattedDateTime()

        val txtAmount = findViewById<TextView>(R.id.txtAmount)
        val txtCreditDebit = findViewById<TextView>(R.id.txtCreditDebit)
        val txtGst = findViewById<TextView>(R.id.txtGst)
        val txtGstAmount = findViewById<TextView>(R.id.txtGstAmount)

        if(transaction.credit> BigDecimal(0)){
            txtAmount.text = transaction.credit.toFixedDecimal()
            txtAmount.setTextColor(getColor(R.color.codeGreen))
            txtCreditDebit.text = "Credit"

            txtGst.visibility = View.INVISIBLE
            txtGstAmount.visibility = View.INVISIBLE
        } else{
            txtAmount.text = transaction.debit.toFixedDecimal()
            txtAmount.setTextColor(getColor(R.color.codeRed))
            txtCreditDebit.text = "Debit"

            txtGstAmount.text = (transaction.debit* BigDecimal(.15)).toFixedDecimal()
        }
    }
}