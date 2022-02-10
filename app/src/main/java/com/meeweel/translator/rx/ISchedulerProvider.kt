package com.meeweel.translator.rx

import io.reactivex.rxjava3.core.Scheduler


interface ISchedulerProvider {

    fun ui(): Scheduler
    fun io(): Scheduler
}