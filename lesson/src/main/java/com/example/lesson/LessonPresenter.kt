package com.example.lesson

import com.example.core.http.EntityCallback
import com.example.core.http.HttpClient
import com.example.core.utils.Utils.toast
import com.example.lesson.entity.Lesson
import com.google.gson.reflect.TypeToken

private const val LESSON_PATH = "lessons"

class LessonPresenter(private val activity: LessonActivity) {
    private var lessons: List<Lesson>? = null
    private val type = object : TypeToken<List<Lesson>?>(){}.type
    fun fetchData() {
        HttpClient.INSTANCE.get(
            LESSON_PATH,
            type,
            object : EntityCallback<List<Lesson>?> {
                override fun onSuccess(entity: List<Lesson>?) {
                    this@LessonPresenter.lessons = entity
                    activity.runOnUiThread { activity.showResult(entity ?: emptyList()) }
                }

                override fun onFailure(message: String) {
                    activity.runOnUiThread { toast(message) }
                }
            })
    }

    fun showPlayback() {
        val playbackLessons = mutableListOf<Lesson>()
        lessons?.forEach { lesson ->
            if (lesson.state === Lesson.State.PLAYBACK)
                playbackLessons.add(lesson)
        }
        activity.showResult(playbackLessons)
    }
}