package uz.gita.notesapp.presentation.ui.screens

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import org.wordpress.aztec.Aztec
import org.wordpress.aztec.ITextFormat
import org.wordpress.aztec.toolbar.IAztecToolbarClickListener
import uz.gita.notesapp.R
import uz.gita.notesapp.data.models.NoteData
import uz.gita.notesapp.databinding.ScreenAddNoteBinding
import uz.gita.notesapp.presentation.viewmodel.AddNoteViewModel
import uz.gita.notesapp.presentation.viewmodel.impl.AddNoteViewModelImpl
import uz.gita.notesapp.utils.convertLongToTime2
import uz.gita.notesapp.utils.convertToDate


@AndroidEntryPoint
class AddNoteFragment : Fragment(R.layout.screen_add_note) {
    val binding: ScreenAddNoteBinding by viewBinding(ScreenAddNoteBinding::bind)
    val viewModel: AddNoteViewModel by viewModels<AddNoteViewModelImpl>()
    var type = ""
    lateinit var note: NoteData

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            setUpObserver()
            type = requireArguments().getString("TYPE") as String
            if (type == "EDIT") {
                note = requireArguments().getSerializable("DOCUMENT") as NoteData
                binding.apply {
                    etTitle.setText(note.title)
                    visual.fromHtml(note.description)
                }
            }
            btnApply.setOnClickListener {
                clickButton()
            }
            btnBack.setOnClickListener {
                viewModel.closeScreen()
            }
        }
        Aztec.with(
            binding.visual,
            binding.formattingToolbar,
            object : IAztecToolbarClickListener {
                override fun onToolbarCollapseButtonClicked() {}

                override fun onToolbarExpandButtonClicked() {}

                override fun onToolbarFormatButtonClicked(
                    format: ITextFormat,
                    isKeyboardShortcut: Boolean,
                ) {
                }

                override fun onToolbarHeadingButtonClicked() {}

                override fun onToolbarHtmlButtonClicked() {}

                override fun onToolbarListButtonClicked() {}

                override fun onToolbarMediaButtonClicked(): Boolean = false

            }
        )
        binding.visual.setTextColor(Color.BLACK)
    }

    private fun clickButton() {
        binding.apply {
            if (type == "ADD"){
                viewModel.addNote(NoteData(
                    0,
                    etTitle.text.toString(),
                    visual.toFormattedHtml(),
                    listOf(""),
                    System.currentTimeMillis()
                    ))
            }else {
                note.title = etTitle.text.toString()
                note.description = visual.toFormattedHtml()
                note.created_time = System.currentTimeMillis()
                viewModel.updateNote(note)
            }
        }
    }

    private fun loadData() {
        val note = requireArguments().getSerializable("DOCUMENT") as NoteData
        if (note != null) {
            binding.apply {
                etTitle.setText(note.title)
                visual.fromHtml(note.description)
            }
        }
    }

    @SuppressLint("FragmentLiveDataObserve")
    private fun setUpObserver() {
        viewModel.closeScreenLiveData.observe(this, closeScreenLiveDataObserver)
        viewModel.addNoteLiveData.observe(viewLifecycleOwner, addNoteLiveDataObserver)
    }

    val closeScreenLiveDataObserver = Observer<Unit> {
//        Toast.makeText(context,"back",Toast.LENGTH_SHORT).show()
        findNavController().popBackStack()
    }
    val addNoteLiveDataObserver = Observer<Unit> {
//        Toast.makeText(context, "Add", Toast.LENGTH_SHORT).show()
    }
}