import sketch4j.executor.ExecutorType;
import sketch4j.executor.SketchExecutor;
import sketch4j.request.invocation.BatchInvocationRequest;

public class Main {

    private static BatchInvocationRequest request = new BatchInvocationRequest();



    public static com.opengamma.analytics.math.matrix.DoubleMatrix2D getOuterProduct(com.opengamma.analytics.math.matrix.DoubleMatrix1D arg0, com.opengamma.analytics.math.matrix.DoubleMatrix1D arg1) {
        return (com.opengamma.analytics.math.matrix.DoubleMatrix2D) request.newBatchInvocation(0)
                 .addDeclaringClassesFromPrefix("com.opengamma.analytics.math")
                 .addDeclaringClassesFromPrefix("org.apache.commons.math.linear")
                 .addDeclaringClasses(com.opengamma.analytics.math.matrix.DoubleMatrix2D.class, com.opengamma.analytics.math.matrix.DoubleMatrix1D.class)
                 .addArgument(com.opengamma.analytics.math.matrix.DoubleMatrix1D.class, "arg0", arg0)
                 .addArgument(com.opengamma.analytics.math.matrix.DoubleMatrix1D.class, "arg1", arg1)
                 .setReturnType(com.opengamma.analytics.math.matrix.DoubleMatrix2D.class)
                 .setMaxSearchDepth(4)
                 .invoke();
    }


    public static boolean test() throws Throwable {
        com.opengamma.analytics.math.matrix.DoubleMatrix1D mat1 = new com.opengamma.analytics.math.matrix.DoubleMatrix1D(1, 1, 1);
        com.opengamma.analytics.math.matrix.DoubleMatrix1D mat2 = new com.opengamma.analytics.math.matrix.DoubleMatrix1D(1, 2, 3);
        double[][] resMat = new double[][] { { 1, 2, 3 }, { 1, 2, 3 }, { 1, 2, 3 } };
        com.opengamma.analytics.math.matrix.DoubleMatrix2D res = new com.opengamma.analytics.math.matrix.DoubleMatrix2D(resMat);
        if (res.toString().equals(getOuterProduct(mat1, mat2).toString()))
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
