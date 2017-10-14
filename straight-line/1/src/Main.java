import sketch4j.executor.ExecutorType;
import sketch4j.executor.SketchExecutor;
import sketch4j.request.invocation.BatchInvocationRequest;

public class Main {

    private static BatchInvocationRequest request = new BatchInvocationRequest();



    public static org.apache.commons.math3.linear.RealMatrix invert(org.apache.commons.math3.linear.RealMatrix arg0) {
        return (org.apache.commons.math3.linear.RealMatrix) request.newBatchInvocation(0)
                 .addDeclaringClassesFromPrefix("org.apache.commons.math3.linear")
                 .addDeclaringClasses(org.apache.commons.math3.linear.RealMatrix.class)
                 .addArgument(org.apache.commons.math3.linear.RealMatrix.class, "arg0", arg0)
                 .setReturnType(org.apache.commons.math3.linear.RealMatrix.class)
                 .setMaxSearchDepth(3)
                 .invoke();
    }


    public static boolean test() throws Throwable {
        double [][] mat1 = new double[][]{{1,2,3},{4,5,6}};
        double [][] mat2 = new double[][]{{-0.944444,0.444444},{-0.111111,0.111111},{0.722222,-0.222222}};
        org.apache.commons.math3.linear.RealMatrix mat = new org.apache.commons.math3.linear.Array2DRowRealMatrix(mat1);
        org.apache.commons.math3.linear.RealMatrix target = new org.apache.commons.math3.linear.Array2DRowRealMatrix(mat2);
        org.apache.commons.math3.linear.RealMatrix result = invert(mat);
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 2; ++j) {
                if (Math.abs(target.getEntry(i,j) - result.getEntry(i,j)) > 1e-6) return false;
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
