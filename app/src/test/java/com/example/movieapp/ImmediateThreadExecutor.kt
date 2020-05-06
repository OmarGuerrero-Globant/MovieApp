package com.example.movieapp

import com.example.domain.executors.ThreadExecutor

class ImmediateThreadExecutor : ThreadExecutor {

    override fun execute(runnable : Runnable?) {
        runnable?.run()
    }
}