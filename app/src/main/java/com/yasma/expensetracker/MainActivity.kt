package com.yasma.expensetracker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.yasma.expensetracker.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var mAdView : AdView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        MobileAds.initialize(this) {}

        val adView = AdView(this)

        adView.setAdSize(AdSize.BANNER)

//        adView.adUnitId = "ca-app-pub-3940256099942544/6300978111"



        val viewPager = findViewById<ViewPager>(R.id.view_pager)
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(NewHome_fragmnet(), "home Fragment")
        adapter.addFragment(newfragmnet(), "Month Fragment")
//        adapter.addFragment(OverFragment(), "Over Fragment")
        viewPager.adapter = adapter
        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
       val backgroundColor = resources.getColor(R.color.white)
        bottomNavigationView.setBackgroundColor(backgroundColor)

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> viewPager.currentItem = 0
                R.id.month -> viewPager.currentItem = 1
//                R.id.over -> viewPager.currentItem = 2
                else -> return@setOnNavigationItemSelectedListener false
            }
            true
        }

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                bottomNavigationView.menu.getItem(position).isChecked = true
            }

            override fun onPageScrollStateChanged(state: Int) {
            }
        })


    }
}