import sketch4j.executor.ExecutorType;
import sketch4j.executor.SketchExecutor;
import sketch4j.request.invocation.BatchInvocationRequest;

public class Main {

    private static BatchInvocationRequest request = new BatchInvocationRequest();



    public static double getInnerProduct(com.opengamma.analytics.math.matrix.DoubleMatrix1D arg0, com.opengamma.analytics.math.matrix.DoubleMatrix1D arg1) {
        return (Double) request.newBatchInvocation(0)
                 .addDeclaringClassesFromPrefix("com.opengamma.analytics.math")
                 .addDeclaringClassesFromPrefix("org.apache.commons.math.linear")
                 .addDeclaringClasses(com.opengamma.analytics.math.matrix.DoubleMatrix1D.class)
                 .addArgument(com.opengamma.analytics.math.matrix.DoubleMatrix1D.class, "arg0", arg0)
                 .addArgument(com.opengamma.analytics.math.matrix.DoubleMatrix1D.class, "arg1", arg1)
                 .setReturnType(double.class)
                 .setMaxSearchDepth(3)
                 .invoke();
    }


    public static boolean test() throws Throwable {
        com.opengamma.analytics.math.matrix.DoubleMatrix1D mat1 = new com.opengamma.analytics.math.matrix.DoubleMatrix1D(1, 1, 1);
        com.opengamma.analytics.math.matrix.DoubleMatrix1D mat2 = new com.opengamma.analytics.math.matrix.DoubleMatrix1D(1, 2, 3);
        if (getInnerProduct(mat1, mat2) == 6)
    	return true;
        else
    	return false;
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
