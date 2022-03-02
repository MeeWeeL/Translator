package com.meeweel.historyscreen

import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import androidx.annotation.RequiresApi
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

        binding.descriptionScreenSwipeRefreshLayout.setOnRefreshListener { startLoadingOrShowError() }
        setData()
    }

    private fun setData() {
        binding.descriptionHeader.text = word
        binding.descriptionTextview.text = description
        useCoilToLoadPhoto(binding.descriptionImageview, url!!)
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
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                        setBlur()
                    }
                },
                onError = {
                    imageView.setImageResource(R.drawable.ic_no_photo_vector)
                }
            )
            .build()
        ImageLoader(this).execute(request)
    }

    @RequiresApi(Build.VERSION_CODES.S)
    fun setBlur() {
        val blurEffect = RenderEffect.createBlurEffect(15f,0f, Shader.TileMode.MIRROR)
        binding.descriptionImageview.setRenderEffect(blurEffect)
    }

    companion object {

        private const val DIALOG_FRAGMENT_TAG = "8c7dff51-9769-4f6d-bbee-a3896085e76e"
    }

}
