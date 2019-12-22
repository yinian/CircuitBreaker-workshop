package com.viagra.command;

import org.junit.Test;
import rx.Observable;

import java.util.Iterator;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @Auther: viagra
 * @Date: 2019/12/11 14:48
 * @Description:
 */
public class NonBlockApp {



    @Test
    public void testObservable() {
        Observable<String> observable= new HelloNonBlockCommand("World").observe();

        Iterator<String> iterator = observable.toBlocking().getIterator();
        while(iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    @Test
    public void testToObservable() {
        Observable<String> observable= new HelloNonBlockCommand("World").observe();
        Iterator<String> iterator = observable.toBlocking().getIterator();
        while(iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }


}
