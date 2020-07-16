package com.tjohnn.routinechecks.extensions

import android.view.View
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.tjohnn.routinechecks.R
import com.tjohnn.routinechecks.databinding.AppBarBinding


fun Fragment.attachToolbar(view: View, title: String?, showBackButton: Boolean = true): AppBarBinding {
    val toolbarBinding: AppBarBinding = AppBarBinding.bind(view.findViewById(R.id.app_bar))
    toolbarBinding.titleText.text = title
    if(showBackButton) {
        toolbarBinding.backButton.setOnClickListener {
            this.activity?.onBackPressed()
        }
    } else {
        toolbarBinding.backButton.hide()
    }
    toolbarBinding.toolbar.title = title
    return toolbarBinding
}

fun Fragment.attachToolbar(view: View, @StringRes titleRes: Int, showBackButton: Boolean = true): AppBarBinding {
    return attachToolbar(view, context!!.getString(titleRes), showBackButton)
}

fun Fragment.addFragmentToBackStack(fragment: Fragment, tag: String?, frameId: Int) {
    val transaction = childFragmentManager.beginTransaction()
    transaction.add(frameId, fragment, tag).addToBackStack(tag).commit()
}

fun Fragment.replaceFragment(fragment: Fragment, tag: String?, frameId: Int) {
    val transaction = childFragmentManager.beginTransaction()
    transaction.replace(frameId, fragment, tag).commit()
}