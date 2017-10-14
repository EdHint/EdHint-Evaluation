import sketch4j.executor.ExecutorType;
import sketch4j.executor.SketchExecutor;
import sketch4j.request.invocation.BatchInvocationRequest;

public class Main {

    private static BatchInvocationRequest request = new BatchInvocationRequest();



    public static int daysUntilNow(org.joda.time.LocalDate arg0) {
        return (Integer) request.newBatchInvocation(0)
                 .addDeclaringClassesFromPrefix("org.joda.time")
                 .addDeclaringClasses(org.joda.time.LocalDate.class)
                 .addArgument(org.joda.time.LocalDate.class, "arg0", arg0)
                 .setReturnType(int.class)
                 .setMaxSearchDepth(3)
                 .invoke();
    }


    public static boolean test0() throws Throwable {
        org.joda.time.LocalDate start = new org.joda.time.LocalDate(2015, 11, 12);
        org.joda.time.LocalDate now = org.joda.time.LocalDate.now();
        return daysUntilNow(start) == org.joda.time.Days.daysBetween(start, now).getDays();
    }
    
    public static boolean test1() throws Throwable {
        org.joda.time.LocalDate start = new org.joda.time.LocalDate(2014, 11, 12);
        org.joda.time.LocalDate now = org.joda.time.LocalDate.now();
        return daysUntilNow(start) == org.joda.time.Days.daysBetween(start, now).getDays();
    }
    
    public static boolean test() throws Throwable {
        return test0() && test1();
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
