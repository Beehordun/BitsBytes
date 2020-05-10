package com.biodun.core.base

import androidx.annotation.NonNull
import io.reactivex.Single

interface BaseReactiveUseCase {

    interface GetUseCase<Params, Result> : BaseReactiveUseCase {
        @NonNull
        fun getSingle(params: Params): Single<Result>
    }

}
