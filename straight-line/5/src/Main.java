import sketch4j.executor.ExecutorType;
import sketch4j.executor.SketchExecutor;
import sketch4j.request.invocation.BatchInvocationRequest;

public class Main {

    private static BatchInvocationRequest request = new BatchInvocationRequest();



    public static com.opengamma.analytics.math.matrix.DoubleMatrix2D invert(com.opengamma.analytics.math.matrix.DoubleMatrix2D arg0) {
        return (com.opengamma.analytics.math.matrix.DoubleMatrix2D) request.newBatchInvocation(0)
                 .addDeclaringClassesFromPrefix("com.opengamma.analytics.math")
                 .addDeclaringClassesFromPrefix("org.apache.commons.math.linear")
                 .addDeclaringClasses(com.opengamma.analytics.math.matrix.DoubleMatrix2D.class)
                 .addArgument(com.opengamma.analytics.math.matrix.DoubleMatrix2D.class, "arg0", arg0)
                 .setReturnType(com.opengamma.analytics.math.matrix.DoubleMatrix2D.class)
                 .setMaxSearchDepth(3)
                 .invoke();
    }


    public static boolean test() throws Throwable {
        double[][] mat = new double[][]{{1,2},{3,4}};
        com.opengamma.analytics.math.matrix.DoubleMatrix2D m = new com.opengamma.analytics.math.matrix.DoubleMatrix2D(mat);
        
        double[][] mat2 = new double[][]{{-2,1},{1.5,-0.5}};
    	double[][] res = invert(m).toArray();
    	
    	if(mat2.length != res.length || mat2[0].length != res[0].length)
    		return false;
    	
    	for(int i=0; i<mat2.length; i++){
    		for(int j=0; j<mat2[0].length; j++){
    			if(Math.abs(mat2[i][j] - res[i][j]) > 0.00000005){
    				return false;
    			}
    		}
    	}
    	
    	return true;
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
