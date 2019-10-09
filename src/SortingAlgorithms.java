import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.io.*;
import java.util.Arrays;
import java.util.Random;

public class SortingAlgorithms {
    static ThreadMXBean bean = ManagementFactory.getThreadMXBean();
    /* define constants */
    static long MAXVALUE = 2000000000;
    static long MINVALUE = -2000000000;
    static int numberOfTrials = 10;
    static int MAXINPUTSIZE = (int) Math.pow(2,21); // 24 is largest due to heap
    static int MININPUTSIZE = 1;
    // static int SIZEINCREMENT =  10000000; // not using this since we are doubling the size each time

    static String ResultsFolderPath = "/home/ryan/Results/"; // pathname to results folder
    static FileWriter resultsFile;
    static PrintWriter resultsWriter;

    public static void main(String[] args) {
    /*
        //testing commented out because screenshots have already been taken
        System.out.println("Bubble:");
        verifyBubble();
        System.out.println("Insertion:");
        verifyInsertion();
        System.out.println("Merge:");
        verifyMerge();
        System.out.println("QuickSortNaive:");
        verifyQuickNaive();
        System.out.println("QuickSortNaive:");
        verifyQuickRandom();

        long[] newList = {30, -10, 111, 7, 190, 3, 75, 40, -23, -17, -100, 70, 85, 19, 4000};
        long[] newList2 = {41, 42, -83, 90, 1000, -750, -250, -1999, -8909, 100, 170, 1, 7, 12, -20};

        long[] newList3 = createRandomListOfIntegers(1024);
        long[] newList4 = createRandomListOfIntegers(2048);

        // checking known lists
        printList(newList, newList.length-1);
        System.out.println("list is sorted: " + verifySorted(newList));
        printList(newList2, newList2.length-1);
        System.out.println("list is sorted: " + verifySorted(newList2));

        //checking large lists
        System.out.println("list of 1024 is sorted: " + verifySorted(newList3));
        System.out.println("sorting......");
        mergeSort(newList3);
        System.out.println("list of 1024 is sorted: " + verifySorted(newList3));
        System.out.println("list of 2048 is sorted: " + verifySorted(newList4));
        System.out.println("sorting......");
        mergeSort(newList4);
        System.out.println("list of 2048 is sorted: " + verifySorted(newList4));

        //running three tests so java can *hopefully* optimize and provide nice data by round 2 or 3
        /* **********************************************UNCOMMENT ONE******************************************/
/*
        System.out.println("Running first full experiment ...");
        runFullExperiment("BubbleSort-Exp1-ThrowAway.txt");
        System.out.println("Running second full experiment ...");
        runFullExperiment("BubbleSort-Exp2.txt");
        System.out.println("Running third full experiment ...");
        runFullExperiment("BubbleSort-Exp3.txt");


        System.out.println("Running first full experiment ...");
        runFullExperiment("InsertionSort-Exp1-ThrowAway.txt");
        System.out.println("Running second full experiment ...");
        runFullExperiment("InsertionSort-Exp2.txt");
        System.out.println("Running third full experiment ...");
        runFullExperiment("InsertionSort-Exp3.txt");

        System.out.println("Running first full experiment ...");
        runFullExperiment("MergeSort-Exp1-ThrowAway.txt");
        System.out.println("Running second full experiment ...");
        runFullExperiment("MergeSort-Exp2.txt");
        System.out.println("Running third full experiment ...");
        runFullExperiment("MergeSort-Exp3.txt");
*/
        System.out.println("Running first full experiment ...");
        runFullExperiment("QSNaiveSorted-Exp1-ThrowAway.txt");
        System.out.println("Running second full experiment ...");
        runFullExperiment("QSNaiveSorted-Exp2.txt");
        System.out.println("Running third full experiment ...");
        runFullExperiment("QSNaiveSorted-Exp3.txt");
/*
        System.out.println("Running first full experiment ...");
        runFullExperiment("QSRandomSorted-Exp1-ThrowAway.txt");
        System.out.println("Running second full experiment ...");
        runFullExperiment("QSRandomSorted-Exp2.txt");
        System.out.println("Running third full experiment ...");
        runFullExperiment("QSRandomSorted-Exp3.txt");
*/

    }

