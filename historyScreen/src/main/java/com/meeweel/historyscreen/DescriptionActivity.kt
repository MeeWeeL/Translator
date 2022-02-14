package com.meeweel.historyscreen

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import coil.ImageLoader
import coil.request.LoadRequest
import com.meeweel.utils.AlertDialogFragment
import com.meeweel.utils.databinding.ActivityDescriptionBinding

class DescriptionActivity : AppCompatActivity() {

    var word: String? = null
    var description: String? = null
    var url: String? = null
    private lateinit var binding: ActivityDescriptionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDescriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        word = intent.getStringExtra("word")
        description = intent.getStringExtra("desc")
        url = intent.getStringExtra("url")

//        setActionbarHomeButtonAsUp()
        binding.descriptionScreenSwipeRefreshLayout.setOnRefreshListener { startLoadingOrShowError() }
        setData()
    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return when (item.itemId) {
//            android.R.id.home -> {
//                onBackPressed()
//                true
//            }
//            else -> super.onOptionsItemSelected(item)
//        }
//    }

//    private fun setActionbarHomeButtonAsUp() {
//        supportActionBar?.setHomeButtonEnabled(true)
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//    }

    private fun setData() {
        binding.descriptionHeader.text = word
        binding.descriptionTextview.text = description
        useCoilToLoadPhoto(binding.descriptionImageview, url!!)
//        val bundle = intent.extras
//        binding.descriptionHeader.text = bundle?.getString(WORD_EXTRA)
//        binding.descriptionTextview.text = bundle?.getString(DESCRIPTION_EXTRA)
//        val imageLink = bundle?.getString(URL_EXTRA)
//        if (imageLink.isNullOrBlank()) {
//            stopRefreshAnimationIfNeeded()
//        } else {
//            useCoilToLoadPhoto(binding.descriptionImageview, imageLink)
//        }
    }

    private fun startLoadingOrShowError() {
        if (com.meeweel.repository.retrofit.isOnline(this)) {
            setData()
        } else {
            AlertDialogFragment.newInstance(
                getString(R.string.dialog_title_device_is_offline),
                getString(R.string.dialog_message_device_is_offline)
            ).show(
                supportFragmentManager,
                DIALOG_FRAGMENT_TAG
            )
            stopRefreshAnimationIfNeeded()
        }
    }

    private fun stopRefreshAnimationIfNeeded() {
        if (binding.descriptionScreenSwipeRefreshLayout.isRefreshing) {
            binding.descriptionScreenSwipeRefreshLayout.isRefreshing = false
        }
    }

    private fun useCoilToLoadPhoto(imageView: ImageView, imageLink: String) {
        val request = LoadRequest.Builder(this)
            .data("https:$imageLink")
            .target(
                onStart = {},
                onSuccess = { result ->
                    imageView.setImageDrawable(result)
                },
                onError = {
                    imageView.setImageResource(R.drawable.ic_no_photo_vector)
                }
            )
            //.transformations(
            //    CircleCropTransformation(),
            //)
            .build()

        ImageLoader(this).execute(request)
    }

    companion object {
        //
        private const val DIALOG_FRAGMENT_TAG = "8c7dff51-9769-4f6d-bbee-a3896085e76e"
//
//        private const val WORD_EXTRA = "f76a288a-5dcc-43f1-ba89-7fe1d53f63b0"
//        private const val DESCRIPTION_EXTRA = "0eeb92aa-520b-4fd1-bb4b-027fbf963d9a"
//        private const val URL_EXTRA = "6e4b154d-e01f-4953-a404-639fb3bf7281"
//
//        fun getIntent(
//            context: Context,
//            word: String,
//            description: String,
//            url: String?
//        ): Intent = Intent(context, DescriptionActivity::class.java).apply {
//            putExtra(WORD_EXTRA, word)
//            putExtra(DESCRIPTION_EXTRA, description)
//            putExtra(URL_EXTRA, url)
//        }
    }

}
