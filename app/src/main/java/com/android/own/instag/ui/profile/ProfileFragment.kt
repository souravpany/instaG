package com.android.own.instag.ui.profile

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.android.own.instag.R
import com.android.own.instag.di.component.FragmentComponent
import com.android.own.instag.ui.base.BaseFragment
import com.android.own.instag.ui.photo.PhotoFragment.Companion.RESULT_GALLERY_IMG
import com.android.own.instag.ui.profile.adapter.ProfilePostsAdapter
import com.android.own.instag.utils.common.GridItemDecoration
import com.mindorks.paracamera.Camera
import kotlinx.android.synthetic.main.fragment_profile.*
import java.io.FileNotFoundException
import javax.inject.Inject

class ProfileFragment : BaseFragment<ProfileViewModel>(), View.OnClickListener {

    @Inject
    lateinit var gridLayoutManager: GridLayoutManager

    @Inject
    lateinit var profilePostsAdapter: ProfilePostsAdapter

    @Inject
    lateinit var camera: Camera

    private val resultGalleryImg = 1001


    companion object {

        const val TAG = "ProfileFragment"

        fun newInstance(): ProfileFragment {
            val args = Bundle()
            val fragment = ProfileFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun provideLayoutId(): Int = R.layout.fragment_profile


    override fun injectDependencies(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun setupObservers() {
        super.setupObservers()

        viewModel.profilePosts.observe(this, Observer {
            it.data?.run { profilePostsAdapter.appendData(this) }
        })

        viewModel.childImageField.observe(this, Observer {
            it?.run {
                if (this == "") {
                    profileImageView.setImageBitmap(null)
                    profileImageView.setBackgroundResource(R.drawable.personal_image)
                } else {
                    profileImageView.setBackgroundResource(android.R.color.transparent)
                    profileImageView.setImageBitmap(BitmapFactory.decodeFile(this))
                }
            }
        })

    }

    override fun setupView(view: View) {

        gridViewPost.apply {
            addItemDecoration(GridItemDecoration(20, 3))
            layoutManager = gridLayoutManager
            adapter = profilePostsAdapter
        }

        profileImageView.setOnClickListener(this)

    }

    private fun selectImage() {
        val items = arrayOf<CharSequence>("Take Photo", "Choose from Library", "Cancel")
        val builder = this.let { AlertDialog.Builder(it.context!!) }
        builder.setTitle("Add Photo!")
        builder.setItems(items) { dialog, item ->
            when {
                items[item] == "Take Photo" -> cameraIntent()
                items[item] == "Choose from Library" -> galleryIntent()
                items[item] == "Cancel" -> dialog.dismiss()
            }
        }
        builder.show()
    }

    private fun galleryIntent() {
        Intent(Intent.ACTION_PICK)
            .apply {
                type = "image/*"
            }.run {
                startActivityForResult(this, resultGalleryImg)
            }
    }

    private fun cameraIntent() {
        try {
            camera.takePicture()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    /**
     * Called when a view has been clicked.
     *
     * @param view The view that was clicked.
     */
    override fun onClick(view: View?) {
        if (view != null) {
            when (view.id) {

                R.id.profileImageView -> selectImage()

            }

        }

    }

    @SuppressLint("Recycle")
    override fun onActivityResult(reqCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(reqCode, resultCode, intent)
        if (resultCode == Activity.RESULT_OK) {
            when (reqCode) {
                RESULT_GALLERY_IMG -> {
                    try {
                        intent?.data?.let {
                            activity!!.contentResolver.openInputStream(it).run {

                                val selectedImage: Uri = intent.data!!
                                val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)

                                val cursor = activity!!.contentResolver.query(
                                    selectedImage, filePathColumn, null, null, null
                                )!!
                                cursor.moveToFirst()

                                val columnIndex = cursor.getColumnIndex(filePathColumn[0])
                                val mediaPath = cursor.getString(columnIndex)

                                if (mediaPath == null) {
                                    showMessage(R.string.try_again)
                                    return
                                }
                                this?.let { it1 -> viewModel.onGalleryImageSelected(it1) }
                            }
                        } ?: showMessage(R.string.try_again)
                    } catch (e: FileNotFoundException) {
                        e.printStackTrace()
                        showMessage(R.string.try_again)
                    }
                }
                Camera.REQUEST_TAKE_PHOTO -> {
                    if (camera.cameraBitmapPath == null) {
                        showMessage(R.string.try_again)
                        return
                    }
                    viewModel.onCameraImageTaken {
                        camera.cameraBitmapPath
                    }
                }
            }
        }
    }
}
