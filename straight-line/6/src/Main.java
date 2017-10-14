import sketch4j.executor.ExecutorType;
import sketch4j.executor.SketchExecutor;
import sketch4j.request.invocation.BatchInvocationRequest;

public class Main {

    private static BatchInvocationRequest request = new BatchInvocationRequest();



    public static double[] solveLinear(double[][] arg0, double[] arg1) {
        return (double[]) request.newBatchInvocation(0)
                 .addDeclaringClassesFromPrefix("org.apache.commons.math3.linear")
                 .addArgument(double[][].class, "arg0", arg0)
                 .addArgument(double[].class, "arg1", arg1)
                 .setReturnType(double[].class)
                 .setMaxSearchDepth(6)
                 .setMinSearchDepth(6)
                 .invoke();
    }


    public static boolean test() throws Throwable {
        double[][] matrix = {{3,-1},{1,1}};
        double[] vec = {3,5};
        double[] result = solveLinear(matrix, vec);
        
        double[][] matrix2 = {{1,2,1},{2,-1,3},{3,1,2}};
        double[] vec2 = {7,7,18};
        double[] result2 = solveLinear(matrix2, vec2);
        boolean flag1 = ((result.length == 2) && (result[0] == 2.0) && (result[1] == 3.0));
        boolean flag2 = ((result2.length == 3) && (result2[0] == 7.0) && (result2[1] == 1.0) && (result2[2] == -2.0));
    
        if(flag1 && flag2)
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
