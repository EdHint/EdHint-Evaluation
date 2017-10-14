import sketch4j.executor.ExecutorType;
import sketch4j.executor.SketchExecutor;
import sketch4j.request.invocation.BatchInvocationRequest;

public class Main {

    private static BatchInvocationRequest request = new BatchInvocationRequest();



    public static double[][] sum(double[][] arg0, double[][] arg1) {
        return (double[][]) request.newBatchInvocation(0)
                 .addDeclaringClassesFromPrefix("org.apache.commons.math3.linear")
                 .addDeclaringClasses(double[][].class)
                 .addArgument(double[][].class, "arg0", arg0)
                 .addArgument(double[][].class, "arg1", arg1)
                 .setReturnType(double[][].class)
                 .setMaxSearchDepth(6)
                 .invoke();
    }


    public static boolean equals(double[][] d1, double[][] d2) {
        if(d1.length != d2.length) {
            return false;
        }
        if(d1[0].length != d2[0].length) {
            return false;
        }
        for(int i=0; i<d1.length; i++) {
            for(int j=0; j<d1[0].length; j++) {
                if(Math.abs(d1[i][j] - d2[i][j]) > 1e-6) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public static boolean test0() throws Throwable {
        double[][] a = new double[][]{{1}};
        double[][] b = new double[][]{{2}};
        double[][] target = new double[][]{{3}};
        return equals(sum(a,b),target);
    }
    
    public static boolean test1() throws Throwable {
        double[][] a = new double[][]{{1,2}};
        double[][] b = new double[][]{{2,3}};
        double[][] target = new double[][]{{3,5}};
        return equals(sum(a,b),target);
    }
    
    public static boolean test2() throws Throwable {
        double[][] a = new double[][]{{1,2},{0,4}};
        double[][] b = new double[][]{{2,3},{-1,9}};
        double[][] target = new double[][]{{3,5},{-1,13}};
        return equals(sum(a,b),target);
    }
    
    public static boolean test3() throws Throwable {
        double[][] a = new double[][]{{1,2,3},{0,4,-1}};
        double[][] b = new double[][]{{2,3,0},{-1,9,8}};
        double[][] target = new double[][]{{3,5,3},{-1,13,7}};
        return equals(sum(a,b),target);
    }
    
    public static boolean test4() throws Throwable {
        double[][] a = new double[][]{{1,2,3},{0,4,-1},{0,2,9}};
        double[][] b = new double[][]{{2,3,0},{-1,9,8},{-1,9,0}};
        double[][] target = new double[][]{{3,5,3},{-1,13,7},{-1,11,9}};
        return equals(sum(a,b),target);
    }
    
    public static boolean test5() throws Throwable {
        double[][] a = new double[][]{{-9}};
        double[][] b = new double[][]{{0}};
        double[][] target = new double[][]{{-9}};
        return equals(sum(a,b),target);
    }
    
    public static boolean test6() throws Throwable {
        double[][] a = new double[][]{{1},{2},{3}};
        double[][] b = new double[][]{{-1},{2},{0}};
        double[][] target = new double[][]{{0},{4},{3}};
        return equals(sum(a,b),target);
    }
    
    public static boolean test7() throws Throwable {
        double[][] a = new double[][]{{1,2},{0,4},{0,2}};
        double[][] b = new double[][]{{2,3},{9,8},{-1,0}};
        double[][] target = new double[][]{{3,5},{9,12},{-1,2}};
        return equals(sum(a,b),target);
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
