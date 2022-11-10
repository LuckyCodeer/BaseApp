package com.huodada.lib_common.http;

import io.reactivex.functions.Consumer;

public interface OnError extends Consumer<Throwable> {

    @Override
    default void accept(Throwable throwable) throws Exception {
        onError(new ErrorInfo(throwable));
    }

    void onError(ErrorInfo error) throws Exception;
}
