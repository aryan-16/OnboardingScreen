package com.example.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.LinearLayout.LayoutParams
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {

    lateinit var onBoardingItemAdapter: OnBoardingItemAdapter
    private lateinit var indicatorsContainer : LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setOnboardingItems()
        setupIndicators()
        setCurrentIndicators(0)
    }

    private fun setOnboardingItems (){
        onBoardingItemAdapter = OnBoardingItemAdapter(
            listOf(
                OnboardingItem(
                    onboardingImage = R.drawable.planning,
                    title = "Plan Your Tasks",
                    description = "Plan all your tasks at one place in one go."
                ),
                OnboardingItem(
                    onboardingImage = R.drawable.onlinesupport,
                    title = "24/7 Online Support",
                    description = "Get online support 24/7 with our AI enabled virtual assitant."
                ),
                OnboardingItem(
                    onboardingImage = R.drawable.customerservice,
                    title = "Excellent Customer Service",
                    description = "Our customer service makes our app the best from other online available solutions."
                )

            )
        )

        val onboardingViewPager = findViewById<ViewPager2>(R.id.onBoardingViewPager)
        onboardingViewPager.adapter = onBoardingItemAdapter
        onboardingViewPager.registerOnPageChangeCallback(object :
        ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicators(position)
            }
        })
        (onboardingViewPager.getChildAt(0) as RecyclerView).overScrollMode =
            RecyclerView.OVER_SCROLL_NEVER
        findViewById<ImageView>(R.id.imageNext).setOnClickListener{
            if (onboardingViewPager.currentItem + 1 < onBoardingItemAdapter.itemCount){
                onboardingViewPager.currentItem +=1
            }else{
                navigationToHomeActivity()
            }

        }
        findViewById<TextView>(R.id.textSkip).setOnClickListener {
            navigationToHomeActivity()
        }
        findViewById<MaterialButton>(R.id.btnGetStarted).setOnClickListener {
            navigationToHomeActivity()
        }
    }

    private fun navigationToHomeActivity(){
        startActivity(Intent(applicationContext , HomeActivity::class.java))
        finish()
    }
    private fun setupIndicators(){
        indicatorsContainer = findViewById(R.id.indicatorsContainer)
        val indicators = arrayOfNulls<ImageView>(onBoardingItemAdapter.itemCount)

        val layoutParams  : LinearLayout.LayoutParams  =
            LinearLayout.LayoutParams(WRAP_CONTENT , WRAP_CONTENT)
        layoutParams.setMargins(8 , 0 , 8 , 0)
        for (i in indicators .indices){
            indicators[i] = ImageView(applicationContext)
            indicators[i]?.let {
                it.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext ,
                        R.drawable.indicator_inactive_background

                    )

                )
                       it.layoutParams = layoutParams
                indicatorsContainer.addView(it)
            }
        }

    }
    private fun setCurrentIndicators(position : Int){
        val childCount = indicatorsContainer.childCount
        for (i in 0 until childCount){
            val imageView = indicatorsContainer.getChildAt(i) as ImageView
            if( i == position){
                imageView.setImageDrawable(ContextCompat.getDrawable(
                    applicationContext ,
                    R.drawable.indicator_active_background
                   )
                )

            }else{
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_inactive_background
                    )
                )
            }
        }

    }
}