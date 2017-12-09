package com.flying.xiaopo.poishuhui_kotlin.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.flying.xiaopo.poishuhui_kotlin.R
import com.flying.xiaopo.poishuhui_kotlin.ui.adapter.ContentPagerAdapter
import com.flying.xiaopo.poishuhui_kotlin.ui.fragment.BookFragment
import com.flying.xiaopo.poishuhui_kotlin.ui.fragment.HomeFragment
import com.flying.xiaopo.poishuhui_kotlin.ui.fragment.NewsFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

  companion object {
    val GITHUB_URL = "https://github.com/bluesway/PoiShuhui-Kotlin"
  }

  val nameResList: ArrayList<Int> = arrayListOf(R.string.tab_one, R.string.tab_cate0,  R.string.tab_cate2, R.string.tab_cate3, R.string.tab_three)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    init()


  }

  private fun init() {
    setSupportActionBar(toolbar)
    fab.setOnClickListener { jump2MyGithub() }
    val fragments = ArrayList<Fragment>()


    fragments.add(HomeFragment())
    fragments.add(BookFragment(0))
    /*fragments.add(BookFragment(1))*/
    fragments.add(BookFragment(2))
    fragments.add(BookFragment(3))
    fragments.add(NewsFragment())


    val nameList = nameResList.map(this::getString)

    viewPager.adapter = ContentPagerAdapter(fragments, nameList, supportFragmentManager)
    viewPager.offscreenPageLimit = 2

    tabLayout.setupWithViewPager(viewPager)

    //        Picasso.with(this).setIndicatorsEnabled(true)
  }

  private fun jump2MyGithub() {
    val uri = Uri.parse(GITHUB_URL);
    val intent = Intent(Intent.ACTION_VIEW, uri)
    startActivity(intent)
  }


  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.menu_main, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    val id = item.itemId

    if (id == R.id.action_about) {
      val intent = Intent(this, AboutActivity().javaClass)
      startActivity(intent)
      return true
    }

    return super.onOptionsItemSelected(item)
  }
}
