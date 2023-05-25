package com.application.comeato.Utilities

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.bottomnavigation.BottomNavigationView

class BottomNavigationBehavior(context: Context, attrs: AttributeSet) : CoordinatorLayout.Behavior<BottomNavigationView>(context, attrs) {

    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: BottomNavigationView,
        directTargetChild: View,
        target: View,
        axes: Int,
        type: Int
    ): Boolean {
        return axes == View.SCROLL_AXIS_VERTICAL
    }

    override fun onNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: BottomNavigationView,
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int
    ) {
        if (dyConsumed > 0 && child.visibility == View.VISIBLE) {
            // Scrolling up, hide the bottom navigation bar
            hideBottomNavigation(child)
        } else if (dyConsumed < 0 && child.visibility != View.VISIBLE) {
            // Scrolling down, show the bottom navigation bar
            showBottomNavigation(child)
        }
    }

    private fun hideBottomNavigation(view: BottomNavigationView) {
        view.clearAnimation()
        view.animate().translationY(view.height.toFloat()).duration = 200
    }

    private fun showBottomNavigation(view: BottomNavigationView) {
        view.clearAnimation()
        view.animate().translationY(0f).duration = 200
    }
}