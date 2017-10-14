import sketch4j.executor.ExecutorType;
import sketch4j.executor.SketchExecutor;
import sketch4j.request.invocation.BatchInvocationRequest;

public class Main {

    private static BatchInvocationRequest request = new BatchInvocationRequest();



    public static int secondsSinceMidnight(org.joda.time.DateTime arg0) {
        return (Integer) request.newBatchInvocation(0)
                 .addDeclaringClassesFromPrefix("org.joda.time")
                 .addDeclaringClasses(org.joda.time.DateTime.class)
                 .addArgument(org.joda.time.DateTime.class, "arg0", arg0)
                 .setReturnType(int.class)
                 .setMaxSearchDepth(6)
                 .invoke();
    }


    public static int rightSolution(org.joda.time.DateTime time) {
        org.joda.time.DateTime midnight = time.withTimeAtStartOfDay();
        org.joda.time.Duration duration = new org.joda.time.Duration(midnight, time);
        return duration.toStandardSeconds().getSeconds();
    }
    
    public static boolean test0() throws Throwable {
        org.joda.time.DateTime time = new org.joda.time.DateTime(2013, 10, 13, 23, 59);
        return secondsSinceMidnight(time) == rightSolution(time);
    }
    
    public static boolean test1() throws Throwable {
        org.joda.time.DateTime time = new org.joda.time.DateTime(2013, 10, 13, 0, 0);
        return secondsSinceMidnight(time) == rightSolution(time);
    }
    
    public static boolean test2() throws Throwable {
        org.joda.time.DateTime time = new org.joda.time.DateTime(2013, 10, 13, 12, 45);
        return secondsSinceMidnight(time) == rightSolution(time);
    }
    
    public static boolean test3() throws Throwable {
        org.joda.time.DateTime time = new org.joda.time.DateTime(2013, 10, 13, 13, 45);
        return secondsSinceMidnight(time) == rightSolution(time);
    }
    
    public static boolean test4() throws Throwable {
        org.joda.time.DateTime time = new org.joda.time.DateTime(2016, 10, 13, 13, 45);
        return secondsSinceMidnight(time) == rightSolution(time);
    }
    
    public static boolean test5() throws Throwable {
        org.joda.time.DateTime time = new org.joda.time.DateTime(1990, 2, 28, 3, 17);
        return secondsSinceMidnight(time) == rightSolution(time);
    }
    
    public static boolean test6() throws Throwable {
        org.joda.time.DateTime time = new org.joda.time.DateTime(1999, 3, 8, 5, 31);
        return secondsSinceMidnight(time) == rightSolution(time);
    }
    
    public static boolean test7() throws Throwable {
        org.joda.time.DateTime time = new org.joda.time.DateTime(2012, 12, 25, 22, 3);
        return secondsSinceMidnight(time) == rightSolution(time);
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
