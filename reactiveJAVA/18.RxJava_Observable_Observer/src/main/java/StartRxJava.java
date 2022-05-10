import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.observables.ConnectableObservable;

import java.util.concurrent.TimeUnit;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class StartRxJava {
    public static void main(String[] args) throws InterruptedException {
        Observable<Object> source$ = Observable.create(emitter -> {
            emitter.onNext(123);
            emitter.onNext(456);
        });

        source$.subscribe(System.out::println);

        Observable<String> just$ = Observable.just("Andy", "Cathy", "Allen", "Tom");

        just$.subscribe(System.out::println);

        ConnectableObservable<Long> interval$ = Observable.interval(1, TimeUnit.SECONDS).publish();
        interval$.connect();
        Thread.sleep(2000);

        Disposable disposable = interval$.subscribe(System.out::println);
        Thread.sleep(10000);
        disposable.dispose();

        Thread.sleep(2000);
        interval$.subscribe(System.out::println);
        Thread.sleep(10000);

    }
}
