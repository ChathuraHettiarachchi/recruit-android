package nz.co.test.transactions.presentation.transactions

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import nz.co.test.transactions.R
import nz.co.test.transactions.domain.model.Transaction
import nz.co.test.transactions.utils.ItemOnClickListener
import nz.co.test.transactions.utils.convertToFormattedDateTime
import nz.co.test.transactions.utils.toFixedDecimal
import java.math.BigDecimal

class TransactionsAdapter(private val context: Context): RecyclerView.Adapter<TransactionsAdapter.ViewHolder>() {

    lateinit var items: List<Transaction>
    lateinit var onclickEventListener: ItemOnClickListener

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtTitle: TextView = view.findViewById(R.id.txtTitle)
        val txtDateTime: TextView = view.findViewById(R.id.txtDateTime)
        val txtAmount: TextView = view.findViewById(R.id.txtAmount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_transaction_list, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        val colorCode = if(item.credit> BigDecimal(0)) context.getColor(R.color.codeGreen) else context.getColor(R.color.codeRed)
        val amount = if(item.credit> BigDecimal(0)) item.credit.toFixedDecimal() else item.debit.toFixedDecimal()

        holder.txtTitle.text = item.summary ?: "N/A"
        holder.txtDateTime.text = item.transactionDate.convertToFormattedDateTime() ?: "N/A"
        holder.txtAmount.text = amount
        holder.txtAmount.setTextColor(colorCode)

        holder.itemView.setOnClickListener {
            onclickEventListener.onTransactionItemClick(holder.adapterPosition, items[holder.adapterPosition])
        }
    }
}