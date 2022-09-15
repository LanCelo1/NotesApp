package uz.gita.notesapp.presentation.ui.screens

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import uz.gita.notesapp.R
import uz.gita.notesapp.data.models.TaskData
import uz.gita.notesapp.databinding.ScreenTaskBinding
import uz.gita.notesapp.presentation.adapter.TaskAdapter
import uz.gita.notesapp.presentation.adapter.TaskAdapter2
import uz.gita.notesapp.presentation.viewmodel.TaskViewModel
import uz.gita.notesapp.presentation.viewmodel.impl.TaskViewModelImpl
import uz.gita.notesapp.utils.Manager


@AndroidEntryPoint
class TaskFragment : Fragment(R.layout.screen_task) {
    private val binding: ScreenTaskBinding by viewBinding(ScreenTaskBinding::bind)
    private val viewModel: TaskViewModel by viewModels<TaskViewModelImpl>()
    private var taskAdapter = TaskAdapter()
    private var taskAdapter2 = TaskAdapter2()
    private var _height = 0
    private var job: Job? = null
    private var task: TaskData? = null
    private var isEditable = false
    private var editableNumber = -1
    private var listTasks = ArrayList<TaskData>()

    @SuppressLint("FragmentLiveDataObserve", "FragmentBackPressedCallback")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        loadData()
    }

    private fun loadData() {
        viewModel.getAllTask()
        setUpRecyclerView()
        setUpObservers()
        setUpListeners()
    }

    private fun setUpListeners() {
        binding.apply {
            addTask.setOnClickListener {
                if (!isEditable) {
                    val title = editText.text.toString()
                    viewModel.addTask(title)
                } else {
                    task?.let {
                        it.title = editText.text.toString()
                        viewModel.editTask(it)
//                        listTasks[editableNumber].title = editText.text.toString()
                        isEditable = false
                    }
                }
                hideKeyboard()
            }
        }
        binding.main.post {
            _height = binding.main.height
        }
    }

    @SuppressLint("FragmentLiveDataObserve")
    private fun setUpObservers() {
        viewModel.getAllTaskScreenLiveData.observe(viewLifecycleOwner, getAllTaskScreenObserver)
        viewModel.addTaskLiveData.observe(this, addTaskObserver)
        viewModel.editTaskLiveData.observe(this, editTaskObserver)
        viewModel.openEditTaskScreenLiveData.observe(this, openEditTaskObserver)
        viewModel.removeTaskLiveData.observe(this, removeTaskObserver)
        Manager.searchTaskLivedata.observe(this, searchObserver)
    }

    private fun setUpRecyclerView() {
        binding.recyclerView.apply {
            adapter = taskAdapter.apply {
                setItemClickListener { tasks, position ->
                    isEditable = true
                    Log.d("TTT", "edittask = $")
                    viewModel.openEditTaskScreen(tasks)
                    task = tasks
                    editableNumber = position
//                    showKeyboard()
                    /*val imm = binding.editText.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    activity?.window?.setSoftInputMode(
                        WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE
                    )
                    imm.showSoftInput(binding.editText, 0)*/
                    activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
                    binding.editText.requestFocus()
                    val inputMethodManager =
                        activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.toggleSoftInputFromWindow(activity?.currentFocus!!.windowToken,
                        InputMethodManager.SHOW_FORCED,
                        0)
                }
                setRemoveItemClickListener { task, position ->
                    AlertDialog.Builder(requireContext())
                        .setMessage("Really delete this item?")
                        .setCancelable(false)
                        .setPositiveButton("Ok"
                        ) { dialog, which ->
                            viewModel.removeTask(task)
                            dialog.dismiss()
                        }
                        .setNegativeButton("No") { dialog, which ->
                            dialog.dismiss()
                        }
                        .create()
                        .show()
                }
                setChangeItemClickListener { taskData, i ->
                    Log.d("TTT", "change = ${taskData} $i")
                    if (taskData.isChecked) {
                        taskAdapter.moveItem(i, taskAdapter.differ.currentList.size-1)
                    } else {
                        taskAdapter.moveItem(i, 0)
                    }
                    viewModel.editTask(taskData)
                }
            }
            layoutManager = LinearLayoutManager(this@TaskFragment.context)
        }
    }

    private fun hideKeyboard() {
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        val imm =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm!!.hideSoftInputFromWindow(binding.editText.windowToken, 0)
    }


    private val getAllTaskScreenObserver = Observer<List<TaskData>> {
        Log.d("TTT", "$it")
        var complitedList = ArrayList<TaskData>()
        var notComplitedList = ArrayList<TaskData>()
        it.forEach {
            if (it.isChecked) {
                complitedList.add(it)
            } else {
                notComplitedList.add(it)
            }
        }
        notComplitedList.addAll(complitedList)
        taskAdapter.differ.submitList(notComplitedList)
        if (it.isEmpty()) binding.emptyList.visibility = View.VISIBLE else
            binding.emptyList.visibility = View.GONE
//        taskAdapter.notifyDataSetChanged()
//        taskAdapter2.notifyDataSetChanged()
    }
    private val addTaskObserver = Observer<Unit> {
        viewModel.getAllTask()
        binding.editText.setText("")
    }
    val openEditTaskObserver = Observer<TaskData> {
        binding.apply {
            editText.setText(it.title.toString())
        }
    }
    val removeTaskObserver = Observer<Unit> {
        viewModel.getAllTask()

//        Toast.makeText(context, "deleted", Toast.LENGTH_SHORT).show()
    }
    val editTaskObserver = Observer<Unit> {
        taskAdapter.notifyItemChanged(editableNumber)
//        taskAdapter.submitList(listTasks)
        viewModel.getAllTask()
        binding.apply {
            editText.setText("")
        }
//        Toast.makeText(context, "edit", Toast.LENGTH_SHORT).show()
    }
    val searchObserver = Observer<List<TaskData>> {
        var list = ArrayList<TaskData>()
        it.forEach {
            if (!it.isChecked) {
                list.add(it)
            }
        }
        taskAdapter.differ.submitList(list)
    }

}