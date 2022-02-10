package com.meeweel.translator.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.meeweel.translator.R
import com.meeweel.translator.databinding.ActivityMainBinding
import com.meeweel.model.AppState
import com.meeweel.model.DataModel
import com.meeweel.repository.retrofit.isOnline
import com.meeweel.historyscreen.HistoryActivity
import com.meeweel.translator.ui.main.adapter.MainAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

//    @Inject
//    internal lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: ActivityMainBinding
    lateinit var model: MainViewModel
//    val model: MainViewModel by lazy {
//        ViewModelProvider(this).get(MainViewModel::class.java) // Saving here!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//    }

    private val fabClickListener: android.view.View.OnClickListener = android.view.View.OnClickListener {
        val searchDialogFragment = com.meeweel.utils.SearchDialogFragment.newInstance()
        searchDialogFragment.setOnSearchClickListener(onSearchClickListener)
        searchDialogFragment.show(supportFragmentManager, BOTTOM_SHEET_FRAGMENT_DIALOG_TAG)
    }
    private val onSearchClickListener: com.meeweel.utils.SearchDialogFragment.OnSearchClickListener =
        object : com.meeweel.utils.SearchDialogFragment.OnSearchClickListener {
            override fun onClick(searchWord: String) { model.getData(searchWord,
                com.meeweel.repository.retrofit.isOnline(this@MainActivity)
            ) }
        }

    private val fabClickListener3: android.view.View.OnClickListener = android.view.View.OnClickListener {
        val intent = Intent(this, com.meeweel.historyscreen.HistoryActivity::class.java)
        startActivity(intent)
    }

    private val fabClickListener2: android.view.View.OnClickListener = android.view.View.OnClickListener {
        val searchDialogFragment = com.meeweel.utils.SearchDialogFragment.newInstance()
        searchDialogFragment.setOnSearchClickListener(onSearchClickListener2)
        searchDialogFragment.show(supportFragmentManager, BOTTOM_SHEET_FRAGMENT_DIALOG_TAG)
    }
    private val onSearchClickListener2: com.meeweel.utils.SearchDialogFragment.OnSearchClickListener =
        object : com.meeweel.utils.SearchDialogFragment.OnSearchClickListener {
            override fun onClick(searchWord: String) { model.getData(searchWord, false) }
        }
    private val onListItemClickListener: MainAdapter.OnListItemClickListener =
        object : MainAdapter.OnListItemClickListener {
            override fun onItemClick(data: com.meeweel.model.DataModel) {
                Toast.makeText(this@MainActivity, data.text, Toast.LENGTH_SHORT).show()
            }
        }

    private val observer = Observer<com.meeweel.model.AppState> {
        renderData(it)
    }
    private var adapter: MainAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
//        AndroidInjection.inject(this)

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        iniViewModel()
        initViews()

//        model = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
//        model.liveData().observe(this@MainActivity, observer)

//        binding.searchFab.setOnClickListener {
//            val searchDialogFragment = SearchDialogFragment.newInstance()
//            searchDialogFragment.setOnSearchClickListener(object :
//                SearchDialogFragment.OnSearchClickListener {
//                override fun onClick(searchWord: String) {
//                    model.getData(searchWord, true)
//                }
//            })
//            searchDialogFragment.show(supportFragmentManager, BOTTOM_SHEET_FRAGMENT_DIALOG_TAG)
//        }
    }


    private fun initViews() {
        binding.searchFab.setOnClickListener(fabClickListener)
        binding.searchFab2.setOnClickListener(fabClickListener2)
        binding.searchFab3.setOnClickListener(fabClickListener3)
        binding.mainActivityRecyclerview.layoutManager = LinearLayoutManager(applicationContext)
        binding.mainActivityRecyclerview.adapter = adapter
    }

    private fun iniViewModel() {
        // Убедимся, что модель инициализируется раньше View
        if (binding.mainActivityRecyclerview.adapter != null) {
            throw IllegalStateException("The ViewModel should be initialised first")
        }
        // Теперь ViewModel инициализируется через функцию by viewModel()
        // Это функция, предоставляемая Koin из коробки через зависимость
        // import org.koin.androidx.viewmodel.ext.android.viewModel
        val viewModel: MainViewModel by viewModel()
        model = viewModel
        model.liveData().observe(this@MainActivity, Observer<com.meeweel.model.AppState> { renderData(it) })
    }


    private fun renderData(appState: com.meeweel.model.AppState) {
        when (appState) {
            is com.meeweel.model.AppState.Success -> {
                val dataModel = appState.data
                if (dataModel == null || dataModel.isEmpty()) {
                    showErrorScreen(getString(R.string.empty_server_response_on_success))
                } else {
                    showViewSuccess()
                    if (adapter == null) {
                        binding.mainActivityRecyclerview.layoutManager =
                            LinearLayoutManager(applicationContext)
                        binding.mainActivityRecyclerview.adapter =
                            MainAdapter(onListItemClickListener, dataModel)
                    } else {
                        adapter!!.setData(dataModel)
                    }
                }
            }
            is com.meeweel.model.AppState.Loading -> {
                showViewLoading()
                if (appState.progress != null) {
                    binding.progressBarHorizontal.visibility = VISIBLE
                    binding.progressBarRound.visibility = GONE
                    binding.progressBarHorizontal.progress = appState.progress!!
                } else {
                    binding.progressBarHorizontal.visibility = GONE
                    binding.progressBarRound.visibility = VISIBLE
                }
            }
            is com.meeweel.model.AppState.Error -> {
                showErrorScreen(appState.error.message)
            }
        }
    }

    private fun showErrorScreen(error: String?) {
        showViewError()
        binding.errorTextview.text = error ?: getString(R.string.undefined_error)
        binding.reloadButton.setOnClickListener {
            model.getData("hi", true)
        }
    }

    private fun showViewSuccess() {
        binding.successLinearLayout.visibility = VISIBLE
        binding.loadingFrameLayout.visibility = GONE
        binding.errorLinearLayout.visibility = GONE
    }

    private fun showViewLoading() {
        binding.successLinearLayout.visibility = GONE
        binding.loadingFrameLayout.visibility = VISIBLE
        binding.errorLinearLayout.visibility = GONE
    }

    private fun showViewError() {
        binding.successLinearLayout.visibility = GONE
        binding.loadingFrameLayout.visibility = GONE
        binding.errorLinearLayout.visibility = VISIBLE
    }

    companion object {
        private const val BOTTOM_SHEET_FRAGMENT_DIALOG_TAG =
            "74a54328-5d62-46bf-ab6b-cbf5fgt0-092395"
    }
}