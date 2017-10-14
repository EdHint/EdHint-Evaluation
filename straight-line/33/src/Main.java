import sketch4j.executor.ExecutorType;
import sketch4j.executor.SketchExecutor;
import sketch4j.request.invocation.BatchInvocationRequest;

public class Main {

    private static BatchInvocationRequest request = new BatchInvocationRequest();



    public static int minutesBetween(org.joda.time.DateTime arg0, org.joda.time.DateTime arg1) {
        return (Integer) request.newBatchInvocation(0)
                 .addDeclaringClassesFromPrefix("org.joda.time")
                 .addDeclaringClasses(org.joda.time.DateTime.class)
                 .addArgument(org.joda.time.DateTime.class, "arg0", arg0)
                 .addArgument(org.joda.time.DateTime.class, "arg1", arg1)
                 .setReturnType(int.class)
                 .setMaxSearchDepth(6)
                 .invoke();
    }


    public static int rightSolution(org.joda.time.DateTime begin, org.joda.time.DateTime end) {
        return org.joda.time.Minutes.minutesBetween(begin, end).getMinutes();
    }
    
    public static boolean test0() throws Throwable {
        org.joda.time.DateTime begin = new org.joda.time.DateTime(2013, 10, 13, 23, 59);
        org.joda.time.DateTime end = new org.joda.time.DateTime(2013, 10, 20, 3, 0);
        return rightSolution(begin,end) == minutesBetween(begin,end);
    }
    
    public static boolean test1() throws Throwable {
        org.joda.time.DateTime begin = new org.joda.time.DateTime(2015, 2, 28, 5, 24);
        org.joda.time.DateTime end = new org.joda.time.DateTime(2015, 3, 1, 3, 0);
        return rightSolution(begin,end) == minutesBetween(begin,end);
    }
    
    public static boolean test2() throws Throwable {
        org.joda.time.DateTime begin = new org.joda.time.DateTime(2015, 2, 28, 5, 24);
        org.joda.time.DateTime end = new org.joda.time.DateTime(2015, 5, 1, 3, 0);
        return rightSolution(begin,end) == minutesBetween(begin,end);
    }
    
    public static boolean test3() throws Throwable {
        org.joda.time.DateTime begin = new org.joda.time.DateTime(2015, 2, 28, 5, 24);
        org.joda.time.DateTime end = new org.joda.time.DateTime(2016, 5, 1, 3, 0);
        return rightSolution(begin,end) == minutesBetween(begin,end);
    }
    
    public static boolean test4() throws Throwable {
        org.joda.time.DateTime begin = new org.joda.time.DateTime(2016, 12, 24, 5, 24);
        org.joda.time.DateTime end = new org.joda.time.DateTime(2016, 12, 25, 3, 0);
        return rightSolution(begin,end) == minutesBetween(begin,end);
    }
    
    public static boolean test5() throws Throwable {
        org.joda.time.DateTime begin = new org.joda.time.DateTime(1993, 12, 9, 22, 24);
        org.joda.time.DateTime end = new org.joda.time.DateTime(2016, 12, 25, 3, 0);
        return rightSolution(begin,end) == minutesBetween(begin,end);
    }
    
    public static boolean test6() throws Throwable {
        org.joda.time.DateTime begin = new org.joda.time.DateTime(1993, 12, 9, 22, 24);
        org.joda.time.DateTime end = new org.joda.time.DateTime(2020, 12, 25, 3, 0);
        return rightSolution(begin,end) == minutesBetween(begin,end);
    }
    
    public static boolean test7() throws Throwable {
        org.joda.time.DateTime begin = new org.joda.time.DateTime(1967, 3, 26, 22, 24);
        org.joda.time.DateTime end = new org.joda.time.DateTime(2016, 12, 25, 0, 0);
        return rightSolution(begin,end) == minutesBetween(begin,end);
    }
    
    
    
    public static boolean test() throws Throwable {
        return test0() && test1() && test2() && test3() && test4() && test5() && test6() && test7();
    }
     

    public static void main(String[] args) {
        int maxTime = Integer.parseInt(args[0]);
        SketchExecutor.setType(ExecutorType.JUZI);
        boolean found = false;
        boolean timeout = false;
        int i = 0;
        long begin = System.currentTimeMillis();
        long checkpoint = 0;
        do {
            if(System.currentTimeMillis() - begin >= maxTime * 1000) {
                timeout = true;
                break;
            }
            i ++;
            if(System.currentTimeMillis() - checkpoint >= 5000) {
                System.out.println(i + " executions tried!");
                checkpoint = System.currentTimeMillis();
            }
            request.reset();
            try {
                if(!test()) {
                    throw new RuntimeException("****TEST FAILURE");
                }
                found = true;
                break;
            } catch (Throwable e) {
               // e.printStackTrace();
            }
        } while (SketchExecutor.incrementCounter());
        if(found) {
            System.out.println("Found solution:");
            System.out.println("----------------------------------------------------------------");
            System.out.println(request.getCandidateBatchInvocation(0));
            System.out.println("----------------------------------------------------------------");
        } else if(timeout) {
            System.out.println("Timeout! Max execution time: " + maxTime + " seconds.");
        } else {
            System.out.println("No solution!");
        }
        
        System.out.println("Number of invocations tried: " + request.getNumBatchInvocationsTried(0));
        System.out.println("Search depth: " + request.getCurrentSearchDepth(0));
        System.out.println("Number of executions: " + i);
    }

}
