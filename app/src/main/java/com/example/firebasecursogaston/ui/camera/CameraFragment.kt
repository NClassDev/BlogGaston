package com.example.firebasecursogaston.ui.camera

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.os.Binder
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.firebasecursogaston.R
import com.example.firebasecursogaston.core.Resource
import com.example.firebasecursogaston.data.remote.camera.CameraDataSource
import com.example.firebasecursogaston.databinding.FragmentCameraBinding
import com.example.firebasecursogaston.domain.camera.CameraRepoImpl
import com.example.firebasecursogaston.presentation.camera.CameraViewModel
import com.example.firebasecursogaston.presentation.camera.CameraViewModelFactory


class CameraFragment : Fragment(R.layout.fragment_camera) {

    private val REQUEST_IMAGE_CAPTURE = 2
    private var bitmap: Bitmap? = null
    private lateinit var binding: FragmentCameraBinding

    private val viewModel by viewModels<CameraViewModel> {CameraViewModelFactory(CameraRepoImpl(
        CameraDataSource()
    ))}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCameraBinding.bind(view)
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        }catch (e: ActivityNotFoundException) {
            Toast.makeText(requireContext(), "", Toast.LENGTH_SHORT).show()
        }

        binding.btnUploadPhoto.setOnClickListener {
            bitmap?.let { bitmap ->
                viewModel.uploadPhoto(bitmap, binding.etxtDescription.text.toString().trim()).observe(viewLifecycleOwner) { result ->
                    when (result) {
                        is Resource.Loading -> {
                            Toast.makeText(requireContext(), "Uploading Photo", Toast.LENGTH_SHORT).show()
                        }
                        is Resource.Failure -> {
                            Toast.makeText(requireContext(), "${result.exception}", Toast.LENGTH_SHORT).show()
                        }
                        is Resource.Success -> {
                            findNavController().navigate(R.id.action_cameraFragment_to_homeScreenFragment)
                        }
                    }
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            binding.postImage.setImageBitmap(imageBitmap)
            bitmap = imageBitmap
        }
    }
}