    static void runFullExperiment(String resultsFileName) {

        // making sure that we have results files available or can create new
        try {
            resultsFile = new FileWriter(ResultsFolderPath + resultsFileName);
            resultsWriter = new PrintWriter(resultsFile);
        } catch (Exception e) {
            System.out.println("*****!!!!!  Had a problem opening the results file " + ResultsFolderPath + resultsFileName);
            return;
        }

        //ThreadCPUStopWatch BatchStopwatch = new ThreadCPUStopWatch(); // for timing an entire set of trials
        ThreadCPUStopWatch TrialStopwatch = new ThreadCPUStopWatch(); // for timing an individual trial

        resultsWriter.println("#InputSize    AverageTime"); // # marks a comment in gnuplot data
        resultsWriter.flush();

        // for each size of input we want to test: in this case starting small and doubling the size each time

        for (int inputSize = MININPUTSIZE; inputSize <= MAXINPUTSIZE; inputSize *= 2) {

            System.out.println("Running test for input size " + inputSize + " ... ");
            /* repeat for desired number of trials (for a specific size of input)... */
            // will hold total amount of time
            // will reset after each batch of trials
            long batchElapsedTime = 0;

            /* force garbage collection before each batch of trials run so it is not included in the time */
            System.gc();

            //BatchStopwatch.start(); // comment this line if timing trials individually

            // run the trials
            for (int trial = 0; trial < numberOfTrials; trial++) {
                // variable to store number of triplets
                int counted = 0;

                System.out.print("    Generating test data...");
                // creating our test list
                long[] testList = createRandomListOfIntegers(inputSize);
                // some status messages
                Arrays.sort(testList);

                System.out.print("...done. Sorting....");
                System.out.print("    Running trial batch...");
                /* force garbage collection before each trial run so it is not included in the time */
                System.gc();
                //actual beginning of trial
                TrialStopwatch.start(); // *** uncomment this line if timing trials individually
                /* run the function we're testing on the trial input */
                /* **********************************************UNCOMMENT ONE******************************************/
                //bubbleSort(testList);
                //insertionSort(testList);
                //mergeSort(testList);
                quickSortNaive(testList, 0, testList.length-1);
                //Arrays.sort(testList);
                //quickSortRandom(testList, 0, testList.length-1);
                /* **********************************************UNCOMMENT ONE^^^******************************************/
                batchElapsedTime = batchElapsedTime + TrialStopwatch.elapsedTime(); // end of trial
                // time is added to
                System.out.println("....done.");// *** uncomment this line if timing trials individually
            }

            //batchElapsedTime = BatchStopwatch.elapsedTime(); // *** comment this line if timing trials individually

            double averageTimePerTrialInBatch = (double) batchElapsedTime / (double) numberOfTrials; // calculate the average time per trial in this batch
            /* print data for this size of input */
            resultsWriter.printf("%12d  %15.2f \n", inputSize, (double) averageTimePerTrialInBatch); // might as well make the columns look nice
            resultsWriter.flush();
            System.out.println(" ....done.");
        }
    }


    public static void bubbleSort(long[] list) {
        /* make n passes through the list (n is the length of the list)*/
        for (int i = 0; i < list.length; i++) {
            /* from index 0 to n-1, compare item[index] to its neighbor and swap if needed */
            for (int j = 0; j < list.length - 1 - i; j++) {
                if (list[j] > list[j + 1]) {
                    long tmp = list[j];
                    list[j] = list[j + 1];
                    list[j + 1] = tmp;
                }
            }
        }
    }

    public static void insertionSort(long[] list) {
        int n = list.length;
        for (int i = 1; i < n; i++) {
            long key = list[i];
            int j = i - 1;

            while (j >= 0 && list[j] > key) {
                list[j + 1] = list[j];
                j = j - 1;
            }
            list[j + 1] = key;
        }

    }

    /* function to do our splitting and merging */
    /* Source: https://howtodoinjava.com/algorithm/merge-sort-java-example/ */
    public static long[] mergeSort(long[] list) {
        // no need to sort
        if (list.length <= 1) {
            return list;
        }

        // splitting the array
        long[] left = new long[list.length / 2];
        // right is the size the list minus the size of the first
        long[] right = new long[list.length - left.length];

        System.arraycopy(list, 0, left, 0, left.length);
        System.arraycopy(list, left.length, right, 0, right.length);

        // recursively sorting each half
        mergeSort(left);
        mergeSort(right);

        // merging both of the derived arrays back to the original
        merge(left, right, list);
        return list;
    }

    /* function to do the actual merging */
    public static void merge(long[] left, long[] right, long[] list) {
        // index for first array
        int iLeft = 0;
        // index for second array
        int iRight = 0;
        // index for the merged array
        int iList = 0;

        // compares the elements at iLeft and iRight and moves them to iList
        // corresponding index is increased after a move
        while (iLeft < left.length && iRight < right.length) {
            if (left[iLeft] < (right[iRight])) {
                list[iList] = left[iLeft];
                iLeft++;
            } else {
                list[iList] = right[iRight];
                iRight++;
            }
            iList++;
        }

        // remaining elements are added if one side has more than the other
        System.arraycopy(left, iLeft, list, iList, left.length - iLeft);
        System.arraycopy(right, iRight, list, iList, right.length - iRight);
    }


