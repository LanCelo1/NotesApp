package uz.gita.notesapp.presentation.ui.screens

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.notesapp.R
import uz.gita.notesapp.data.models.NoteData
import uz.gita.notesapp.databinding.ScreenNoteBinding
import uz.gita.notesapp.presentation.adapter.NoteAdapter
import uz.gita.notesapp.presentation.viewmodel.NoteViewModel
import uz.gita.notesapp.presentation.viewmodel.impl.NoteViewModelImpl
import uz.gita.notesapp.utils.Manager
import uz.gita.notesapp.utils.showSnackbar

@AndroidEntryPoint
class NoteFragment : Fragment(R.layout.screen_note) {
    private val binding: ScreenNoteBinding by viewBinding(ScreenNoteBinding::bind)
    private val viewModel: NoteViewModel by viewModels<NoteViewModelImpl>()
    private val noteAdapter = NoteAdapter()
    private var position = -1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // loadData()
        setUpRecyclerView()
        setUpListener()
        setUpObservers()
        viewModel.getAllNotes()
    }


    @SuppressLint("FragmentLiveDataObserve")
    private fun setUpObservers() {
        viewModel.getAllNotesLiveData.observe(viewLifecycleOwner, getAllNotesLiveDataObserver)
        viewModel.addNoteLiveData.observe(viewLifecycleOwner, addNoteLiveDataObserver)
        viewModel.openEditNotesLiveData.observe(this, openEditNotesLiveDataObserver)
        viewModel.openAddScreenLiveData.observe(this, openAddScreenLiveDataObserver)
        viewModel.deleteNoteLiveData.observe(viewLifecycleOwner, deleteNoteLiveDataObserver)
        Manager.searchNoteLivedata.observe(this,searchObserver)
    }

    private fun setUpListener() {
        binding.btnAdd.setOnClickListener {
            viewModel.openAddScreen()
        }
    }

    private fun setUpRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            adapter = noteAdapter.apply {
                ItemClickListener {
                    viewModel.openEditNoteScreen(it)
                }
                DeleteItemListener {note,pos->
                    position = pos
                    AlertDialog.Builder(requireContext())
                        .setMessage("Really delete this item?")
                        .setCancelable(false)
                        .setPositiveButton("Ok"
                        ) { dialog, which ->
                            viewModel.deleteNote(note)
                            notifyItemRemoved(pos)
                            dialog.dismiss()
                        }
                        .setNegativeButton("No") { dialog, which ->
                            dialog.dismiss()
                        }
                        .create()
                        .show()
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    val getAllNotesLiveDataObserver = Observer<List<NoteData>> {
        noteAdapter.submitList(it)
        if (it.isEmpty()) binding.emptyList.visibility = View.VISIBLE else
            binding.emptyList.visibility = View.GONE
        noteAdapter.notifyDataSetChanged()
    }
    val addNoteLiveDataObserver = Observer<Unit> {
        // Snackbar
        showSnackbar(requireView(),"Add note successfully!")
    }
    val openEditNotesLiveDataObserver = Observer<NoteData> {
        val bundle = Bundle()
        bundle.putSerializable("DOCUMENT", it)
        bundle.putString("TYPE", "EDIT")
        findNavController().navigate(R.id.action_mainFragment_to_addNoteFragment, bundle)
    }
    val openAddScreenLiveDataObserver = Observer<Unit>{
        val bundle = Bundle()
        bundle.putString("TYPE", "ADD")
        findNavController().navigate(R.id.action_mainFragment_to_addNoteFragment, bundle)
    }
    val deleteNoteLiveDataObserver = Observer<Unit> {
        //Snackbar
//        Toast.makeText(requireContext(), "getAllNotes function is worked", Toast.LENGTH_SHORT).show()
        viewModel.getAllNotes()
//        showSnackbar(requireView(),"Successfully deleted!")
    }
    val searchObserver = Observer<List<NoteData>>{
        noteAdapter.submitList(it)
    }
}