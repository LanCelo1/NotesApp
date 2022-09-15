package uz.gita.notesapp.presentation.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import uz.gita.notesapp.presentation.ui.screens.NoteFragment
import uz.gita.notesapp.presentation.ui.screens.TaskFragment

class ViewPagerAdapter(lc: Lifecycle, fm: FragmentManager) : FragmentStateAdapter(fm, lc) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment =
        when (position) {
            0 -> NoteFragment()
            1 -> TaskFragment()
            else -> NoteFragment()
        }
}