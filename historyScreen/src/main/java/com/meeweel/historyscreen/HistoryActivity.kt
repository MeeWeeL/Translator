package com.meeweel.historyscreen

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.meeweel.historyscreen.databinding.ActivityHistoryBinding
import com.meeweel.model.AppState
import com.meeweel.model.DataModel
import org.koin.core.qualifier.named
import org.koin.java.KoinJavaComponent

class HistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryBinding
    private val historyScope =
        KoinJavaComponent.getKoin().createScope("historyScope", named("HistoryActivity"))

    private val onListItemClickListener: HistoryAdapter.OnListItemClickListener =
        object : HistoryAdapter.OnListItemClickListener {
            override fun onItemClick(data: DataModel) {
                val intent = Intent(this@HistoryActivity, DescriptionActivity::class.java)
                intent.putExtra("word", data.text)
                intent.putExtra("url", data.meanings!!.get(0).imageUrl)
                intent.putExtra("desc", data.meanings!![0].translation!!.translation)
                startActivity(intent)
            }
        }

    private val adapter: HistoryAdapter by lazy { HistoryAdapter(onListItemClickListener) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        iniViewModel()
        initViews()
    }

    override fun onResume() {
        super.onResume()
//        model.getData()
    }

    private fun iniViewModel() {
        if (binding.historyActivityRecyclerview.adapter != null) {
            throw IllegalStateException("The ViewModel should be initialised first")
        }
        val model: HistoryViewModel by historyScope.inject()
        model.subscribe().observe(this@HistoryActivity, Observer<AppState> { renderData(it) })
        model.getData()
    }

    private fun initViews() {
        binding.historyActivityRecyclerview.adapter = adapter
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                val dataModel = appState.data
                if (dataModel == null || dataModel.isEmpty()) {
                    showErrorScreen(getString(R.string.empty_server_response_on_success))
                } else {
                    showViewSuccess()
                    if (adapter == null) {
                        binding.historyActivityRecyclerview.layoutManager =
                            LinearLayoutManager(applicationContext)
                        binding.historyActivityRecyclerview.adapter =
                            HistoryAdapter(onListItemClickListener)
                    } else {
                        adapter.setData(dataModel)
                    }
                }
            }
            is AppState.Loading -> {
                showViewLoading()
                if (appState.progress != null) {
                    binding.progressBarHorizontal.visibility = View.VISIBLE
                    binding.progressBarRound.visibility = View.GONE
                    binding.progressBarHorizontal.progress = appState.progress!!
                } else {
                    binding.progressBarHorizontal.visibility = View.GONE
                    binding.progressBarRound.visibility = View.VISIBLE
                }
            }
            is AppState.Error -> {
                showErrorScreen(appState.error.message)
            }
        }
    }

    private fun showErrorScreen(error: String?) {
        showViewError()
    }

    private fun showViewSuccess() {
        binding.loadingFrameLayout.visibility = View.GONE
    }

    private fun showViewLoading() {
        binding.loadingFrameLayout.visibility = View.VISIBLE
    }

    private fun showViewError() {
        binding.loadingFrameLayout.visibility = View.GONE
    }
}
