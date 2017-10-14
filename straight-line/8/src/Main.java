import sketch4j.executor.ExecutorType;
import sketch4j.executor.SketchExecutor;
import sketch4j.request.invocation.BatchInvocationRequest;

public class Main {

    private static BatchInvocationRequest request = new BatchInvocationRequest();



    public static double predict(double[][] arg0, double arg1) {
        return (Double) request.newBatchInvocation(0)
                 .addDeclaringClassesFromPrefix("org.apache.commons.math.stat")
                 .addDeclaringClasses(double[][].class)
                 .addArgument(double[][].class, "arg0", arg0)
                 .addArgument(double.class, "arg1", arg1)
                 .setReturnType(double.class)
                 .setMaxSearchDepth(3)
                 .invoke();
    }


    public static boolean test1() throws Throwable {
        double[][] known = new double[][] {{1, 2}, {2, 3}, {3, 4}, {4, 5}, {5, 6}};
        double x = 1.5;
        return predict(known, x) == 2.5;
    }
    
    public static boolean test2() throws Throwable {
        double[][] known = new double[][] {{1, 3}, {2, 4}, {3, 5}, {4, 6}, {5, 7}};
        double x = 2.5;
        return predict(known, x) == 4.5;
    }
    
    public static boolean test() throws Throwable {
        return test1() && test2();
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
