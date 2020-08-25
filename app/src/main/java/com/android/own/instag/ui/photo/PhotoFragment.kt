package com.android.own.instag.ui.photo

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.android.own.instag.R
import com.android.own.instag.di.component.FragmentComponent
import com.android.own.instag.ui.base.BaseFragment
import com.android.own.instag.ui.main.MainSharedViewModel
import com.android.own.instag.utils.common.Event
import com.mindorks.paracamera.Camera
import kotlinx.android.synthetic.main.fragment_photo.*
import java.io.FileNotFoundException
import javax.inject.Inject


class PhotoFragment : BaseFragment<PhotoViewModel>() {

    companion object {

        const val TAG = "PhotoFragment"
        const val RESULT_GALLERY_IMG = 1001

        fun newInstance(): PhotoFragment {
            val args = Bundle()
            val fragment = PhotoFragment()
            fragment.arguments = args
            return fragment
        }
    }

    @Inject
    lateinit var mainSharedViewModel: MainSharedViewModel

    @Inject
    lateinit var camera: Camera

    override fun provideLayoutId(): Int = R.layout.fragment_photo

    override fun injectDependencies(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun setupObservers() {
        super.setupObservers()

        viewModel.loading.observe(this, Observer {
            pb_loading.visibility = if (it) View.VISIBLE else View.GONE
        })

        viewModel.post.observe(this, Observer {
            it.getIfNotHandled()?.run {
                mainSharedViewModel.newPost.postValue(Event(this))
                mainSharedViewModel.onHomeRedirect()
            }
        })
    }

    override fun setupView(view: View) {
        view_gallery.setOnClickListener {
            Intent(Intent.ACTION_PICK)
                .apply {
                    type = "image/*"
                }.run {
                    startActivityForResult(this, RESULT_GALLERY_IMG)
                }
        }

        view_camera.setOnClickListener {
            try {
                camera.takePicture()
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

    override fun onActivityResult(reqCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(reqCode, resultCode, intent)
        if (resultCode == RESULT_OK) {
            when (reqCode) {
                RESULT_GALLERY_IMG -> {
                    try {
                        intent?.data?.let {
                            activity?.contentResolver?.openInputStream(it)?.run {
                                viewModel.onGalleryImageSelected(this)
                            }
                        } ?: showMessage(R.string.try_again)
                    } catch (e: FileNotFoundException) {
                        e.printStackTrace()
                        showMessage(R.string.try_again)
                    }
                }
                Camera.REQUEST_TAKE_PHOTO -> {
                    viewModel.onCameraImageTaken { camera.cameraBitmapPath }
                }
            }
        }
    }

}