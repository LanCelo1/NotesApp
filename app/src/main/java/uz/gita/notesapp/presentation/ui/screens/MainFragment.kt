package uz.gita.notesapp.presentation.ui.screens

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2.*
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uz.gita.notesapp.R
import uz.gita.notesapp.data.models.TypeData
import uz.gita.notesapp.databinding.ScreenMainBinding
import uz.gita.notesapp.presentation.adapter.ViewPagerAdapter
import uz.gita.notesapp.presentation.viewmodel.MainViewModel
import uz.gita.notesapp.presentation.viewmodel.impl.MainViewModelImpl


@AndroidEntryPoint
class MainFragment : Fragment(R.layout.screen_main) {
    val binding: ScreenMainBinding by viewBinding(ScreenMainBinding::bind)
    val viewModel: MainViewModel by viewModels<MainViewModelImpl>()
    val pagerAdapter : ViewPagerAdapter by lazy { ViewPagerAdapter(lifecycle,childFragmentManager) }
    var  searchJob : Job? = null

    @SuppressLint("FragmentLiveDataObserve")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.btnNote.setOnClickListener { clickNote() }
        binding.btnTask.setOnClickListener { clickTask() }
        binding.viewpager2.adapter = ViewPagerAdapter(lifecycle,childFragmentManager)
        binding.viewpager2.currentItem = 0
        viewModel.changeFragmentLiveData.observe(viewLifecycleOwner, changeFragmentLiveDataObserver)
        viewModel.openAddScreenLiveData.observe(this, openAddScreenLiveDataObserver)
        binding.viewpager2.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            private var settled = false
            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                if (state == SCROLL_STATE_DRAGGING) {
                    settled = false
                }
                if (state == SCROLL_STATE_SETTLING) {
                    settled = true
                }
                if (state == SCROLL_STATE_IDLE && !settled) {

                }
            }

            override fun onPageSelected(position: Int) {
                if (position == 0) clickNote() else clickTask()
            }
        })

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                var type = if (binding.viewpager2.currentItem == 0) TypeData.NOTE else TypeData.TASK
                searchJob?.cancel()
                searchJob = lifecycleScope.launch {
                    newText?.let {
                        delay(500)
                        viewModel.searchText(newText,type)
                    }
                }
                return false
            }

        })
    }

    private fun clickTask() {
        /*binding.btnNote.setImageResource(R.drawable.ic_note_unfill)
        binding.btnTask.setImageResource(R.drawable.ic_task_filled)*/
        binding.btnNote.setTextColor(Color.parseColor("#D5888585"))
        binding.btnTask.setTextColor(Color.parseColor("#FEBF00"))
        viewModel.changeFragmentsByIndex(1)
        binding.searchView.queryHint = "Search tasks"
    }

    private fun clickNote() {
        /*binding.btnNote.setImageResource(R.drawable.ic_note_fill)
        binding.btnTask.setImageResource(R.drawable.ic_task_unfilled)*/
        binding.btnTask.setTextColor(Color.parseColor("#D5888585"))
        binding.btnNote.setTextColor(Color.parseColor("#FEBF00"))
        viewModel.changeFragmentsByIndex(0)
        binding.searchView.queryHint = "Search notes"
    }

    val changeFragmentLiveDataObserver = Observer<Int> {
        binding.viewpager2.currentItem = it
    }
    val openAddScreenLiveDataObserver = Observer<Unit> {
        val bundle = Bundle()
        bundle.putString("TYPE","ADD")
        findNavController().navigate(R.id.action_mainFragment_to_addNoteFragment,bundle)
    }
}