    // creates random unsorted list
    public static long[] createRandomListOfIntegers(int size)
    {
        Random ran = new Random();
        long[] newList = new long[size];
        for (int j = 0; j < size; j++) {
            newList[j] = ran.nextInt();
        }
        return newList;
    }
    // from https://www.programcreek.com/2012/11/quicksort-array-in-java/
    public static void quickSortNaive(long list[], long begin, long end)
    {
        //if (begin <= end) {
            long partitionInd = partitionQSNaive(list, begin, end);

            if(partitionInd-1>begin)
                quickSortNaive(list, begin, partitionInd-1);
            if(partitionInd+1<end)
                quickSortNaive(list, partitionInd + 1, end);
        //}
    }
    // finds the and returns the next partition index or pivot
    public static long partitionQSNaive(long list[], long begin, long end)
    {
        long pivot = list[(int)end];
        //long i = (begin-1);
        //had to change from begin-1 for sorted list

        for ( long j = begin; j <end; j++)
        {
            if (list[(int)j] < pivot) {
                long swapTmp = list[(int)j];
                list[(int)begin] = list[(int)j];
                list[(int)j] = swapTmp;
                j++;
            }
        }
        long swapTmp = list[(int)begin];
        list[(int)begin] = pivot;
        list[(int)end] = swapTmp;

        return begin;
    }
    // adapted from: https://jordanspencerwu.github.io/randomized-quick-sort/
    public static void quickSortRandom(long[] list, long begin, long end)
    {
        // making sure the start index is less than the end
        if (begin < end) {
            long pivot = randomPartition(list, begin, end);// getting the pivot point
            quickSortRandom(list, begin, pivot - 1);// using recursion for each half from that pivot
            quickSortRandom(list, pivot + 1, end);
        }
    }

    public static long randomPartition(long[] list, long begin, long end)
    {
        // finding a random value to partition on
        Random rand = new Random();
        long randomEndIndex = begin + rand.nextInt(((int)end - (int)begin + 1));
        swap(list, end, randomEndIndex);

        return partitionQSR(list, begin, end);
    }

    // creating the partition
    public static long partitionQSR(long[] list, long begin, long end) {
        long pivotValue = list[(int)end];
        long pivotIndex = begin;

        for (long j = begin; j < end; j++) {
            if (list[(int)j] <= pivotValue) {
                swap(list, pivotIndex, j);
                pivotIndex = pivotIndex + 1;
            }
        }
        swap(list, pivotIndex, end);
        return pivotIndex;
    }
    // swapper function
    public static void swap(long[] list, long ind1, long ind2)
    {
        long tmp = list[(int)ind1];
        list[(int)ind1] = list[(int)ind2];
        list[(int)ind2] = tmp;
    }

    // printing lists
    public static void printList(long[] list, int listSize)
    {
        int i = 0;
        for (; i < listSize; i++)
        {
            System.out.print(list[i] + " ");
        }
        System.out.println();
    }

    // function to check if list is sorted
    public static boolean verifySorted(long[] list)
    {
        // using an iterative method
        // list is size 1 or 0  then it is sorted
        if(list.length == 1 || list.length == 0)
            return true;
       for ( int i = 0; i < list.length-1; i++) {
           if (list[i] > list[i+1]) { // if a previous value is greater than the next value, list is not sorted
               return false;
           }
       }
       return true; // array is sorted otherwise
    }

    // function to verify bubble sort
    public static void verifyBubble()
    {
        // make a list size 10, print, sort and then make sure it is sorted and print again
        int size = 10;
        long[] list = createRandomListOfIntegers(size);
        System.out.println("unsorted:");
        printList(list, size);
        bubbleSort(list);
        System.out.println("sorted:");
        printList(list, size);
    }

    // function to verify insertion sort
    public static void verifyInsertion()
    {
        int size = 10;
        long[] list = createRandomListOfIntegers(size);
        System.out.println("unsorted:");
        printList(list, size);
        insertionSort(list);
        System.out.println("sorted:");
        printList(list, size);
    }
    // verifying merge
    public static void verifyMerge()
    {
        int size = 10;
        long[] list = createRandomListOfIntegers(size);
        System.out.println("unsorted:");
        printList(list, size);
        mergeSort(list);
        System.out.println("sorted:");
        printList(list,size);
    }
    // verifying quick naive
    public static void verifyQuickNaive()
    {
        int size = 10;
        long[] list = createRandomListOfIntegers(size);
        System.out.println("unsorted:");
        printList(list, size);
        quickSortNaive(list,0, size - 1);
        System.out.println("sorted:");
        printList(list,size);
    }

    //verifying quick random
    public static void verifyQuickRandom()
    {
        int size = 10;
        long[] list = createRandomListOfIntegers(size);
        System.out.println("unsorted:");
        printList(list, size);
        quickSortRandom(list,0, size - 1);
        System.out.println("sorted:");
        printList(list,size);
    }


}
