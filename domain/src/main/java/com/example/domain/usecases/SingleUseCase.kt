package com.example.domain.usecases

import com.example.domain.executors.PostExecutionThread
import com.example.domain.executors.ThreadExecutor
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

abstract class SingleUseCase<INPUT , OUTPUT : Any> (private val threadExecutor: ThreadExecutor,
                                                    private val postExecutionThread: PostExecutionThread) {

    abstract fun buildSingleUseCase(params : INPUT?) : Single<OUTPUT>

    fun execute(params : INPUT?) : Single<OUTPUT> {
        return this.buildSingleUseCase(params).subscribeOn(Schedulers.from(threadExecutor))
            .observeOn(postExecutionThread.getScheduler())
    }
}