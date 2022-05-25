package cn.skyui.practice.rxjava;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

public class RxJavaTest {


    public static void main(String[] args) {

        startTime();
        startTime();

        try {
            Thread.sleep(3000);
            closeTimer();
            startTime();
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private static Disposable mDisposable;

    /**
     * 启动定时器
     */
    public static void startTime() {
        if(mDisposable != null && !mDisposable.isDisposed()) {
            return;
        }
        Observable.interval(1000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(Long value) {
                        System.out.println("value="+value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("onError");
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("onComplete");
                    }
                });
    }

    /**
     * 关闭定时器
     */
    public static void closeTimer(){
        if (mDisposable != null) {
            mDisposable.dispose();
            mDisposable = null;
        }
    }

}
