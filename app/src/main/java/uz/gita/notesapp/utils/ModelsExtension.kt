package uz.gita.notesapp.utils

import android.annotation.SuppressLint
import android.os.Build
import android.os.SystemClock
import android.util.Log
import uz.gita.notesapp.data.local.room.entity.NoteEntity
import uz.gita.notesapp.data.local.room.entity.TaskEntity
import uz.gita.notesapp.data.models.NoteData
import uz.gita.notesapp.data.models.TaskData
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

fun NoteEntity.getNoteData(list : List<String>): NoteData{
    return NoteData(
        id!!, title!!, description!!,list, created_time!!, isPinned,false
    )
}

fun TaskEntity.getTaskData() : TaskData{
    return TaskData(
        id, title,isChecked
    )
}

@SuppressLint("SimpleDateFormat")
fun convertLongToTime(time: Long): String {
    val date = Date(time)
    val format = SimpleDateFormat("yyyy.MM.dd HH:mm")
    return format.format(date)
}
fun convertToDate() : String{
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern(" MMM uuuu")
        current.format(formatter)
        return current.toString()
    }else {
        return convertLongToTime(SystemClock.elapsedRealtimeNanos())
    }
}
fun convertLongToTime2(time: Long): String {
    val date = Date(time)
    val format = SimpleDateFormat("yyyy.MM.dd HH:mm")
    return format.format(date)
}
fun Long.toDateString(dateFormat: Int =  DateFormat.MEDIUM): String {
    val df = DateFormat.getDateInstance(dateFormat, Locale.getDefault())
    return df.format(this)
}