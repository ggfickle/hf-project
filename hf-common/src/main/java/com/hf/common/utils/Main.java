package com.hf.common.utils;

import java.util.Arrays;

public class Main {

    public static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            // 分区操作，将数组分成两个区域
            int pivot = partition(arr, low, high);
            // 对左右两个子序列分别进行快速排序
            quickSort(arr, low, pivot - 1);
            quickSort(arr, pivot + 1, high);
        }
    }

    private static int partition(int[] arr, int low, int high) {
        // 选择最后一个元素作为基准值
        int pivot = arr[high];
        // 定义左指针和右指针
        int i = low - 1;
        for (int j = low; j < high; j++) {
            // 如果当前元素小于等于基准值，交换当前元素和左指针所指元素，并将左指针右移
            if (arr[j] <= pivot) {
                i++;
                swap(arr, i, j);
            }
        }
        // 将基准值放到左右分区的中间位置
        swap(arr, i + 1, high);
        // 返回基准值所在的位置
        return i + 1;
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {
        int[] arr = { 6, 5, 3, 1, 8, 7, 2, 4 };
        quickSort(arr, 0, arr.length - 1);
        System.out.println(Arrays.toString(arr));
    }
}
