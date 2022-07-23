package com.learnjava.forkjoin;

import com.learnjava.util.DataSet;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

import static com.learnjava.util.CommonUtil.delay;
import static com.learnjava.util.CommonUtil.stopWatch;
import static com.learnjava.util.LoggerUtil.log;

public class ForkJoinWithRecursive extends RecursiveTask<List<String>> {

    private List<String> inputList;

    public ForkJoinWithRecursive(List<String> inputList) {
        this.inputList = inputList;
    }

    public static void main(String[] args) {

        stopWatch.start();
        List<String> namesList = DataSet.namesList();
        ForkJoinWithRecursive forkJoinWithRecursive = new ForkJoinWithRecursive(namesList);
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

    @Override
    protected List<String> compute() {
        if (inputList.size() <= 1) {
            return List.of(addNameLengthTransform(inputList.get(0)));
        }

        int middlePoint = inputList.size() / 2;
        ForkJoinTask<List<String>> leftFork = new ForkJoinWithRecursive(inputList.subList(0, middlePoint)).fork();
        inputList = inputList.subList(middlePoint, inputList.size());
        List<String> rightResult = compute();
        List<String> leftResult = leftFork.join();

        ArrayList<String> result = new ArrayList<>(leftResult);
        result.addAll(rightResult);

        return result;
    }
}
