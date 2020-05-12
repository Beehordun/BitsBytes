package com.example.testshared

import com.biodun.core.scheduler.SchedulerInterface
import io.reactivex.schedulers.Schedulers

class TestScheduler : SchedulerInterface {

    override fun mainThread() = Schedulers.trampoline()
    override fun io() = Schedulers.trampoline()
    override fun computation() = Schedulers.trampoline()
}
