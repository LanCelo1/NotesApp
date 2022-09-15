package uz.gita.notesapp.data.models

import uz.gita.notesapp.data.local.room.entity.TaskEntity

data class StateTask(val countries : List<TaskEntity>) {
    data class Header(
        val title : String,
        val bodies : List<Body>
    ) {
        data class Body(
            var title: String,
            var id: Int,
        )
    }
}