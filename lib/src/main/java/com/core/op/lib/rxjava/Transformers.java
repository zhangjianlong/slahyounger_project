package com.core.op.lib.rxjava;


import com.core.op.lib.rxjava.utils.MapWithIndex;

import rx.Observable;

public final class Transformers {


    public static <T> Observable.Transformer<T, MapWithIndex.Indexed<T>> mapWithIndex() {
        return MapWithIndex.instance();
    }
}