import sketch4j.executor.ExecutorType;
import sketch4j.executor.SketchExecutor;
import sketch4j.request.invocation.BatchInvocationRequest;

public class Main {

    private static BatchInvocationRequest request = new BatchInvocationRequest();



    public static com.opengamma.analytics.math.linearalgebra.SVDecompositionCommonsResult evaluate(com.opengamma.analytics.math.matrix.DoubleMatrix2D arg0) {
        return (com.opengamma.analytics.math.linearalgebra.SVDecompositionCommonsResult) request.newBatchInvocation(0)
                 .addDeclaringClassesFromPrefix("com.opengamma.analytics.math")
                 .addDeclaringClassesFromPrefix("org.apache.commons.math.linear")
                 .addDeclaringClasses(com.opengamma.analytics.math.matrix.DoubleMatrix2D.class, com.opengamma.analytics.math.linearalgebra.SVDecompositionCommonsResult.class)
                 .addArgument(com.opengamma.analytics.math.matrix.DoubleMatrix2D.class, "arg0", arg0)
                 .setReturnType(com.opengamma.analytics.math.linearalgebra.SVDecompositionCommonsResult.class)
                 .setMaxSearchDepth(3)
                 .invoke();
    }


    public static boolean test() throws Throwable {
    	double[][] xMat = new double[][]{{1,2},{2,2},{2,1}};
    	com.opengamma.analytics.math.matrix.DoubleMatrix2D x = new com.opengamma.analytics.math.matrix.DoubleMatrix2D(xMat);
    	com.opengamma.analytics.math.linearalgebra.SVDecompositionResult res2 = evaluate(x);
    	double inv_sqrt2 = 1/Math.sqrt(2);
    	double[][] UMat = new double[][]{{3/Math.sqrt(34),-1/Math.sqrt(2)},{4/Math.sqrt(34),0},{3/Math.sqrt(34),1/Math.sqrt(2)}};
    	double[][] SMat = new double[][]{{Math.sqrt(17),0},{0,1}};
    	double[][] VTMat = new double[][]{{inv_sqrt2,inv_sqrt2},{inv_sqrt2,-inv_sqrt2}};
    	
    	double[][] UMatRes = res2.getU().toArray();
    	double[][] SMatRes = res2.getS().toArray();
    	double[][] VTMatRes = res2.getVT().toArray();
    	
    	if(UMat.length != UMatRes.length ||
    	SMat.length != SMatRes.length ||
    	VTMat.length != VTMatRes.length){
    		return false;
    	}
    	
    	for(int i=0; i<UMat.length; i++){
    		for(int j=0;j<UMat[i].length; j++){
    			if(Math.abs(UMat[i][j]-UMatRes[i][j])>0.000005)
    				return false;
    		}
    	}
    	
    	for(int i=0; i<SMat.length; i++){
    		for(int j=0;j<SMat[i].length; j++){
    			if(Math.abs(SMat[i][j]-SMatRes[i][j])>0.000005)
    				return false;
    		}
    	}
    	
    	for(int i=0; i<VTMat.length; i++){
    		for(int j=0;j<VTMat[i].length; j++){
    			if(Math.abs(VTMat[i][j]-VTMatRes[i][j])>0.000005)
    				return false;
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
