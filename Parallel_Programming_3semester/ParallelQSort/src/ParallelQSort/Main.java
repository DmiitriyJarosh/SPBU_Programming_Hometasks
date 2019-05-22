package ParallelQSort;

import mpi.MPI;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class Main {

    public static int NUM_OF_THREADS = 0;
    public static int ARRAY_LENGTH = 0;

    public static void main(String[] args) {
        String inputFilename, outputFilename;
        int[] shareSize = new int[1];
        int[] tmp = null;
        int[] res;
        boolean[] finalFlag = {false};
        MPI.Init(args);
        if (args.length >= 5) {
            inputFilename = args[args.length - 2];
            outputFilename = args[args.length - 1];
        } else {
            if (MPI.COMM_WORLD.Rank() == 0) {
                System.out.println("Last two args should be names of input and output files!!!");
            }
            return;
        }
        NUM_OF_THREADS = MPI.COMM_WORLD.Size();
        if (MPI.COMM_WORLD.Rank() == 0) {
            try {
                BufferedReader in = new BufferedReader(new FileReader(inputFilename));
                String[] input = in.readLine().split(" ");
                in.close();
                tmp = new int[input.length];
                for (int i = 0; i < input.length; i++) {
                    tmp[i] = Integer.parseInt(input[i]);
                }
                if (tmp.length < (NUM_OF_THREADS * NUM_OF_THREADS * NUM_OF_THREADS)) {
                    res = SingleQSort.quickSort(tmp, 0, tmp.length - 1);
                    printRes(res, outputFilename);
                    finalFlag[0] = true;
                } else {
                    shareSize[0] = tmp.length;
                }
            } catch (Exception e) {
                finalFlag[0] = true;
                e.printStackTrace();
            }
        }
        MPI.COMM_WORLD.Bcast(finalFlag, 0 , 1, MPI.BOOLEAN, 0);
        if (finalFlag[0]) {
            return;
        }
        MPI.COMM_WORLD.Bcast(shareSize, 0, 1, MPI.INT, 0);
        int[] arr = new int[shareSize[0]];
        if (MPI.COMM_WORLD.Rank() == 0) {
            copyArrayPart(tmp, arr, 0, 0, tmp.length);
        }
        MPI.COMM_WORLD.Bcast(arr, 0, arr.length, MPI.INT, 0);
        ARRAY_LENGTH = arr.length;
        res = Qsort(arr);
        if (MPI.COMM_WORLD.Rank() == 0) {
            printRes(res, outputFilename);
        }
        MPI.Finalize();
    }

    public static void printRes(int[] res, String outputFilename) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(outputFilename));
            for (int i = 0; i < res.length; i++) {
                out.write(res[i] + " ");
            }
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int[] Qsort(int[] a) {
            int[] res = new int[a.length];
            new ParallelQSort(a, res).run();
            return res;
    }


    public static void copyArrayPart(int[] from, int[] to, int startFrom, int startTo, int length) {
        for (int i = startFrom; i < startFrom + length; i++) {
            to[startTo] = from[i];
            startTo++;
        }
    }

    public static int addToEndOfArray(int[] to, int[] from, int iter, int length) {
        for (int i = 0; i < length; i++) {
            to[iter] = from[i];
            iter++;
        }
        return iter;
    }
}
