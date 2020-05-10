package com.biodun.core.scheduler

import io.reactivex.Scheduler

interface SchedulerInterface {
    fun mainThread(): Scheduler
    fun io(): Scheduler
}
