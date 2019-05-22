package ParallelQSort;

import mpi.MPI;
import java.util.Arrays;

import static ParallelQSort.Main.*;

public class ParallelQSort {

    private static final int LENGTH_TAG = 2;
    private static final int MSG_TAG = 1;
    private static final int BOOL_EMULATOR_LENGTH = 1;
    private int[] array;
    private int[] res;
    private int num;

    public ParallelQSort(int[] array, int[] res) {
        num = MPI.COMM_WORLD.Rank();
        this.res = res;
        if (num != NUM_OF_THREADS - 1) {
            this.array = Arrays.copyOfRange(array, array.length / NUM_OF_THREADS * num, array.length / NUM_OF_THREADS * (num + 1));
        } else {
            this.array = Arrays.copyOfRange(array, array.length / NUM_OF_THREADS * num, array.length);
        }
    }

    public void run() {
        int[] dividers = new int[NUM_OF_THREADS - 1];
        int[] arrLength = new int[BOOL_EMULATOR_LENGTH];
        int iter = 0;
        int[] arr = new int[ARRAY_LENGTH];
        int[] preRes = new int[ARRAY_LENGTH];
        array = SingleQSort.quickSort(array, 0, array.length - 1);
        int[] elems = new int[NUM_OF_THREADS];
        for (int i = 0; i < NUM_OF_THREADS; i++) {
            elems[i] = array[i * Main.ARRAY_LENGTH / (NUM_OF_THREADS * NUM_OF_THREADS)];
        }
        if (num != 0) {
            MPI.COMM_WORLD.Send(elems, 0, NUM_OF_THREADS, MPI.INT, 0, MSG_TAG);
        } else {
            int[] buffer = new int[NUM_OF_THREADS * NUM_OF_THREADS];
            copyArrayPart(elems, buffer, 0, 0, elems.length);
            for (int i = 1; i < NUM_OF_THREADS; i++) {
                MPI.COMM_WORLD.Recv(buffer, i * NUM_OF_THREADS, NUM_OF_THREADS, MPI.INT, i, MSG_TAG);
            }
            buffer = SingleQSort.quickSort(buffer, 0 , buffer.length - 1);
            for (int i = 2; i < NUM_OF_THREADS + 1; i++) {
                dividers[i - 2] = i * NUM_OF_THREADS + NUM_OF_THREADS / 2 - 1;
            }
        }
        MPI.COMM_WORLD.Bcast(dividers, 0, NUM_OF_THREADS - 1, MPI.INT, 0);

        //placing dividers in our array

        boolean flag;
        int j;
        for (int i = 0; i < dividers.length; i++) {
            flag = false;
            j = 0;
            while (j < array.length) {
                if (array[j] > dividers[i]) {
                    dividers[i] = j - 1;
                    flag = true;
                    break;
                }
                j++;
            }
            if (!flag) {
                dividers[i] = dividers.length - 1;
            }
        }

        //exchange parts

        for (int i = 0; i < dividers.length; i++) {
            if (num != i) {
                arrLength[0] = (i > 0) ? dividers[i] - dividers[i - 1] : dividers[0] + 1;
                MPI.COMM_WORLD.Send(arrLength, 0, BOOL_EMULATOR_LENGTH, MPI.INT, i, LENGTH_TAG);
                if (arrLength[0] > 0) {
                    copyArrayPart(array, arr, (i > 0) ? dividers[i - 1] + 1 : 0, 0, arrLength[0]);
                    MPI.COMM_WORLD.Send(arr, 0, arrLength[0], MPI.INT, i, MSG_TAG);
                }
            } else {
                for (int k = 0; k < NUM_OF_THREADS; k++) {
                    if (k != num) {

                        MPI.COMM_WORLD.Recv(arrLength, 0, BOOL_EMULATOR_LENGTH, MPI.INT, k, LENGTH_TAG);
                        if (arrLength[0] > 0) {
                            MPI.COMM_WORLD.Recv(arr, 0, arrLength[0], MPI.INT, k, MSG_TAG);
                            iter = addToEndOfArray(preRes, arr, iter, arrLength[0]);
                        }
                    } else {
                        if (num != NUM_OF_THREADS - 1) {
                            arrLength[0] = (k > 0) ? dividers[k] - dividers[k - 1] : dividers[0] + 1;
                            copyArrayPart(array, arr, (k > 0) ? dividers[k - 1] + 1 : 0, 0, arrLength[0]);
                            iter = addToEndOfArray(preRes, arr, iter, arrLength[0]);
                        } else {
                            arrLength[0] = array.length - dividers[dividers.length - 1] - 1;
                            copyArrayPart(array, arr, dividers[dividers.length - 1] + 1, 0, arrLength[0]);
                            iter = addToEndOfArray(preRes, arr, iter, arrLength[0]);
                        }
                    }
                }
            }
        }

        if (num != NUM_OF_THREADS - 1) {
            arrLength[0] = array.length - dividers[dividers.length - 1] - 1;
            MPI.COMM_WORLD.Send(arrLength, 0, BOOL_EMULATOR_LENGTH, MPI.INT, NUM_OF_THREADS - 1, LENGTH_TAG);
            if (arrLength[0] > 0) {
                copyArrayPart(array, arr, dividers[dividers.length - 1] + 1, 0, arrLength[0]);
                MPI.COMM_WORLD.Send(arr, 0, arrLength[0], MPI.INT, NUM_OF_THREADS - 1, MSG_TAG);
            }
        } else {
            for (int k = 0; k < NUM_OF_THREADS; k++) {
                if (k != num) {
                    MPI.COMM_WORLD.Recv(arrLength, 0, BOOL_EMULATOR_LENGTH, MPI.INT, k, LENGTH_TAG);
                    if (arrLength[0] > 0) {
                        MPI.COMM_WORLD.Recv(arr, 0, arrLength[0], MPI.INT, k, MSG_TAG);
                        iter = addToEndOfArray(preRes, arr, iter, arrLength[0]);
                    }
                } else {
                    arrLength[0] = array.length - dividers[dividers.length - 1] - 1;
                    copyArrayPart(array, arr, dividers[dividers.length - 1] + 1, 0, arrLength[0]);
                    iter = addToEndOfArray(preRes, arr, iter, arrLength[0]);
                }
            }
        }

        preRes = SingleQSort.quickSort(preRes, 0, iter - 1);

        int resIter = 0;

        if (num != 0) {
            arrLength[0] = iter;
            MPI.COMM_WORLD.Send(arrLength, 0, BOOL_EMULATOR_LENGTH, MPI.INT, 0, LENGTH_TAG);
            if (arrLength[0] > 0) {
                MPI.COMM_WORLD.Send(preRes, 0, arrLength[0], MPI.INT, 0, MSG_TAG);
            }
        } else {
            for (int k = 0; k < NUM_OF_THREADS; k++) {
                if (k != num) {
                    MPI.COMM_WORLD.Recv(arrLength, 0, BOOL_EMULATOR_LENGTH, MPI.INT, k, LENGTH_TAG);
                    if (arrLength[0] > 0) {
                        MPI.COMM_WORLD.Recv(arr, 0, arrLength[0], MPI.INT, k, MSG_TAG);
                        resIter = addToEndOfArray(res, arr, resIter, arrLength[0]);
                    }
                } else {
                    arrLength[0] = iter;
                    resIter = addToEndOfArray(res, preRes, resIter, arrLength[0]);
                }
            }
        }
    }
}
