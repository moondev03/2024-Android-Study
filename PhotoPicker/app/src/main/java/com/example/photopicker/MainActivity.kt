package com.example.photopicker

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.MediaController
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.photopicker.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val pickMediaLauncher = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            val contentResolver = applicationContext.contentResolver
            val mimeType = contentResolver.getType(uri)

            when {
                mimeType?.startsWith("image/") == true -> {
                    setImage(uri)
                }
                mimeType?.startsWith("video/") == true -> {
                    setVideo(uri)
                }
                else -> {
                    Toast.makeText(this@MainActivity, "알 수 없는 미디어 타입입니다.", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(this@MainActivity, "선택된 파일이 없습니다.", Toast.LENGTH_SHORT).show()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initListener()
    }

    private fun initListener(){
        binding.apply {

            // 이미디와 비디오 모두
            btnBoth.setOnClickListener {
                pickMediaLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo))
            }

            // 이미지만
            btnImage.setOnClickListener {
                pickMediaLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }

            // 비디오만
            btnVideo.setOnClickListener {
                pickMediaLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.VideoOnly))
            }

            // MIME 타입 지원
            btnGif.setOnClickListener {
                val mimeType = "image/gif"
                pickMediaLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.SingleMimeType(mimeType)))
            }
        }
    }

    private fun setImage(uri: Uri){
        binding.vvMain.visibility = View.INVISIBLE
        binding.ivMain.visibility = View.VISIBLE

        Glide.with(this)
            .load(uri)
            .optionalCenterCrop()
            .into(binding.ivMain)
    }

    private fun setVideo(uri: Uri){
        binding.vvMain.visibility = View.VISIBLE
        binding.ivMain.visibility = View.INVISIBLE

        binding.vvMain.setMediaController(MediaController(this))
        binding.vvMain.setVideoURI(uri)

        binding.vvMain.setOnPreparedListener {
            binding.vvMain.start()
        }
    }
}