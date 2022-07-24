package com.learnjava.forkjoin;

import com.learnjava.util.DataSet;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

import static com.learnjava.util.CommonUtil.delay;
import static com.learnjava.util.CommonUtil.stopWatch;
import static com.learnjava.util.LoggerUtil.log;

public class ForkJoinWithRecursive2 extends RecursiveTask<List<String>> {

    private List<String> inputList;

    public ForkJoinWithRecursive2(List<String> inputList) {
        this.inputList = inputList;
    }

    public static void main(String[] args) {

        stopWatch.start();
        List<String> namesList = DataSet.namesList();
        ForkJoinWithRecursive2 forkJoinWithRecursive = new ForkJoinWithRecursive2(namesList);
        ForkJoinPool pool = new ForkJoinPool();
        List<String> resultList = pool.invoke(forkJoinWithRecursive);
        stopWatch.stop();
        log("Final Result : " + resultList);
        log("Total Time Taken : " + stopWatch.getTime());
    }


    private static String addNameLengthTransform(String name) {
        delay(500);
        return name.length() + " - " + name;
    }

    @SneakyThrows
    @Override
    protected List<String> compute() {
        if (inputList.size() <= 1) {
            return List.of(addNameLengthTransform(inputList.get(0)));
        }

        ForkJoinTask<List<String>> fork = new ForkJoinWithRecursive2(List.of(inputList.get(0))).fork();

        inputList = inputList.subList(1, inputList.size());

        List<String> list = compute();
        List<String> firstEle = fork.join();

        ArrayList<String> result = new ArrayList<>(firstEle);
        result.addAll(list);

        return result;
    }
}
