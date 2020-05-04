package com.example.domain.executors

import io.reactivex.Scheduler

interface PostExecutionThread {
    fun getScheduler() : Scheduler
}