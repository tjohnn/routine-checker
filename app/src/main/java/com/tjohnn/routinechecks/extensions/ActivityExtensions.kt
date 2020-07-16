package com.tjohnn.routinechecks.extensions

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun AppCompatActivity.addFragmentToBackStack(fragment: Fragment, tag: String?, frameId: Int) {
    val transaction = supportFragmentManager.beginTransaction()
    transaction.add(frameId, fragment, tag).addToBackStack(tag).commit()
}

fun AppCompatActivity.replaceFragment(fragment: Fragment, tag: String?, frameId: Int) {
    val transaction = supportFragmentManager.beginTransaction()
    transaction.replace(frameId, fragment, tag).commit()
}