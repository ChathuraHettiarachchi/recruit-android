package nz.co.test.transactions.presentation.transactions

import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import nz.co.test.transactions.R
import nz.co.test.transactions.di.component.DaggerAppComponent
import nz.co.test.transactions.domain.model.Transaction
import nz.co.test.transactions.presentation.transaction_details.TransactionDetailsActivity
import nz.co.test.transactions.utils.ItemOnClickListener
import javax.inject.Inject

class TransactionListActivity : AppCompatActivity(), ItemOnClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var recyclerView: RecyclerView
    lateinit var progressBar: ProgressBar
    lateinit var transactionsAdapter: TransactionsAdapter

    private val viewModel: TransactionListViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)
            .get(TransactionListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction_list)
        DaggerAppComponent.create().inject(this)

        initUI()
    }

    private fun initUI() {
        transactionsAdapter = TransactionsAdapter(this@TransactionListActivity)

        recyclerView = findViewById(R.id.recyclerView)
        progressBar = findViewById(R.id.progressBar)
        recyclerView.visibility = View.INVISIBLE

        transactionsAdapter.items = emptyList()
        transactionsAdapter.onclickEventListener = this
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@TransactionListActivity)
            adapter = transactionsAdapter
        }

        loadData()
    }

    private fun loadData() {
        viewModel.populateAllTransactions()
        lifecycleScope.launchWhenCreated {
            viewModel.transactionsState.collect{
                if (!it.isLoading && it.data.isNotEmpty()) {
                    transactionsAdapter.items = it.data
                    transactionsAdapter.notifyDataSetChanged()
                    recyclerView.visibility = View.VISIBLE
                    progressBar.visibility = View.INVISIBLE
                }
            }
        }
    }

    override fun onTransactionItemClick(position: Int, transaction: Transaction) {
        val intent = Intent(this@TransactionListActivity, TransactionDetailsActivity::class.java)
        intent.putExtra("transaction", transaction)
        startActivity(intent)
    }
}