import sketch4j.executor.ExecutorType;
import sketch4j.executor.SketchExecutor;
import sketch4j.request.invocation.BatchInvocationRequest;

public class Main {

    private static BatchInvocationRequest request = new BatchInvocationRequest();



    public static org.apache.commons.math3.geometry.euclidean.twod.Vector2D eigenvalue(org.apache.commons.math3.linear.RealMatrix arg0, int arg1) {
        return (org.apache.commons.math3.geometry.euclidean.twod.Vector2D) request.newBatchInvocation(0)
                 .addDeclaringClassesFromPrefix("org.apache.commons.math3.linear")
                 .addDeclaringClasses(org.apache.commons.math3.linear.RealMatrix.class, org.apache.commons.math3.geometry.euclidean.twod.Vector2D.class)
                 .addArgument(org.apache.commons.math3.linear.RealMatrix.class, "arg0", arg0)
                 .addArgument(int.class, "arg1", arg1)
                 .setReturnType(org.apache.commons.math3.geometry.euclidean.twod.Vector2D.class)
                 .setMaxSearchDepth(4)
                 .invoke();
    }


    public static boolean test0() throws Throwable {
    	double[][] mat = new double[][] { { 0, -20 }, { 10, 10 } };
    	org.apache.commons.math3.linear.RealMatrix matrix = new org.apache.commons.math3.linear.Array2DRowRealMatrix(mat);
    	org.apache.commons.math3.geometry.euclidean.twod.Vector2D result = eigenvalue(matrix, 0);
    	org.apache.commons.math3.geometry.euclidean.twod.Vector2D target = new org.apache.commons.math3.geometry.euclidean.twod.Vector2D(5, 5*Math.sqrt(7));
    	return Math.abs(result.getX() - target.getX()) < 1e-6 && Math.abs(result.getY() - target.getY()) < 1e-6;
    }
    
    public static boolean test1() throws Throwable {
    	double[][] mat = new double[][] { { 0, 2 }, { 2, 0 } };
    	org.apache.commons.math3.linear.RealMatrix matrix = new org.apache.commons.math3.linear.Array2DRowRealMatrix(mat);
    	org.apache.commons.math3.geometry.euclidean.twod.Vector2D result = eigenvalue(matrix, 1);
    	org.apache.commons.math3.geometry.euclidean.twod.Vector2D target = new org.apache.commons.math3.geometry.euclidean.twod.Vector2D(-2, 0);
    	return Math.abs(result.getX() - target.getX()) < 1e-6 && Math.abs(result.getY() - target.getY()) < 1e-6;
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
