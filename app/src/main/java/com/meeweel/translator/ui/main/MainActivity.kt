package com.meeweel.translator.ui.main

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewTreeObserver
import android.view.animation.AnticipateInterpolator
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.meeweel.translator.R
import com.meeweel.translator.databinding.ActivityMainBinding
import com.meeweel.model.AppState
import com.meeweel.model.DataModel
import com.meeweel.historyscreen.HistoryActivity
import com.meeweel.translator.ui.main.adapter.MainAdapter
import com.meeweel.utils.SearchDialogFragment
import com.meeweel.utils.network.OnlineLiveData
import org.koin.core.qualifier.named
import org.koin.java.KoinJavaComponent.getKoin


private const val SLIDE_LEFT_DURATION = 1000L
private const val COUNTDOWN_DURATION = 2000L
private const val COUNTDOWN_INTERVAL = 1000L

class MainActivity : AppCompatActivity() {

    private var isNetworkAvailable: Boolean = true
    private lateinit var binding: ActivityMainBinding

    private val myScope = getKoin().createScope("myScope",named("MainActivity"))
    val model: MainViewModel by myScope.inject()

    private val fabClickListener: android.view.View.OnClickListener = android.view.View.OnClickListener {
        val searchDialogFragment = SearchDialogFragment.newInstance()
        searchDialogFragment.setOnSearchClickListener(onSearchClickListener)
        searchDialogFragment.show(supportFragmentManager, BOTTOM_SHEET_FRAGMENT_DIALOG_TAG)
    }
    private val onSearchClickListener: SearchDialogFragment.OnSearchClickListener =
        object : SearchDialogFragment.OnSearchClickListener {
            override fun onClick(searchWord: String) { model.getData(searchWord,
                isNetworkAvailable
            ) }
        }

    private val fabHistoryClickListener: android.view.View.OnClickListener = android.view.View.OnClickListener {
        val intent = Intent(this, HistoryActivity::class.java)
        startActivity(intent)
    }

    private val fabSearchInStorageClickListener: android.view.View.OnClickListener = android.view.View.OnClickListener {
        val searchDialogFragment = SearchDialogFragment.newInstance()
        searchDialogFragment.setOnSearchClickListener(onStorageSearchClickListener)
        searchDialogFragment.show(supportFragmentManager, BOTTOM_SHEET_FRAGMENT_DIALOG_TAG)
    }
    private val onStorageSearchClickListener: SearchDialogFragment.OnSearchClickListener =
        object : SearchDialogFragment.OnSearchClickListener {
            override fun onClick(searchWord: String) { model.getData(searchWord, false) }
        }
    private val onListItemClickListener: MainAdapter.OnListItemClickListener =
        object : MainAdapter.OnListItemClickListener {
            override fun onItemClick(data: DataModel) {
                Toast.makeText(this@MainActivity, data.text, Toast.LENGTH_SHORT).show()
            }
        }

    private var adapter: MainAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setDefaultSplashScreen()
        iniViewModel()
        initViews()
        subscribeToNetworkState()
    }

    private fun initViews() {
        binding.searchFab.setOnClickListener(fabClickListener)
        binding.searchFab2.setOnClickListener(fabSearchInStorageClickListener)
        binding.searchFab3.setOnClickListener(fabHistoryClickListener)
        binding.mainActivityRecyclerview.layoutManager = LinearLayoutManager(applicationContext)
        binding.mainActivityRecyclerview.adapter = adapter
    }

    private fun iniViewModel() {

        if (binding.mainActivityRecyclerview.adapter != null) {
            throw IllegalStateException("The ViewModel should be initialised first")
        }
        model.liveData().observe(this@MainActivity, Observer<AppState> { renderData(it) })
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
                        binding.mainActivityRecyclerview.layoutManager =
                            LinearLayoutManager(applicationContext)
                        binding.mainActivityRecyclerview.adapter =
                            MainAdapter(onListItemClickListener, dataModel)
                    } else {
                        adapter!!.setData(dataModel)
                    }
                }
            }
            is AppState.Loading -> {
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
            is AppState.Error -> {
                showErrorScreen(appState.error.message)
            }
        }
    }

    private fun setDefaultSplashScreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            setSplashScreenHideAnimation()
        }

        setSplashScreenDuration()
    }

    @RequiresApi(31)
    private fun setSplashScreenHideAnimation() {
        splashScreen.setOnExitAnimationListener { splashScreenView ->
            val slideLeft = ObjectAnimator.ofFloat(
                splashScreenView,
                View.TRANSLATION_X,
                0f,
                -splashScreenView.height.toFloat()
            )
            slideLeft.interpolator = AnticipateInterpolator()
            slideLeft.duration = SLIDE_LEFT_DURATION

            slideLeft.doOnEnd { splashScreenView.remove() }
            slideLeft.start()
        }
    }

    private fun setSplashScreenDuration() {
        var isHideSplashScreen = false

        object : CountDownTimer(COUNTDOWN_DURATION, COUNTDOWN_INTERVAL) {
            override fun onTick(millisUntilFinished: Long) {}
            override fun onFinish() {
                isHideSplashScreen = true
            }
        }.start()

        val content: View = findViewById(android.R.id.content)
        content.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    return if (isHideSplashScreen) {
                        content.viewTreeObserver.removeOnPreDrawListener(this)
                        true
                    } else {
                        false
                    }
                }
            }
        )
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

    private fun subscribeToNetworkState() {
        OnlineLiveData(this).observe(
            this,
            {
                isNetworkAvailable = it
                if (!isNetworkAvailable) {
                    Toast.makeText(
                        this,
                        "No internet",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        )
    }

    companion object {
        private const val BOTTOM_SHEET_FRAGMENT_DIALOG_TAG =
            "74a54328-5d62-46bf-ab6b-cbf5fgt0-092395"
    }
}